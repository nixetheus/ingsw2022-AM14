package it.polimi.ingsw.messages;

import it.polimi.ingsw.helpers.MessageMain;
import it.polimi.ingsw.helpers.MessageSecondary;
import java.util.Vector;

public class ClientResponse extends Message {

  private String response;
  private Vector<Integer> playerOrderId;

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

  public Vector<Integer> getPlayerOrderId() {
    return playerOrderId;
  }

  public void setPlayerOrderId(Vector<Integer> playerOrderId) {
    this.playerOrderId = playerOrderId;
  }
}