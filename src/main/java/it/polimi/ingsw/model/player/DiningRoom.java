package it.polimi.ingsw.model.player;

import it.polimi.ingsw.helpers.Constants;

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
    students = new int[Constants.getNColors()];
  }

  /**
   * This method adds a new Student to the entrance
   *
   * @param color The color of the student that will be added to the dining room
   */
  public void addStudent(int color) {
    students[color]++;
  }

  /**
   * This method remove a student from the dining room
   *
   * @param color The color of the student that will be removed from the dining room
   */
  public void removeStudent(int color) {
    students[color]--;
  }

  public int[] getStudents() {
    return students.clone();
  }


}