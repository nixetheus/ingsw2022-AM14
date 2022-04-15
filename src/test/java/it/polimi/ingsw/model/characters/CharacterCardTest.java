package it.polimi.ingsw.model.characters;

import org.junit.Assert;
import org.junit.Test;

public class CharacterCardTest {

  /**
   * This method tests the costs methods for the CharacterCard class
   */
  @Test
  public void characterCostTest() {
    int cost = (int)(Math.random() * 100);
    CharacterCard testCard = new CharacterCard(null, cost, new int[5]);
    Assert.assertEquals(cost, testCard.getCost());
    testCard.increaseCost();
    Assert.assertEquals(cost + 1, testCard.getCost());
  }

}
