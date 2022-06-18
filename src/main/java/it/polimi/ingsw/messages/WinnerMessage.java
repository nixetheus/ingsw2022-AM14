package it.polimi.ingsw.messages;

import it.polimi.ingsw.helpers.MessageMain;
import it.polimi.ingsw.helpers.MessageSecondary;

public class WinnerMessage extends Message{

  private int winnerId;

  public WinnerMessage(MessageSecondary messageSecondary) {
    this.messageMain = MessageMain.INFO;
    this.messageSecondary = messageSecondary;
  }

  public int getWinnerId() {
    return winnerId;
  }

  public void setWinnerId(int winnerId) {
    this.winnerId = winnerId;
  }
}
