package it.polimi.ingsw.model.player;

import java.util.NoSuchElementException;
import java.util.Vector;

/**
 * The Player is the entity used to model the behaviour and action a person might do in the game.
 */
public class Player {

  private final int playerId;
  private final String playerNickname;
  private final PlayerBoard playerBoard;
  private final Vector<Assistent> playableAssistent;
  private Assistent assistent;
  private int coins;

  /**
   * Constructor method for the Player class
   *
   * @param playerId       Unique identifier for each player
   * @param playerNickname Unique name chosen by each player
   */

  public Player(int playerId, String playerNickname) {
    this.playerId = playerId;
    this.playerNickname = playerNickname;
    this.coins = 0;
    this.assistent = null;
    this.playerBoard = new PlayerBoard();
    this.playableAssistent = new Vector<>();
    this.playableAssistent.add(new Assistent(1,2,1));
    this.playableAssistent.add(new Assistent(1,2,2));
    this.playableAssistent.add(new Assistent(1,2,3));
    this.playableAssistent.add(new Assistent(1,2,4));



  }

  public Vector<Assistent> getPlayableAssistent() {
    return playableAssistent;
  }

  /**
   * This method allows a player to play an Assistant and it removes that assistant form the
   * playable ones
   *
   * @param assistantId used to identify which assistant is been played
   */
  public void playAssistent(int assistantId) throws NoSuchElementException {
    Assistent returnAssistant = playableAssistent.stream()
        .filter(assistent1 -> assistent1.getAssistantId() == assistantId).findFirst().get();
    this.assistent = returnAssistant;
    this.playableAssistent.remove(returnAssistant);
  }

  /**
   * This method is used to move a student to the entrance or to the dining room
   *
   * @param location used to identify where the student will be put in (0 for the entrance 1 for
   *                 dining room)
   * @param color    used to identify the color of the student
   */
  public void moveToPlayerBoard(int location, int color) {
    if (location == 0) {
      playerBoard.moveToEntrance(color);
    } else {
      playerBoard.moveToDiningRoom(color);
    }
  }

  /**
   * This method add a coin to the player "wallet"
   */
  public void addCoin() {
    this.coins++;
  }

  public int getCoins() {
    return coins;
  }

  public int getPlayerId() {
    return playerId;
  }

  public String getPlayerNickname() {
    return playerNickname;
  }

  public Assistent getAssistent() {
    return assistent;
  }

  public PlayerBoard getPlayerBoard() {
    return playerBoard;
  }

}
