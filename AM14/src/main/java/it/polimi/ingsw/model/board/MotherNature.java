package it.polimi.ingsw.model.board;

import it.polimi.ingsw.helpers.Constants;
import java.util.Random;

/**
 * This class is used to model the character of Mother Nature
 */

public class MotherNature {

  //Attributes
  private int position;

  /**
   * Constructor method for the MotherNature Class
   *
   */
  public MotherNature() {
    this.position = (int)(Math.random() * Constants.getInitialNumIslands());
  }

  /**
   * This method moves MotherNature
   *
   * @param moves Number of movements of motherNature
   */
  public void move(int moves, int numIslands) {
    position = (position + moves) % numIslands;
  }

  public int getPosition() {
    return position;
  }
}
