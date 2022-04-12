package it.polimi.ingsw.model.player;

/**
 * This class is used to model the assistant card that each player is supposed to play in order to
 * establish their initiative and Mother Nature's movements
 */

public class Assistant {

  //Attributes
  private final int moves;
  private final int speed;
  private final int assistantId;

  /**
   * Constructor method for the Assistant class
   *
   * @param moves       used to indicate the maximum moves that mother nature can do this turn
   * @param speed       used to indicate the value for the initiative
   * @param assistantId used to indicate which assistant has been played
   */
  public Assistant(int moves, int speed, int assistantId) {
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
