package it.polimi.ingsw.helpers;

public class StudentsPlayerId {

  private final int playerId;
  private final int[] students;

  public StudentsPlayerId(int playerId, int[] students) {
    this.playerId = playerId;
    this.students = students;
  }

  public int getPlayerId() {
    return playerId;
  }

  public int[] getStudents() {
    return students;
  }
}
