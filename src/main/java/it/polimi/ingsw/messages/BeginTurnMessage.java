package it.polimi.ingsw.messages;

import it.polimi.ingsw.helpers.MessageMain;
import it.polimi.ingsw.helpers.MessageSecondary;
import it.polimi.ingsw.helpers.StudentsPlayerId;
import java.util.Vector;

public class BeginTurnMessage extends Message {

  Vector<StudentsPlayerId> studentDiningRoom;
  Vector<StudentsPlayerId> studentEntrance;
  Vector<int[]> professors;
  Vector<int[]> studentsIsland;
  Vector<Integer> towersIsland;
  Integer[] islandsIds;
  int[] towersColor;
  Vector<int[]> studentsCloudTiles;
  Vector<Integer> playableAssistantId;
  Vector<Integer> PurchasableCharacterId;
  int[] charactersCosts;
  Vector<int[]> charactersStudents;
  int motherNaturePosition;
  int[] playerCoins;

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

  public Vector<int[]> getProfessors() {
    return professors;
  }

  public void setProfessors(Vector<int[]> professors) {
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

  public int getMotherNaturePosition() {
    return motherNaturePosition;
  }

  public void setMotherNaturePosition(int motherNaturePosition) {
    this.motherNaturePosition = motherNaturePosition;
  }

  public Vector<Integer> getTowersIsland() {
    return towersIsland;
  }

  public void setTowersIsland(Vector<Integer> towersIsland) {
    this.towersIsland = towersIsland;
  }

  public int[] getTowersColor() {
    return towersColor;
  }

  public void setTowersColor(int[] towersColor) {
    this.towersColor = towersColor;
  }

  public int[] getPlayerCoins() {
    return playerCoins;
  }

  public void setPlayerCoins(int[] playerCoins) {
    this.playerCoins = playerCoins;
  }

  public int[] getCharactersCosts() {
    return charactersCosts;
  }

  public void setCharactersCosts(int[] charactersCosts) {
    this.charactersCosts = charactersCosts;
  }

  public Vector<int[]> getCharactersStudents() {
    return charactersStudents;
  }

  public void setCharactersStudents(Vector<int[]> charactersStudents) {
    this.charactersStudents = charactersStudents;
  }

  public Integer[] getIslandsIds() {
    return islandsIds;
  }

  public void setIslandsIds(Integer[] islandsIds) {
    this.islandsIds = islandsIds;
  }
}
