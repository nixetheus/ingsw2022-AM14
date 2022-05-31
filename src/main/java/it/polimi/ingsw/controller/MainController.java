package it.polimi.ingsw.controller;

import it.polimi.ingsw.helpers.MessageMain;
import it.polimi.ingsw.helpers.MessageSecondary;
import it.polimi.ingsw.helpers.StudentsPlayerId;
import it.polimi.ingsw.helpers.Towers;
import it.polimi.ingsw.messages.BeginTurnMessage;
import it.polimi.ingsw.messages.ClientResponse;
import it.polimi.ingsw.messages.InfoRequestMessage;
import it.polimi.ingsw.messages.LoginMessage;
import it.polimi.ingsw.messages.LoginMessageResponse;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MoveMessage;
import it.polimi.ingsw.messages.PlayMessage;
import it.polimi.ingsw.model.CloudTile;
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
  //private Vector<Message> messages;
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
            loginResponse.setPlayerId(0);
            loginResponse.setResponse("Game parameters correctly set!");
            loginResponse.setMessageSecondary(MessageSecondary.LOBBY);

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
                long nCurrentPlayers = teams.stream().map(team -> team.getPlayers().size())
                    .reduce(0, Integer::sum);

                teams.elementAt((int) nCurrentPlayers % 2).addPlayer(newPlayer);
              }

            } else {
              Team newTeam = new Team(teams.size(), Towers.values()[teams.size()]);
              newTeam.addPlayer(newPlayer);
              teams.add(newTeam);
            }

            // If players are all in, setup game
            if (teams.stream().map(team -> team.getPlayers().size()).reduce(0, Integer::sum)
                == numberOfPlayers) {
              setupGame();

              // SEND WELCOME MESSAGE
              loginResponse.setResponse("Welcome aboard " + newPlayer.getPlayerNickname() + "!  ");
              loginResponse.setPlayerId(turnManager.getCurrentNumberOfUsers());
              loginResponse.setMessageSecondary(MessageSecondary.LOBBY);

              // SEND READY MESSAGE TODO: CHANGE SECONDARY
              LoginMessageResponse loginMessageResponse2 = new LoginMessageResponse(
                  MessageSecondary.INFO_HELP);
              loginMessageResponse2
                  .setResponse("Everyone is ready now! We shall let the game start!");
              loginMessageResponse2.setPlayerId(-1);
              messages.add(loginMessageResponse2);

              Vector<Message> changeTurnMessages = changeTurnMessage(MessageSecondary.INIT_GAME);
              messages.addAll(changeTurnMessages);

              messages.add(sendClientResponse(
                  MessageSecondary.ASSISTANT, "It's your turn to play an assistant",
                  game.getCurrentPlayer().getPlayerId()));


            } else {
              loginResponse.setResponse("Welcome aboard " + newPlayer.getPlayerNickname() + "!");
              loginResponse.setPlayerId(
                  turnManager.getCurrentNumberOfUsers());
              // FIRST PLAYER
              if (teams.stream().map(team -> team.getPlayers().size()).reduce(0, Integer::sum)
                  == 1) {
                loginResponse.setMessageSecondary(MessageSecondary.ASK_GAME_PARAMS);
              } else {
                loginResponse.setPlayerId(turnManager.getCurrentNumberOfUsers());
                loginResponse.setMessageSecondary(MessageSecondary.LOBBY);
              }
            }
          } else {
            loginResponse.setResponse("Error while creating new player, please try again!");
          }

          turnManager.updateCounters();
          turnManager.changeState();

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
    everythingOkay = true;//!(msg.getPlayerId() == 0);  // ;

    boolean isCharacterPlayed = (turnManager.getMainGamePhase() == MessageMain.MOVE) &&
        msg.getMessageSecondary() == MessageSecondary.CHARACTER;

    // Check phase is the right one
    everythingOkay = everythingOkay &&
        (((msg.getMessageMain() == turnManager.getMainGamePhase()) &&
            (msg.getMessageSecondary() == turnManager.getSecondaryPhase())) ||
            isCharacterPlayed);

     if (msg.getMessageMain() == MessageMain.MOVE) {
      this.game.setCurrentPlayerIndex(turnManager.getCurrentNumberOfUsersPlayedActionPhase());}

    Message gameResponse = null;
    if (everythingOkay) {
      switch (msg.getMessageMain()) {
        case MOVE:
          gameResponse = moveController.elaborateMessage((MoveMessage) msg, game);
          messages.add(gameResponse);
          break;
        case PLAY:
          gameResponse = playController.elaborateMessage((PlayMessage) msg, game);
          messages.add(gameResponse);
          break;
        default:
          break;
      }
    }

    if (gameResponse != null) {

      // Update turn
      turnManager.updateCounters();
      turnManager.changeState();

      // Set current player
      if (msg.getMessageSecondary() == MessageSecondary.ASSISTANT
          && turnManager.getCurrentNumberOfPlayedAssistants() != numberOfPlayers) {

        this.game.setCurrentPlayerIndex(turnManager.getCurrentNumberOfPlayedAssistants());

        // New current player turn to play assistant
        messages.add(sendClientResponse(
            MessageSecondary.ASK_ASSISTANT, "It's your turn to play an assistant",
            game.getCurrentPlayer().getPlayerId()));


      } else if (msg.getMessageMain() == MessageMain.MOVE) {

        this.game.setCurrentPlayerIndex(turnManager.getCurrentNumberOfUsersPlayedActionPhase());

        // Message to update gui
        messages.addAll(changeTurnMessage(MessageSecondary.INFRA_TURN));

        // Message to help to understand the game from cli
        // TODO
        if (turnManager.getCurrentNumberOfStudentsFromEntrance() ==
            turnManager.getNumberStudentsFromEntrance()) {

          messages.add(sendClientResponse(
              MessageSecondary.ASK_MN, "Select island to move mother nature",
              game.getCurrentPlayer().getPlayerId()));

        } else {
          messages.add(sendClientResponse(
              MessageSecondary.ASK_STUDENT_ENTRANCE, "Move another student",
              game.getCurrentPlayer().getPlayerId()));
        }

        if (turnManager.getSecondaryPhase() == MessageSecondary.ASK_CLOUD) {
          messages.add(
              sendClientResponse(
                  MessageSecondary.ASK_CLOUD, "Take one cloud",
                  game.getCurrentPlayer().getPlayerId()));
        }

      }

      // If appropriate change game order in game
      if (msg.getMessageMain() == MessageMain.PLAY
          && msg.getMessageSecondary() == MessageSecondary.ASSISTANT
          && turnManager.getCurrentNumberOfPlayedAssistants() == numberOfPlayers) {

        //set player order
        this.game.orderBasedOnAssistant();  // TODO

        //send order message
        ClientResponse order = new ClientResponse(MessageSecondary.GAME_ORDER);
        order.setPlayerId(-1);

        Vector<Integer> playerOrderIdVector = new Vector<>();

        for (Player player : game.getGameOrder()) {
          playerOrderIdVector.add(player.getPlayerId());
        }

        order.setPlayerOrderId(playerOrderIdVector);
        messages.add(order);

      } else if (msg.getMessageMain() == MessageMain.MOVE
          && msg.getMessageSecondary() == MessageSecondary.CLOUD_TILE
          && turnManager.getCurrentNumberOfUsersPlayedActionPhase() == numberOfPlayers) {
        this.game.reverseOrderEndTurn();
      }



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
    turnManager.setNumberStudentsFromEntrance(this.game.getStudentOnCloudTiles());
  }

  /**
   * This method compiles the begin-turn message at the beginning of the game and each turn
   *
   * @param messageSecondary the message secondary to create the message
   * @return the vector of messages to be sent to each client
   */
  public Vector<Message> changeTurnMessage(MessageSecondary messageSecondary) {
    Vector<Message> returnedVector = new Vector<>();

    for (Team team : this.game.getTeams()) {
      for (Player player : team.getPlayers()) {

        BeginTurnMessage beginTurnMessage = new BeginTurnMessage(messageSecondary);
        beginTurnMessage.setPlayerId(player.getPlayerId());

        Vector<StudentsPlayerId> studentsAtEntrances = new Vector<>();
        Vector<StudentsPlayerId> studentDiningRooms = new Vector<>();
        Vector<int[]> professors = new Vector<>();

        for (Team teamStudents : this.game.getTeams()) {
          for (Player playerStudents : teamStudents.getPlayers()) {

            studentsAtEntrances.add(new StudentsPlayerId(playerStudents.getPlayerId(),
                playerStudents.getPlayerBoard().getEntrance().getStudents()));
            beginTurnMessage.setStudentEntrance(studentsAtEntrances);

            studentDiningRooms.add(new StudentsPlayerId(playerStudents.getPlayerId(),
                playerStudents.getPlayerBoard().getDiningRoom().getStudents()));
            beginTurnMessage.setStudentDiningRoom(studentDiningRooms);

            // Professors
            int[] playerProfessors = new int[5];
            professors.add(playerProfessors);
            for (int profIndex = 0; profIndex < 5; profIndex++) {
              if (game.getProfessorControlPlayer()[profIndex] != null) {
                if (game.getProfessorControlPlayer()[profIndex].getPlayerId()
                    == playerStudents.getPlayerId()) {
                  professors.elementAt(playerStudents.getPlayerId())[profIndex] = 1;
                }
              }
            }
          }
        }

        beginTurnMessage.setStudentEntrance(studentsAtEntrances);
        beginTurnMessage.setStudentDiningRoom(studentDiningRooms);

        beginTurnMessage.setMotherNaturePosition(game.getMainBoard().getMotherNature().getPosition());
        beginTurnMessage.setProfessors(professors);


        Vector<int[]> studentsIslands = new Vector<>();
        Vector<Integer> towersIslands = new Vector<>();
        for (Island island : game.getMainBoard().getIslands()) {
          int[] studentIsland = island.getStudents();
          studentsIslands.add(studentIsland);
          towersIslands.add(island.getNumberOfTowers());
        }
        beginTurnMessage.setStudentsIsland(studentsIslands);
        beginTurnMessage.setTowersIsland(towersIslands);

        Vector<Integer> playableAssistantId = new Vector<>();
        for (Assistant assistant : player.getPlayableAssistant()) {
          playableAssistantId.add(assistant.getAssistantId());
        }
        beginTurnMessage.setPlayableAssistantId(playableAssistantId);

        // Clouds
        Vector<int[]> clouds = new Vector<>();
        for (CloudTile cloud : game.getCloudTiles()) {
          clouds.add(cloud.getStudents());
        }
        beginTurnMessage.setStudentsCloudTiles(clouds);

        //TODO character
        returnedVector.add(beginTurnMessage);
      }
    }
    return returnedVector;
  }

  private Message sendClientResponse(MessageSecondary messageSecondary, String response, int id) {
    ClientResponse clientResponse = new ClientResponse(messageSecondary);
    clientResponse.setResponse(response);
    clientResponse.setPlayerId(id);
    return clientResponse;
  }
}
