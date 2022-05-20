package it.polimi.ingsw.messages;

import it.polimi.ingsw.helpers.MessageMain;
import it.polimi.ingsw.helpers.MessageSecondary;

public class LoginMessageResponse extends Message {

  String response;

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
}
