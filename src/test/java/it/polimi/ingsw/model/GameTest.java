package it.polimi.ingsw.model;


import it.polimi.ingsw.helpers.Color;
import it.polimi.ingsw.helpers.Constants;
import it.polimi.ingsw.helpers.Places;
import it.polimi.ingsw.model.board.Island;
import it.polimi.ingsw.model.player.Player;
import java.util.Arrays;
import java.util.Optional;
import java.util.Vector;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GameTest {

  Game expertGame;
  Game noExpertGame;
  Player activePlayer;
  Player otherPlayer;

  @Before
  public void setUp() throws Exception {

    this.expertGame = new Game(Constants.getTwoPlayerMode(), true);
    this.noExpertGame = new Game(Constants.getThreePlayerMode(), false);

    expertGame.addPlayerToTeam(expertGame.addTeam(), 0, "ale");
    expertGame.addPlayerToTeam(expertGame.addTeam(), 1, "dario");

    this.activePlayer = expertGame.getTeams().get(0).getPlayers().get(0);
    this.otherPlayer = expertGame.getTeams().get(1).getPlayers().get(0);
  }

  /**
   * Test for the createCloud method, it controls if the clouds will be created in the right way at
   * the beginning of the game
   */
  @Test
  public void testCreateCloudTiles() {
    Assert.assertEquals(expertGame.getCloudTiles().size(), expertGame.getPlayerNumber());
  }

  /**
   * test for the fillCloud methode, it controls if the cloudTiles will be filled correctly by
   * checking the number of student on each tile if it is equal to the expected number
   */
  @Test
  public void testFillCloudTiles() {
    Vector<CloudTile> testClouds = expertGame.getCloudTiles();
    for (CloudTile cloudTile : testClouds) {
      Assert.assertEquals(Arrays.stream(testClouds.get(cloudTile.getId())
              .getStudents()).sum(),
          expertGame.getStudentOnCloudTiles());
    }
  }

  /**
   * Testing the moveNature method and it ensures that, independently from the original position of
   * motherNature, she will be moved by the correct number of steps chosen by the player according
   * to the played assistant
   */
  @Test
  public void testMoveNature() {
    int motherNatureInitialPosition;
    int motherNatureFinalPosition;
    motherNatureInitialPosition = expertGame.getMainBoard().getMotherNature().getPosition();
    expertGame.moveNature(5);
    motherNatureFinalPosition = expertGame.getMainBoard().getMotherNature().getPosition();

    Assert.assertEquals(motherNatureFinalPosition,
        (motherNatureInitialPosition + 5) % (expertGame.getMainBoard().getIslands().size()));
  }

  /**
   * Testing the takeCloud method ensuring that the chosen cloudTile students will be correctly
   * added to the active player entrance removing them from the cloudTile
   */
  @Test
  public void testTakeCloud() {
    expertGame.takeCloud(activePlayer, 1);
    Assert.assertEquals(
        Arrays.stream(activePlayer.getPlayerBoard().getEntrance().getStudents()).sum(),
        expertGame.getStudentAtEntrance() + expertGame.getStudentOnCloudTiles());
  }

  /**
   * Testing the setPurchasableCharacter method in the nonExpert match, if fact the
   * purchasableCharacter vector must be null
   */
  @Test
  public void testSetPurchasableCharacterNoExpertMode() {
    //Assert.assertNull(noExpertGame.getPurchasableCharacter());
  }

  /**
   * Testing the setPurchasableCharacter method assuming a expert game, ensuring that the
   * purchasableCharacter attributes will be correctly created and filled
   */
  @Test
  public void testSetPurchasableCharacterExpertMode() {
  }

  /**
   * testing the setGameParameter method ensuring that the parameters in the gameParameter.json file
   * will be correctly read and set
   */
  @Test
  public void testSetGameParameter() {
    Assert.assertEquals(expertGame.getPlayerNumber(), 2);
    Assert.assertEquals(expertGame.getPlayerTowerNumber(), 8);
    Assert.assertEquals(expertGame.getStudentAtEntrance(), 7);
    Assert.assertEquals(expertGame.getStudentOnCloudTiles(), 3);


  }

  /**
   * testing the moveStudent method(during a diningRoom case) and it ensures that the student will
   * be removed from the entrance and added to the diningRoom
   */
  @Test
  public void testMoveStudentDiningRoomCase() {
    expertGame.moveStudent(activePlayer, Places.ENTRANCE, Places.DINING_ROOM, 2,
        Optional.empty());
    Assert.assertEquals(
        Arrays.stream(activePlayer.getPlayerBoard().getDiningRoom().getStudents()).sum(), 1);
    Assert.assertEquals(
        Arrays.stream(activePlayer.getPlayerBoard().getEntrance().getStudents()).sum(),
        expertGame.getStudentAtEntrance() - 1);
  }

  /**
   * Testing the moveStudent method(during an island case) and it ensures that the student will be
   * removed from the entrance and added to the selected island To avoid a possible side effect due
   * to the random color of students into the entrance this test also add a student with the same
   * color of the one added to the island
   */
  @Test
  public void testMoveStudentCloudCase() {
    activePlayer.getPlayerBoard().getEntrance().addStudent(3);
    expertGame.moveStudent(activePlayer, Places.ENTRANCE, Places.ISLAND, 3, Optional.of(5));
    Island chosenIsland = expertGame.getMainBoard().getIslands().get(5);
    Assert.assertEquals(Arrays.stream(chosenIsland.getStudents()).sum(), 2);
    Assert.assertEquals(
        Arrays.stream(activePlayer.getPlayerBoard().getEntrance().getStudents()).sum(),
        expertGame.getStudentAtEntrance());
  }

  /**
   * Testing the give professor method, it ensures that the professor will be correctly given to the
   * right player
   */
  @Test
  public void testGiveProfessorToPlayer() {
    Player[] testProfessorArray = new Player[]{null, null, activePlayer, null, null};
    activePlayer.moveToPlayerBoard(Places.DINING_ROOM, Color.GREEN.ordinal());
    expertGame.giveProfessorToPlayer(Color.GREEN.ordinal());

    Assert.assertEquals(expertGame.getProfessorControlPlayer()[Color.GREEN.ordinal()].toString(),
        testProfessorArray[Color.GREEN.ordinal()].toString());
  }

  /**
   * Testing the give professor method, it ensures that in case of the same number of students the
   * professor remains to the player that controls it before the last movement
   */

  @Test
  public void testGiveProfessorToPlayerSameNumberOfStudent() {
    Player[] testProfessorArray = new Player[]{null, null, activePlayer, null, null};
    Assert.assertNull(expertGame.getProfessorControlPlayer()[Color.GREEN.ordinal()]);
    activePlayer.moveToPlayerBoard(Places.DINING_ROOM, Color.GREEN.ordinal());
    otherPlayer.moveToPlayerBoard(Places.DINING_ROOM, Color.GREEN.ordinal());
    expertGame.giveProfessorToPlayer(Color.GREEN.ordinal());
    Assert.assertEquals(expertGame.getProfessorControlPlayer()[Color.GREEN.ordinal()].toString(),
        testProfessorArray[Color.GREEN.ordinal()].toString());
  }

  /**
   * Testing the give professor method, it ensures that the professor will be correctly given to the
   * right player even if the controller change during the game
   */


  @Test
  public void testGiveProfessorToAnotherPlayer() {
    Player[] testProfessorArray = new Player[]{null, null, otherPlayer, null, null};
    activePlayer.moveToPlayerBoard(Places.DINING_ROOM, Color.GREEN.ordinal());
    expertGame.giveProfessorToPlayer(Color.GREEN.ordinal());
    otherPlayer.moveToPlayerBoard(Places.DINING_ROOM, Color.GREEN.ordinal());
    otherPlayer.moveToPlayerBoard(Places.DINING_ROOM, Color.GREEN.ordinal());
    expertGame.giveProfessorToPlayer(Color.GREEN.ordinal());
    Assert.assertEquals(expertGame.getProfessorControlPlayer()[Color.GREEN.ordinal()].toString(),
        testProfessorArray[Color.GREEN.ordinal()].toString());
  }
}


