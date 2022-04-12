package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.helpers.Effects;
import it.polimi.ingsw.model.board.MainBoard;
import it.polimi.ingsw.model.board.StudentsBag;
import org.junit.Test;

public class CharactersTest {

  // MAIN BOARD CHARACTERS TEST

  /**
   *
   */
  @Test
  public void noTowerInfluenceTest() {

    StudentsBag testBag = new StudentsBag();
    MainBoard boardTest = new MainBoard(testBag);
    MainBoardCharacters noTowersCardTest = new MainBoardCharacters(Effects.NO_TOWERS_INFLUENCE,
        Effects.NO_TOWERS_INFLUENCE.getCost());

    noTowersCardTest.applyEffect(boardTest);
    // TODO Assert What?
  }

}
