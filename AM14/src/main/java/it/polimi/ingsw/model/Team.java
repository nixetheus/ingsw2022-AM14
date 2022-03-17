package it.polimi.ingsw.model;

import java.util.Vector;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.helpers.Constants;
import it.polimi.ingsw.model.player.Player;

/**
 * Team is the entity used to... TODO
 */
public class Team {

  // Attributes
  int id;
  int towersColor;
  int availableTowers;
  Vector<Player> players = new Vector<Player>();

  /**
   * TODO
   *
   * @param teamId Unique identifier for each team
   * @param towerColor Unique color of each team's tower
   * @param firstPlayer First player of each team
   * @throws InvalidGameObjectException Throws InvalidGameObjectException if Player parameter
   *    has invalid attributes
   */
  public Team(int teamId, int towerColor, Player firstPlayer) throws InvalidGameObjectException {
    id = teamId;
    towersColor = towerColor;
    availableTowers = Constants.getMaxTowers();
    addPlayer(firstPlayer);
  }

  /**
   * Adds a new Player to the Team
   *
   * @param playerToAdd New player to be added to the team
   * @throws java.security.InvalidParameterException
   * Throws InvalidParameterException if Player parameter has invalid attributes
   */
  public void addPlayer(Player playerToAdd) throws InvalidGameObjectException {

    // Check player integrity
    if (isPlayerOkay(playerToAdd)) {
      players.add(playerToAdd);
    } else {
      throw new InvalidGameObjectException("Player object is corrupted!");
    }
  }

  /**
   * Gives numberOfTowers towers to the Team
   *
   * @param numberOfTowers Number of towers to give
   * @throws InvalidMoveException Throws InvalidMoveException if the number of towers would be
   *    greater than the maximum allowed by rules
   */
  public void addTowers(int numberOfTowers) throws InvalidMoveException {
    if (availableTowers + numberOfTowers <= Constants.getMaxTowers()) {
      availableTowers += numberOfTowers;
    } else {
      throw new InvalidMoveException("N. of towers can't exceed " + Constants.getMaxTowers());
    }
  }

  /**
   * Removes numberOfTowers towers from the Team
   *
   * @param numberOfTowers Number of towers to remove
   * @throws InvalidMoveException Throws InvalidMoveException if the number of towers would be
   *    less than zero
   */
  public void removeTowers(int numberOfTowers) throws InvalidMoveException {
    if (availableTowers - numberOfTowers >= 0) {
      availableTowers -= numberOfTowers;
    } else {
      throw new InvalidMoveException("N. of towers can't be less than 0!");
    }
  }

  public int getId() {
    return id;
  }

  public int getTowersColor() {
    return towersColor;
  }

  public int getAvailableTowers() {
    return availableTowers;
  }

  public Vector<Player> getPlayers() {
    return players;
  }

  /**
   * TODO
   *
   * @param playerToCheck Player objects to be checked for integrity
   * @return Returns true if Player is within rules, false otherwise
   */
  private boolean isPlayerOkay(Player playerToCheck) {
    // TODO
    return true;
  }
}
