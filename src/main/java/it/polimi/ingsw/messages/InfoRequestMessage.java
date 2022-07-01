package it.polimi.ingsw.messages;

import it.polimi.ingsw.helpers.MessageMain;
import it.polimi.ingsw.helpers.MessageSecondary;

/**
 * InfoRequestMessage used to require information
 */
public class InfoRequestMessage extends Message {

  private int objectId;

  /**
   * Constructor method for InfoRequestMessage
   *
   * @param messageSecondary The messageSecondary to be set
   */
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