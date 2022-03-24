package it.polimi.ingsw.model.player;

import it.polimi.ingsw.exceptions.InvalidMoveException;

/**
 * This class is used to model the board that each player have composed by: ProfessorTable Entrance
 * DiningRoom
 */
public class PlayerBoard {

  //Attributes
  private final DiningRoom diningRoom;
  private final Entrance entrance;
  //private ProfessorTable professorTable;

  /**
   * Constructor method for the PlayerBoard class
   */
  public PlayerBoard() {
    this.diningRoom = new DiningRoom();
    this.entrance = new Entrance();
    // this.professorTable = new ProfessorTable();
  }

  /**
   * This method allows to move a student drom the entrance to the dining room
   *
   * @param color The color of the student to move into the dining room
   */
  public void moveToDiningRoom(int color) {
    try {
      diningRoom.addStudent(color);
    } catch (InvalidMoveException e) {
      System.out.println(e.getMessage());
      // TODO
    }
  }

  /**
   * This method allows to move a student drom the entrance to the entrance
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

