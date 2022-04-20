package it.polimi.ingsw.controller;

import it.polimi.ingsw.helpers.CardTypes;
import it.polimi.ingsw.helpers.GamePhases;
import it.polimi.ingsw.helpers.Places;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.player.Player;
import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.Vector;

/**
 * Main controller class that manage the other controller and do the correct phase and correct
 * player check
 */
public class MainController {

  private final LoginController loginController;
  private final PlayerController playerController;
  private final GameController gameController;
  private final TurnManager turnManager;
  private final Vector<Integer> playerOrderId;
  private Game currentGame;
  private int activePlayerId;
  private GamePhases currentPhase;
  private int studentsMoved = 0;

  /**
   * Constructor for the MainController Class
   */
  public MainController() {
    this.turnManager = new TurnManager();
    this.loginController = new LoginController();
    this.playerController = new PlayerController();
    this.gameController = new GameController(this.loginController);
    this.playerOrderId = new Vector<>();
  }

  /**
   * It creates and set the new game, setting the current phase and chose the game order
   *
   * @param gameMode The number of player allowed into the game
   * @param isExpert If  the game will be expert or not
   * @throws FileNotFoundException If the json file are not found
   */
  public void setCurrentGame(int gameMode, boolean isExpert) throws FileNotFoundException {

    this.currentGame = gameController.setUpGame(gameMode, isExpert);
    this.playerController.setCurrentGame(this.currentGame);
    this.currentPhase = GamePhases.PLANNING;
    for (int currentPlayerId = 0; currentPlayerId < currentGame.getPlayerNumber();
        currentPlayerId++) {
      this.playerOrderId.add(currentPlayerId);
    }
    this.activePlayerId = playerOrderId.elementAt(0);
    //send message to play the assistant at player 0
  }


  /**
   * This method checks if the player and the part of the player turn are what tey supposed to be
   *
   * @param actionPlayerId The playerId of the player that is trying to do the action
   * @param messagePhase   The supposed phase for that action
   * @return true the player and the phase are correct false otherwise
   */
  public boolean legitActionCheck(int actionPlayerId, GamePhases messagePhase) {
    return actionPlayerId == activePlayerId && messagePhase == currentPhase || (
        messagePhase == GamePhases.ACTION && currentPhase != GamePhases.PLANNING);
  }

  /**
   * This method search for the player with that id
   *
   * @param activePlayerId The id of the player  we want to find
   * @return Returns the player with that id if present null otherwise
   */
  public Player findCurrentPlayer(int activePlayerId) {

    for (Team team : currentGame.getTeams()) {
      for (Player player : team.getPlayers()) {

        if (player.getPlayerId() == activePlayerId) {
          return player;
        }
      }
    }
    return null;
  }

  /**
   * This method moves mother nature by a number of jumps
   *
   * @param actionPlayerId The playerId of the player that is trying to do the action
   * @param messagePhase   The supposed phase for that action
   * @param moves          the number of jumps that the player want to do
   */
  public void moveMotherNature(int actionPlayerId, GamePhases messagePhase, int moves) {
    if (legitActionCheck(actionPlayerId, messagePhase)) {

      if (moves <= findCurrentPlayer(actionPlayerId).getAssistant().getMoves()) {
        playerController.moveMotherNature(moves);
        nextPhase();
      } else {
        //too many jumps error message
      }

    } else {
      //send error message
    }
  }

  /**
   * This method move a certain student from one place to another
   *
   * @param actionPlayerId The playerId of the player that is trying to do the action
   * @param messagePhase   The supposed phase for that action
   * @param color          The color of the student moved to another location
   * @param islandNumber   If the student will be placed into an island this is the number of that
   *                       island
   * @param placeFrom      The place where the student is taken from
   * @param placeTo        The place where the student is put into
   */
  public void moveStudent(int actionPlayerId, GamePhases messagePhase, int color,
      Optional<Integer> islandNumber,
      Places placeFrom, Places placeTo) {
    if (legitActionCheck(actionPlayerId, messagePhase)) {
      playerController
          .moveStudent(findCurrentPlayer(activePlayerId), color, placeFrom, placeTo, islandNumber);
      this.studentsMoved++;
      if (studentsMoved == currentGame.getStudentOnCloudTiles()) {
        nextPhase();
      }
    } else {
      //send error message
    }
  }

  /**
   * This method allows a player to play a card assistant or activate a character
   *
   * @param actionPlayerId The playerId of the player that is trying to do the action
   * @param messagePhase   The supposed phase for that action
   * @param cardType       The type of the card played by the player
   * @param cardId         The id of the card played
   */
  public void playCard(int actionPlayerId, GamePhases messagePhase, CardTypes cardType,
      int cardId) {
    if (legitActionCheck(actionPlayerId, messagePhase)) {
      playerController.playCard(findCurrentPlayer(activePlayerId), cardId, cardType);
      //if everyOne has played his/her assistant
      if (actionPlayerId == playerOrderId.lastElement()) {
        nextPhase();
      }

    } else {
      //send error message
    }
  }

  /**
   * This method allows a player to take a cloud tile of his/her choice
   *
   * @param actionPlayerId The playerId of the player that is trying to do the action
   * @param messagePhase   The supposed phase for that action
   * @param idCloudTile    The id of the tile taken
   */
  public void takeCloudTile(int actionPlayerId, GamePhases messagePhase, int idCloudTile) {
    if (legitActionCheck(actionPlayerId, messagePhase)) {
      if (idCloudTile < currentGame.getPlayerNumber() - 1) {
        playerController.takeCloudTile(findCurrentPlayer(activePlayerId), idCloudTile);
        nextTurn();
      } else {
        //invalid argument message
      }
    } else {
      // send error message
    }
  }

  /**
   * This method chance the current game phase
   */
  public void nextPhase() {
    this.currentPhase = turnManager.nextPhase(this.currentPhase);
  }

  /**
   * This method change the turn and set everything for a new round
   */
  public void nextTurn() {
    if (activePlayerId != playerOrderId.lastElement()) {
      this.activePlayerId = turnManager.nextTurn(playerOrderId, activePlayerId);
      this.currentPhase = GamePhases.MOVE_STUDENTS;

    } else {
      this.currentPhase = turnManager.nextRound();

    }
    this.studentsMoved = 0;
  }

  public LoginController getLoginController() {
    return loginController;
  }

  public PlayerController getPlayerController() {
    return playerController;
  }

  public GameController getGameController() {
    return gameController;
  }

  public TurnManager getTurnManager() {
    return turnManager;
  }

  public Game getCurrentGame() {
    return currentGame;
  }

  public int getActivePlayerId() {
    return activePlayerId;
  }

  public GamePhases getCurrentPhase() {
    return currentPhase;
  }

  public void setCurrentPhase(GamePhases currentPhase) {
    this.currentPhase = currentPhase;
  }

  public Vector<Integer> getPlayerOrderId() {
    return playerOrderId;
  }


  public int getStudentsMoved() {
    return studentsMoved;
  }


}
