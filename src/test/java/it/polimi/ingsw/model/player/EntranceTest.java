package it.polimi.ingsw.model.player;

import java.util.Arrays;
import junit.framework.TestCase;


/**
 * EntranceTest class tests Entrance class
 */
public class EntranceTest extends TestCase {

  Entrance testEntrance;
  int[] arrayTest;

  /**
   * testAddStudent test the add student method, creating a new entrance, use the addStudent method
   * and assuming it equals to an array calculated by hand
   */

  public void testAddStudent() {
    testEntrance = new Entrance(new int[]{0, 0, 0, 0, 0});
    arrayTest = new int[]{6, 0, 1, 0, 0};
    testEntrance.addStudent(2);
    testEntrance.addStudent(0);

    assertEquals(Arrays.toString(testEntrance.getStudents()), Arrays.toString(arrayTest));
  }

  /**
   * Test for ensure that the number of student will never become less than zero
   */

  public void testRemoveStudentIfZero() {
    testEntrance = new Entrance(new int[]{1, 1, 1, 1, 1});
    arrayTest = new int[]{0, 5, 0, 0, 0};
    testEntrance.removeStudent(0);
    assertEquals(Arrays.toString(testEntrance.getStudents()), Arrays.toString(arrayTest));
  }

  /**
   * testRemoveStudent test the remove student method, creating a new entrance, use the addStudent
   * followed by a remove method and assuming it equals to an array calculated by hand
   */

  public void testRemoveStudent() {
    testEntrance = new Entrance(new int[]{0, 0, 0, 0, 0});
    arrayTest = new int[]{6, 0, 0, 0, 0};
    testEntrance.addStudent(0);
    testEntrance.addStudent(0);
    testEntrance.removeStudent(0);
    assertEquals(Arrays.toString(testEntrance.getStudents()), Arrays.toString(arrayTest));
  }
}
