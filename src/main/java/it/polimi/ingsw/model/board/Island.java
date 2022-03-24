package it.polimi.ingsw.model.board;

import it.polimi.ingsw.helpers.Constants;

/**
 * The Island entity is used to simulate one of the island present in the game board where player
 * can position students to grow influences and conquer them
 */
public class Island {

  private final int[] students;
  private int ownerId;
  private int numberOfTowers;

  /**
   * Constructor method for the Island class. Defaults ownerId to null (or -1), students to an empty
   * array and numberOfTowers to zero
   */
  public Island() {
    ownerId = -1;
    students = new int[Constants.getNColors()];
    numberOfTowers = 0;
  }

  /**
   * Adds a student of the required color to the island
   *
   * @param color The color of the student to add to the island
   */
  public void addStudent(int color) {
    students[color]++;
  }

  /**
   * Merges two islands together combining their parameters (number of towers and students) into the
   * first one.
   *
   * @param island Island whose parameters have to be combined
   */
  public void addIsland(Island island) {
    numberOfTowers += island.getNumberOfTowers();
    for (int color = 0; color < Constants.getNColors(); color++) {
      students[color] += island.getStudents()[color];
    }
  }

  /**
   * Removes the current owner from the Island
   */
  public void removeOwner() {
    ownerId = -1;
  }

  /**
   * Changes the Island's owner to a new one
   *
   * @param newOwnerId The new owner of the Island
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
