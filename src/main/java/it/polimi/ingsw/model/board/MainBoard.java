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
      //TODO
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
   * it checks that the owners of the islands adjacent to the one in
   * position "numIslandConquered" are equal and if they are,
   * it joins them into a single island, and removes the other
   *
   * @param numIslandConquered number that identifies the island
   */
  public void joinIsland(int numIslandConquered) {
    numIslandConquered = numIslandConquered % islands.size();
    int owner = islands.get(numIslandConquered).getOwnerId();
    int islandToJoin;
    //check right
    if (numIslandConquered == islands.size() - 1) {
      islandToJoin = 0;
    } else {
      islandToJoin = numIslandConquered + 1;
    }
    if (owner == islands.get(islandToJoin).getOwnerId()) {
      islands.get(numIslandConquered).addIsland(islands.get(islandToJoin));
      islands.remove(islandToJoin);
    }
    //check left
    if (numIslandConquered == 0) {
      islandToJoin = islands.size() - 1;
    } else {
      islandToJoin = numIslandConquered - 1;
    }
    if (owner == islands.get(islandToJoin).getOwnerId()) {
      islands.get(numIslandConquered).addIsland(islands.get(islandToJoin));
      islands.remove(islandToJoin);
    }
  }

  public Vector<Island> getIslands() {
    return islands;
  }

  public MotherNature getMotherNature() {
    return motherNature;
  }
}
