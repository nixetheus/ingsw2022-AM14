package it.polimi.ingsw.model.board;

import it.polimi.ingsw.helpers.Constants;
import org.junit.Test;

public class StudentsBagTest {

  /**
   * Method testBagMax tries to overflow the student bag by asking more students than available
   */
  @Test
  public void testBagMax() {
    int[] testOverflow = new int[5];

    StudentsBag bag = new StudentsBag();
    for (int i = 0; i < Constants.getStudentsForColor() + 1; i++)
      testOverflow = bag.pickRandomStudents(Constants.getNColors());

    assert(testOverflow.length == 0);
  }

}
