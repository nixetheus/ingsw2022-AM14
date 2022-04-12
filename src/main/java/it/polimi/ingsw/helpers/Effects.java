package it.polimi.ingsw.helpers;

public enum Effects {

  TAKE_PROFESSOR_EQUAL(2),

  INCREASE_MOVEMENT_TWO(1),
  REPLACE_STUDENT_ENTRANCE(1),
  EXCHANGE_TWO_ENTRANCE_DINING(1),
  PUT_ONE_DINING_ROOM(2),
  RETURN_THREE_DINING_ROOM_BAG(3),

  TAKE_STUDENT_PUT_ISLAND(1),
  FALSE_NATURE_MOVEMENT(3),
  NO_ENTRY_NATURE(2),
  NO_TOWERS_INFLUENCE(3),
  ADD_TWO_INFLUENCE(2),
  NO_INFLUENCE_COLOR(3);

  private final int initialCost;

  Effects(int cost) {
    initialCost = cost;
  }

  public int getCost() {
    return initialCost;
  }

}
