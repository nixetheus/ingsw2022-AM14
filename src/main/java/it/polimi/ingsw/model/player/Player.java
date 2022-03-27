package it.polimi.ingsw.model.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.ingsw.exceptions.InvalidMoveException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Vector;


/**
 * The Player is the entity used to model the behaviour and action a person might do in the game.
 */
public class Player {

  private final int playerId;
  private final String playerNickname;
  private final PlayerBoard playerBoard;
  private final Vector<Assistant> playableAssistants;
  private Assistant assistant;
  private int coins;

  /**
   * Constructor method for the Player class
   *
   * @param playerId       Unique identifier for each player
   * @param playerNickname Unique name chosen by each player
   */

  public Player(int playerId, String playerNickname) throws FileNotFoundException {
    this.playerId = playerId;
    this.playerNickname = playerNickname;
    this.coins = 0;
    this.assistant = null;
    this.playerBoard = new PlayerBoard();
    this.playableAssistants = new Vector<>();
    JsonArray array = JsonParser
        .parseReader(new FileReader("src/main/resources/json/assistants.json")).getAsJsonArray();
    for (Object o : array) {
      JsonObject object = (JsonObject) o;
      int moves = object.get("MOVES").getAsInt();
      int speed = object.get("SPEED").getAsInt();
      int assistantId = object.get("ASSISTANT_ID").getAsInt();
      playableAssistants.add(new Assistant(moves, speed, assistantId));
    }


  }

  public Vector<Assistant> getPlayableAssistant() {
    return playableAssistants;
  }

  /**
   * This method allows a player to play an Assistant and it removes that assistant form the
   * playable ones
   *
   * @param assistantId used to identify which assistant is been played
   */
  public void playAssistant(int assistantId) throws InvalidMoveException {
    Assistant returnAssistant;
    if (playableAssistants.stream()
        .anyMatch(assistant1 -> assistant1.getAssistantId() == assistantId)) {
      returnAssistant = playableAssistants.stream()
          .filter(assistant1 -> assistant1.getAssistantId() == assistantId).findFirst().get();
    } else {
      throw new InvalidMoveException("TODO");
    }
    this.assistant = returnAssistant;
    this.playableAssistants.remove(returnAssistant);
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

  public Assistant getAssistant() {
    return assistant;
  }

  public PlayerBoard getPlayerBoard() {
    return playerBoard;
  }

}
