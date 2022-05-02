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
   * It controls if the parameters from the user are correct
   *
   * @param msg The message to check
   * @return True if everything went ok
   */
  public Boolean checkGameParameters(LoginMessage msg) {
    return msg.getNumberOfPlayer() > 1 && msg.getNumberOfPlayer() <= 4;
  }

  /**
   * This method creates a player
   *
   * @param msg The message in which i can find the nickname
   * @return The created player
   * @throws FileNotFoundException if the json file will not be found
   */
  public Player createPlayer(LoginMessage msg) throws FileNotFoundException {
    return new Player(msg.getPlayerId(), msg.getNickName());
  }

}
