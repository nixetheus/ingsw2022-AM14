package it.polimi.ingsw.messages;

import it.polimi.ingsw.helpers.MessageMain;
import it.polimi.ingsw.helpers.MessageSecondary;

public class LoginMessage extends Message {

  private int numberOfPlayer;
  private boolean gameMode;
  private String nickName;

  public LoginMessage(MessageSecondary messageSecondary) {
    this.messageMain = MessageMain.LOGIN;
    this.messageSecondary = messageSecondary;
  }

  public int getNumberOfPlayer() {
    return numberOfPlayer;
  }

  public void setNumberOfPlayer(int numberOfPlayer) {
    this.numberOfPlayer = numberOfPlayer;
  }

  public boolean isGameMode() {
    return gameMode;
  }

  public void setGameMode(boolean gameMode) {
    this.gameMode = gameMode;
  }


  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }


}
