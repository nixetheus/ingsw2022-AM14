package it.polimi.ingsw.helpers;

/**
 * StudentsPlayerId class used to know who has the students
 */
public class StudentsPlayerId {

  private final int playerId;
  private final int[] students;

  /**
   * Constructor class for StudentPlayerId class
   *
   * @param playerId The id of the player that owns the students
   * @param students The students owned
   */
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
