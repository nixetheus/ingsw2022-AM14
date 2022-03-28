package it.polimi.ingsw.model.effects;

import org.junit.Assert;
import org.junit.Test;

/**
 * GameEffectsTest testing the GameEffects class
 */
public class GameEffectsTest {

  GameEffects gameEffects;

  /**
   * Test for the applyEffect method
   */
  @Test
  public void applyEffectTest() {
    gameEffects = new GameEffects();
    gameEffects.applyEffect(0);
    Assert.assertTrue(gameEffects.getTakeProfessorEqual());
  }

  /**
   * Test for the expireEffect method
   */
  @Test
  public void expireEffectTest() {
    gameEffects = new GameEffects();
    gameEffects.applyEffect(0);
    gameEffects.expireEffect(0);
    Assert.assertFalse(gameEffects.getTakeProfessorEqual());
  }
}
