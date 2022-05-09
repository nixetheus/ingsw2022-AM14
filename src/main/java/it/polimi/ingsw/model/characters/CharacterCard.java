package it.polimi.ingsw.model.characters;


import it.polimi.ingsw.helpers.Effects;

/**
 * Class used to model the charter card in the expert version of the game
 */
public class CharacterCard {

  final Effects cardEffect;
  int cost;
  int[] students;

  public CharacterCard(Effects effect, int neededCoins, int[] cardStudents) {

    cost = neededCoins;
    cardEffect = effect;
    students = cardStudents;
  }

  public void applyEffect(CharacterStruct params) {
    throw new IllegalStateException("Unexpected value: " + cardEffect);
  }

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