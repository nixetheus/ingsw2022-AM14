package it.polimi.ingsw.model.controller;

import it.polimi.ingsw.controller.MoveController;
import it.polimi.ingsw.controller.PlayController;
import it.polimi.ingsw.helpers.Color;
import it.polimi.ingsw.helpers.MessageSecondary;
import it.polimi.ingsw.helpers.Towers;
import it.polimi.ingsw.messages.MoveMessage;
import it.polimi.ingsw.messages.MoveMessageResponse;
import it.polimi.ingsw.messages.PlayMessage;
import it.polimi.ingsw.messages.PlayMessageResponse;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.player.Player;
import java.io.IOException;
import java.util.Vector;
import org.junit.Assert;
import org.junit.Test;

public class MoveControllerTest {

  @Test
  public void moveMotherNatureTest() throws IOException {

    Vector<Team> teams = new Vector<>();
    Team team1 = new Team(0, Towers.BLACK);
    Team team2 = new Team(1, Towers.WHITE);

    Player player1 = new Player(0, "test1");
    player1.playAssistant(9);
    team1.addPlayer(player1);
    Player player2 = new Player(0, "test2"); team1.addPlayer(player2);
    teams.add(team1); teams.add(team2);

    Game testGame = new Game();
    testGame.setTeams(teams);
    testGame.setExpert(false);

    MoveController moveControllerTest = new MoveController();
    MoveMessage moveMessageTest = new MoveMessage(MessageSecondary.MOVE_MN);

    int moves = 4;
    int islandId = (testGame.getMainBoard().getMotherNature().getPosition()
        + moves) % testGame.getMainBoard().getIslands().size();
    moveMessageTest.setIslandNumber(islandId);

    MoveMessageResponse response =
        (MoveMessageResponse) moveControllerTest.elaborateMessage(moveMessageTest, testGame);
    Assert.assertEquals(response.getMessageSecondary(), MessageSecondary.MOVE_MN);
    Assert.assertEquals(response.getPlayerId(), player1.getPlayerId());
    Assert.assertEquals(testGame.getMainBoard().getMotherNature().getPosition(), islandId);
  }

  @Test
  public void moveMotherNatureNegativeTest() throws IOException {

    Vector<Team> teams = new Vector<>();
    Team team1 = new Team(0, Towers.BLACK);
    Team team2 = new Team(1, Towers.WHITE);

    Player player1 = new Player(0, "test1");
    player1.playAssistant((int) (Math.random() * 10));
    team1.addPlayer(player1);
    Player player2 = new Player(0, "test2"); team1.addPlayer(player2);
    teams.add(team1); teams.add(team2);

    Game testGame = new Game();
    testGame.setTeams(teams);
    testGame.setExpert(false);

    MoveController moveControllerTest = new MoveController();
    MoveMessage moveMessageTest = new MoveMessage(MessageSecondary.MOVE_MN);

    int moves = player1.getAssistant().getMoves() + 1;
    int islandId = (testGame.getMainBoard().getMotherNature().getPosition()
        + moves) % testGame.getMainBoard().getIslands().size();
    moveMessageTest.setIslandNumber(islandId);

    MoveMessageResponse response =
        (MoveMessageResponse) moveControllerTest.elaborateMessage(moveMessageTest, testGame);
    Assert.assertNull(response);
  }

  @Test
  public void moveCloudTileTest() throws IOException {

    Vector<Team> teams = new Vector<>();
    Team team1 = new Team(0, Towers.BLACK);
    Team team2 = new Team(1, Towers.WHITE);

    Player player1 = new Player(0, "test1"); team1.addPlayer(player1);
    Player player2 = new Player(0, "test2"); team1.addPlayer(player2);
    teams.add(team1); teams.add(team2);

    Game testGame = new Game();
    testGame.setTeams(teams);
    testGame.setExpert(false);

    MoveController moveControllerTest = new MoveController();
    MoveMessage moveMessageTest = new MoveMessage(MessageSecondary.CLOUD_TILE);

    int cloudId = (int) (Math.random() * testGame.getCloudTiles().size());
    moveMessageTest.setCloudTileNumber(cloudId);

    MoveMessageResponse response =
        (MoveMessageResponse) moveControllerTest.elaborateMessage(moveMessageTest, testGame);
    Assert.assertEquals(response.getMessageSecondary(), MessageSecondary.CLOUD_TILE);
    Assert.assertEquals(response.getPlayerId(), player1.getPlayerId());
    Assert.assertEquals(response.getCloudTileNumber(), cloudId);
  }

  @Test
  public void moveEntranceDiningRoomTest() throws IOException {

    Vector<Team> teams = new Vector<>();
    Team team1 = new Team(0, Towers.BLACK);
    Team team2 = new Team(1, Towers.WHITE);

    Player player1 = new Player(0, "test1"); team1.addPlayer(player1);
    Player player2 = new Player(0, "test2"); team1.addPlayer(player2);
    teams.add(team1); teams.add(team2);

    Game testGame = new Game();
    testGame.setTeams(teams);
    testGame.setExpert(false);

    MoveController moveControllerTest = new MoveController();
    MoveMessage moveMessageTest = new MoveMessage(MessageSecondary.ENTRANCE);

    int index = 0;
    while (testGame.getCurrentPlayer().getPlayerBoard().getEntrance().getStudents()[index] <= 0)
      index++;

    moveMessageTest.setStudentColor(index);
    moveMessageTest.setPlace(0);
    moveMessageTest.setPlayerId(0);

    MoveMessageResponse response =
        (MoveMessageResponse) moveControllerTest.elaborateMessage(moveMessageTest, testGame);
    Assert.assertEquals(response.getMessageSecondary(), MessageSecondary.MOVE_STUDENT_ENTRANCE);
  }

  @Test
  public void moveEntranceIslandTest() throws IOException {

    Vector<Team> teams = new Vector<>();
    Team team1 = new Team(0, Towers.BLACK);
    Team team2 = new Team(1, Towers.WHITE);

    Player player1 = new Player(0, "test1"); team1.addPlayer(player1);
    Player player2 = new Player(0, "test2"); team1.addPlayer(player2);
    teams.add(team1); teams.add(team2);

    Game testGame = new Game();
    testGame.setTeams(teams);
    testGame.setExpert(false);

    MoveController moveControllerTest = new MoveController();
    MoveMessage moveMessageTest = new MoveMessage(MessageSecondary.ENTRANCE);

    int index = 0;
    while (testGame.getCurrentPlayer().getPlayerBoard().getEntrance().getStudents()[index] <= 0)
      index++;

    moveMessageTest.setStudentColor(index);
    moveMessageTest.setPlace(1);

    MoveMessageResponse response =
        (MoveMessageResponse) moveControllerTest.elaborateMessage(moveMessageTest, testGame);
    Assert.assertEquals(response.getMessageSecondary(), MessageSecondary.MOVE_STUDENT_ENTRANCE);
  }

}
