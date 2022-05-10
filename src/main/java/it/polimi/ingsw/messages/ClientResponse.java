package it.polimi.ingsw.messages;

import it.polimi.ingsw.helpers.MessageMain;
import it.polimi.ingsw.helpers.MessageSecondary;

public class ClientResponse extends Message {

  private String response;

  public ClientResponse(MessageSecondary messageSecondary) {
    this.messageMain = MessageMain.INFO;
    this.messageSecondary = messageSecondary;
  }

  public String getResponse() {
    return response;
  }

  public void setResponse(String response) {
    this.response = response;
  }
}