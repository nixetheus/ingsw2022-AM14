package it.polimi.ingsw.model.board;

import it.polimi.ingsw.helpers.Constants;
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

    int testLimitMoves = 999999;
    MotherNature motherNature = new MotherNature(startPlace);
    Assert.assertEquals(startPlace, motherNature.getPosition());

    motherNature.move((startPlace+3)% Constants.getInitialNumIslands());
    Assert.assertEquals((startPlace + 3) % Constants.getInitialNumIslands(), motherNature.getPosition());

    // Moves > islands

    motherNature = new MotherNature(startPlace);
    motherNature.move((startPlace+testLimitMoves)% Constants.getInitialNumIslands());
    Assert.assertEquals((startPlace + testLimitMoves) % Constants.getInitialNumIslands(),
        motherNature.getPosition());
  }

}
