package it.polimi.ingsw.helpers;

public enum Effects {


  INCREASE_MOVEMENT_TWO(1, 0, CardType.PLAYER),
  REPLACE_STUDENT_ENTRANCE(1, 6, CardType.PLAYER),
  EXCHANGE_TWO_ENTRANCE_DINING(1, 0, CardType.PLAYER),
  PUT_ONE_DINING_ROOM(2, 4, CardType.PLAYER),
  RETURN_THREE_DINING_ROOM_BAG(3, 0, CardType.PLAYER),

  TAKE_STUDENT_PUT_ISLAND(1, 4, CardType.MAINBOARD),
  FALSE_NATURE_MOVEMENT(3, 0, CardType.MAINBOARD),
  NO_ENTRY_NATURE(2, 0, CardType.MAINBOARD),
  NO_TOWERS_INFLUENCE(3, 0, CardType.MAINBOARD),
  ADD_TWO_INFLUENCE(2, 0, CardType.MAINBOARD),
  NO_INFLUENCE_COLOR(3, 0, CardType.MAINBOARD),

  TAKE_PROFESSOR_EQUAL(2, 0, CardType.GAME),

  DEFAULT_EFFECT(0, 0, CardType.NONE);

  private final CardType type;
  private final int initialCost;
  private final int nOfStudents;

  Effects(int cost, int nOfStudentsNeeded, CardType typeC) {
    type = typeC;
    initialCost = cost;
    nOfStudents = nOfStudentsNeeded;
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
}
