package it.polimi.ingsw.model.board;

import it.polimi.ingsw.helpers.Color;
import it.polimi.ingsw.helpers.Constants;
import java.util.Arrays;
import java.util.Random;

/**
 * TODO
 */
public class StudentsBag {

  Random random = new Random();
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
   * Creates and array of students out of the remaining ones in the bag
   *
   * @param numberOfStudents Number of students to get from the Students Bag
   */
  public int[] pickRandomStudents(int numberOfStudents) {

    int[] returnStudents = new int[numberOfStudents];
    int[] availableColors;

    if (Arrays.stream(students).sum() <= 0)
      return new int[0];

    for (int student = 0; student < numberOfStudents; student++) {
      availableColors = Arrays.stream(new int[]{0, 1, 2, 3, 4})
          .filter(number -> students[number] > 0).toArray();
      int randomColor = availableColors[random.nextInt(availableColors.length)];
      returnStudents[student] = randomColor;
      students[randomColor]--;
    }

    return returnStudents;
  }
}
