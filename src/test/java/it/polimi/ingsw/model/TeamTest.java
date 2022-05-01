package it.polimi.ingsw.model;

import it.polimi.ingsw.helpers.Constants;
import it.polimi.ingsw.helpers.Towers;
import it.polimi.ingsw.model.board.StudentsBag;
import it.polimi.ingsw.model.player.Player;
import java.io.FileNotFoundException;
import org.junit.Assert;
import org.junit.Test;

/**
 * TeamTest class tests Team class
 */
public class TeamTest {

  private final StudentsBag studentsBag = new StudentsBag();
  private final int studentAtEntrance = 0;

  /**
   * testAddPlayer method tests the initialization of the Team class with one player
   */
  @Test
  public void testAddPlayer() throws FileNotFoundException {
    Player testPlayer = new Player(1, "Ale");
    testPlayer.initializePlayerBoard(studentsBag.pickRandomStudents(studentAtEntrance));
    Team testTeam = new Team(0, Towers.WHITE);
    testTeam.addPlayer(testPlayer);

    assert (!testTeam.getPlayers().isEmpty());
    assert (testTeam.getPlayers().contains(testPlayer));
  }

  /**
   * testAddPlayerTwice method tests the addition of a team member to the Team class
   */
  @Test
  public void testAddPlayerTwice() throws FileNotFoundException {
    Player testPlayer = new Player(1, "Luca");
    testPlayer.initializePlayerBoard(studentsBag.pickRandomStudents(studentAtEntrance));
    Player testPlayerTwo = new Player(2, "Dario");
    testPlayerTwo.initializePlayerBoard(studentsBag.pickRandomStudents(studentAtEntrance));

    Team testTeam = new Team(0, Towers.WHITE);
    testTeam.addPlayer(testPlayer);
    testTeam.addPlayer(testPlayerTwo);

    assert (!testTeam.getPlayers().isEmpty());
    assert (testTeam.getPlayers().contains(testPlayer));
    assert (testTeam.getPlayers().contains(testPlayerTwo));
    assert (testTeam.getPlayers().size() == 2);
  }

  /**
   * testAddPlayerTwice method tests the addition and removal of towers from a team
   */
  @Test
  public void addTowersTest() {

    int toAdd = (int) (Math.random() * 100);
    int toRemove = Math.min((int) (Math.random() * 100), Constants.getMaxTowers() + toAdd);

    Team testTeam = new Team(0, Towers.WHITE);
    Assert.assertEquals(Constants.getMaxTowers(), testTeam.getAvailableTowers());
    testTeam.addTowers(toAdd);
    Assert.assertEquals(Constants.getMaxTowers() + toAdd, testTeam.getAvailableTowers());
    testTeam.removeTowers(toRemove);
    Assert.assertEquals(
        Constants.getMaxTowers() + toAdd - toRemove,
        testTeam.getAvailableTowers());
  }

}
