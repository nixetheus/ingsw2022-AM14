package it.polimi.ingsw.model;

import it.polimi.ingsw.helpers.Color;
import it.polimi.ingsw.helpers.Constants;

/**
 * CloudTile class is the entity used to make the Cloud The number of clouds is equals to the
 * numbers of players
 */
public class CloudTile {

  //Attributes
  private final int id;
  private final int[] students = new int[Constants.getNColors()];

  /**
   * Constructor method:
   *
   * @param id Unique identifier for each Cloud students[] initialized to 0
   */
  public CloudTile(int id) {
    this.id = id;
  }

  /**
   * addStudent method fills cloud tile
   *
   * @param colors identify the array of the students to put in the cloudTile
   */
  public void fillCloud(int[] colors) {
    for (int color : colors) {
      students[color]++;
    }
  }

  /**
   * emptyCloud is used to empty the CloudTile, it returns the array students and sets it to 0
   *
   * @return an array of students
   */
  public int[] emptyCloud() {
    int[] studentsToReturn = students.clone();
    for (Color color : Color.values()) {
      students[color.ordinal()] = 0;
    }
    return studentsToReturn;
  }

  public int[] getStudents() {
    return students;
  }

  public int getId() {
    return id;
  }

}
