package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.LoginMessage;
import it.polimi.ingsw.model.player.Player;
import java.io.FileNotFoundException;

/**
 * LoginController control any kind of error incurring in the log in phase
 */
public class LoginController {

  /**
   * Constructor method for the LoginController class
   */
  public LoginController() {

  }

  /**
   *
   */
  public Boolean checkGameParameters(LoginMessage msg) {
    return msg.getNumberOfPlayer() > 1 && msg.getNumberOfPlayer() < 4;
  }

  public Player createPlayer(LoginMessage msg) throws FileNotFoundException {
    //TODO check if is a non duplicate name
    return null;
  }

}
