package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.helpers.Effects;
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

  /**
   * Test for default effect (exception thrown)
   */
  @Test(expected = IllegalStateException.class)
  public void defaultEffectTest() {
    CharacterCard defaultEffect = new CharacterCard(Effects.DEFAULT_EFFECT, 0, new int[5]);
    defaultEffect.applyEffect(new CharacterStruct());
  }

  /**
   * Test for default remove effect (exception thrown)
   */
  @Test(expected = IllegalStateException.class)
  public void defaultRemoveEffectTest() {
    CharacterCard defaultRemoveEffect = new CharacterCard(Effects.DEFAULT_EFFECT, 0, new int[5]);
    defaultRemoveEffect.removeEffect(new CharacterStruct());
  }

}
