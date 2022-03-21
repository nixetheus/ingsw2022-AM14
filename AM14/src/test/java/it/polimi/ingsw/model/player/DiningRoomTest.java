package it.polimi.ingsw.model.player;

import java.util.Arrays;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * DiningRoomTest class tests DiningRoom class
 */

public class DiningRoomTest extends TestCase {

  DiningRoom testDiningRoom;

  /**
   * Testing the addStudent method
   */
  @Test
  public void testAddStudent() {
    testDiningRoom = new DiningRoom();
    int[] arrayTest = new int[]{1, 0, 1, 0, 0};
    testDiningRoom.addStudent(2);
    testDiningRoom.addStudent(0);

    assertEquals(Arrays.toString(testDiningRoom.getStudents()), Arrays.toString(arrayTest));
  }

  /**
   * Test for assure that the number of students never becomes less than zero
   */
  @Test
  public void testRemoveStudentIfZero() {
    testDiningRoom = new DiningRoom();
    int[] arrayTest = new int[]{0, 0, 0, 0, 0};
    testDiningRoom.removeStudent(0);
    assertEquals(Arrays.toString(testDiningRoom.getStudents()), Arrays.toString(arrayTest));
  }

  /**
   * Testing the removeStudent method
   */
  @Test
  public void testRemoveStudent() {
    testDiningRoom = new DiningRoom();
    int[] arrayTest = new int[]{1, 0, 0, 0, 0};
    testDiningRoom.addStudent(0);
    testDiningRoom.addStudent(0);
    testDiningRoom.removeStudent(0);
    assertEquals(Arrays.toString(testDiningRoom.getStudents()), Arrays.toString(arrayTest));
  }

  /**
   * Test for assure that the number of student will never excede the maximum
   */
  @Test
  public void testFullDiningRoom() {
    testDiningRoom = new DiningRoom();
    int i;
    int[] arrayTest = new int[]{0, 0, 10, 0, 0};
    for (i = 0; i < 11; i++) {
      testDiningRoom.addStudent(2);
    }
    assertEquals(Arrays.toString(testDiningRoom.getStudents()), Arrays.toString(arrayTest));
  }
}
