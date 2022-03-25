package it.polimi.ingsw.model.board;

import it.polimi.ingsw.helpers.Constants;
import java.util.ArrayList;
import java.util.Arrays;
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
    MainBoard mainBoard = new MainBoard();
    Assert.assertEquals(mainBoard.getIslands().size(), Constants.getInitialNumIslands());
    mainBoard.addToIsland(0, 3);
    mainBoard.addToIsland(0, 3);
    mainBoard.addToIsland(3, 3);
    ArrayList<Island> test = mainBoard.getIslands();
    Assert.assertEquals(Arrays.toString(test.get(3).getStudents()), "[2, 0, 0, 1, 0]");
  }
}
