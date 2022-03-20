package it.polimi.ingsw.model.board;

import it.polimi.ingsw.helpers.Color;
import it.polimi.ingsw.helpers.Constants;
import java.util.Random;

/**
 * TODO
 */
public class StudentsBag {

  Random random;
  int[] students = new int[Constants.getNColors()];

  /**
   * TODO
   */
  public StudentsBag() {
    // iterate over enums using for loop
    for (Color color : Color.values()) {
      students[color.ordinal()] = Constants.getStudentsForColor();
    }
  }

  /**
   * TODO
   */
  public int[] pickRandomStudents(int numberOfStudents) {

    int[] returnStudents = new int[numberOfStudents];

    // TODO: fill array and empty bag IF NOT < 0

    return returnStudents;
  }
}
