package it.polimi.ingsw.model.board;

/**
 * This class is used to model the character of Mother Nature
 */

public class MotherNature {

  //Attributes
  private int position;

  /**
   * Constructor method for the MotherNature Class
   *
   * @param position The initial position of MotherNature
   */
  public MotherNature(int position) {
    this.position = position;
  }

  /**
   * This method moves MotherNature, it takes the total number of islands to avoid overflow
   *
   * @param moves        Number of movements of motherNature
   * @param numOfIslands Total number of islands
   */
  public void move(int moves, int numOfIslands) {
    position = (position + moves) % numOfIslands;
  }

  public int getPosition() {
    return position;
  }
}
