package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidMoveException;
import it.polimi.ingsw.model.board.StudentsBag;
import it.polimi.ingsw.model.effects.MainBoardEffects;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;

/**
 * The CharacterCardTest class tests the CharacterCard class
 */
public class CharacterCardTest {

  private CharacterCard testCharacterCard;
  private StudentsBag studentsBag;

  /**
   * TestIncreaseCost tests the increaseCost method
   */
  @Test
  public void testIncreaseCost() {
    studentsBag = new StudentsBag();
    testCharacterCard = new CharacterCard(1, 2, 3, studentsBag, 4, 2, 0, false);
    testCharacterCard.increaseCost();
    Assert.assertEquals(testCharacterCard.getCost(), 3);
  }

  /**
   * TestPlaceNoEntry tests the placeNoEntry method
   */
  @Test
  public void testPlaceNoEntry() throws InvalidMoveException {
    studentsBag = new StudentsBag();
    testCharacterCard = new CharacterCard(1, 2, 3, studentsBag, 4, 2, 1, false);
    testCharacterCard.placeNoEntry();
    Assert.assertEquals(testCharacterCard.getNoEntryTile(), 0);
  }

  /**
   * TestPlaceNoEntry tests the placeNoEntry method with 0 no entry tile left
   */
  @Test
  public void testPlaceNoEntryZeroLeft() {
    studentsBag = new StudentsBag();
    testCharacterCard = new CharacterCard(1, 2, 3, studentsBag, 4, 2, 0, false);
    Exception exception = null;
    try {
      testCharacterCard.placeNoEntry();
    } catch (InvalidMoveException e) {
      exception = e;
    }
    Assert.assertNotNull(exception);
  }

  /**
   * TestActivateEffect( tests the ActivateEffect method to check if the correct class is modified
   * and the correct affect will be activated
   */
  @Test
  public void testActivateEffect() {
    studentsBag = new StudentsBag();
    testCharacterCard = new CharacterCard(1, 2, 3, studentsBag, 4, 2, 0, false);
    MainBoardEffects mainBoardEffects = new MainBoardEffects();
    mainBoardEffects.applyEffect(4);
    Assert.assertEquals(testCharacterCard.getEffect().getClass(), mainBoardEffects.getClass());
    testCharacterCard.activateEffect();
    mainBoardEffects.applyEffect(4);
    //TODO

  }

  /**
   * TestRemoveStudent tests the RemoveStudent method with no replacement
   */
  @Test
  public void testRemoveStudentNoReplacement() {
    studentsBag = new StudentsBag();
    testCharacterCard = new CharacterCard(1, 2, 3, studentsBag, 4, 2, 0, false);
    int[] studentToSet = new int[]{0, 0, 1, 0, 0, 0};
    testCharacterCard.setStudents(studentToSet);
    testCharacterCard.removeStudent(2, studentsBag);
    int[] arrayTest = new int[]{0, 0, 0, 0, 0, 0};

    Assert
        .assertEquals(Arrays.toString(testCharacterCard.getStudents()), Arrays.toString(arrayTest));
  }

  /**
   * TestRemoveStudent tests the RemoveStudent method with replacement
   */
  @Test
  public void testRemoveStudentWithReplacement() {
    studentsBag = new StudentsBag();
    testCharacterCard = new CharacterCard(1, 2, 3, studentsBag, 4, 2, 0, true);
    Assert.assertEquals(Arrays.stream(testCharacterCard.getStudents()).sum(), 3);
  }

}
