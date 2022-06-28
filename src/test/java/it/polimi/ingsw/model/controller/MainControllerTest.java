package it.polimi.ingsw.model.controller;

import it.polimi.ingsw.controller.MainController;
import it.polimi.ingsw.helpers.Color;
import it.polimi.ingsw.helpers.Constants;
import it.polimi.ingsw.helpers.MessageSecondary;
import it.polimi.ingsw.messages.LoginMessage;
import it.polimi.ingsw.messages.MoveMessage;
import it.polimi.ingsw.messages.PlayMessage;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.player.Player;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.Semaphore;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * MainControllerTest tests the MainController methods
 */
public class MainControllerTest {

  MainController mainController;
  Game game;
  int selectedIsland = 5;
  LoginMessage nick1;
  LoginMessage params;
  LoginMessage nick2;
  PlayMessage assistant1;
  PlayMessage assistant2;
  MoveMessage student1;
  MoveMessage student2;
  MoveMessage student3;
  MoveMessage student4;
  MoveMessage student5;
  MoveMessage student6;
  MoveMessage motherNatureMovement1;
  MoveMessage motherNatureMovement2;
  MoveMessage takeCloudTile1;
  MoveMessage takeCloudTile2;

  /**
   * SetUp method that prepare every message suppose to come from the user
   */
  @Before
  public void setUp() throws InterruptedException {
    this.mainController = new MainController();
    this.game = new Game();
    mainController.setGame(game);
    mainController.setServerSemaphore(new Semaphore(1));

    nick1 = new LoginMessage(MessageSecondary.PLAYER_PARAMS);
    nick1.setNickName("ale");

    params = new LoginMessage(MessageSecondary.GAME_PARAMS);
    params.setNumberOfPlayer(2);
    params.setGameExpert(true);

    nick2 = new LoginMessage(MessageSecondary.PLAYER_PARAMS);
    nick2.setNickName("Luca");

    assistant1 = new PlayMessage(MessageSecondary.ASSISTANT);
    assistant1.setAssistantId(9);
    assistant1.setPlayerId(0);

    assistant2 = new PlayMessage(MessageSecondary.ASSISTANT);
    assistant2.setAssistantId(8);
    assistant2.setPlayerId(1);

    student1 = new MoveMessage(MessageSecondary.ENTRANCE);
    student1.setStudentColor(Color.RED.ordinal());
    student1.setPlace(0);
    student1.setPlayerId(0);

    student2 = new MoveMessage(MessageSecondary.ENTRANCE);
    student2.setStudentColor(Color.RED.ordinal());
    student2.setPlace(0);
    student2.setPlayerId(0);

    student3 = new MoveMessage(MessageSecondary.ENTRANCE);
    student3.setStudentColor(Color.RED.ordinal());
    student3.setPlace(0);
    student3.setPlayerId(0);

    student4 = new MoveMessage(MessageSecondary.ENTRANCE);
    student4.setStudentColor(Color.YELLOW.ordinal());
    student4.setIslandNumber(selectedIsland);
    student4.setPlace(1);
    student4.setPlayerId(1);

    student5 = new MoveMessage(MessageSecondary.ENTRANCE);
    student5.setStudentColor(Color.GREEN.ordinal());
    student5.setPlace(0);
    student5.setPlayerId(1);

    student6 = new MoveMessage(MessageSecondary.ENTRANCE);
    student6.setStudentColor(Color.RED.ordinal());
    student6.setPlace(0);
    student6.setPlayerId(1);

    motherNatureMovement1 = new MoveMessage(MessageSecondary.MOVE_MN);
    motherNatureMovement1.setPlayerId(0);

    motherNatureMovement2 = new MoveMessage(MessageSecondary.MOVE_MN);
    motherNatureMovement2.setPlayerId(1);

    takeCloudTile1 = new MoveMessage(MessageSecondary.CLOUD_TILE);
    takeCloudTile1.setCloudTileNumber(1);
    takeCloudTile1.setPlayerId(0);

    takeCloudTile2 = new MoveMessage(MessageSecondary.CLOUD_TILE);
    takeCloudTile2.setCloudTileNumber(0);
    takeCloudTile2.setPlayerId(1);

  }

  /**
   * TestElaborateMessage tests a fixed turn of a match comparing it with the known result
   * calculated by hand
   */
  @Test
  public void testElaborateMessage() throws IOException {

    //LOGIN FIRST CLIENT
    mainController.elaborateMessage(nick1);
    Assert.assertEquals(1, mainController.getTeams().size());

    //GAME PARAMETERS
    mainController.elaborateMessage(params);
    Assert.assertEquals(2, mainController.getNumberOfPlayers());
    Assert.assertTrue(mainController.isGameExpert());

    //LOGIN SECOND CLIENT
    mainController.elaborateMessage(nick2);
    Assert.assertEquals(2, mainController.getTeams().size());

    //PLAY FIRST PLAYER ASSISTANT
    mainController.elaborateMessage(assistant1);
//    Assert.assertEquals(9,
//       mainController.getGame().getActivePlayer().getAssistant().getAssistantId());

    //PLAY SECOND PLAYER ASSISTANT
    mainController.elaborateMessage(assistant2);
    //   Assert.assertEquals(8,
//       mainController.getGame().getActivePlayer().getAssistant().getAssistantId());

    //ADDING RIGHT STUDENTS TO THE PLAYERS
    for (Team team : mainController.getTeams()) {
      for (Player player : team.getPlayers()) {
        if (player.getPlayerId() == 0) {
          player.getPlayerBoard().getEntrance().addStudent(Color.RED.ordinal());
          player.getPlayerBoard().getEntrance().addStudent(Color.RED.ordinal());
          player.getPlayerBoard().getEntrance().addStudent(Color.RED.ordinal());
        } else {
          player.getPlayerBoard().getEntrance().addStudent(Color.YELLOW.ordinal());
          player.getPlayerBoard().getEntrance().addStudent(Color.GREEN.ordinal());
          player.getPlayerBoard().getEntrance().addStudent(Color.RED.ordinal());
        }
      }
    }

    //FIRST PLAYER TURN
    //STUDENTS
    int beginMotherNaturePosition = mainController.getGame().getMainBoard().getMotherNature()
        .getPosition();

    mainController.elaborateMessage(student1);
    Assert.assertEquals(1,
        Arrays.stream(mainController.getGame().getCurrentPlayer().getPlayerBoard().getDiningRoom()
            .getStudents()).sum());
    mainController.elaborateMessage(student2);
    Assert.assertEquals(2,
        Arrays.stream(mainController.getGame().getCurrentPlayer().getPlayerBoard().getDiningRoom()
            .getStudents()).sum());
    mainController.elaborateMessage(student3);
    Assert.assertEquals(3,
        Arrays.stream(mainController.getGame().getCurrentPlayer().getPlayerBoard().getDiningRoom()
            .getStudents()).sum());

    //MOTHER NATURE
    int oldMotherNaturePosition = mainController.getGame().getMainBoard().getMotherNature()
        .getPosition();
    motherNatureMovement1.setIslandNumber((oldMotherNaturePosition + 3) % 12);
    mainController.elaborateMessage(motherNatureMovement1);
    Assert.assertEquals((oldMotherNaturePosition + 3) % 12,
        mainController.getGame().getMainBoard().getMotherNature().getPosition());

    //CLOUD TILE
    mainController.elaborateMessage(takeCloudTile1);
    Assert.assertEquals(10,
        Arrays.stream(mainController.getGame().getCurrentPlayer().getPlayerBoard().getEntrance()
            .getStudents()).sum());

    //SECOND PLAYER TURN
    //STUDENTS
    mainController.elaborateMessage(student4);
    if (beginMotherNaturePosition == selectedIsland ||
        (beginMotherNaturePosition + Constants.getInitialNumIslands() / 2) % Constants
            .getInitialNumIslands() == selectedIsland) {
      Assert.assertEquals(1,
          Arrays.stream(mainController.getGame().getMainBoard().getIslands().get(selectedIsland)
              .getStudents())
              .sum());
    }
    else{
    Assert.assertEquals(2,
        Arrays.stream(
            mainController.getGame().getMainBoard().getIslands().get(selectedIsland).getStudents())
            .sum());
    }
    mainController.elaborateMessage(student5);
    Assert.assertEquals(1,
        Arrays.stream(mainController.getGame().getCurrentPlayer().getPlayerBoard().getDiningRoom()
            .getStudents()).sum());
    mainController.elaborateMessage(student6);
    Assert.assertEquals(2,
        Arrays.stream(mainController.getGame().getCurrentPlayer().getPlayerBoard().getDiningRoom()
            .getStudents()).sum());

    //MOTHER NATURE
    oldMotherNaturePosition = mainController.getGame().getMainBoard().getMotherNature()
        .getPosition();
    motherNatureMovement2.setIslandNumber((oldMotherNaturePosition + 2) % 12);
    mainController.elaborateMessage(motherNatureMovement2);
    Assert.assertEquals((oldMotherNaturePosition + 2) % 12,
        mainController.getGame().getMainBoard().getMotherNature().getPosition());

    //TAKE CLOUD
    mainController.elaborateMessage(takeCloudTile2);
    Assert.assertEquals(10,
        Arrays.stream(mainController.getGame().getCurrentPlayer().getPlayerBoard().getEntrance()
            .getStudents()).sum());
  }

}
