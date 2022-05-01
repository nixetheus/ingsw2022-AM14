package it.polimi.ingsw.messages;

import it.polimi.ingsw.helpers.MessageMain;
import it.polimi.ingsw.helpers.MessageSecondary;

public class InfoRequestMessage extends Message {

  private int objectId;

  public InfoRequestMessage(MessageSecondary messageSecondary) {
    this.messageMain = MessageMain.INFO;
    this.messageSecondary = messageSecondary;
  }

  public int getObjectId() {
    return objectId;
  }

  public void setObjectId(int objectId) {
    this.objectId = objectId;
  }
}