package it.polimi.ingsw.model.effects;

/**
 * Game is a class that model the behavior of character effect that modify something related to the
 * game class
 */
public class GameEffects implements Effects {

  private boolean takeProfessorEqual;

  /**
   * Constructor of GameEffects class
   */
  public GameEffects() {
    takeProfessorEqual = false;
  }

  /**
   * @param effectNumber Used to know which effect will be activate
   */

  public void applyEffect(int effectNumber) {
    takeProfessorEqual = true;
  }

  /**
   * @param effectNumber Used to know which effect will expire
   */
  public void expireEffect(int effectNumber) {
    takeProfessorEqual = false;
  }

  public boolean getTakeProfessorEqual() {
    return takeProfessorEqual;
  }
}

