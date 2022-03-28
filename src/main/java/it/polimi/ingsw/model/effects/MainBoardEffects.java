package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.helpers.Constants;

/**
 * MainBoardEffect is a class that model the behavior of character effect that modify something
 * related to the main board class
 */

public class MainBoardEffects implements Effects {


  private final boolean[] possibleEffects;

  /**
   * Constructor class for MainBoardEffectsClass
   */
  public MainBoardEffects() {
    possibleEffects = new boolean[Constants.getMaxNumberOfMainBoardEffects()];
  }

  /**
   * @param effectNumber Used to know which effect will be activate
   */
  public void applyEffect(int effectNumber) {
    possibleEffects[effectNumber] = true;
  }

  /**
   * @param effectNumber Used to know which effect will expire
   */
  public void expireEffect(int effectNumber) {
    possibleEffects[effectNumber] = false;
  }

  public boolean[] getPossibleEffects() {
    return possibleEffects;
  }

}

