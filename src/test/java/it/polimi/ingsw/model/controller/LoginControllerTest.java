package it.polimi.ingsw.model.controller;

import it.polimi.ingsw.controller.LoginController;
import it.polimi.ingsw.helpers.MessageSecondary;
import it.polimi.ingsw.messages.LoginMessage;
import it.polimi.ingsw.model.player.Player;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * LoginControllerTest tests the LoginController methods
 */
public class LoginControllerTest {

  LoginMessage msg;
  LoginController loginController = new LoginController();
  Player testPlayer;


  /**
   * Set up method
   */
  @Before
  public void setUp() throws IOException {
    this.msg = new LoginMessage(MessageSecondary.GAME_PARAMS);
    this.testPlayer = new Player(0, "ale");

  }

  /**
   * Method to check if checkGameParameters works properly in a non valid number of players and in a
   * valid one
   */
  @Test
  public void testCheckGameParameters() {
    msg.setNumberOfPlayer(4);
    Assert.assertTrue(loginController.checkGameParameters(msg));
    msg.setNumberOfPlayer(0);
    Assert.assertFalse(loginController.checkGameParameters(msg));
  }

  /**
   * Method to check if createPlayer works
   */
  @Test
  public void testCreatePlayer() throws IOException {
    msg.setNickName("ale");
    msg.setPlayerId(0);
    Assert.assertEquals(loginController.createPlayer(msg,0).getPlayerNickname(),msg.getNickName());
    Assert.assertEquals(loginController.createPlayer(msg,0).getPlayerId(),msg.getPlayerId());

  }
}

