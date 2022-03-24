package it.polimi.ingsw.model;

import it.polimi.ingsw.helpers.Towers;
import it.polimi.ingsw.model.player.Player;
import org.junit.Test;

/**
 * TeamTest class tests Team class
 */
public class TeamTest {

  /**
   * testAddPlayer method tests the initialization of the Team class with one player
   */
  @Test
  public void testAddPlayer() {
    Player testPlayer = new Player();
    Team testTeam = new Team(0, Towers.WHITE.ordinal(), testPlayer);

    assert (!testTeam.getPlayers().isEmpty());
    assert (testTeam.getPlayers().contains(testPlayer));
  }

  /**
   * testAddPlayerTwice method tests the addition of a team member to the Team class
   */
  @Test
  public void testAddPlayerTwice() {
    Player testPlayer = new Player();
    Player testPlayerTwo = new Player();

    Team testTeam = new Team(0, Towers.WHITE.ordinal(), testPlayer);
    testTeam.addPlayer(testPlayerTwo);

    assert (!testTeam.getPlayers().isEmpty());
    assert (testTeam.getPlayers().contains(testPlayer));
    assert (testTeam.getPlayers().contains(testPlayerTwo));
    assert (testTeam.getPlayers().size() == 2);
  }

}
