package it.polimi.ingsw.model.board;

import it.polimi.ingsw.helpers.Constants;

/**
 *
 */
public class Island {

  private int ownerId;
  private final int[] students;
  private final int numberOfTowers;

  /**
   *
   */
  public Island() {
    ownerId = -1;
    students = new int[Constants.getNColors()];
    numberOfTowers = 0;
  }

  /**
   *
   * @param color TODO
   */
  public void addStudent(int color) {
    // TODO: checks?
    students[color]++;
  }

  /**
   *
   */
  public int[] withdrawStudents() {
    // TODO
    return new int[5];
  }

  /**
   *
   */
  public void removeOwner() {
    ownerId = -1;
  }

  /**
   *
   * @param newOwnerId TODO
   */
  public void setOwner(int newOwnerId) {
    ownerId = newOwnerId;
  }

  public int getOwnerId() {
    return ownerId;
 }

  public int[] getStudents() {
    return students;
  }

  public int getNumberOfTowers() {
    return numberOfTowers;
  }
}
