package it.polimi.ingsw.model.player;

import java.util.Arrays;
import junit.framework.TestCase;

/**
 * PlayerBoardTest class tests PlayerBoard class
 */
public class PlayerBoardTest extends TestCase {

  PlayerBoard testPlayerBoard;
  int[] arrayTest;

  /**
   * testMoveToDiningRoom tests moveToDiningRoom method,creating a new  playerBoard and adding a
   * student to it and assuming it equals to an array calculated by hand
   */

  public void testMoveToDiningRoom() {
    testPlayerBoard = new PlayerBoard(new int[]{0, 0, 0, 0, 0});
    arrayTest = new int[]{0, 0, 0, 0, 1};
    testPlayerBoard.moveToDiningRoom(4);
    assertEquals(Arrays.toString(testPlayerBoard.getDiningRoom().getStudents()),
        Arrays.toString(arrayTest));
  }

  /**
   * testMoveToEntrance tests moveToEntrance method,creating a new playerBoard followed by a
   * moveToEntrance and it would be equals to an array calculated by hand
   */

  public void testMoveToEntrance() {
    testPlayerBoard = new PlayerBoard(new int[]{5, 0, 0, 0, 0});
    arrayTest = new int[]{5, 0, 0, 1, 0};
    testPlayerBoard.moveToEntrance(3);
    assertEquals(Arrays.toString(testPlayerBoard.getEntrance().getStudents()),
        Arrays.toString(arrayTest));
  }

  /**
   * testRemoveFromDiningRoom tests removeFromDiningRoom method,creating a new playerBoard followed
   * by a removeFromDiningRoom and it would be equals to an array calculated by hand
   */
  public void testRemoveFromDiningRoom() {
    testPlayerBoard = new PlayerBoard(new int[]{0, 0, 0, 0, 0});
    arrayTest = new int[]{0, 0, 0, 0, 0};
    testPlayerBoard.moveToDiningRoom(4);
    testPlayerBoard.removeFromDiningRoom(4);
    assertEquals(Arrays.toString(testPlayerBoard.getDiningRoom().getStudents()),
        Arrays.toString(arrayTest));
  }

  /**
   * testRemoveFromEntrance tests removeFromEntrance method,creating a new playerBoard followed by a
   * removeFromEntrance and it would be equals to an array calculated by hand
   */
  public void testRemoveFromEntrance() {
    testPlayerBoard = new PlayerBoard(new int[]{5, 0, 0, 0, 0});
    arrayTest = new int[]{4, 0, 0, 0, 0};
    testPlayerBoard.removeFromEntrance(0);
    assertEquals(Arrays.toString(testPlayerBoard.getEntrance().getStudents()),
        Arrays.toString(arrayTest));
  }
}
