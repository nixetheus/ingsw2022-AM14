package it.polimi.ingsw.model.characters;


import it.polimi.ingsw.helpers.Effects;

/**
 * Class used to model the charter card in the expert version of the game
 */
public class CharacterCard {

  final Effects cardEffect;
  int cost;

  public CharacterCard(Effects effect, int neededCoins) {
    cost = neededCoins;
    cardEffect = effect;
  }

  public void applyEffect() {
    System.out.println("This line should never get printed");
  }

  public int getCost() {
    return cost;
  }

  public void increaseCost() {
    cost++;
  }

  // TODO: RESET EFFECTS METHOD

}