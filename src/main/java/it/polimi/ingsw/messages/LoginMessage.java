package it.polimi.ingsw.messages;

import it.polimi.ingsw.helpers.MessageMain;
import it.polimi.ingsw.helpers.MessageSecondary;

/**
 * LoginMessage class used for initial login
 */
public class LoginMessage extends Message {

  private String nickName;
  private int numberOfPlayer;
  private boolean gameExpert;


  /**
   * Constructor method for LoginMessage class
   *
   * @param messageSecondary The messageSecondary to be set
   */
  public LoginMessage(MessageSecondary messageSecondary) {
    this.messageMain = MessageMain.LOGIN;
    this.messageSecondary = messageSecondary;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public int getNumberOfPlayer() {
    return numberOfPlayer;
  }

  public void setNumberOfPlayer(int numberOfPlayer) {
    this.numberOfPlayer = numberOfPlayer;
  }

  public boolean isGameExpert() {
    return gameExpert;
  }

  public void setGameExpert(boolean gameMode) {
    this.gameExpert = gameMode;
  }


}
