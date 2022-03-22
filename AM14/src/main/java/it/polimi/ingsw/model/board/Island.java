package it.polimi.ingsw.model.board;

import it.polimi.ingsw.helpers.Constants;

/**
 *
 */
public class Island {

  private int ownerId;
  private final int[] students;
  private int numberOfTowers;

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
    students[color]++;
  }

  /**
   *
   */
  public void addIsland(Island island) {
    numberOfTowers += island.getNumberOfTowers();
    for (int color = 0; color < Constants.getNColors(); color++) {
      students[color] += island.getStudents()[color];
    }
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
