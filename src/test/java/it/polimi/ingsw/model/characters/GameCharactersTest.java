package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.helpers.Constants;
import it.polimi.ingsw.helpers.Effects;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.MainBoard;
import java.io.FileNotFoundException;
import org.junit.Test;

public class GameCharactersTest {


  /**
   * This method tests the Character Card that allows the current player to take control of a
   * professor even if their number of students match that of someone else
   */
  @Test
  public void takeProfessorEqualEffectTest() throws FileNotFoundException {
    /*Game gameTest = new Game(Constants.getTwoPlayerMode(), true);
    CharacterStruct params = new CharacterStruct(); params.currentGame = gameTest;

    CharacterCard takeProfessorEqualCard = new GameCharacters(Effects.TAKE_PROFESSOR_EQUAL,
        Effects.TAKE_PROFESSOR_EQUAL.getCost(), new int[5]);

    // TODO: setup players to have same number of students

    takeProfessorEqualCard.applyEffect(params);
    // TODO: assert
    takeProfessorEqualCard.removeEffect(params);
    // TODO: assert*/
  }

  /**
   * Test for default effect (exception thrown)
   */
  //@Test(expected = IllegalStateException.class)
  public void defaultGameEffectTest() {
    /*CharacterCard defaultGameEffect = new GameCharacters(Effects.DEFAULT_EFFECT, 0, new int[5]);
    defaultGameEffect.applyEffect(new CharacterStruct());*/
  }

  /**
   * Test for default remove effect (exception thrown)
   */
  //@Test(expected = IllegalStateException.class)
  public void defaultRemoveGameEffectTest() {
    /*CharacterCard defaultRemoveGameEffect = new GameCharacters(Effects.DEFAULT_EFFECT, 0,
        new int[5]);
    defaultRemoveGameEffect.removeEffect(new CharacterStruct());*/
  }

}
