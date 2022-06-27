package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.helpers.Color;
import it.polimi.ingsw.helpers.Effects;
import it.polimi.ingsw.helpers.Places;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.board.StudentsBag;
import it.polimi.ingsw.model.player.Player;
import java.util.Vector;

/**
 * GameCharacter class modelling the character that modifies the state of a player
 */
public class PlayerCharacters extends CharacterCard {

  public PlayerCharacters(Effects effect, int neededCoins, int[] students) {
    super(effect, neededCoins, students);
  }

  /**
   * This method overrides the standard behaviour of the CharacterCard class, parent of
   * PlayerCharacters, by implementing all the game effects concerning the change of state of the
   * current player
   *
   * @param params A struct containing all the object modifiable by any effect. This makes the
   *               applyEffect method in all the different child class have the same signature, thus
   *               making it interchangeable
   */
  @Override
  public void applyEffect(CharacterStruct params) {

    switch (cardEffect) {
      case INCREASE_MOVEMENT_TWO:
        increaseMovementTwoEffect(params.currentPlayer);
        break;
      case REPLACE_STUDENT_ENTRANCE:
        replaceStudentEntranceEffect(params.currentPlayer, params.studentsCard,
            params.studentsEntrance);
        break;
      case EXCHANGE_TWO_ENTRANCE_DINING:
        exchangeTwoEntranceDiningEffect(params.currentPlayer, params.studentsEntrance,
            params.studentsDiningRoom);
        break;
      case PUT_ONE_DINING_ROOM:
        putOneDiningRoomEffect(params.currentPlayer, params.color);
        break;
      case RETURN_THREE_DINING_ROOM_BAG:
        returnThreeDiningRoomBag(params.teams, params.color, params.studentsBag);
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + cardEffect);
    }

  }

  /**
   * This method overrides the standard behaviour of the CharacterCard class, parent of
   * PlayerCharacters, by removing all the game effects concerning the change of state of the
   * current player. It removes only those effects that need it but still has a case for each
   * possible effect to ensure complete interchangeability
   *
   * @param params A struct containing all the object modifiable by any effect. This makes the
   *               removeEffect method in all the different child class have the same signature,
   *               thus making it interchangeable
   */
  @Override
  public void removeEffect(CharacterStruct params) {

    switch (cardEffect) {
      case INCREASE_MOVEMENT_TWO:
        decreaseMovementTwoEffect(params.currentPlayer);
        break;
      case REPLACE_STUDENT_ENTRANCE:
      case EXCHANGE_TWO_ENTRANCE_DINING:
      case PUT_ONE_DINING_ROOM:
      case RETURN_THREE_DINING_ROOM_BAG:
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + cardEffect);
    }

  }

  /**
   * This method implements the Character Card used to increase the possible movements of Mother
   * Nature by two
   *
   * @param currentPlayer The player whose assistant will have two more possible moves for Mother
   *                      Nature
   */
  private void increaseMovementTwoEffect(Player currentPlayer) {
    currentPlayer.additionalMovesAssistant(2);
  }

  /**
   * This method removes the effect of the Character Card used to increase the possible movements of
   * Mother Nature by two
   *
   * @param currentPlayer The player whose assistant has two more possible moves for Mother Nature
   */
  private void decreaseMovementTwoEffect(Player currentPlayer) {
    currentPlayer.removeAdditionalMovesAssistant();
  }

  /**
   * This method implements the Character Card used to change some students from this card with some
   * from the current player's entrance
   *
   * @param currentPlayer    The player playing the card
   * @param studentsCard     The array of students that will move from this card to the entrance
   * @param studentsEntrance The array of students that will move from the entrance to this card
   */
  private void replaceStudentEntranceEffect(Player currentPlayer, int[] studentsCard,
      int[] studentsEntrance) {

    for (Color color : Color.values()) {

      // Remove students from card, add students from entrance
      students[color.ordinal()] -= studentsCard[color.ordinal()];
      students[color.ordinal()] += studentsEntrance[color.ordinal()];

      for (int colorInFrom = 0; colorInFrom < studentsCard[color.ordinal()]; colorInFrom++) {
        currentPlayer.moveToPlayerBoard(Places.ENTRANCE, color.ordinal());
      }

      for (int colorInFrom = 0; colorInFrom < studentsEntrance[color.ordinal()]; colorInFrom++) {
        currentPlayer.removeFromPlayerBoard(Places.ENTRANCE, color.ordinal());
      }
    }

  }

  /**
   * This method implements the Character Card used to exchange up to two students between a
   * player's entrance and dining room
   *
   * @param currentPlayer    The player playing the card
   * @param studentsEntrance The array of students coming from the player's entrance
   * @param studentsDining   The array of students coming from the player's dining room
   */
  private void exchangeTwoEntranceDiningEffect(Player currentPlayer, int[] studentsEntrance,
      int[] studentsDining) {

    for (Color color : Color.values()) {

      for (int studentE = 0; studentE < studentsEntrance[color.ordinal()]; studentE++) {
        currentPlayer.moveToPlayerBoard(Places.DINING_ROOM, color.ordinal());
        currentPlayer.removeFromPlayerBoard(Places.ENTRANCE, color.ordinal());
      }

      for (int studentDR = 0; studentDR < studentsDining[color.ordinal()]; studentDR++) {
        currentPlayer.moveToPlayerBoard(Places.ENTRANCE, color.ordinal());
        currentPlayer.removeFromPlayerBoard(Places.DINING_ROOM, color.ordinal());
      }
    }

  }

  /**
   * This method implements the Character Card used to put one student from this card to the
   * player's dining room
   *
   * @param currentPlayer The player playing the card
   * @param color         The color of the student to be moved
   */
  private void putOneDiningRoomEffect(Player currentPlayer, Color color) {
    currentPlayer.moveToPlayerBoard(Places.DINING_ROOM, color.ordinal());
    students[color.ordinal()]--;
    students[(int) (Math.random() * Color.values().length)]++;
  }

  /**
   * This method implements the Character Card used to return up to three students of a desired
   * color of each player to the students' bag
   *
   * @param teams The vector containing all the teams playing the game
   * @param color The color of the students to be removed
   */
  private void returnThreeDiningRoomBag(Vector<Team> teams, Color color, StudentsBag studentsBag) {

    for (Team team : teams) {
      for (Player player : team.getPlayers()) {

        int numberToRemove =
            Math.min(player.getPlayerBoard().getDiningRoom().getStudents()[color.ordinal()], 3);

        for (int studentIndex = 0; studentIndex < numberToRemove; studentIndex++) {
          player.removeFromPlayerBoard(Places.DINING_ROOM, color.ordinal());
          studentsBag.addStudent(color.ordinal());
        }
      }
    }

  }

}
