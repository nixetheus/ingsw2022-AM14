package it.polimi.ingsw.model.controller;

import it.polimi.ingsw.controller.InfoController;
import it.polimi.ingsw.helpers.Color;
import it.polimi.ingsw.helpers.MessageSecondary;
import it.polimi.ingsw.helpers.Towers;
import it.polimi.ingsw.messages.ClientResponse;
import it.polimi.ingsw.messages.InfoRequestMessage;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.CloudTile;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.board.Island;
import it.polimi.ingsw.model.characters.CharacterCard;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerBoard;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;
import org.junit.Assert;
import org.junit.Test;

public class InfoControllerTest {

  InfoController infoController = new InfoController();

  /**
   *
   */
  @Test
  public void infoHELP() throws IOException {

    Vector<Team> teams = new Vector<>();
    Team team1 = new Team(0, Towers.BLACK);
    Team team2 = new Team(1, Towers.WHITE);

    Player player1 = new Player(0, "test1"); team1.addPlayer(player1);
    Player player2 = new Player(0, "test2"); team1.addPlayer(player2);
    teams.add(team1); teams.add(team2);

    Game testGame = new Game();
    testGame.setTeams(teams);
    testGame.setExpert(false);

    InfoRequestMessage infoRequestMessage = new InfoRequestMessage(MessageSecondary.INFO_HELP);

    Vector<Message> responses = infoController.elaborateMessage(infoRequestMessage, testGame);
    ClientResponse response = (ClientResponse) responses.elementAt(0);
    Assert.assertEquals(response.getResponse(), "TODO");
  }

  /**
   *
   */
  @Test
  public void infoMotherNatureTest() throws IOException {

    Vector<Team> teams = new Vector<>();
    int moves = (int) (Math.random() * 100);
    Team team1 = new Team(0, Towers.BLACK);
    Team team2 = new Team(1, Towers.WHITE);

    Player player1 = new Player(0, "test1"); team1.addPlayer(player1);
    Player player2 = new Player(0, "test2"); team1.addPlayer(player2);
    teams.add(team1); teams.add(team2);

    Game testGame = new Game();
    testGame.setTeams(teams);
    testGame.setExpert(false);

    testGame.moveNature(moves);
    InfoRequestMessage infoRequestMessage = new InfoRequestMessage(MessageSecondary.INFO_MN);

    Vector<Message> responses = infoController.elaborateMessage(infoRequestMessage, testGame);
    ClientResponse response = (ClientResponse) responses.elementAt(0);
    Assert.assertEquals(response.getResponse(),
            "Mother Nature is on island number " + testGame.getMainBoard().getMotherNature()
            .getPosition() + "\n"
    );
  }

  /**
   *
   */
  @Test
  public void infoIslandTest() throws IOException {

    Vector<Team> teams = new Vector<>();
    Team team1 = new Team(0, Towers.BLACK);
    Team team2 = new Team(1, Towers.WHITE);

    Player player1 = new Player(0, "test1"); team1.addPlayer(player1);
    Player player2 = new Player(0, "test2"); team1.addPlayer(player2);
    teams.add(team1); teams.add(team2);

    Game testGame = new Game();
    testGame.setTeams(teams);
    testGame.setExpert(false);

    InfoRequestMessage infoRequestMessage = new InfoRequestMessage(MessageSecondary.INFO_ISLAND);

    int testId = testGame.getMainBoard().getMotherNature().getPosition();
    Island testIsland = testGame.getMainBoard().getIslands().elementAt(testId);
    infoRequestMessage.setObjectId(testId);

    StringBuilder testString = new StringBuilder("Island number " + (testId + 1) + " has:\n");
    testString.append(testIsland.getNumberOfTowers()).append(" of team: ");
    testString.append((testIsland.getOwnerId() == -1) ? "none" : testIsland.getOwnerId());
    testString.append("\n");

    // Test string: add students
    for (Color color : Color.values()) {
      testString.append(testIsland.getStudents()[color.ordinal()]).append(" ")
          .append(color).append(" students;\n");
    }
    Vector<Message> responses = infoController.elaborateMessage(infoRequestMessage, testGame);
    ClientResponse response = (ClientResponse) responses.elementAt(0);
    Assert.assertEquals(response.getResponse(), testString.toString());

  }

  /**
   *
   */
  @Test
  public void infoPlayerTest() throws IOException {
    Vector<Team> teams = new Vector<>();
    Team team1 = new Team(0, Towers.BLACK);
    Team team2 = new Team(1, Towers.WHITE);

    Player player1 = new Player(0, "test1"); team1.addPlayer(player1);
    Player player2 = new Player(1, "test2"); team1.addPlayer(player2);
    teams.add(team1); teams.add(team2);

    Game testGame = new Game();
    testGame.setTeams(teams);
    testGame.setExpert(false);

    InfoRequestMessage infoRequestMessage = new InfoRequestMessage(MessageSecondary.INFO_PLAYER);

    StringBuilder returnString = new StringBuilder("PLAYER " + player1.getPlayerNickname() + ":\n");
    returnString.append("COINS: ").append(player1.getCoins()).append("\n");

    returnString.append("CURRENT ASSISTANT: ");
    returnString.append((player1.getAssistant() == null) ? "NONE" : player1.getAssistant().toString());
    returnString.append("\n");

    PlayerBoard infoPB = player1.getPlayerBoard();
    returnString.append("ENTRANCE: ");

    for (Color color : Color.values()) {
      returnString.append(infoPB.getEntrance().getStudents()[color.ordinal()]).append(" ")
          .append(color).append(" students;\n");
    }

    returnString.append("DINING ROOM: ");

    for (Color color : Color.values()) {
      returnString.append(infoPB.getDiningRoom().getStudents()[color.ordinal()]).append(" ")
          .append(color).append(" students;\n");
    }

    infoRequestMessage.setPlayerId(player1.getPlayerId());
    Vector<Message> responses = infoController.elaborateMessage(infoRequestMessage, testGame);
    ClientResponse response = (ClientResponse) responses.elementAt(0);
    Assert.assertEquals(response.getResponse(), returnString.toString());
  }

  /**
   *
   */
  @Test
  public void infoCharactersTest() throws IOException {
    Vector<Team> teams = new Vector<>();
    int moves = (int) (Math.random() * 100);
    Team team1 = new Team(0, Towers.BLACK);
    Team team2 = new Team(1, Towers.WHITE);

    Player player1 = new Player(0, "test1"); team1.addPlayer(player1);
    Player player2 = new Player(0, "test2"); team1.addPlayer(player2);
    teams.add(team1); teams.add(team2);

    Game testGame = new Game();
    testGame.setTeams(teams);
    testGame.setExpert(true);

    testGame.moveNature(moves);
    InfoRequestMessage infoRequestMessage = new InfoRequestMessage(MessageSecondary.INFO_CHARACTER);
    infoRequestMessage.setObjectId(0);

    int characterId = 0;
    CharacterCard infoCard = testGame.getPurchasableCharacter().elementAt(characterId);

    Vector<Message> responses = infoController.elaborateMessage(infoRequestMessage, testGame);
    ClientResponse response = (ClientResponse) responses.elementAt(0);
    Assert.assertEquals(response.getResponse(),
        "Character number " + characterId + " costs " + infoCard.getCost() + " coins");

    // ERROR
    infoRequestMessage.setObjectId(100);
    responses = infoController.elaborateMessage(infoRequestMessage, testGame);
    response = (ClientResponse) responses.elementAt(0);
    Assert.assertEquals(response.getResponse(), "Error, the character does not exists");
  }

  /**
   *
   */
  @Test
  public void infoAssistantsTest() throws IOException {
    Vector<Team> teams = new Vector<>();
    Team team1 = new Team(0, Towers.BLACK);
    Team team2 = new Team(1, Towers.WHITE);

    Player player1 = new Player(0, "test1"); team1.addPlayer(player1);
    Player player2 = new Player(0, "test2"); team1.addPlayer(player2);
    teams.add(team1); teams.add(team2);

    Game testGame = new Game();
    testGame.setTeams(teams);
    testGame.setExpert(false);

    InfoRequestMessage infoRequestMessage = new InfoRequestMessage(MessageSecondary.INFO_ASSISTANTS);
    infoRequestMessage.setPlayerId(player1.getPlayerId());

    StringBuilder returnString = new StringBuilder("You can play assistants n:");
    for (Assistant assistant : player1.getPlayableAssistant()) {
      returnString.append(" ").append(assistant.getAssistantId());
    }

    Vector<Message> responses = infoController.elaborateMessage(infoRequestMessage, testGame);
    ClientResponse response = (ClientResponse) responses.elementAt(0);
    Assert.assertEquals(response.getResponse(), returnString.toString());

    // ERROR
    infoRequestMessage.setObjectId(100);
    responses = infoController.elaborateMessage(infoRequestMessage, testGame);
    response = (ClientResponse) responses.elementAt(0);
    Assert.assertEquals(response.getResponse(), "Error, the player does not exists");
  }

  /**
   *
   */
  @Test
  public void infoCloudTileTest() throws IOException {
    Vector<Team> teams = new Vector<>();
    Team team1 = new Team(0, Towers.BLACK);
    Team team2 = new Team(1, Towers.WHITE);

    Player player1 = new Player(0, "test1"); team1.addPlayer(player1);
    Player player2 = new Player(0, "test2"); team1.addPlayer(player2);
    teams.add(team1); teams.add(team2);

    Game testGame = new Game();
    testGame.setTeams(teams);
    testGame.setExpert(false);

    int cloudId = 0;
    CloudTile cloudTile = testGame.getCloudTiles().elementAt(cloudId);

    InfoRequestMessage infoRequestMessage = new InfoRequestMessage(MessageSecondary.INFO_CLOUD_TILE);
    infoRequestMessage.setObjectId(cloudId);

    StringBuilder returnString = new StringBuilder("Cloud tile number " + (cloudId + 1) + " has:\n");

    for (Color color : Color.values()) {
      returnString
          .append(cloudTile.getStudents()[color.ordinal()]).append(" ")
          .append(color).append(" students;\n");
    }

    Vector<Message> responses = infoController.elaborateMessage(infoRequestMessage, testGame);
    ClientResponse response = (ClientResponse) responses.elementAt(0);
    Assert.assertEquals(response.getResponse(), returnString.toString());

    // ERROR
    infoRequestMessage.setObjectId(100);
    responses = infoController.elaborateMessage(infoRequestMessage, testGame);
    response = (ClientResponse) responses.elementAt(0);
    Assert.assertEquals(response.getResponse(), "Error, the cloud tile does not exists");
  }

}
