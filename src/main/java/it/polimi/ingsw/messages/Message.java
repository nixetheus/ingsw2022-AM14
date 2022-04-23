package it.polimi.ingsw.messages;

import it.polimi.ingsw.helpers.MessageMain;
import it.polimi.ingsw.helpers.MessageSecondary;
import java.io.Serializable;

public abstract class Message implements Serializable {

  protected int playerId;
  protected MessageMain messageMain;
  protected MessageSecondary messageSecondary;

  public int getPlayerId() {
    return playerId;
  }

  public void setPlayerId(int playerId) {
    this.playerId = playerId;
  }

  public MessageMain getMessageMain() {
    return messageMain;
  }

  public void setMessageMain(MessageMain messageMain) {
    this.messageMain = messageMain;
  }

  public MessageSecondary getMessageSecondary() {
    return messageSecondary;
  }

  public void setMessageSecondary(MessageSecondary messageSecondary) {
    this.messageSecondary = messageSecondary;
  }
}
