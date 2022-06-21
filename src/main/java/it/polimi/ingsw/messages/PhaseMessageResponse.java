package it.polimi.ingsw.messages;

import it.polimi.ingsw.helpers.MessageMain;
import it.polimi.ingsw.helpers.MessageSecondary;

/**
 * PhaseMessageResponse class used as response about what to do next
 */
public class PhaseMessageResponse extends Message{

  private String whatTodo;

  /**
   * Constructor method for PhaseMessageResponse class
   * @param messageSecondary  The messageSecondary to be set
   */
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
