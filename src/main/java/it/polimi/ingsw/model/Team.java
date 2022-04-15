package it.polimi.ingsw.model;

import it.polimi.ingsw.helpers.Constants;
import it.polimi.ingsw.helpers.Towers;
import it.polimi.ingsw.model.player.Player;
import java.util.Vector;

/**
 * The Team is the entity used to model the behaviour of those playing the game. For two or three
 * players' games the entity acts as a capsule for a single player giving it a more complete
 * behaviour. In case of four players' game it acts as a grouping mechanism for each of the couple
 * of players.
 */
public class Team {

  // Attributes
  private final int id;
  private final Towers towersColor;
  private final Vector<Player> players;
  private int availableTowers;

  /**
   * Constructor method for the Team class
   *
   * @param teamId     Unique identifier for each team
   * @param towerColor Unique color of each team's tower
   */
  public Team(int teamId, Towers towerColor) {
    id = teamId;
    players = new Vector<>();
    towersColor = towerColor;
    availableTowers = Constants.getMaxTowers();
  }

  /**
   * This method adds a new Player to the Team
   *
   * @param playerToAdd New player to be added to the team
   */
  public void addPlayer(Player playerToAdd) {
    players.add(playerToAdd);
  }

  /**
   * This method gives numberOfTowers towers to the Team
   *
   * @param numberOfTowers Number of towers to give
   */
  public void addTowers(int numberOfTowers) {
    availableTowers += numberOfTowers;
  }

  /**
   * This method removes numberOfTowers towers from the Team
   *
   * @param numberOfTowers Number of towers to remove
   */
  public void removeTowers(int numberOfTowers) {
    availableTowers -= numberOfTowers;
  }

  public int getId() {
    return id;
  }

  public Towers getTowersColor() {
    return towersColor;
  }

  public int getAvailableTowers() {
    return availableTowers;
  }

  public Vector<Player> getPlayers() {
    return players;
  }

}
