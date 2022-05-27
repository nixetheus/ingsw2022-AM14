package it.polimi.ingsw.messages;

import it.polimi.ingsw.helpers.MessageMain;
import it.polimi.ingsw.helpers.MessageSecondary;
import it.polimi.ingsw.helpers.StudentsPlayerId;
import java.util.Vector;

public class BeginTurnMessage extends Message {

  Vector<StudentsPlayerId> studentDiningRoom;
  Vector<StudentsPlayerId> studentEntrance;
  int[] professors;
  Vector<int[]> studentsIsland;
  Vector<int[]> studentsCloudTiles;
  Vector<Integer> playableAssistantId;
  Vector<Integer> PurchasableCharacterId;

  public BeginTurnMessage(MessageSecondary messageSecondary) {
    this.messageMain = MessageMain.PHASE;
    this.messageSecondary = messageSecondary;
  }

  public Vector<StudentsPlayerId> getStudentDiningRoom() {
    return studentDiningRoom;
  }

  public void setStudentDiningRoom(
      Vector<StudentsPlayerId> studentDiningRoom) {
    this.studentDiningRoom = studentDiningRoom;
  }

  public Vector<StudentsPlayerId> getStudentEntrance() {
    return studentEntrance;
  }

  public void setStudentEntrance(
      Vector<StudentsPlayerId> studentEntrance) {
    this.studentEntrance = studentEntrance;
  }

  public int[] getProfessors() {
    return professors;
  }

  public void setProfessors(int[] professors) {
    this.professors = professors;
  }

  public Vector<int[]> getStudentsIsland() {
    return studentsIsland;
  }

  public void setStudentsIsland(Vector<int[]> studentsIsland) {
    this.studentsIsland = studentsIsland;
  }

  public Vector<int[]> getStudentsCloudTiles() {
    return studentsCloudTiles;
  }

  public void setStudentsCloudTiles(Vector<int[]> studentsCloudTiles) {
    this.studentsCloudTiles = studentsCloudTiles;
  }

  public Vector<Integer> getPlayableAssistantId() {
    return playableAssistantId;
  }

  public void setPlayableAssistantId(Vector<Integer> playableAssistantId) {
    this.playableAssistantId = playableAssistantId;
  }

  public Vector<Integer> getPurchasableCharacterId() {
    return PurchasableCharacterId;
  }

  public void setPurchasableCharacterId(Vector<Integer> purchasableCharacterId) {
    PurchasableCharacterId = purchasableCharacterId;
  }
}
