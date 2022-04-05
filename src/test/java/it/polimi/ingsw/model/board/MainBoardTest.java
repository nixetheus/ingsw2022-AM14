package it.polimi.ingsw.model.board;

import it.polimi.ingsw.helpers.Color;
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
   * It checks that the size of islands vector is equal to the initial numbers of Islands
   * It tries to add 3 students on the third island and
   * sum the student added in the MainBoard constructor
   */
  @Test
  public void testAddToIsland() {
    StudentsBag testSB = new StudentsBag();
    MainBoard mainBoard = new MainBoard(testSB);
    Assert.assertEquals(mainBoard.getIslands().size(), Constants.getInitialNumIslands());

    //find the color of the student added in the constructor of Main board
    int[] initialVector = mainBoard.getIslands().get(3).getStudents();
    int colorStudentToFind = -1;
    for(Color color : Color.values())
      colorStudentToFind = (initialVector[color.ordinal()] == 1) ? color.ordinal() : colorStudentToFind;

    //Add 3 students in the third island
    mainBoard.addToIsland(0, 3);
    mainBoard.addToIsland(0, 3);
    mainBoard.addToIsland(3, 3);
    Vector<Island> test = mainBoard.getIslands();

    //Create the correct array to compare
    int[] correctArr = {2, 0, 0, 1, 0};
    correctArr[colorStudentToFind]++;

    Assert.assertEquals(Arrays.toString(correctArr), Arrays.toString(test.get(3).getStudents()));
  }

  /**
   * Testing the joinIsland Method
   * This method tests the borderline case where joinIsland is called at position 0
   *
   * Initial disposition of the islands:  00 002 ... 3
   * Expected disposition of the islands: 000023 ...
   * It starts to add 2 students in first (0) position of the islands vector
   * 3 students in the second position (1) and 1 student in the last position (n-1)
   * Set the same owner (color = 2) for each island
   * Create a islandTests vector with the expected values inside
   * and then call the joinIslandMethod
   */
  @Test
  public void testJoinIsland() {

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
