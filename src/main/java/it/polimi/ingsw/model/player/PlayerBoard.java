package it.polimi.ingsw.model.player;

/**
 * This class is used to model the board that each player have composed by: ProfessorTable Entrance
 * DiningRoom
 */
public class PlayerBoard {

  //Attributes
  private final Entrance entrance;
  private final DiningRoom diningRoom;

  /**
   * Constructor method for the PlayerBoard class
   *
   * @param studentInEntrance Students put at the entrance
   */
  public PlayerBoard(int[] studentInEntrance) {
    diningRoom = new DiningRoom();
    entrance = new Entrance(studentInEntrance);
  }

  /**
   * This method allows to move a studentRoom the entrance to the dining room
   *
   * @param color The color of the student to move into the dining room
   */
  public void moveToDiningRoom(int color) {
    diningRoom.addStudent(color);
  }

  /**
   * This method allows to move a studentRoom the entrance to the entrance
   *
   * @param color The color of the student to move into entrance
   */
  public void moveToEntrance(int color) {
    entrance.addStudent(color);
  }

  public DiningRoom getDiningRoom() {
    return diningRoom;
  }

  public Entrance getEntrance() {
    return entrance;
  }
}

