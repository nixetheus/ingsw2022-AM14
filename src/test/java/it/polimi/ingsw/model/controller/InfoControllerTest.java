package it.polimi.ingsw.model.controller;

import it.polimi.ingsw.controller.InfoController;
import it.polimi.ingsw.helpers.Color;
import it.polimi.ingsw.helpers.MessageSecondary;
import it.polimi.ingsw.helpers.Towers;
import it.polimi.ingsw.messages.InfoRequestMessage;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.board.Island;
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
  public void infoMotherNatureTest() throws IOException {

    Vector<Team> teams = new Vector<>();
    int moves = (int) (Math.random() * 100);
    teams.add(new Team(0, Towers.BLACK));
    teams.add(new Team(1, Towers.WHITE));
    /*Game testGame = new Game(false, teams);

    testGame.moveNature(moves);
    InfoRequestMessage infoRequestMessage = new InfoRequestMessage(MessageSecondary.INFO_MN);

    Assert.assertEquals(infoController.elaborateMessage(infoRequestMessage, testGame).getResponse(),
        "Mother Nature is on island number " + testGame.getMainBoard().getMotherNature()
            .getPosition() + "\n");*/
  }

  /**
   *
   */
  @Test
  public void infoIslandTest() throws IOException {

    int testId = 0;
    Vector<Team> teams = new Vector<>();
    teams.add(new Team(0, Towers.BLACK));
    teams.add(new Team(1, Towers.WHITE));
    /*Game testGame = new Game(false, teams);
    InfoRequestMessage infoRequestMessage = new InfoRequestMessage(MessageSecondary.INFO_ISLAND);

    testId = testGame.getMainBoard().getMotherNature().getPosition();
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

    Assert.assertEquals(infoController.elaborateMessage(infoRequestMessage, testGame).getResponse(),
        testString.toString());*/

  }

  /**
   *
   */
  @Test
  public void infoPlayerTest() {

  }

  /**
   *
   */
  @Test
  public void infoCharactersTest() {

  }

  /**
   *
   */
  @Test
  public void infoAssistantsTest() {

  }

  /**
   *
   */
  @Test
  public void infoCloudTileTest() {

  }

}
