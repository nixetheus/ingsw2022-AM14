package it.polimi.ingsw.model.player;

import it.polimi.ingsw.exceptions.InvalidMoveException;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;

/**
 * DiningRoomTest class tests DiningRoom class
 */

public class DiningRoomTest {

  DiningRoom testDiningRoom;

  /**
   * Testing the addStudent method
   */
  @Test
  public void testAddStudent() throws InvalidMoveException {
    testDiningRoom = new DiningRoom();
    int[] arrayTest = new int[]{1, 0, 1, 0, 0};
    testDiningRoom.addStudent(2);
    testDiningRoom.addStudent(0);

    Assert.assertEquals(Arrays.toString(testDiningRoom.getStudents()), Arrays.toString(arrayTest));
  }

  /**
   * Test for assure that the number of students never becomes less than zero
   */
  @Test
  public void testRemoveStudentIfZero() throws InvalidMoveException {
    testDiningRoom = new DiningRoom();
    int[] arrayTest = new int[]{0, 0, 0, 0, 0};
    testDiningRoom.removeStudent(0);
    Assert.assertEquals(Arrays.toString(testDiningRoom.getStudents()), Arrays.toString(arrayTest));
  }

  /**
   * Testing the removeStudent method
   */
  @Test
  public void testRemoveStudent() throws InvalidMoveException {
    testDiningRoom = new DiningRoom();
    int[] arrayTest = new int[]{1, 0, 0, 0, 0};
    testDiningRoom.addStudent(0);
    testDiningRoom.addStudent(0);
    testDiningRoom.removeStudent(0);
    Assert.assertEquals(Arrays.toString(testDiningRoom.getStudents()), Arrays.toString(arrayTest));
  }

  /**
   * Test for assure that the number of student will never exceed the maximum
   */
  @Test
  public void testFullDiningRoom() throws InvalidMoveException {
    testDiningRoom = new DiningRoom();
    int i;
    int[] arrayTest = new int[]{0, 0, 10, 0, 0};
    for (i = 0; i < 11; i++) {
      testDiningRoom.addStudent(2);
    }
    Assert.assertEquals(Arrays.toString(testDiningRoom.getStudents()), Arrays.toString(arrayTest));
  }
}
