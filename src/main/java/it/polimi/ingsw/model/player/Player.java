package it.polimi.ingsw.model.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.ingsw.helpers.Places;
import it.polimi.ingsw.model.board.StudentsBag;
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
   * @param studentsBag    Students put at the entrance
   */

  public Player(
      int playerId,
      String playerNickname,
      StudentsBag studentsBag,
      int numberOfStudentAtEntrance) throws FileNotFoundException {

    this.coins = 0;
    this.assistant = null;
    this.playerId = playerId;
    this.playerNickname = playerNickname;
    this.playableAssistants = new Vector<>();
    this.playerBoard = new PlayerBoard(studentsBag.pickRandomStudents(numberOfStudentAtEntrance));

    String jsonFile = "src/main/resources/json/assistants.json";
    JsonArray assistants = JsonParser.parseReader(new FileReader(jsonFile)).getAsJsonArray();

    for (Object assistant : assistants) {
      JsonObject object = (JsonObject) assistant;
      int moves = object.get("MOVES").getAsInt();
      int speed = object.get("SPEED").getAsInt();
      int assistantId = object.get("ASSISTANT_ID").getAsInt();
      playableAssistants.add(new Assistant(moves, speed, assistantId));
    }
  }

  /**
   * This method allows a player to play an Assistant, and it removes that assistant form the
   * playable ones
   *
   * @param assistantId used to identify which assistant has been played
   */
  public void playAssistant(int assistantId) {

    Assistant returnAssistant;
    returnAssistant = playableAssistants.stream()
        .filter(assistant -> assistant.getAssistantId() == assistantId).findFirst().get();

    this.assistant = returnAssistant;
    this.playableAssistants.remove(returnAssistant);
  }

  /**
   * This method is used to move a student to the entrance or to the dining room
   *
   * @param place used to identify where the student will be put in
   * @param color used to identify the color of the student
   */
  public void moveToPlayerBoard(Places place, int color) {
    if (place == Places.ENTRANCE) {
      playerBoard.moveToEntrance(color);
    } else if (place == Places.DINING_ROOM) {
      playerBoard.moveToDiningRoom(color);
    }
  }

  /**
   * This method adds a coin to the player "wallet"
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

  public Vector<Assistant> getPlayableAssistant() {
    return playableAssistants;
  }

}
