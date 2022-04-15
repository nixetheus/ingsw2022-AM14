package it.polimi.ingsw.model.player;


import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;

/**
 * DiningRoomTest class tests DiningRoom class
 */

public class DiningRoomTest {

  DiningRoom testDiningRoom;

  /**
   * Testing the addStudent method, it ensures that the right student will be correctly added thanks
   * a test case calculated by hand
   */
  @Test
  public void testAddStudent() {
    testDiningRoom = new DiningRoom();
    int[] arrayTest = new int[]{1, 0, 1, 0, 0};
    testDiningRoom.addStudent(2);
    testDiningRoom.addStudent(0);

    Assert.assertEquals(Arrays.toString(testDiningRoom.getStudents()), Arrays.toString(arrayTest));
  }

  /**
   * Testing the removeStudent method, it ensures that the right student will be correctly removed
   * with a test case calculated by hand
   */
  @Test
  public void testRemoveStudent() {
    testDiningRoom = new DiningRoom();
    int[] arrayTest = new int[]{1, 0, 0, 0, 0};
    testDiningRoom.addStudent(0);
    testDiningRoom.addStudent(0);
    testDiningRoom.removeStudent(0);
    Assert.assertEquals(Arrays.toString(testDiningRoom.getStudents()), Arrays.toString(arrayTest));
  }

}
