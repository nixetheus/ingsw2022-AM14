package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.helpers.Effects;
import org.junit.Test;

public class GameCharactersTest {

  /**
   * Test for default effect (exception thrown)
   */
  @Test(expected = IllegalStateException.class)
  public void defaultGameEffectTest() {
    CharacterCard defaultGameEffect = new GameCharacters(Effects.DEFAULT_EFFECT, 0, new int[5]);
    defaultGameEffect.applyEffect(new CharacterStruct());
  }

  /**
   * Test for default remove effect (exception thrown)
   */
  @Test(expected = IllegalStateException.class)
  public void defaultRemoveGameEffectTest() {
    CharacterCard defaultRemoveGameEffect = new GameCharacters(Effects.DEFAULT_EFFECT, 0, new int[5]);
    defaultRemoveGameEffect.removeEffect(new CharacterStruct());
  }

}
