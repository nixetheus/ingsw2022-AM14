package it.polimi.ingsw.model.board;

import it.polimi.ingsw.helpers.Constants;
import java.util.Vector;

public class MainBoard {

  //Attributes
  private final Vector<Island> islands;
  private final MotherNature motherNature;

  /**
   * Constructor method: it creates Islands array, and it places on each of them one student
   */
  public MainBoard() {
    motherNature = new MotherNature(MainBoard.pickStartPlaceMotherNature());
    islands = new Vector<>();
    for (int i = 0; i < Constants.getInitialNumIslands(); i++) {
      islands.add(new Island());
      //pick random Student
    }
  }

  /**
   * Static method to make a random int
   *
   * @return a random integer from 0 to initial number of islands - 1
   */
  static public int pickStartPlaceMotherNature() {
    return (int) (Math.random() * Constants.getInitialNumIslands());
  }

  /**
   * Method to calculate the team with the greatest influence
   *
   * @return the integer that represents the team
   */
  public int calculateInfluence() {
    //TODO
    return 42;
  }

  /**
   * addToIsland: method to add a student on an island
   *
   * @param color     the color of the student
   * @param numIsland number of island to place the student
   */
  public void addToIsland(int color, int numIsland) {
    islands.get(numIsland).addStudent(color);
  }

  /**
   * @param numIsland number that identifies the island
   */
  public void joinIsland(int numIsland) {
    //TODO
  }

  public Vector<Island> getIslands() {
    return islands;
  }

  public MotherNature getMotherNature() {
    return motherNature;
  }
}
