package it.polimi.ingsw.controller;

import it.polimi.ingsw.helpers.MessageMain;
import it.polimi.ingsw.helpers.MessageSecondary;
import it.polimi.ingsw.helpers.Towers;
import it.polimi.ingsw.messages.ClientResponse;
import it.polimi.ingsw.messages.InfoRequestMessage;
import it.polimi.ingsw.messages.LoginMessage;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MoveMessage;
import it.polimi.ingsw.messages.PlayMessage;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.player.Player;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
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
  // Attributes
  private Game game;

  // Game parameters
  private int numberOfPlayers;
  private boolean isGameExpert;

  // Server
  private Semaphore serverSemaphore;
  private Vector<Socket> socketClientsOut;

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
  public ClientResponse elaborateMessage(Message msg) throws IOException {

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
  private ClientResponse elaborateLoginMessage(LoginMessage msg) throws IOException {

    // Check if phase is correct
    if (turnManager.getMainGamePhase() == MessageMain.LOGIN &&
        msg.getMessageSecondary() == turnManager.getSecondaryPhase()) {

      switch (msg.getMessageSecondary()) {
        case GAME_PARAMS:
          // If parameters are okay set them and change game state
          if (loginController.checkGameParameters(msg)) {
            isGameExpert = msg.isGameExpert();
            numberOfPlayers = msg.getNumberOfPlayer();
            turnManager.setNumberOfUsers(numberOfPlayers);
            turnManager.updateCounters();
            turnManager.changeState();
            this.serverSemaphore.release();
          }
          break;

        case PLAYER_PARAMS:

          Player newPlayer = loginController.createPlayer(msg);

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
            }
          }

          break;

        default:
          break;
      }
    }
    return null;
  }

  /**
   * This method elaborates game messages, differentiating between the ones used to play a card
   * specifically, a character or an assistant card, and those used to move elements inside the game
   * board. It also checks whether an action can be performed.
   *
   * @param msg The game message to be elaborated
   */
  private ClientResponse elaborateGameMessage(Message msg) {
//TODO create message response
    boolean everythingOkay = true;

    // Check player is current player OR phase is login
    everythingOkay = !(msg.getPlayerId() == 0);  // ;

    boolean isCharacterPlayed = (turnManager.getMainGamePhase() == MessageMain.MOVE) &&
        msg.getMessageSecondary() == MessageSecondary.CHARACTER;

    // Check phase is the right one
    everythingOkay = everythingOkay &&
        ((!(msg.getMessageMain() == turnManager.getMainGamePhase()) &&
            !(msg.getMessageSecondary() == turnManager.getSecondaryPhase())) ||
            isCharacterPlayed);

    if (everythingOkay) {
      switch (msg.getMessageMain()) {
        case MOVE:
          //TODO set string
          everythingOkay = moveController.elaborateMessage((MoveMessage) msg, game);
          break;
        case PLAY:
          //TODO set string
          everythingOkay = playController.elaborateMessage((PlayMessage) msg, game);
          break;
        default:
          break;
      }
    }

   //TODO if string != null
    if (everythingOkay) {
      // Update turn
      turnManager.updateCounters();
      turnManager.changeState();
      // TODO give game current player number
      // TODO if appropriate change game order in game
    } else {
      // Send error message
      // TODO
    }
    return null;
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

}
