package it.polimi.ingsw.messages;

import it.polimi.ingsw.helpers.Color;
import it.polimi.ingsw.helpers.MessageMain;
import it.polimi.ingsw.helpers.MessageSecondary;

/**
 * PlayMessage class used from client to server to send a play move
 */
public class PlayMessage extends Message {

  private int assistantId;
  private int characterId;

  // Character parameters
  private Color color;
  private int numIsland = -1;
  private int motherNatureMoves = -1;

  private int[] studentsCard;
  private int[] studentsEntrance;
  private int[] studentsDiningRoom;

  // For gui (it's positions instead of colors)
  private int[] studentsCardGUI;
  private int[] studentsEntranceGUI;

  /**
   * Constructor method for PlayMessage class
   * @param messageSecondary The messageSecondary to be set
   */
  public PlayMessage(MessageSecondary messageSecondary) {
    this.messageMain = MessageMain.PLAY;
    this.messageSecondary = messageSecondary;
  }

  public int getAssistantId() {
    return assistantId;
  }

  public void setAssistantId(int assistantId) {
    this.assistantId = assistantId;
  }

  public int getCharacterId() {
    return characterId;
  }

  public void setCharacterId(int characterId) {
    this.characterId = characterId;
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public int getNumIsland() {
    return numIsland;
  }

  public void setNumIsland(int numIsland) {
    this.numIsland = numIsland;
  }

  public int getMotherNatureMoves() {
    return motherNatureMoves;
  }

  public void setMotherNatureMoves(int motherNatureMoves) {
    this.motherNatureMoves = motherNatureMoves;
  }

  public int[] getStudentsCard() {
    return studentsCard;
  }

  public void setStudentsCard(int[] studentsCard) {
    this.studentsCard = studentsCard;
  }

  public int[] getStudentsEntrance() {
    return studentsEntrance;
  }

  public void setStudentsEntrance(int[] studentsEntrance) {
    this.studentsEntrance = studentsEntrance;
  }

  public int[] getStudentsDiningRoom() {
    return studentsDiningRoom;
  }

  public void setStudentsDiningRoom(int[] studentsDiningRoom) {
    this.studentsDiningRoom = studentsDiningRoom;
  }

  public int[] getStudentsCardGUI() {
    return studentsCardGUI;
  }

  public void setStudentsCardGUI(int[] studentsCardGUI) {
    this.studentsCardGUI = studentsCardGUI;
  }

  public int[] getStudentsEntranceGUI() {
    return studentsEntranceGUI;
  }

  public void setStudentsEntranceGUI(int[] studentsEntranceGUI) {
    this.studentsEntranceGUI = studentsEntranceGUI;
  }
}