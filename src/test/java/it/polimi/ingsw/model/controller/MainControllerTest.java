package it.polimi.ingsw.model.controller;

import it.polimi.ingsw.controller.MainController;
import it.polimi.ingsw.helpers.GamePhases;
import it.polimi.ingsw.model.player.Player;
import java.io.FileNotFoundException;
import java.util.Vector;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * MainControllerTest tests the MainController methods
 */
public class MainControllerTest {

  MainController mainController;

  /**
   * Before method to create the main controller, adding the player to the LoginController and
   * creating the new match
   *
   * @throws FileNotFoundException if the json file will not be found
   */
  @Before
  public void setUp() throws FileNotFoundException {
    mainController = new MainController();
    mainController.getLoginController().addPlayer("Ale");
    mainController.getLoginController().addPlayer("Luca");
    mainController.getLoginController().addPlayer("Dario");

    mainController.setCurrentGame(2, false);
  }

  /**
   * This method tests if the order at the beginning of the game is correct and also the phase, at
   * the beginning of the game the order id determinate by the player id
   */
  @Test
  public void testSetCurrentGame() {
    Vector<Integer> testVectorOrder = new Vector<>();
    testVectorOrder.add(0);
    testVectorOrder.add(1);
    testVectorOrder.add(2);

    Assert.assertEquals(mainController.getCurrentPhase(), GamePhases.PLANNING);
    Assert.assertEquals(mainController.getPlayerOrderId(), testVectorOrder);
  }

  /**
   * This method control in every situation if legitActionCheck returns what is supposed to return
   */
  @Test
  public void testLegitActionCheck() {
    Assert.assertTrue(mainController.legitActionCheck(0, GamePhases.PLANNING));
    Assert.assertFalse(mainController.legitActionCheck(0, GamePhases.MOVE_MOTHER_NATURE));
    Assert.assertFalse(mainController.legitActionCheck(1, GamePhases.PLANNING));
    Assert.assertFalse(mainController.legitActionCheck(1, GamePhases.MOVE_MOTHER_NATURE));
  }

  /**
   * This method tests if the findCurrentPlayer method works and find the correct player
   */
  @Test
  public void testFindCurrentPlayer() {
    Player playerToFind = mainController.findCurrentPlayer(mainController.getActivePlayerId());
    Assert.assertEquals(playerToFind,
        mainController.getCurrentGame().getTeams().get(0).getPlayers().get(0));
  }

  /**
   * This method tests if the mother nature will be correctly moved
   */
  @Test
  public void testMoveMotherNature() {

  }

  /**
   * This method tests if the moveStudent method moves the correct student from the correct place,
   * from the correct player to add it in the correct place
   */
  @Test
  public void testMoveStudent() {

  }

  /**
   * TODO
   */
  @Test
  public void testPlayCard() {

  }

  /**
   * It test if the correct tile is taken and also if it is added to the correct player entrance
   */
  @Test
  public void testTakeCloudTile() {

  }

  /**
   * This method tests if the method nextPhase is correct and sets the right gamePhase
   */
  @Test
  public void testNextPhase() {
    mainController.nextPhase();
    Assert.assertEquals(mainController.getCurrentPhase(), GamePhases.MOVE_STUDENTS);
    mainController.nextPhase();
    Assert.assertEquals(mainController.getCurrentPhase(), GamePhases.MOVE_MOTHER_NATURE);
    mainController.nextPhase();
    Assert.assertEquals(mainController.getCurrentPhase(), GamePhases.CLOUD_TILE_PHASE);
  }

  /**
   * This method tests if the current player will be chosen properly after every turn
   */
  @Test
  public void testNextTurn() {
    Assert.assertEquals(mainController.getActivePlayerId(), 0);
    mainController.nextTurn();
    Assert.assertEquals(mainController.getActivePlayerId(), 1);
    mainController.nextTurn();
    Assert.assertEquals(mainController.getActivePlayerId(), 2);
  }
}
