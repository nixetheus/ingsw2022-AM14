package it.polimi.ingsw.model.player;

/**
 * This class is used to model the assistent card that each player is supposed to play inorder to
 * establish his/her iniziative
 */

public class Assistent {

  //Attributes
  private final int moves;
  private final int speed;
  private final int assistantId;

  /**
   * Constructor method for the Assistent class
   *
   * @param moves       used to indicate the moximum moves that mother nature can do this turn
   * @param speed       used to indicate the value for the iniziative
   * @param assistantId used to indicate which assistant has been played
   */
  public Assistent(int moves, int speed, int assistantId) {
    this.moves = moves;
    this.speed = speed;
    this.assistantId = assistantId;
  }

  public int getMoves() {
    return moves;
  }

  public int getSpeed() {
    return speed;
  }

  public int getAssistantId() {
    return assistantId;
  }
}
