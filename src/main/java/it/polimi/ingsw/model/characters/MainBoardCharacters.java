package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.helpers.Color;
import it.polimi.ingsw.helpers.Effects;
import it.polimi.ingsw.model.board.MainBoard;

/**
 * GameCharacter class modelling the character that modifies the state of the gameBoard
 */
public class MainBoardCharacters extends CharacterCard {

  public MainBoardCharacters(Effects effect, int neededCoins, int[] students) {
    super(effect, neededCoins, students);
  }

  /**
   * This method overrides the standard behaviour of the CharacterCard class, parent of
   * MainBoardCharacters, by implementing all the game effects concerning the change of state of the
   * game's main board
   *
   * @param params A struct containing all the object modifiable by any effect. This makes the
   *               applyEffect method in all the different child class have the same signature, thus
   *               making it interchangeable
   */
  @Override
  public void applyEffect(CharacterStruct params) {

    switch (cardEffect) {
      case TAKE_STUDENT_PUT_ISLAND:
        takeStudentPutIslandEffect(params.mainBoard, params.numIsland, params.color);
        break;
      case NO_ENTRY_NATURE:
        noEntryNatureEffect(params.mainBoard, params.numIsland);
        break;
      case NO_TOWERS_INFLUENCE:
        noTowersInfluenceEffect(params.mainBoard);
        break;
      case ADD_TWO_INFLUENCE:
        addTwoInfluenceEffect(params.mainBoard);
        break;
      case NO_INFLUENCE_COLOR:
        noInfluenceColorEffect(params.mainBoard, params.color);
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + cardEffect);
    }

  }

  /**
   * This method overrides the standard behaviour of the CharacterCard class, parent of
   * MainBoardCharacters, by removing all the game effects concerning the change of state of the
   * game's main board. It removes only those effects that need it but still has a case for each
   * possible effect to ensure complete interchangeability
   *
   * @param params A struct containing all the object modifiable by any effect. This makes the
   *               removeEffect method in all the different child class have the same signature,
   *               thus making it interchangeable
   */
  @Override
  public void removeEffect(CharacterStruct params) {

    switch (cardEffect) {
      case TAKE_STUDENT_PUT_ISLAND:
        break;
      case NO_ENTRY_NATURE:
        break;
      case NO_TOWERS_INFLUENCE:
        yesTowersInfluenceEffect(params.mainBoard);
        break;
      case ADD_TWO_INFLUENCE:
        removeTwoInfluenceEffect(params.mainBoard);
        break;
      case NO_INFLUENCE_COLOR:
        allInfluenceColorEffect(params.mainBoard);
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + cardEffect);
    }
  }

  /**
   * This method implements the Character Card used to take one student from this card and put it in
   * one island
   *
   * @param mainBoard The game's main board object
   * @param numIsland The number of the affected island
   * @param color     Color of the student to be moved
   */
  private void takeStudentPutIslandEffect(MainBoard mainBoard, int numIsland, Color color) {
    mainBoard.addToIsland(color.ordinal(), numIsland);
  }

  /**
   * This method implements the Character Card used to block the movement of Mother Nature on one
   * island
   *
   * @param mainBoard The game's main board object
   * @param numIsland The number of the affected island that can't be used by mother nature this
   *                  turn
   */
  private void noEntryNatureEffect(MainBoard mainBoard, int numIsland) {
    mainBoard.setIslandsNoEntry(numIsland);
  }

  /**
   * This method removes the effect of the Character Card used to block the movement of Mother
   * Nature on one island
   *
   * @param mainBoard The game's main board object
   * @param numIsland The number of the affected island that has to be reverted
   */
  private void yesEntryNatureEffect(MainBoard mainBoard, int numIsland) {
    mainBoard.resetIslandsNoEntry(numIsland);
  }

  /**
   * This method implements the Character Card used to remove the counting of towers during the
   * calculation of influences
   *
   * @param mainBoard The game's main board object
   */
  private void noTowersInfluenceEffect(MainBoard mainBoard) {
    mainBoard.setAreTowersCounted(false);
  }

  /**
   * This method removes the effect of the Character Card used to remove the counting of towers
   * during the calculation of influences
   *
   * @param mainBoard The game's main board object
   */
  private void yesTowersInfluenceEffect(MainBoard mainBoard) {
    mainBoard.setAreTowersCounted(true);
  }

  /**
   * This method implements the Character Card used to add two points to the current player's
   * influence total
   *
   * @param mainBoard The game's main board object
   */
  private void addTwoInfluenceEffect(MainBoard mainBoard) {
    mainBoard.setInfluencePlus(2);
  }

  /**
   * This method removes the effect of the Character Card used to add two points to the current
   * player's influence total
   *
   * @param mainBoard The game's main board object
   */
  private void removeTwoInfluenceEffect(MainBoard mainBoard) {
    mainBoard.setInfluencePlus(0);
  }

  /**
   * This method implements the Character Card used to remove one color from the calculation of
   * influences
   *
   * @param mainBoard The game's main board object
   * @param color     The color whose influence will not be counted
   */
  private void noInfluenceColorEffect(MainBoard mainBoard, Color color) {
    mainBoard.setForbiddenColor(color);
  }

  /**
   * This method removes the effect of the Character Card used to remove one color from the
   * calculation of influences
   *
   * @param mainBoard The game's main board object
   */
  private void allInfluenceColorEffect(MainBoard mainBoard) {
    mainBoard.setForbiddenColor(null);
  }
}
