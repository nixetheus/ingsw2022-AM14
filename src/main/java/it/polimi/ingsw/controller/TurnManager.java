package it.polimi.ingsw.controller;

import it.polimi.ingsw.helpers.GamePhases;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.player.Player;
import java.util.Vector;


/**
 * Turn manager class control and manage the turn of a player
 */
public class TurnManager {


  /**
   * Constructor method for TurnManager class
   */
  public TurnManager() {
  }


  /**
   * This method change the current turn phase
   */
  public GamePhases nextPhase(GamePhases currentPhase) {
    if (currentPhase.equals(GamePhases.PLANNING)) {
      currentPhase = GamePhases.MOVE_STUDENTS;
    } else if (currentPhase.equals(GamePhases.MOVE_STUDENTS)) {
      currentPhase = GamePhases.MOVE_MOTHER_NATURE;
    } else if (currentPhase.equals(GamePhases.MOVE_MOTHER_NATURE)) {
      currentPhase = GamePhases.CLOUD_TILE_PHASE;
    }
    return currentPhase;
  }

  /**
   * This method sets the gameOrder from the maximum speed to the minimum one
   *
   * @return The gameOrder calculated
   */
  public Vector<Integer> setGameOrder(Game currentGame) {
    Vector<Integer> playerOrderId = new Vector<>();
    int maxSpeed;
    int idPlayerMaxSpeed = 0;
    int speed;
    while (playerOrderId.size() != currentGame.getPlayerNumber()) {
      maxSpeed = 0;

      for (Team team : currentGame.getTeams()) {
        for (Player player : team.getPlayers()) {

          if (!playerOrderId.contains(player.getPlayerId())) {
            speed = player.getAssistant().getSpeed();

            if (speed > maxSpeed) {
              maxSpeed = speed;
              idPlayerMaxSpeed = player.getPlayerId();

            }
          }

        }
      }

      playerOrderId.add(idPlayerMaxSpeed);

    }
    return playerOrderId;
  }

  /**
   * This method changes the active player
   *
   * @param playerOrderId  The current round order
   * @param activePlayerId The current active player
   * @return the new active player
   */
  public int nextTurn(Vector<Integer> playerOrderId, int activePlayerId) {
    int previousIndexActivePlayer = playerOrderId.indexOf(activePlayerId);
    if (activePlayerId == playerOrderId.lastElement()) {
      this.nextRound();
      return -1;
    } else {
      activePlayerId = playerOrderId.elementAt(previousIndexActivePlayer + 1);
    }
    return activePlayerId;
  }

  /**
   * This method set the current phase to the first one and creates the played assistant vector
   */
  public GamePhases nextRound() {
    //send message to the first player in order to play the first assistant
    return GamePhases.PLANNING;
  }


}
