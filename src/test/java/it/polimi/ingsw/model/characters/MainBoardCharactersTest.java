package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.helpers.Color;
import it.polimi.ingsw.helpers.Constants;
import it.polimi.ingsw.helpers.Effects;
import it.polimi.ingsw.model.board.MainBoard;
import it.polimi.ingsw.model.board.StudentsBag;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;

public class MainBoardCharactersTest {

  CharacterStruct params = new CharacterStruct();

  StudentsBag studentsBagTest = new StudentsBag();
  MainBoard mainBoardTest = new MainBoard(studentsBagTest);

  public MainBoardCharactersTest() {
    params.mainBoard = mainBoardTest;
    params.numIsland = (int) (Math.random() * Constants.getInitialNumIslands());
    params.color = Color.YELLOW;
  }

  /**
   * This method tests the Character Card that takes one student from this card and puts it in one
   * Island
   */
  @Test
  public void takeStudentPutIslandEffectTest() {
    CharacterCard takeStudentPutIsland = new MainBoardCharacters(
        Effects.TAKE_STUDENT_PUT_ISLAND, Effects.TAKE_STUDENT_PUT_ISLAND.getCost(),
        studentsBagTest.pickRandomStudents(5));

    int[] arrayTest = params.mainBoard.getIslands().get(params.numIsland).getStudents();
    arrayTest[params.color.ordinal()] += 1;

    takeStudentPutIsland.applyEffect(params);
    Assert.assertEquals(Arrays.toString(arrayTest),
        Arrays.toString(params.mainBoard.getIslands().get(params.numIsland).getStudents()));

    takeStudentPutIsland.removeEffect(params);  // Should not have effect
    Assert.assertEquals(Arrays.toString(arrayTest),
        Arrays.toString(params.mainBoard.getIslands().get(params.numIsland).getStudents()));
  }

  /**
   * This method tests that the no Entry property is applied and removed correctly from an Island
   */
  @Test
  public void EntryNatureEffectTest() {
    CharacterCard noEntryNature = new MainBoardCharacters(
        Effects.NO_ENTRY_NATURE, Effects.NO_ENTRY_NATURE.getCost(), new int[0]);
    noEntryNature.applyEffect(params);
    Assert.assertTrue(params.mainBoard.getIslands().get(params.numIsland).isNoEntry());
    noEntryNature.removeEffect(params);
    Assert.assertFalse(params.mainBoard.getIslands().get(params.numIsland).isNoEntry());
  }

  /**
   * This method tests that towers are not counted if the correspondent Character Card was activated
   * and the deactivation of this effect
   */
  @Test
  public void noTowersInfluenceEffectTest() {
    CharacterCard noTowersInfluence = new MainBoardCharacters(
        Effects.NO_TOWERS_INFLUENCE, Effects.NO_TOWERS_INFLUENCE.getCost(), new int[0]);
    noTowersInfluence.applyEffect(params);
    Assert.assertFalse(params.mainBoard.areTowersCounted());
    noTowersInfluence.removeEffect(params);
    Assert.assertTrue(params.mainBoard.areTowersCounted());
  }

  /**
   * This method tests the Character Card that adds plus two to the player influence
   */
  @Test
  public void addTwoInfluenceEffectTest() {
    CharacterCard addTwoInfluence = new MainBoardCharacters(
        Effects.ADD_TWO_INFLUENCE, Effects.ADD_TWO_INFLUENCE.getCost(), new int[0]);
    addTwoInfluence.applyEffect(params);
    Assert.assertEquals(params.mainBoard.getInfluencePlus(), 2);
    addTwoInfluence.removeEffect(params);
    Assert.assertEquals(params.mainBoard.getInfluencePlus(), 0);
  }

  /**
   * This method tests the Character Card that removes a color's influence by checking a parameter
   */
  @Test
  public void noInfluenceColorEffectTest() {
    CharacterCard noInfluenceColor = new MainBoardCharacters(
        Effects.NO_INFLUENCE_COLOR, Effects.NO_INFLUENCE_COLOR.getCost(), new int[0]);
    noInfluenceColor.applyEffect(params);
    Assert.assertEquals(params.mainBoard.getForbiddenColor(), params.color);
    noInfluenceColor.removeEffect(params);
    Assert.assertNull(params.mainBoard.getForbiddenColor());
  }

  /**
   * Test for default effect (exception thrown)
   */
  @Test(expected = IllegalStateException.class)
  public void defaultMBEffectTest() {
    CharacterCard defaultMBEffect = new MainBoardCharacters(Effects.DEFAULT_EFFECT, 0, new int[5]);
    defaultMBEffect.applyEffect(new CharacterStruct());
  }

  /**
   * Test for default remove effect (exception thrown)
   */
  @Test(expected = IllegalStateException.class)
  public void defaultRemoveMBEffectTest() {
    CharacterCard defaultRemoveMBEffect = new MainBoardCharacters(Effects.DEFAULT_EFFECT, 0,
        new int[5]);
    defaultRemoveMBEffect.removeEffect(new CharacterStruct());
  }

}
