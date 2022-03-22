package it.polimi.ingsw.model.board;

import org.junit.Test;

public class StudentsBagTest {
  @Test
  public void testBag() {
    StudentsBag bag = new StudentsBag();
    for (int i = 0; i < 55; i++)
      bag.pickRandomStudents(5);
  }
}
