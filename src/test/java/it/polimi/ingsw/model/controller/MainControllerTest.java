package it.polimi.ingsw.model.controller;

import it.polimi.ingsw.controller.MainController;
import it.polimi.ingsw.helpers.CardTypes;
import it.polimi.ingsw.helpers.Color;
import it.polimi.ingsw.helpers.GamePhases;
import it.polimi.ingsw.helpers.Places;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.Player;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Optional;
import java.util.Vector;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * MainControllerTest tests the MainController methods
 */
public class MainControllerTest {

  MainController mainController;
  Player activePlayer;
  Assistant playedAssistant;

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

    activePlayer = mainController.findCurrentPlayer(0);

    activePlayer.playAssistant(5);
    playedAssistant = activePlayer.getAssistant();


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
    mainController.setCurrentPhase(GamePhases.MOVE_MOTHER_NATURE);
    int initialMotherNaturePosition = mainController.getCurrentGame().getMainBoard()
        .getMotherNature().getPosition();
    mainController.moveMotherNature(activePlayer.getPlayerId(), GamePhases.MOVE_MOTHER_NATURE, 3);
    int finalMotherNaturePosition = mainController.getCurrentGame().getMainBoard().getMotherNature()
        .getPosition();
    Assert.assertEquals(finalMotherNaturePosition,
        (initialMotherNaturePosition + 3) % mainController.getCurrentGame().getMainBoard()
            .getIslands().size());
  }

  /**
   * This method tests if the moveStudent method moves the correct student from the correct place,
   * from the correct player to add it in the correct place
   */
  @Test
  public void testMoveStudentDiningRoomCase() {
    mainController.setCurrentPhase(GamePhases.MOVE_STUDENTS);
    //move student until allowed to each player turn

    while (mainController.getStudentsMoved() < mainController.getCurrentGame()
        .getStudentOnCloudTiles()) {
      activePlayer.moveToPlayerBoard(Places.ENTRANCE, Color.YELLOW
          .ordinal());
      mainController.moveStudent(activePlayer.getPlayerId(), GamePhases.MOVE_STUDENTS, Color.YELLOW
          .ordinal(), Optional.empty(), Places.ENTRANCE, Places.DINING_ROOM);
    }

    //assert if the students in the dining room are correct
    Assert.assertEquals(
        Arrays.stream(activePlayer.getPlayerBoard().getDiningRoom().getStudents()).sum(),
        mainController.getCurrentGame().getStudentOnCloudTiles());

    //assert if the number of student in the entrance remains the same, due to the moveToPlayerBoard
    Assert.assertEquals(
        Arrays.stream(activePlayer.getPlayerBoard().getEntrance().getStudents()).sum(),
        mainController.getCurrentGame().getStudentAtEntrance());

    //assert if the professor will be correctly given
    Assert.assertEquals(mainController.getCurrentGame().getProfessorControlPlayer()[Color.YELLOW
        .ordinal()], activePlayer);

    //assert if the phase is correctly changed
    Assert.assertEquals(mainController.getCurrentPhase(), GamePhases.MOVE_MOTHER_NATURE);
  }

  /**
   * This method tests if the moveStudent method moves the correct student from the correct place,
   * from the correct player to add it in the correct place
   */
  @Test
  public void testMoveStudentIslandCase() {
    mainController.setCurrentPhase(GamePhases.MOVE_STUDENTS);
    activePlayer.moveToPlayerBoard(Places.ENTRANCE, Color.YELLOW
        .ordinal());
    mainController.moveStudent(activePlayer.getPlayerId(), GamePhases.MOVE_STUDENTS, Color.YELLOW
        .ordinal(), Optional.of(5), Places.ENTRANCE, Places.ISLAND);

    //assert if the number of student on the island is correct
    Assert.assertEquals(
        Arrays.stream(
            mainController.getCurrentGame().getMainBoard().getIslands().get(5).getStudents()).sum(),
        2);

    //assert if the number of student in the entrance remains the same, due to the moveToPlayerBoard
    Assert.assertEquals(
        Arrays.stream(activePlayer.getPlayerBoard().getEntrance().getStudents()).sum(),
        mainController.getCurrentGame().getStudentAtEntrance());
  }

  /**
   * Testing the PlayCard method, ensuring that the assistant will be correctly played and set to
   * the right player
   */
  @Test
  public void testPlayCardAssistantCase() {
    mainController.setCurrentPhase(GamePhases.PLANNING);
    mainController
        .playCard(activePlayer.getPlayerId(), GamePhases.PLANNING, CardTypes.ASSISTANT, 3);
    Assert.assertNotNull(activePlayer.getAssistant());
    Assert.assertEquals(activePlayer.getAssistant().getAssistantId(), 3);
    Assert.assertEquals(activePlayer.getAssistant().getSpeed(), 4);
    Assert.assertEquals(activePlayer.getAssistant().getMoves(), 2);
  }

  /**
   * TODO
   */
  @Test
  public void testPlayCardCharacterCase() {
    mainController.playCard(activePlayer.getPlayerId(), GamePhases.ACTION, CardTypes.CHARACTER, 2);
  }

  /**
   * It test if the correct tile is taken and also if it is added to the correct player entrance
   */
  @Test
  public void testTakeCloudTile() {
    mainController.setCurrentPhase(GamePhases.CLOUD_TILE_PHASE);
    mainController.takeCloudTile(activePlayer.getPlayerId(), GamePhases.CLOUD_TILE_PHASE, 1);
    Assert.assertEquals(
        Arrays.stream(activePlayer.getPlayerBoard().getEntrance().getStudents()).sum(),
        mainController.getCurrentGame().getStudentAtEntrance() + mainController.getCurrentGame()
            .getStudentOnCloudTiles());
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
