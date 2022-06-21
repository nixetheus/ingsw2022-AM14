package it.polimi.ingsw.model.characters;


import it.polimi.ingsw.helpers.Effects;

/**
 * Class used to model the charter card in the expert version of the game
 */
public class CharacterCard {

  final Effects cardEffect;
  int cost;
  int[] students;

  /**
   * Constructor method for CharacterCard class
   *
   * @param effect       The character effect
   * @param neededCoins  His cost
   * @param cardStudents The students that will be placed on the card
   */
  public CharacterCard(Effects effect, int neededCoins, int[] cardStudents) {

    cost = neededCoins;
    cardEffect = effect;
    students = cardStudents;
  }

  /**
   * Method used to apply the effect of a character
   *
   * @param params What the character need to change into the game
   */
  public void applyEffect(CharacterStruct params) {
    throw new IllegalStateException("Unexpected value: " + cardEffect);
  }

  /**
   * Method used to remove the effect of a character
   *
   * @param params What the character need to change into the game
   */
  public void removeEffect(CharacterStruct params) {
    throw new IllegalStateException("Unexpected value: " + cardEffect);
  }

  public int getCost() {
    return cost;
  }

  public void increaseCost() {
    cost++;
  }

  public Effects getCardEffect() {
    return cardEffect;
  }

  public int[] getStudents() {
    return students;
  }


}