package it.polimi.ingsw.model.board;

import org.junit.Assert;
import org.junit.Test;

/**
 * MotherNatureTest class tests MotherNature Class
 */
public class MotherNatureTest {

  private final int startPlace = MainBoard.pickStartPlaceMotherNature();

  /**
   * Testing the move Method with both normal moves and moves grater than islandNumber
   */
  @Test
  public void testMoves() {

    int numIslands = 12;
    int testLimitMoves = 999999;
    MotherNature motherNature = new MotherNature(startPlace);
    Assert.assertEquals(startPlace, motherNature.getPosition());

    motherNature.move(3, numIslands);
    Assert.assertEquals((startPlace + 3) % numIslands, motherNature.getPosition());

    // Moves > islands
    numIslands--;
    motherNature = new MotherNature(startPlace);
    motherNature.move(testLimitMoves, numIslands);
    Assert.assertEquals((startPlace + testLimitMoves) % numIslands,
        motherNature.getPosition());
  }

}
