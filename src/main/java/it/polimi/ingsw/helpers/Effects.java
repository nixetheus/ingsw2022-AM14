package it.polimi.ingsw.helpers;

public enum Effects {


  INCREASE_MOVEMENT_TWO(1, 0),
  REPLACE_STUDENT_ENTRANCE(1, 6),
  EXCHANGE_TWO_ENTRANCE_DINING(1, 0),
  PUT_ONE_DINING_ROOM(2, 4),
  RETURN_THREE_DINING_ROOM_BAG(3, 0),

  TAKE_STUDENT_PUT_ISLAND(1, 4),
  FALSE_NATURE_MOVEMENT(3, 0),
  NO_ENTRY_NATURE(2, 0),
  TAKE_PROFESSOR_EQUAL(2, 0),
  NO_TOWERS_INFLUENCE(3, 0),
  ADD_TWO_INFLUENCE(2, 0),
  NO_INFLUENCE_COLOR(3, 0),

  DEFAULT_EFFECT(0, 0);

  private final int initialCost;
  private final int nOfStudents;

  Effects(int cost, int nOfStudentsNeeded) {
    initialCost = cost;
    nOfStudents = nOfStudentsNeeded;
  }

  public int getCost() {
    return initialCost;
  }

}
