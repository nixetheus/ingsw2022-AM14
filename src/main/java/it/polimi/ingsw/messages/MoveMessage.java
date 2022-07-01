package it.polimi.ingsw.messages;

import it.polimi.ingsw.helpers.MessageMain;
import it.polimi.ingsw.helpers.MessageSecondary;

/**
 * MoveMessage used during a move from client to server
 */
public class MoveMessage extends Message {

  private int place;
  private int studentColor;
  private int islandNumber;
  private int cloudTileNumber;
  private int studentNumber = -1;

  /**
   * Constructor method for MoveMessage class
   *
   * @param messageSecondary The messageSecondary to be set
   */
  public MoveMessage(MessageSecondary messageSecondary) {
    this.messageMain = MessageMain.MOVE;
    this.messageSecondary = messageSecondary;
  }

  public int getPlace() {
    return place;
  }

  public void setPlace(int place) {
    this.place = place;
  }

  public int getStudentColor() {
    return studentColor;
  }

  public void setStudentColor(int studentColor) {
    this.studentColor = studentColor;
  }

  public int getIslandNumber() {
    return islandNumber;
  }

  public void setIslandNumber(int islandNumber) {
    this.islandNumber = islandNumber;
  }

  public int getCloudTileNumber() {
    return cloudTileNumber;
  }

  public void setCloudTileNumber(int cloudTileNumber) {
    this.cloudTileNumber = cloudTileNumber;
  }

  public int getStudentNumber() {
    return studentNumber;
  }

  public void setStudentNumber(int studentNumber) {
    this.studentNumber = studentNumber;
  }
}
