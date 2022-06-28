package it.polimi.ingsw.model.controller;

import it.polimi.ingsw.controller.PlayController;
import it.polimi.ingsw.helpers.Color;
import it.polimi.ingsw.helpers.MessageSecondary;
import it.polimi.ingsw.helpers.Towers;
import it.polimi.ingsw.messages.ClientResponse;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.PlayMessage;
import it.polimi.ingsw.messages.PlayMessageResponse;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.board.StudentsBag;
import it.polimi.ingsw.model.characters.CharacterCard;
import it.polimi.ingsw.model.characters.CharacterStruct;
import it.polimi.ingsw.model.player.Player;
import java.io.IOException;
import java.util.Vector;
import org.junit.Assert;
import org.junit.Test;

public class PlayControllerTest {

  // PLAY ASSISTANT
  @Test
  public void testPlayAssistant() throws IOException {

    Vector<Team> teams = new Vector<>();
    Team team1 = new Team(0, Towers.BLACK);
    Team team2 = new Team(1, Towers.WHITE);

    Player player1 = new Player(0, "test1"); team1.addPlayer(player1);
    Player player2 = new Player(0, "test2"); team1.addPlayer(player2);
    teams.add(team1); teams.add(team2);

    Game testGame = new Game();
    testGame.setTeams(teams);
    testGame.setExpert(false);

    PlayController playControllerTest = new PlayController();
    PlayMessage playMessageTest = new PlayMessage(MessageSecondary.ASSISTANT);

    // CAN PLAY
    int assistantTestId = 9;
    playMessageTest.setPlayerId(player1.getPlayerId());
    playMessageTest.setAssistantId(assistantTestId);

    PlayMessageResponse response =
        (PlayMessageResponse) playControllerTest.elaborateMessage(playMessageTest, testGame);
    Assert.assertEquals(response.getMessageSecondary(), MessageSecondary.ASK_ASSISTANT);
    Assert.assertEquals(response.getPlayerId(), -1);
    Assert.assertEquals(response.getPreviousPlayerId(), player1.getPlayerId());
    Assert.assertEquals(response.getAssistantId(), assistantTestId);

    // CANNOT PLAY
    assistantTestId = 100;
    playMessageTest.setPlayerId(player1.getPlayerId());
    playMessageTest.setAssistantId(assistantTestId);

    response = (PlayMessageResponse) playControllerTest.elaborateMessage(playMessageTest, testGame);
    Assert.assertNull(response);
  }

  // PLAY CHARACTER
  @Test
  public void testPlayCharacter() throws IOException {

    Vector<Team> teams = new Vector<>();
    Team team1 = new Team(0, Towers.BLACK);
    Team team2 = new Team(1, Towers.WHITE);

    Player player1 = new Player(0, "test1");
    player1.playAssistant(0);
    team1.addPlayer(player1);
    Player player2 = new Player(0, "test2"); team1.addPlayer(player2);
    teams.add(team1); teams.add(team2);

    Game testGame = new Game();
    testGame.setTeams(teams);
    testGame.setExpert(true);

    PlayController playControllerTest = new PlayController();
    PlayMessage playMessageTest = new PlayMessage(MessageSecondary.CHARACTER);

    // CAN PLAY
    int characterId = (int) (Math.random() * 2);

    while (testGame.getCurrentPlayer().getCoins()
        != testGame.getPurchasableCharacter().elementAt(characterId).getCost())
      testGame.getCurrentPlayer().addCoin();

    CharacterCard characterCard = testGame.getPurchasableCharacter().elementAt(characterId);

    playMessageTest.setCharacterId(characterId);
    playMessageTest.setPlayerId(player1.getPlayerId());

    playMessageTest.setColor(Color.RED);
    playMessageTest.setNumIsland(5);
    playMessageTest.setMotherNatureMoves(-1);
    playMessageTest.setStudentsCard(characterCard.getStudents());
    playMessageTest.setStudentsEntrance(player1.getPlayerBoard().getEntrance().getStudents());
    playMessageTest.setStudentsDiningRoom(player1.getPlayerBoard().getDiningRoom().getStudents());

    PlayMessageResponse response =
        (PlayMessageResponse) playControllerTest.elaborateMessage(playMessageTest, testGame);
    Assert.assertEquals(response.getMessageSecondary(), MessageSecondary.CHARACTER);
    Assert.assertEquals(response.getCharacterId(), characterId);

     //CANNOT PLAY
    while (testGame.getCurrentPlayer().getCoins()
        >= testGame.getPurchasableCharacter().elementAt(characterId).getCost())
      testGame.getCurrentPlayer().removeCoins(1);

    response = (PlayMessageResponse) playControllerTest.elaborateMessage(playMessageTest, testGame);
    Assert.assertNull(response);
 }
}
