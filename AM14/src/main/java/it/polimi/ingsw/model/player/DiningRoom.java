package it.polimi.ingsw.model.player;

import it.polimi.ingsw.exceptions.InvalidMoveException;
import it.polimi.ingsw.helpers.Constants;
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
    students = new int[Constants.getNColors()];
  }

  /**
   * This method adds a new Student to the entrance
   *
   * @param color The color of the student that will be added to the dining room
   * @throws InvalidMoveException TODO
   */
  public void addStudent(int color) throws InvalidMoveException {
    if(students[color] + 1 > Constants.getMaxStudentsDiningRoom()){
      students[color]++;
    } else {
      throw new InvalidMoveException("TODO");
    }
  }

  /**
   * This method remove a student from the dining room
   *
   * @param color The color of the student that will be removed from the dining room
   */
  public void removeStudent(int color) throws InvalidMoveException {
    if(students[color] - 1 > 0){
      students[color]--;
    } else {
      throw new InvalidMoveException("TODO");
    }
  }

  public int[] getStudents() {
    return Arrays.copyOf(students, students.length);
  }


}