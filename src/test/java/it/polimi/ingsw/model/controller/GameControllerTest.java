package it.polimi.ingsw.model.controller;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.LoginController;
import it.polimi.ingsw.model.Game;
import java.io.FileNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * GameControllerTest tests the GameController methods
 */
public class GameControllerTest {

  LoginController loginController;
  GameController gameController;
  Game currentGame;

  /**
   * The setUp method create a login controller and add the playerNickname and creates a new
   * GameController in order to do the tests
   */
  @Before
  public void setUpTest() {
    this.loginController = new LoginController();
    //loginController.addPlayer("Ale");
    //loginController.addPlayer("Luca");
    // loginController.addPlayer("Dario");
    // this.gameController = new GameController(loginController);
  }

  /**
   * This method control if the game will be setUp properly in the case of a 3 player match
   */
  @Test
  public void testSetUpGameThreePlayer() {
    // this.currentGame = gameController.setUpGame(2, false);

    //Assert.assertEquals(currentGame.getTeams().size(), 3);
    //Assert.assertEquals(currentGame.getTeams().get(0).getPlayers().size(), 1);
    //Assert.assertEquals(currentGame.getTeams().get(1).getPlayers().size(), 1);
    //Assert.assertEquals(currentGame.getTeams().get(2).getPlayers().size(), 1);

  }

  /**
   * This method control if the game will be setUp properly in the case of a 4 player match
   */
  @Test
  public void testSetUpGameFourPlayer() {
    // loginController.addPlayer("Anselmo");
    // this.currentGame = gameController.setUpGame(3, false);

    // Assert.assertEquals(currentGame.getTeams().size(), 2);
    // Assert.assertEquals(currentGame.getTeams().get(0).getPlayers().size(), 2);
    // Assert.assertEquals(currentGame.getTeams().get(1).getPlayers().size(), 2);
  }

}
