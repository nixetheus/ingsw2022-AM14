package it.polimi.ingsw.controller;

import it.polimi.ingsw.helpers.MessageMain;
import it.polimi.ingsw.helpers.MessageSecondary;
import it.polimi.ingsw.helpers.Towers;
import it.polimi.ingsw.messages.BeginTurnMessage;
import it.polimi.ingsw.messages.ClientResponse;
import it.polimi.ingsw.messages.InfoRequestMessage;
import it.polimi.ingsw.messages.LoginMessage;
import it.polimi.ingsw.messages.LoginMessageResponse;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MoveMessage;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.board.Island;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.Player;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;
import java.util.concurrent.Semaphore;

/**
 * Main controller class that manage the other controller and do the correct phase and correct
 * player check
 */
public class MainController {

  // Controllers
  private final TurnManager turnManager;
  private final InfoController infoController;
  private final PlayController playController;
  private final MoveController moveController;
  private final LoginController loginController;
  private final Vector<Team> teams;
  private Vector<Message> messages;
  // Attributes
  private Game game;

  // Game parameters
  private int numberOfPlayers;
  private boolean isGameExpert;

  // Server
  private Semaphore serverSemaphore;

  /**
   * Constructor for the MainController Class
   */
  public MainController() {

    teams = new Vector<>();

    turnManager = new TurnManager();
    infoController = new InfoController();
    playController = new PlayController();
    moveController = new MoveController();
    loginController = new LoginController();
  }

  /**
   * Sets the game as given by the server
   *
   * @param newGame the game created by the Server
   */
  public void setGame(Game newGame) {
    this.game = newGame;
  }

  /**
   * Gets the server's semaphore for controlling logic
   *
   * @param serverSemaphore The server's Semaphore
   */
  public void setServerSemaphore(Semaphore serverSemaphore) throws InterruptedException {
    this.serverSemaphore = serverSemaphore;
    this.serverSemaphore.acquire();
  }

  /**
   * This method assigns the message received from a player to the respective sub-controller
   *
   * @param msg The message received from the player
   */
  public Vector<Message> elaborateMessage(Message msg) throws IOException {

    if (msg.getMessageMain() == MessageMain.INFO) {
      return infoController.elaborateMessage((InfoRequestMessage) msg, game);
    } else if (msg.getMessageMain() == MessageMain.LOGIN) {
      return elaborateLoginMessage((LoginMessage) msg);
    } else {
      return elaborateGameMessage(msg);
    }

  }

  /**
   * This method elaborates login messages, differentiating between the ones send to set the details
   * of each player and those used to set the parameters of the game
   *
   * @param msg The Login message received
   */
  private Vector<Message> elaborateLoginMessage(LoginMessage msg) throws IOException {
    Vector<Message> messages = new Vector<>();

    LoginMessageResponse loginResponse = new LoginMessageResponse(msg.getMessageSecondary());
    messages.add(loginResponse);

    // Check if phase is correct
    if (turnManager.getMainGamePhase() == MessageMain.LOGIN &&
        msg.getMessageSecondary() == turnManager.getSecondaryPhase()) {

      switch (msg.getMessageSecondary()) {
        case GAME_PARAMS:
          // If parameters are okay set them and change game state else send error message
          if (loginController.checkGameParameters(msg)) {
            isGameExpert = msg.isGameExpert();
            numberOfPlayers = msg.getNumberOfPlayer();
            turnManager.setNumberOfUsers(numberOfPlayers);
            turnManager.updateCounters();
            turnManager.changeState();
            this.serverSemaphore.release();
            loginResponse.setResponse("Game parameters correctly set!");

          } else {
            loginResponse.setResponse("Error! Game parameters are not correct! Retry!");

          }
          break;

        case PLAYER_PARAMS:

          Player newPlayer = loginController
              .createPlayer(msg, turnManager.getCurrentNumberOfUsers());

          // If player is not null, add it to a team, change state
          if (newPlayer != null) {

            // Where a player goes is based on the number of players for this game
            if (numberOfPlayers == 4) {
              if (teams.size() < 2) {
                Team newTeam = new Team(teams.size(), Towers.values()[teams.size()]);
                newTeam.addPlayer(newPlayer);
                teams.add(newTeam);
              } else {
                long nCurrentPlayers = teams.stream().map(team -> team.getPlayers().size()).count();
                teams.elementAt((int) nCurrentPlayers % 2).addPlayer(newPlayer);
              }

            } else {
              Team newTeam = new Team(teams.size(), Towers.values()[teams.size()]);
              newTeam.addPlayer(newPlayer);
              teams.add(newTeam);
            }

            // Change state
            turnManager.updateCounters();
            turnManager.changeState();

            // If players are all in, setup game
            if (teams.stream().map(team -> team.getPlayers().size()).count() == numberOfPlayers) {
              setupGame();
              loginResponse.setResponse("Welcome aboard " + newPlayer.getPlayerNickname() + "!  ");
              loginResponse.setPlayerId(
                  turnManager.getCurrentNumberOfUsers());//TODO check

              LoginMessageResponse loginMessageResponse2 = new LoginMessageResponse(
                  MessageSecondary.GAME_PARAMS);
              loginMessageResponse2
                  .setResponse("Everyone is ready now! We shall let the game start!");
              loginMessageResponse2.setPlayerId(-1);
              messages.add(loginMessageResponse2);

              Vector<Message> changeTurnMessages = changeTurnMessage(MessageSecondary.INIT_GAME);
              messages.addAll(changeTurnMessages);


            } else {
              loginResponse.setResponse("Welcome aboard " + newPlayer.getPlayerNickname() + "!");
              loginResponse.setPlayerId(
                  turnManager.getCurrentNumberOfUsers());//TODO check

            }
          } else {
            loginResponse.setResponse("Error while creating new player, please try again!");
          }

          break;

        default:
          loginResponse.setResponse("Unexpected error! This should've not happened!");
          break;
      }
    }
    return messages;
  }

  /**
   * This method elaborates game messages, differentiating between the ones used to play a card
   * specifically, a character or an assistant card, and those used to move elements inside the game
   * board. It also checks whether an action can be performed.
   *
   * @param msg The game message to be elaborated
   */
  private Vector<Message> elaborateGameMessage(Message msg) {
    boolean everythingOkay;
    Vector<Message> messages = new Vector<>();

    // Check player is current player OR phase is login
    everythingOkay = !(msg.getPlayerId() == 0);  // ;

    boolean isCharacterPlayed = (turnManager.getMainGamePhase() == MessageMain.MOVE) &&
        msg.getMessageSecondary() == MessageSecondary.CHARACTER;

    // Check phase is the right one
    everythingOkay = everythingOkay &&
        ((!(msg.getMessageMain() == turnManager.getMainGamePhase()) &&
            !(msg.getMessageSecondary() == turnManager.getSecondaryPhase())) ||
            isCharacterPlayed);

    Message gameResponse = null;
    if (everythingOkay) {
      switch (msg.getMessageMain()) {
        case MOVE:
          gameResponse = moveController.elaborateMessage((MoveMessage) msg, game);
          messages.add(gameResponse);
          break;
        case PLAY:
          //TODO dario

          // gameResponse= playController.elaborateMessage((PlayMessage) msg, game);
          //messages.add(gameResponse);
          break;
        default:
          break;
      }
    }

    if (gameResponse != null) {
      // Update turn
      turnManager.updateCounters();
      //responseString += turnManager.changeState();

      // Set current player
      if (msg.getMessageSecondary() == MessageSecondary.ASSISTANT) {
        this.game.setCurrentPlayerIndex(turnManager.getCurrentNumberOfPlayedAssistants());
      } else if (msg.getMessageMain() == MessageMain.MOVE) {
        this.game.setCurrentPlayerIndex(turnManager.getCurrentNumberOfUsersPlayedActionPhase());
      }

      // If appropriate change game order in game
      if (msg.getMessageMain() == MessageMain.PLAY
          && msg.getMessageSecondary() == MessageSecondary.ASSISTANT
          && turnManager.getCurrentNumberOfPlayedAssistants() == numberOfPlayers) {
        this.game.orderBasedOnAssistant();
        //send turn message
      } else if (msg.getMessageMain() == MessageMain.MOVE
          && msg.getMessageSecondary() == MessageSecondary.CLOUD_TILE
          && turnManager.getCurrentNumberOfUsersPlayedActionPhase() == numberOfPlayers) {
        //send turn message
        this.game.reverseOrderEndTurn();
      }

      //gameResponse.setResponse(responseString);

    } else {
      ClientResponse error = new ClientResponse(MessageSecondary.ERROR);
      error.setResponse(
          "Error! The inserted inputs are not correct or it is not your turn!\n");
      messages.add(error);
    }
    return messages;
  }

  /**
   * It creates and sets the new game, setting the current phase and choosing the game order
   *
   * @throws FileNotFoundException If the json file is not found
   */
  public void setupGame() throws IOException {
    this.game.setTeams(teams);
    this.game.setExpert(isGameExpert);
    turnManager.setNumberStudentsFromEntrance(this.game.getStudentAtEntrance());
  }

  /**
   * This method compile the begin turn message at the beginning of the game and each turn
   * @param messageSecondary the message secondary to create the message
   * @return the vector of messages to be sent to each client
   */
  public Vector<Message> changeTurnMessage(MessageSecondary messageSecondary) {
    Vector<Message> returnedVector = new Vector<>();

    for (Team team : this.game.getTeams()) {
      for (Player player : team.getPlayers()) {

        BeginTurnMessage beginTurnMessage = new BeginTurnMessage(messageSecondary);
        beginTurnMessage.setPlayerId(player.getPlayerId());

        Vector<int[]> studentsAtEntrances = new Vector<>();
        Vector<int[]> studentDiningRooms = new Vector<>();

        for (Team teamStudents : this.game.getTeams()) {
          for (Player playerStudents : teamStudents.getPlayers()) {

            studentsAtEntrances.add(playerStudents.getPlayerBoard().getEntrance().getStudents());
            beginTurnMessage.setStudentEntrance(studentsAtEntrances);

            studentDiningRooms.add(playerStudents.getPlayerBoard().getDiningRoom().getStudents());
            beginTurnMessage.setStudentDiningRoom(studentDiningRooms);
          }
        }

        beginTurnMessage.setStudentEntrance(studentsAtEntrances);
        beginTurnMessage.setStudentDiningRoom(studentDiningRooms);

        int[] professorControlIdPlayer = new int[]{-1, -1, -1, -1, -1};
        // for(int i=0;i<5;i++){
        //professorControlIdPlayer[i]=game.getProfessorControlPlayer()[i].getPlayerId();
        // }
        beginTurnMessage.setProfessors(professorControlIdPlayer);

        Vector<int[]> studentsIslands = new Vector<>();
        for (Island island : game.getMainBoard().getIslands()) {
          int[] studentIsland = island.getStudents();
          studentsIslands.add(studentIsland);
        }
        beginTurnMessage.setStudentsIsland(studentsIslands);

        Vector<Integer> playableAssistantId = new Vector<>();
        for (Assistant assistant : player.getPlayableAssistant()) {
          playableAssistantId.add(assistant.getAssistantId());
        }
        beginTurnMessage.setPlayableAssistantId(playableAssistantId);

        //TODO character
        returnedVector.add(beginTurnMessage);
      }
    }
    return returnedVector;
  }
}
