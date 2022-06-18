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
   * This method moves MotherNature, just setting the new position
   */
  public void move(int newMotherNaturePosition) {
    this.position = newMotherNaturePosition;
  }

  public int getPosition() {
    return position;
  }
}
