package it.polimi.ingsw.model.player;

import it.polimi.ingsw.exceptions.InvalidMoveException;
import it.polimi.ingsw.helpers.Places;
import it.polimi.ingsw.model.board.StudentsBag;
import java.io.FileNotFoundException;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;


/**
 * Test for Player class
 */

public class PlayerTest {

  Player testPlayer;
  DiningRoom testDiningRoom;
  StudentsBag studentsBag = new StudentsBag();

  /**
   * Test for the playAssistant method to ensure that the played assistant is correct and that is no
   * longer available into the playableAssistants
   */

  @Test
  public void testPlayAssistant() throws FileNotFoundException, InvalidMoveException {
    testPlayer = new Player(1, "ale", studentsBag, 2);
    Assistant assistant1 = new Assistant(1, 2, 2);

    testPlayer.playAssistant(2);
    Assert.assertEquals(testPlayer.getAssistant().getAssistantId(), assistant1.getAssistantId());
    Assert.assertFalse(testPlayer.getPlayableAssistant().contains(testPlayer.getAssistant()));
  }

  /**
   * Test for the playAssistant method testing the case of an assistant already played
   *
   * @throws InvalidMoveException If the assistant is already played
   */
  @Test
  public void testPlayAssistantAlreadyPlayed() throws FileNotFoundException, InvalidMoveException {
    /*testPlayer = new Player(1, "ale", studentsBag, 2);
    InvalidMoveException exception = null;
    testPlayer.playAssistant(2);
    testPlayer.playAssistant(2);
    Assert.assertNotNull(exception);*/
    // TODO LUCA: broken
  }

  /**
   * Test for the moveTo playerBoard method in case of a student put into the entrance,that ensures
   * the student will be correctly added in the right place
   */
  @Test
  public void testMoveToPlayerBoardEntranceCase() throws FileNotFoundException {
    testPlayer = new Player(2, "ale", studentsBag, 2);
    testPlayer.moveToPlayerBoard(Places.ENTRANCE, 3);
    Assert
        .assertEquals(Arrays.stream(testPlayer.getPlayerBoard().getEntrance().getStudents()).sum(),
            3);
  }

  /**
   * Test for the moveTo playerBoard method in case of a student put into the diningRoom,that
   * ensures the student will be correctly added in the right place
   *
   * @throws InvalidMoveException if the dining room is full
   */
  @Test
  public void testMoveToPlayerBoardDiningCase() throws InvalidMoveException, FileNotFoundException {
    testPlayer = new Player(2, "ale", studentsBag, 2);
    testDiningRoom = new DiningRoom();
    testPlayer.moveToPlayerBoard(Places.DINING_ROOM, 2);
    testDiningRoom.addStudent(2);
    Assert.assertEquals(Arrays.toString(testPlayer.getPlayerBoard().getDiningRoom().getStudents()),
        Arrays.toString(testDiningRoom.getStudents()));
  }

  /**
   * Test for the addCoin method, it ensures that a player will receive a coin in the right way
   */
  @Test
  public void testAddCoin() throws FileNotFoundException {
    testPlayer = new Player(1, "marco", studentsBag, 2);
    testPlayer.addCoin();
    Assert.assertEquals(testPlayer.getCoins(), 1);
  }
}
