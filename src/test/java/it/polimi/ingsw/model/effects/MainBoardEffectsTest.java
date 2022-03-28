package it.polimi.ingsw.model.effects;

import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;

/**
 * MainBoardEffectsTest testing the MainBoardEffects class
 */
public class MainBoardEffectsTest {

  MainBoardEffects mainBoardEffects;
  boolean[] testArray;

  /**
   * Test for the applyEffect method
   */
  @Test
  public void applyEffectTest() {
    mainBoardEffects = new MainBoardEffects();
    testArray = new boolean[]{false, false, false, true, false, false};
    mainBoardEffects.applyEffect(3);
    Assert.assertEquals(Arrays.toString(mainBoardEffects.getPossibleEffects()),
        Arrays.toString(testArray));
  }

  /**
   * Test for the expireEffect method
   */
  @Test
  public void expireEffectTest() {
    mainBoardEffects = new MainBoardEffects();
    testArray = new boolean[]{false, false, false, false, false, false};
    mainBoardEffects.applyEffect(1);
    mainBoardEffects.expireEffect(1);
    Assert.assertEquals(Arrays.toString(mainBoardEffects.getPossibleEffects()),
        Arrays.toString(testArray));
  }
}
