package it.polimi.ingsw.model;

import it.polimi.ingsw.helpers.Towers;
import it.polimi.ingsw.model.board.StudentsBag;
import it.polimi.ingsw.model.player.Player;
import java.io.FileNotFoundException;
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
    Player testPlayer = new Player(1, "Ale", studentsBag, studentAtEntrance);
    Team testTeam = new Team(0, Towers.WHITE.ordinal(), testPlayer);

    assert (!testTeam.getPlayers().isEmpty());
    assert (testTeam.getPlayers().contains(testPlayer));
  }

  /**
   * testAddPlayerTwice method tests the addition of a team member to the Team class
   */
  @Test
  public void testAddPlayerTwice() throws FileNotFoundException {
    Player testPlayer = new Player(1, "Luca", studentsBag, studentAtEntrance);
    Player testPlayerTwo = new Player(2, "Dario", studentsBag, studentAtEntrance);

    Team testTeam = new Team(0, Towers.WHITE.ordinal(), testPlayer);
    testTeam.addPlayer(testPlayerTwo);

    assert (!testTeam.getPlayers().isEmpty());
    assert (testTeam.getPlayers().contains(testPlayer));
    assert (testTeam.getPlayers().contains(testPlayerTwo));
    assert (testTeam.getPlayers().size() == 2);
  }

}
