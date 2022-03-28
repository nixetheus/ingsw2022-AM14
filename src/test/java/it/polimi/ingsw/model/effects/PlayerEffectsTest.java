package it.polimi.ingsw.model.effects;


import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;

/**
 * PlayerEffectsTest testing the PlayerEffects class
 */
public class PlayerEffectsTest {

  PlayerEffects playerEffects;
  Boolean[] testArray;

  /**
   * Test for the applyEffect method
   */
  @Test
  public void applyEffectTest() {
    playerEffects = new PlayerEffects();
    testArray = new Boolean[]{false, true, false, false, false};
    playerEffects.applyEffect(1);
    Assert.assertEquals(Arrays.toString(playerEffects.getPossibleEffects()),
        Arrays.toString(testArray));

  }

  /**
   * Test for the expireEffect method
   */
  @Test
  public void expireEffectTest() {
    playerEffects = new PlayerEffects();
    testArray = new Boolean[]{false, false, false, false, false};
    playerEffects.applyEffect(4);
    playerEffects.expireEffect(4);

    Assert.assertEquals(Arrays.toString(playerEffects.getPossibleEffects()),
        Arrays.toString(testArray));
  }
}

