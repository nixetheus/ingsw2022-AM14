package it.polimi.ingsw.model.player;

import it.polimi.ingsw.exceptions.InvalidMoveException;
import it.polimi.ingsw.helpers.Places;
import java.io.FileNotFoundException;
import java.util.Arrays;
import junit.framework.TestCase;
import org.junit.Assert;


/**
 * Test for Player class
 */

public class PlayerTest extends TestCase {

  Player testPlayer;
  DiningRoom testDiningRoom;
  Entrance testEntrance;

  /**
   * Test for the playAssistant method to ensure that the played assistant is correct and that is no
   * longer available into the playableAssistants
   */


  public void testPlayAssistant() throws FileNotFoundException, InvalidMoveException {
    testPlayer = new Player(1, "ale");
    Assistant assistant1 = new Assistant(1, 2, 2);

    testPlayer.playAssistant(2);
    assertEquals(testPlayer.getAssistant().getAssistantId(), assistant1.getAssistantId());
    assertFalse(testPlayer.getPlayableAssistant().contains(testPlayer.getAssistant()));
  }

  /**
   * Test for the playAssistant method testing the case of an assistant already played
   *
   * @throws InvalidMoveException If the assistant is already played
   */
  public void testPlayAssistantAlreadyPlayed() throws FileNotFoundException, InvalidMoveException {
    testPlayer = new Player(1, "ale");
    InvalidMoveException exception = null;
    testPlayer.playAssistant(2);
    try {
      testPlayer.playAssistant(2);
    } catch (InvalidMoveException e) {
      exception = e;
    }
    Assert.assertNotNull(exception);
  }

  /**
   * Test for the moveTo playerBoard method in case of a student put into the entrance
   */

  public void testMoveToPlayerBoardEntranceCase() throws FileNotFoundException {
    testPlayer = new Player(2, "ale");
    testEntrance = new Entrance();
    testPlayer.moveToPlayerBoard(Places.ENTRANCE, 3);
    testEntrance.addStudent(3);
    assertEquals(Arrays.toString(testPlayer.getPlayerBoard().getEntrance().getStudents()),
        Arrays.toString(testEntrance.getStudents()));
  }

  /**
   * Test for the moveTo playerBoard method in case of a student put into the diningRoom
   *
   * @throws InvalidMoveException if the dining room is full
   */

  public void testMoveToPlayerBoardDiningCase() throws InvalidMoveException, FileNotFoundException {
    testPlayer = new Player(2, "ale");
    testDiningRoom = new DiningRoom();
    testPlayer.moveToPlayerBoard(Places.DINING_ROOM, 2);
    testDiningRoom.addStudent(2);
    assertEquals(Arrays.toString(testPlayer.getPlayerBoard().getDiningRoom().getStudents()),
        Arrays.toString(testDiningRoom.getStudents()));
  }

  /**
   * Test for the addCoin method
   */

  public void testAddCoin() throws FileNotFoundException {
    testPlayer = new Player(1, "marco");
    testPlayer.addCoin();
    assertEquals(testPlayer.getCoins(), 1);
  }
}
