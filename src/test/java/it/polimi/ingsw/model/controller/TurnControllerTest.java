package it.polimi.ingsw.model.controller;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.LoginController;
import it.polimi.ingsw.controller.TurnManager;
import it.polimi.ingsw.helpers.GamePhases;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;
import java.io.FileNotFoundException;
import java.util.Vector;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * TurnControllerTest tests the TurnController methods
 */
public class TurnControllerTest {

  GamePhases currentPhase;
  TurnManager turnManager;
  GameController gameController;
  Game currentGame;
  Vector<Integer> gameOrder;
  Player dario;
  Player luca;
  Player ale;

  /**
   * This setUp method creates the turnManager that will be tasted and also create a new game adding
   * the player
   *
   * @throws FileNotFoundException if the json file will not be found
   */
  @Before
  public void seUpTest() throws FileNotFoundException {

    this.turnManager = new TurnManager();

    LoginController loginController = new LoginController();
    loginController.addPlayer("Ale");
    loginController.addPlayer("Luca");
    loginController.addPlayer("Dario");

    this.gameController = new GameController(loginController);
    this.currentGame = gameController.setUpGame(2, false);

    ale = currentGame.getTeams().get(0).getPlayers().get(0);
    luca = currentGame.getTeams().get(1).getPlayers().get(0);
    dario = currentGame.getTeams().get(2).getPlayers().get(0);


  }

  /**
   * This method tests if the phase will be modified properly during every gamePhase
   */
  @Test
  public void testNextPhase() {
    this.currentPhase = turnManager.nextPhase(GamePhases.PLANNING);
    Assert.assertEquals(currentPhase, GamePhases.MOVE_STUDENTS);

    this.currentPhase = turnManager.nextPhase(this.currentPhase);
    Assert.assertEquals(currentPhase, GamePhases.MOVE_MOTHER_NATURE);

    this.currentPhase = turnManager.nextPhase(this.currentPhase);
    Assert.assertEquals(currentPhase, GamePhases.CLOUD_TILE_PHASE);
  }

  /**
   * This method tests if the game order will be modified in the correct way when all the players
   * have played their assistants this turn
   */
  @Test
  public void testSetGameOrder() {
    Vector<Integer> testVectorOrder = new Vector<>();
    testVectorOrder.add(1);
    testVectorOrder.add(2);
    testVectorOrder.add(0);

    ale.playAssistant(1);
    luca.playAssistant(3);
    dario.playAssistant(2);

    this.gameOrder = turnManager.setGameOrder(this.currentGame);
    Assert.assertEquals(gameOrder, testVectorOrder);
  }

  /**
   * This method tests the nextTurn method and if the game order will be respected everytime the
   * turn of a player finishes, if the player is not the last of the round it returns the id of the
   * new activePlayer otherwise it returns -1
   */
  @Test
  public void testNextTurn() {
    Vector<Integer> testOrderId = new Vector<>();
    int activePlayerId;
    testOrderId.add(2);
    testOrderId.add(0);
    testOrderId.add(1);

    activePlayerId = turnManager.nextTurn(testOrderId, 0);
    Assert.assertEquals(activePlayerId, 1);

    activePlayerId = turnManager.nextTurn(testOrderId, 1);
    Assert.assertEquals(activePlayerId, -1);

  }

  /**
   * This method tests the nexTurn method that sets the gamePhase properly
   */
  @Test
  public void testNextRound() {
    this.currentPhase = turnManager.nextRound();
    Assert.assertEquals(currentPhase, GamePhases.PLANNING);
  }
}
