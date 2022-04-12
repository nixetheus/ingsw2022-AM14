package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.helpers.Effects;
import it.polimi.ingsw.model.Game;

public class GameCharacters extends CharacterCard {

  public GameCharacters(Effects effect, int neededCoins) {
    super(effect, neededCoins);
  }

  /**
   * TODO
   */
  public void applyEffect(Game currentGame) {
    switch (cardEffect) {
      case TAKE_PROFESSOR_EQUAL:
        takeProfessorEqualEffect(currentGame);
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + cardEffect);
    }
  }

  /**
   * @param currentGame
   */
  private void takeProfessorEqualEffect(Game currentGame) {
    // TODO: add one to currentPlayer influence (same effect)
    // TODO: Needs variable in game
  }
}
