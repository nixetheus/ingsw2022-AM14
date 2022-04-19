package it.polimi.ingsw.model.controller;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.LoginController;
import it.polimi.ingsw.controller.PlayerController;
import it.polimi.ingsw.helpers.CardTypes;
import it.polimi.ingsw.helpers.Color;
import it.polimi.ingsw.helpers.Places;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * PlayerControllerTest tests the PlayerController methods
 */
public class PlayerControllerTest {

  Player activePlayer;
  private PlayerController playerController;
  private GameController gameController;
  private Game currentGame;

  @Before
  public void setUp() throws FileNotFoundException {

    LoginController loginController = new LoginController();
    loginController.addPlayer("Ale");
    loginController.addPlayer("Luca");
    loginController.addPlayer("Dario");

    this.gameController = new GameController(loginController);
    this.currentGame = gameController.setUpGame(2, false);

    playerController = new PlayerController();
    playerController.setCurrentGame(this.currentGame);
    activePlayer = currentGame.getTeams().get(0).getPlayers().get(0);

  }

  /**
   * This method tests if the movement of a student by a certain player will be done in the proper
   * way
   */
  @Test
  public void testMoveStudentIslandCase() {
    activePlayer.moveToPlayerBoard(Places.ENTRANCE, Color.BLUE.ordinal());
    playerController.moveStudent(activePlayer, Color.BLUE.ordinal(), Places.ENTRANCE, Places.ISLAND,
        Optional.of(5));

    Assert.assertEquals(
        Arrays.stream(currentGame.getMainBoard().getIslands().get(5).getStudents()).sum(), 2);

    Assert.assertEquals(
        Arrays.stream(activePlayer.getPlayerBoard().getEntrance().getStudents()).sum(),
        currentGame.getStudentAtEntrance());
  }

  /**
   * This method tests if in the other case the move student will be done properly
   */
  @Test
  public void testMoveStudentDiningRoomCase() {
    activePlayer.moveToPlayerBoard(Places.ENTRANCE, Color.BLUE.ordinal());
    playerController
        .moveStudent(activePlayer, Color.BLUE.ordinal(), Places.ENTRANCE, Places.DINING_ROOM,
            Optional.empty());

    Assert.assertEquals(
        Arrays.stream(activePlayer.getPlayerBoard().getDiningRoom().getStudents()).sum(), 1);

    Assert.assertEquals(
        Arrays.stream(activePlayer.getPlayerBoard().getEntrance().getStudents()).sum(),
        currentGame.getStudentAtEntrance());
  }

  /**
   * This method tests the movement of mother nature and control if it is correct
   */
  @Test
  public void testMoveMotherNature() {
    int initialMotherNaturePosition = currentGame.getMainBoard().getMotherNature().getPosition();
    playerController.moveMotherNature(3);
    int finalMotherNaturePosition = currentGame.getMainBoard().getMotherNature().getPosition();

    Assert.assertEquals(finalMotherNaturePosition,
        (initialMotherNaturePosition + 3) % currentGame.getMainBoard().getIslands().size());
  }

  /**
   * This method controls if an assistant card will be correctly played by a certain player
   */
  @Test
  public void testPlayCardAssistantCase() {
    playerController.playCard(activePlayer, 3, CardTypes.ASSISTANT);
    Assert.assertNotNull(activePlayer.getAssistant());
    Assert.assertEquals(activePlayer.getAssistant().getAssistantId(), 3);
    Assert.assertEquals(activePlayer.getAssistant().getSpeed(), 4);
    Assert.assertEquals(activePlayer.getAssistant().getMoves(), 2);
  }

  /**
   * This method controls if an assistant card will be correctly played by a certain player
   */
  @Test
  public void testPlayCardCharacterCase() {

  }

  /**
   * This method controls if the cloudTile will be successfully taken by a certain player and the
   * student on that tile are added in the right place
   */
  @Test
  public void testTakeCloudTile() {
    playerController.takeCloudTile(activePlayer, 1);
    Assert.assertEquals(
        Arrays.stream(activePlayer.getPlayerBoard().getEntrance().getStudents()).sum(),
        currentGame.getStudentAtEntrance() + currentGame
            .getStudentOnCloudTiles());
  }

}
