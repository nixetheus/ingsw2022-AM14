package it.polimi.ingsw.model.controller;

import it.polimi.ingsw.controller.TurnManager;
import it.polimi.ingsw.helpers.MessageMain;
import it.polimi.ingsw.helpers.MessageSecondary;
import org.junit.Assert;
import org.junit.Test;

/**
 * TurnControllerTest tests the TurnController methods
 */
public class TurnControllerTest {

  @Test
  public void testTurnManager() {

    TurnManager test = new TurnManager();

    // BEGINNING
    Assert.assertEquals(test.getCurrentState(), 0);
    Assert.assertEquals(test.getMainGamePhase(), MessageMain.LOGIN);
    Assert.assertEquals(test.getSecondaryPhase(), MessageSecondary.PLAYER_PARAMS);
    Assert.assertEquals(test.getCurrentNumberOfUsers(), 0);
    Assert.assertEquals(test.getCurrentNumberOfPlayedAssistants(), 0);
    Assert.assertEquals(test.getCurrentNumberOfStudentsFromEntrance(), 0);
    Assert.assertEquals(test.getCurrentNumberOfUsersPlayedActionPhase(), 0);

    // FIRST PLAYER
    test.updateCounters();
    test.changeState();

    Assert.assertEquals(test.getCurrentState(), 1);
    Assert.assertEquals(test.getCurrentNumberOfUsers(), 1);
    Assert.assertEquals(test.getSecondaryPhase(), MessageSecondary.GAME_PARAMS);

    // GAME PARAMS
    test.setNumberOfUsers(2);

    test.updateCounters();
    test.changeState();

    Assert.assertEquals(test.getCurrentState(), 2);
    Assert.assertEquals(test.getMainGamePhase(), MessageMain.LOGIN);
    Assert.assertEquals(test.getSecondaryPhase(), MessageSecondary.PLAYER_PARAMS);

    // SECOND PLAYER
    test.updateCounters();
    test.changeState();

    Assert.assertEquals(test.getCurrentState(), 3);
    Assert.assertEquals(test.getMainGamePhase(), MessageMain.PLAY);
    Assert.assertEquals(test.getSecondaryPhase(), MessageSecondary.ASSISTANT);

    // FIRST AND SECOND PLAYER ASSISTANTS
    test.updateCounters();

    Assert.assertEquals(test.getCurrentNumberOfPlayedAssistants(), 1);

    test.updateCounters();
    test.changeState();

    Assert.assertEquals(test.getCurrentState(), 4);
    Assert.assertEquals(test.getMainGamePhase(), MessageMain.MOVE);
    Assert.assertEquals(test.getSecondaryPhase(), MessageSecondary.ENTRANCE);
    Assert.assertEquals(test.getCurrentNumberOfPlayedAssistants(), 2);

    // FIRST PLAYER TURN

    // STUDENT ENTRANCE
    test.setNumberStudentsFromEntrance(3);
    test.updateCounters();
    Assert.assertEquals(test.getCurrentNumberOfStudentsFromEntrance(), 1);
    test.updateCounters();
    Assert.assertEquals(test.getCurrentNumberOfStudentsFromEntrance(), 2);
    test.updateCounters();
    Assert.assertEquals(test.getCurrentNumberOfStudentsFromEntrance(), 3);
    test.changeState();

    Assert.assertEquals(test.getCurrentState(), 5);
    Assert.assertEquals(test.getMainGamePhase(), MessageMain.MOVE);
    Assert.assertEquals(test.getSecondaryPhase(), MessageSecondary.MOVE_MN);

    // MOTHER NATURE
    test.updateCounters();
    test.changeState();

    Assert.assertEquals(test.getCurrentState(), 6);
    Assert.assertEquals(test.getMainGamePhase(), MessageMain.MOVE);
    Assert.assertEquals(test.getSecondaryPhase(), MessageSecondary.CLOUD_TILE);

    // CLOUD TILE
    test.updateCounters();
    test.changeState();

    Assert.assertEquals(test.getCurrentState(), 4);
    Assert.assertEquals(test.getMainGamePhase(), MessageMain.MOVE);
    Assert.assertEquals(test.getSecondaryPhase(), MessageSecondary.ENTRANCE);
    Assert.assertEquals(test.getCurrentNumberOfUsersPlayedActionPhase(), 1);

    // SECOND PLAYER TURN

    // STUDENT ENTRANCE
    test.updateCounters();
    test.updateCounters();
    test.updateCounters();
    test.changeState();

    Assert.assertEquals(test.getCurrentState(), 5);
    Assert.assertEquals(test.getMainGamePhase(), MessageMain.MOVE);
    Assert.assertEquals(test.getSecondaryPhase(), MessageSecondary.MOVE_MN);

    // MOTHER NATURE
    test.updateCounters();
    test.changeState();

    Assert.assertEquals(test.getCurrentState(), 6);
    Assert.assertEquals(test.getMainGamePhase(), MessageMain.MOVE);
    Assert.assertEquals(test.getSecondaryPhase(), MessageSecondary.CLOUD_TILE);

    // CLOUD TILE
    test.updateCounters();
    Assert.assertEquals(test.getCurrentNumberOfUsersPlayedActionPhase(), 2);
    test.changeState();

    Assert.assertEquals(test.getCurrentState(), 3);
    Assert.assertEquals(test.getMainGamePhase(), MessageMain.PLAY);
    Assert.assertEquals(test.getSecondaryPhase(), MessageSecondary.ASSISTANT);
    Assert.assertEquals(test.getCurrentNumberOfUsersPlayedActionPhase(), 0);

  }

}
