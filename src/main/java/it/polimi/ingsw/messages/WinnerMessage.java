package it.polimi.ingsw.messages;

import java.util.Vector;
import it.polimi.ingsw.helpers.MessageMain;
import it.polimi.ingsw.helpers.MessageSecondary;


/**
 * WinnerMessage to send who won
 */
public class WinnerMessage extends Message{

  private int winnerId;
  private int numberOfPlayers;
  private Vector<String> playersTeam;

  /**
   * Constructor class for WinnerMessage class
   * @param messageSecondary The messageSecondary to be set
   */
  public WinnerMessage(MessageSecondary messageSecondary) {
    this.messageMain = MessageMain.END;
    this.messageSecondary = messageSecondary;
  }

  public int getWinnerId() {
    return winnerId;
  }

  public void setWinnerId(int winnerId) {
    this.winnerId = winnerId;
  }

  public int getNumberOfPlayers() {
    return numberOfPlayers;
  }

  public void setNumberOfPlayers(int numberOfPlayers) {
    this.numberOfPlayers = numberOfPlayers;
  }

  public Vector<String> getPlayersTeam() {
    return playersTeam;
  }

  public void setPlayersTeam(Vector<String> playersTeam) {
    this.playersTeam = playersTeam;
  }
}
