package it.polimi.ingsw.model.player;

import java.util.Arrays;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * PlayerBoardTest class tests PlayerBoard class
 */
public class PlayerBoardTest extends TestCase {

  PlayerBoard testPlayerBoard;
  int[] arrayTest;

  /**
   * testMoveToDiningRoom tests moveToDiningRoom method
   */
  @Test
  public void testMoveToDiningRoom() {
    testPlayerBoard = new PlayerBoard();
    arrayTest = new int[]{0, 0, 0, 0, 1};
    testPlayerBoard.moveToDiningRoom(4);
    assertEquals(Arrays.toString(testPlayerBoard.getDiningRoom().getStudents()),
        Arrays.toString(arrayTest));
  }

  /**
   * testMoveToEntrance tests moveToEntrance method
   */
  @Test
  public void testMoveToEntrance() {
    testPlayerBoard = new PlayerBoard();
    arrayTest = new int[]{0, 0, 0, 1, 0};
    testPlayerBoard.moveToEntrance(3);
    assertEquals(Arrays.toString(testPlayerBoard.getEntrance().getStudents()),
        Arrays.toString(arrayTest));
  }
}
