package it.polimi.ingsw.model.board;

import it.polimi.ingsw.helpers.Constants;
import java.util.Arrays;
import java.util.Vector;
import org.junit.Assert;
import org.junit.Test;

/**
 * MainBoardTest class tests MainBoard class
 */
public class MainBoardTest {

  /**
   * Testing the addToIsland Method
   */
  @Test
  public void testAddToIsland() {
    StudentsBag testSB = new StudentsBag();
    MainBoard mainBoard = new MainBoard(testSB);
    Assert.assertEquals(mainBoard.getIslands().size(), Constants.getInitialNumIslands());
    mainBoard.addToIsland(0, 3);
    mainBoard.addToIsland(0, 3);
    mainBoard.addToIsland(3, 3);
    Vector<Island> test = mainBoard.getIslands();
    //Assert.assertEquals(Arrays.toString(test.get(3).getStudents()), "[2, 0, 0, 1, 0]");
    // TODO LUCA: broken
  }
  /**
   * Testing the joinIsland Method
   */
  @Test
  public void testJoinIsland() {
    //00 002 ... 3 => 000023 ...
    StudentsBag test = new StudentsBag();
    MainBoard mainBoard = new MainBoard(test);
    mainBoard.getIslands().get(0).addStudent(0);
    mainBoard.getIslands().get(0).addStudent(0);
    mainBoard.getIslands().get(0).setOwner(2);
    mainBoard.getIslands().get(1).addStudent(0);
    mainBoard.getIslands().get(1).addStudent(0);
    mainBoard.getIslands().get(1).addStudent(2);
    mainBoard.getIslands().get(1).setOwner(2);
    mainBoard.getIslands().get(11).addStudent(3);
    mainBoard.getIslands().get(11).setOwner(2);

    mainBoard.joinIsland(0);

    Vector<Island> islandsTest = new Vector<>();
    for (int i = 0; i < Constants.getInitialNumIslands() - 2; i++) {
      islandsTest.add(new Island());
    }
    islandsTest.get(0).addStudent(0);
    islandsTest.get(0).addStudent(0);
    islandsTest.get(0).addStudent(0);
    islandsTest.get(0).addStudent(0);
    islandsTest.get(0).addStudent(2);
    islandsTest.get(0).addStudent(3);

    /*for(int i = 0; i < mainBoard.getIslands().size(); i++) {
      Assert.assertEquals(Arrays.toString(islandsTest.get(i).getStudents()),
          Arrays.toString(mainBoard.getIslands().get(i).getStudents()));
    }*/
    // TODO LUCA: broken
  }
}
