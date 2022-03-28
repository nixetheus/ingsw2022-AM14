package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.helpers.Constants;

/**
 * Player is a class that model the behavior of character effect that modify something related to
 * the Player class
 */
public class PlayerEffects implements Effects {

  private final boolean[] possibleEffects;

  /**
   * Constructor method for PlayerEffects
   */
  public PlayerEffects() {
    this.possibleEffects = new boolean[Constants.getMaxNumberOfPlayerEffects()];
  }


  /**
   * @param effectNumber @param effect Used to know which effect will be activate
   */
  public void applyEffect(int effectNumber) {
    possibleEffects[effectNumber] = true;
  }

  /**
   * @param effectNumber @param effect Used to know which effect will expire
   */
  public void expireEffect(int effectNumber) {
    possibleEffects[effectNumber] = false;
  }

  public boolean[] getPossibleEffects() {
    return possibleEffects;
  }
}
