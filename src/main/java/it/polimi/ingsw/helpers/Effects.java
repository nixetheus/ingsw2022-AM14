package it.polimi.ingsw.helpers;

import static it.polimi.ingsw.helpers.CharactersFlags.COLOR_FLAG;
import static it.polimi.ingsw.helpers.CharactersFlags.NUM_ISLAND_FLAG;
import static it.polimi.ingsw.helpers.CharactersFlags.STUDENTS_CARD_FLAG;
import static it.polimi.ingsw.helpers.CharactersFlags.STUDENTS_DINING_ROOM_FLAG;
import static it.polimi.ingsw.helpers.CharactersFlags.STUDENTS_ENTRANCE_FLAG;

import java.util.Vector;

public enum Effects {


  INCREASE_MOVEMENT_TWO(1, 0, CardType.PLAYER, 0,
      "This character made mother nature move 2 further island than the indicated value on your assistant"),
  REPLACE_STUDENT_ENTRANCE(1, 6, CardType.PLAYER,
      (1 << STUDENTS_ENTRANCE_FLAG.ordinal()) | (1 << STUDENTS_CARD_FLAG.ordinal()),
      "This character allows to replace up to 3 student from this card to your entrance"),
  EXCHANGE_TWO_ENTRANCE_DINING(1, 0, CardType.PLAYER,
      (1 << STUDENTS_ENTRANCE_FLAG.ordinal()) | (1 << STUDENTS_DINING_ROOM_FLAG.ordinal()),
      "This character allows to exchange up to 2 student from your dining room and your entrance"),
  PUT_ONE_DINING_ROOM(2, 4, CardType.PLAYER,
      (1 << STUDENTS_DINING_ROOM_FLAG.ordinal()) | (1 << STUDENTS_CARD_FLAG.ordinal()),
      "This character allows to chose a student on this card and put it in your dining room"),
  RETURN_THREE_DINING_ROOM_BAG(3, 0, CardType.PLAYER, 1 << COLOR_FLAG.ordinal(),
      "This character allows to chose a student color and every player must return 3 student of that color from their dining room to the bag"),

  TAKE_STUDENT_PUT_ISLAND(1, 4, CardType.MAINBOARD,
      (1 << NUM_ISLAND_FLAG.ordinal()) | (1 << STUDENTS_CARD_FLAG.ordinal()),
      "This character allows to take a student from this card and put it on an island of your choice"),
  FALSE_NATURE_MOVEMENT(3, 0, CardType.MAINBOARD, 1 << NUM_ISLAND_FLAG.ordinal(),
      "This character allows to chose an island and resolving that island as mother nature ends her movement there"),
  NO_ENTRY_NATURE(2, 0, CardType.MAINBOARD, 1 << NUM_ISLAND_FLAG.ordinal(),
      "This character allows to put a no entry tile on an island (mother nature cannot ends her movement there"),
  NO_TOWERS_INFLUENCE(3, 0, CardType.MAINBOARD, 0,
      "This character blocks towers from adding points in the influence calculation"),
  ADD_TWO_INFLUENCE(2, 0, CardType.MAINBOARD, 0,
      "This character adds 2 more influence to the influence calculation"),
  NO_INFLUENCE_COLOR(3, 0, CardType.MAINBOARD, 1 << STUDENTS_CARD_FLAG.ordinal(),
      "This character blocks a color from adding influence"),

  TAKE_PROFESSOR_EQUAL(2, 0, CardType.GAME, 0,
      "This character allows to take a professor even if the number of students in your dining room is equal to another player "),

  DEFAULT_EFFECT(0, 0, CardType.NONE, 0, null);

  private final int objectsFlags;
  private final CardType type;
  private final int initialCost;
  private final int nOfStudents;
  private final String stringEffectCard;

  Effects(int cost, int nOfStudentsNeeded, CardType typeC, int flags, String stringEffect) {
    type = typeC;
    initialCost = cost;
    nOfStudents = nOfStudentsNeeded;
    stringEffectCard = stringEffect;
    objectsFlags = flags;

  }

  public int getCost() {
    return initialCost;
  }

  public CardType getType() {
    return type;
  }

  public int getInitialCost() {
    return initialCost;
  }

  public int getNOfStudents() {
    return nOfStudents;
  }

  public String getStringEffectCard() {
    return stringEffectCard;
  }

  public int getObjectsFlags() {
    return objectsFlags;
  }
}
