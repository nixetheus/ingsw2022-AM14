package it.polimi.ingsw.model.board;

import it.polimi.ingsw.helpers.Color;
import it.polimi.ingsw.helpers.Constants;
import java.util.Arrays;
import java.util.Random;

/**
 * The StudentsBag entity is used to simulate the bag present in the game from where players can
 * pick random students to make various moves during the game itself
 */
public class StudentsBag {

  Random random = new Random();
  int[] students = new int[Constants.getNColors()];

  /**
   * Constructor for StudentsBag class. Initializes available students for color to the total number
   * of students divided by the number of available colors
   */
  public StudentsBag() {
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

    // Side case
    if (Arrays.stream(students).sum() <= 0) {
      return new int[0];
    }

    int[] returnStudents = new int[numberOfStudents];

    for (int student = 0; student < numberOfStudents; student++) {

      // Get colors whose number of students available is greater than zero
      int[] availableColors = Arrays.stream(new int[]{0, 1, 2, 3, 4})
          .filter(number -> students[number] > 0).toArray();

      // Add student to return, remove it from bag
      int randomColor = availableColors[random.nextInt(availableColors.length)];
      returnStudents[student] = randomColor;
      students[randomColor]--;

    }
    return returnStudents;
  }

  public int[] getStudents() {
    return students;
  }
}
