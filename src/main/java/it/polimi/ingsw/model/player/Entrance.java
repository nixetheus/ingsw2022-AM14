package it.polimi.ingsw.model.player;

import it.polimi.ingsw.helpers.Constants;
import java.util.Arrays;

/**
 * This class is used to model the entrance in witch you can add students to bring them either to
 * the islands or to the dining room
 */
public class Entrance {

  //Attributes
  private final int[] students = new int[Constants.getNColors()];

  /**
   * Constructor method for the Entrance class
   *
   * @param studentsRandomlyChosenColors Students put at the entrance at the beginning of the game
   */
  public Entrance(int[] studentsRandomlyChosenColors) {
    for (int color : studentsRandomlyChosenColors) {
      this.students[color]++;
    }
  }

  /**
   * This method adds a new Student to the entrance
   *
   * @param color The color of the student that will be added to the entrance
   */
  public void addStudent(int color) {
    students[color] = students[color] + 1;
  }

  /**
   * This method remove a student to the entrance
   *
   * @param color The color of the student that will be removed from the entrance
   */
  public void removeStudent(int color) {
    students[color]--;
    if (students[color] < 0) {
      students[color] = 0;
    }
  }

  public int[] getStudents() {
    return Arrays.copyOf(students, students.length);
  }
}
