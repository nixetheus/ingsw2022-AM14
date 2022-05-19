package it.polimi.ingsw.messages;

import it.polimi.ingsw.helpers.MessageMain;
import it.polimi.ingsw.helpers.MessageSecondary;

public class PhaseMessageResponse extends Message{

  private String whatTodo;

  public PhaseMessageResponse(MessageSecondary messageSecondary) {
    this.messageMain = MessageMain.PHASE;
    this.messageSecondary = messageSecondary;
  }

  public String getWhatTodo() {
    return whatTodo;
  }

  public void setWhatTodo(String whatTodo) {
    this.whatTodo = whatTodo;
  }
}
