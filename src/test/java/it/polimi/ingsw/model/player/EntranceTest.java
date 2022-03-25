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
   * testAddStudent test the add student method
   */

  public void testAddStudent() {
    testEntrance = new Entrance();
    arrayTest = new int[]{1, 0, 1, 0, 0};
    testEntrance.addStudent(2);
    testEntrance.addStudent(0);

    assertEquals(Arrays.toString(testEntrance.getStudents()), Arrays.toString(arrayTest));
  }

  /**
   * test for ensure that the number of student will never become less than zero
   */

  public void testRemoveStudentIfZero() {
    testEntrance = new Entrance();
    arrayTest = new int[]{0, 0, 0, 0, 0};
    testEntrance.removeStudent(0);
    assertEquals(Arrays.toString(testEntrance.getStudents()), Arrays.toString(arrayTest));
  }

  /**
   * testAddStudent test the remove student method
   */

  public void testRemoveStudent() {
    testEntrance = new Entrance();
    arrayTest = new int[]{1, 0, 0, 0, 0};
    testEntrance.addStudent(0);
    testEntrance.addStudent(0);
    testEntrance.removeStudent(0);
    assertEquals(Arrays.toString(testEntrance.getStudents()), Arrays.toString(arrayTest));
  }
}
