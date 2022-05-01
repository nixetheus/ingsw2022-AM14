package it.polimi.ingsw.model.board;

import it.polimi.ingsw.helpers.Constants;
import java.util.Arrays;
import org.junit.Test;

/**
 * StudentsBagTest class tests StudentsBag class
 */
public class StudentsBagTest {

  /**
   * testPickStudents method tests pickRandomStudents method of StudentsBag class (normal
   * behaviour)
   */
  @Test
  public void testPickStudents() {

    int[] testNormalBehaviour;
    StudentsBag bag = new StudentsBag();
    testNormalBehaviour = bag.pickRandomStudents(1);

    assert (testNormalBehaviour.length == 5);
    assert (testNormalBehaviour[0] >= 0 && testNormalBehaviour[0] <= 4);
  }

  /**
   * testMaxAvailableStudents method tests whether it's possible to get the actual number of max
   * students using the pickRandomStudents method of StudentsBag class
   */
  @Test
  public void testMaxAvailableStudents() {

    int[] testMax;
    StudentsBag bag = new StudentsBag();
    testMax = bag.pickRandomStudents(
        Constants.getStudentsForColor() * Constants.getNColors());

    assert (Arrays.stream(testMax).sum()
        == Constants.getStudentsForColor() * Constants.getNColors());
    assert (Arrays.stream(bag.getStudents()).sum() == 0);
  }

  /**
   * testTooManyStudents method tests ensures it's not possible to get more than max number of
   * students using the pickRandomStudents method of StudentsBag class
   */
  @Test
  public void testTooManyStudents() {

    StudentsBag bag = new StudentsBag();
    try {
      bag.pickRandomStudents(Constants.getStudentsForColor() * Constants.getNColors() + 1);
    } catch (Exception e) {
      assert (true);
      return;
    }
    assert (false);
  }

}
