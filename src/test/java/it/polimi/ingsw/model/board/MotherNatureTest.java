package it.polimi.ingsw.model.board;

import org.junit.Assert;
import org.junit.Test;

/**
 * MotherNatureTest class tests MotherNature Class
 */
public class MotherNatureTest {

  private final int x = MainBoard.pickStartPlaceMotherNature();

  /**
   * Testing the move Method
   */
  @Test
  public void testMoves(){
    MotherNature motherNature = new MotherNature(x);
    int numIslands = 12;
    Assert.assertEquals(x, motherNature.getPosition());
    motherNature.move(3, numIslands);
    Assert.assertEquals((x + 3) % numIslands, motherNature.getPosition());
    //moves > islands
    numIslands--;
    motherNature = new MotherNature(x);
    motherNature.move(123, numIslands);
    Assert.assertEquals((x + 123) % numIslands, motherNature.getPosition());
    System.out.println(motherNature.getPosition());
  }

}
