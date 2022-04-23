package it.polimi.ingsw.messages;

import it.polimi.ingsw.helpers.MessageMain;
import it.polimi.ingsw.helpers.MessageSecondary;

public class MoveMessage extends Message {

  private int place;
  private int studentColor;
  private int islandNumber;
  private int cloudTileNumber;

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
}
