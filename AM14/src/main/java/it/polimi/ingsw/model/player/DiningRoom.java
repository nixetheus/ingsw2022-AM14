package it.polimi.ingsw.model.player;

import java.util.Arrays;

/**
 * This class is used to model the dining room
 */
public class DiningRoom {

  //Attributes
  private final int[] students;


  /**
   * Constructor method for the DiningRoom class
   */
  public DiningRoom() {
    students = new int[]{0, 0, 0, 0, 0};
  }

  /**
   * This method adds a new Student to the entrance
   *
   * @param color The color of the student that will be added to the dining room
   */
  public void addStudent(int color) {
    students[color]++;
    if(students[color]>10){
      students[color]=10;
    }
  }

  /**
   * This method remove a student from the dining room
   *
   * @param color The color of the student that will be removed from the dining room
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