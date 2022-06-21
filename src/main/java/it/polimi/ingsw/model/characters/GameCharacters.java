package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.helpers.Effects;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.MainBoard;

/**
 * GameCharacter class modelling the character that modifies the state of the game
 */
public class GameCharacters extends CharacterCard {

  public GameCharacters(Effects effect, int neededCoins, int[] students) {
    super(effect, neededCoins, students);
  }

  /**
   * This method overrides the standard behaviour of the CharacterCard class, parent of
   * GameCharacters, by implementing all the game effects concerning the change of state of the
   * current game
   *
   * @param params A struct containing all the object modifiable by any effect. This makes the
   *               applyEffect method in all the different child class have the same signature, thus
   *               making it interchangeable
   */
  @Override
  public void applyEffect(CharacterStruct params) {

    switch (cardEffect) {
      case FALSE_NATURE_MOVEMENT:
        falseNatureMovementEffect(params.currentGame, params.motherNatureMoves, params.mainBoard);
        break;
      case TAKE_PROFESSOR_EQUAL:
        takeProfessorEqualEffect(params.currentGame);
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + cardEffect);
    }

  }

  /**
   * This method overrides the standard behaviour of the CharacterCard class, parent of
   * GameCharacters, by removing all the game effects concerning the change of state of the current
   * game. It removes only those effects that need it but still has a case for each possible effect
   * to ensure complete interchangeability
   *
   * @param params A struct containing all the object modifiable by any effect. This makes the
   *               removeEffect method in all the different child class have the same signature,
   *               thus making it interchangeable
   */
  @Override
  public void removeEffect(CharacterStruct params) {

    switch (cardEffect) {
      case FALSE_NATURE_MOVEMENT:
        break;
      case TAKE_PROFESSOR_EQUAL:
        noTakeProfessorEqualEffect(params.currentGame);
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + cardEffect);
    }

  }

  /**
   * This method implements the Character Card used to simulate the movement of Mother Nature and
   * thus the possible conquering of an island
   *
   * @param currentGame       The current Game object
   * @param motherNatureMoves The number of moves Mother Nature has to take
   * @param board             The game's main board object
   */
  private void falseNatureMovementEffect(Game currentGame, int motherNatureMoves, MainBoard board) {
    currentGame.moveNature(motherNatureMoves);
    board.moveMotherNature(-motherNatureMoves);  // TODO: could break something?
  }

  /**
   * This method implements the Character Card used to take control of a professor even when the
   * players have the same number of students in the dining room of one color
   *
   * @param currentGame The current game object
   */
  private void takeProfessorEqualEffect(Game currentGame) {
    currentGame.setInfluenceEqualProfessors(1);
  }

  /**
   * This method removes the effect of the Character Card used to take control of a professor even
   * when the players have the same number of students in the dining room of one color
   *
   * @param currentGame The current game object
   */
  private void noTakeProfessorEqualEffect(Game currentGame) {
    currentGame.setInfluenceEqualProfessors(0);
  }
}

