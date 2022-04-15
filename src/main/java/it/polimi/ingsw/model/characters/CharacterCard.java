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
    System.out.println("This line should never get printed");
  }

  public void removeEffect(CharacterStruct params) {
    System.out.println("Neither should this");
  }

  public int getCost() {
    return cost;
  }

  public void increaseCost() {
    cost++;
  }

}