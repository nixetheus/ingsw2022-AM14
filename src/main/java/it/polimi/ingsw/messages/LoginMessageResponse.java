package it.polimi.ingsw.messages;

import it.polimi.ingsw.helpers.MessageMain;
import it.polimi.ingsw.helpers.MessageSecondary;

/**
 * LoginMessageResponse used as a response form server to client
 */
public class LoginMessageResponse extends Message {

  String response;
  int numberOfPlayers;

  /**
   * Constructor method for LoginMessageResponse class
   *
   * @param messageSecondary The messageSecondary to be set
   */
  public LoginMessageResponse(MessageSecondary messageSecondary) {
    this.messageMain = MessageMain.LOGIN;
    this.messageSecondary = messageSecondary;
  }


  public String getResponse() {
    return response;
  }

  public void setResponse(String response) {
    this.response = response;
  }

  public int getNumberOfPlayers() {
    return numberOfPlayers;
  }

  public void setNumberOfPlayers(int numberOfPlayers) {
    this.numberOfPlayers = numberOfPlayers;
  }
}
