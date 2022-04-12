package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.helpers.Effects;
import it.polimi.ingsw.model.player.Player;

public class PlayerCharacters extends CharacterCard {

  public PlayerCharacters(Effects effect, int neededCoins) {
    super(effect, neededCoins);
  }

  /**
   * TODO
   */
  public void applyEffect(Player currentPlayer) {
    switch (cardEffect) {
      case INCREASE_MOVEMENT_TWO:
        increaseMovementTwoEffect(currentPlayer);
        break;
      case REPLACE_STUDENT_ENTRANCE:
        replaceStudentEntranceEffect(currentPlayer);
        break;
      case EXCHANGE_TWO_ENTRANCE_DINING:
        exchangeTwoEntranceDiningEffect(currentPlayer);
        break;
      case PUT_ONE_DINING_ROOM:
        putOneDiningRoomEffect(currentPlayer);
        break;
      case RETURN_THREE_DINING_ROOM_BAG:
        returnThreeDiningRoomBag(currentPlayer);
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + cardEffect);
    }
  }

  /**
   * @param currentPlayer
   */
  private void increaseMovementTwoEffect(Player currentPlayer) {
    // TODO: needs variable
  }

  /**
   * @param currentPlayer
   */
  private void replaceStudentEntranceEffect(Player currentPlayer) {
    // TODO: method needed, getters not good
  }

  /**
   * @param currentPlayer
   */
  private void exchangeTwoEntranceDiningEffect(Player currentPlayer) {
    // TODO: method needed, getters not good
  }

  /**
   * @param currentPlayer
   */
  private void putOneDiningRoomEffect(Player currentPlayer) {
    // TODO: method needed, getters not good
  }

  /**
   * @param currentPlayer
   */
  private void returnThreeDiningRoomBag(Player currentPlayer) {
    currentPlayer.getPlayerBoard().getDiningRoom().removeStudent(0);  // TODO: method needed, getters not good
  }
}
