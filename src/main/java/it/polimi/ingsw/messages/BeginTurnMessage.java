package it.polimi.ingsw.messages;

import it.polimi.ingsw.helpers.MessageMain;
import it.polimi.ingsw.helpers.MessageSecondary;
import it.polimi.ingsw.helpers.StudentsPlayerId;
import java.util.Vector;


/**
 * BeginTurnMessage used to send the game state during the match
 */
public class BeginTurnMessage extends Message {

  int[] playerCoins;
  int[] towersColor;
  int activePLayerId;
  int[] towersNumber;
  Integer[] islandsIds;
  int[] charactersCosts;
  int motherNaturePosition;
  Vector<int[]> professors;
  boolean[] islandsNoEntry;
  Vector<int[]> studentsIsland;
  Vector<Integer> towersIsland;
  Vector<int[]> charactersStudents;
  Vector<int[]> studentsCloudTiles;
  Vector<Integer> playableAssistantId;
  Vector<Integer> PurchasableCharacterId;
  Vector<StudentsPlayerId> studentEntrance;
  Vector<StudentsPlayerId> studentDiningRoom;


  /**
   * Constructor method for BeginTurnMessage
   *
   * @param messageSecondary The message secondary to be set
   */
  public BeginTurnMessage(MessageSecondary messageSecondary) {
    this.messageMain = MessageMain.PHASE;
    this.messageSecondary = messageSecondary;
  }

  public int[] getPlayerCoins() {
    return playerCoins;
  }

  public void setPlayerCoins(int[] playerCoins) {
    this.playerCoins = playerCoins;
  }

  public int[] getTowersColor() {
    return towersColor;
  }

  public void setTowersColor(int[] towersColor) {
    this.towersColor = towersColor;
  }

  public int getActivePLayerId() {
    return activePLayerId;
  }

  public void setActivePLayerId(int activePLayerId) {
    this.activePLayerId = activePLayerId;
  }

  public int[] getTowersNumber() {
    return towersNumber;
  }

  public void setTowersNumber(int[] towersNumber) {
    this.towersNumber = towersNumber;
  }

  public Integer[] getIslandsIds() {
    return islandsIds;
  }

  public void setIslandsIds(Integer[] islandsIds) {
    this.islandsIds = islandsIds;
  }

  public int[] getCharactersCosts() {
    return charactersCosts;
  }

  public void setCharactersCosts(int[] charactersCosts) {
    this.charactersCosts = charactersCosts;
  }

  public int getMotherNaturePosition() {
    return motherNaturePosition;
  }

  public void setMotherNaturePosition(int motherNaturePosition) {
    this.motherNaturePosition = motherNaturePosition;
  }

  public Vector<int[]> getProfessors() {
    return professors;
  }

  public void setProfessors(Vector<int[]> professors) {
    this.professors = professors;
  }

  public boolean[] getIslandsNoEntry() {
    return islandsNoEntry;
  }

  public void setIslandsNoEntry(boolean[] islandsNoEntry) {
    this.islandsNoEntry = islandsNoEntry;
  }

  public Vector<int[]> getStudentsIsland() {
    return studentsIsland;
  }

  public void setStudentsIsland(Vector<int[]> studentsIsland) {
    this.studentsIsland = studentsIsland;
  }

  public Vector<Integer> getTowersIsland() {
    return towersIsland;
  }

  public void setTowersIsland(Vector<Integer> towersIsland) {
    this.towersIsland = towersIsland;
  }

  public Vector<int[]> getCharactersStudents() {
    return charactersStudents;
  }

  public void setCharactersStudents(Vector<int[]> charactersStudents) {
    this.charactersStudents = charactersStudents;
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

  public Vector<StudentsPlayerId> getStudentEntrance() {
    return studentEntrance;
  }

  public void setStudentEntrance(
      Vector<StudentsPlayerId> studentEntrance) {
    this.studentEntrance = studentEntrance;
  }

  public Vector<StudentsPlayerId> getStudentDiningRoom() {
    return studentDiningRoom;
  }

  public void setStudentDiningRoom(
      Vector<StudentsPlayerId> studentDiningRoom) {
    this.studentDiningRoom = studentDiningRoom;
  }
}