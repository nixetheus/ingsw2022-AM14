package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.helpers.Effects;
import it.polimi.ingsw.model.board.MainBoard;

public class MainBoardCharacters extends CharacterCard {

  public MainBoardCharacters(Effects effect, int neededCoins) {
    super(effect, neededCoins);
  }

  /**
   * TODO
   */
  public void applyEffect(MainBoard mainBoard) {
    switch (cardEffect) {
      case TAKE_STUDENT_PUT_ISLAND:
        takeStudentPutIslandEffect(mainBoard);
        break;
      case FALSE_NATURE_MOVEMENT:
        falseNatureMovementEffect(mainBoard);
        break;
      case NO_ENTRY_NATURE:
        noEntryNatureEffect(mainBoard);
        break;
      case NO_TOWERS_INFLUENCE:
        noTowersInfluenceEffect(mainBoard);
        break;
      case ADD_TWO_INFLUENCE:
        addTwoInfluenceEffect(mainBoard);
        break;
      case NO_INFLUENCE_COLOR:
        noInfluenceColorEffect(mainBoard);
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + cardEffect);
    }
  }

  /**
   * @param mainBoard
   */
  private void takeStudentPutIslandEffect(MainBoard mainBoard) {
    // TODO: needs number of island
  }

  /**
   * @param mainBoard
   */
  private void falseNatureMovementEffect(MainBoard mainBoard) {
    // TODO: needs another param that says which island, also needs Game (could be moved to Game too)
    mainBoard.moveMotherNature(500);
  }

  /**
   * @param mainBoard
   */
  private void noEntryNatureEffect(MainBoard mainBoard) {
    // TODO: Needs to set variable in Island number x to negate access
  }

  /**
   * @param mainBoard
   */
  private void noTowersInfluenceEffect(MainBoard mainBoard) {
    // TODO: Needs variable to check whether or not to use towers for influence
  }

  /**
   * @param mainBoard
   */
  private void addTwoInfluenceEffect(MainBoard mainBoard) {
    // TODO: Needs variable to check whether or not to add two
  }

  /**
   * @param mainBoard
   */
  private void noInfluenceColorEffect(MainBoard mainBoard) {
    // TODO: Needs variable
  }
}
