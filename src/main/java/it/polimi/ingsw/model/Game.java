package it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.ingsw.helpers.Constants;
import it.polimi.ingsw.helpers.Effects;
import it.polimi.ingsw.helpers.Places;
import it.polimi.ingsw.helpers.Towers;
import it.polimi.ingsw.model.board.Island;
import it.polimi.ingsw.model.board.MainBoard;
import it.polimi.ingsw.model.board.StudentsBag;
import it.polimi.ingsw.model.characters.CharacterCard;
import it.polimi.ingsw.model.characters.GameCharacters;
import it.polimi.ingsw.model.characters.MainBoardCharacters;
import it.polimi.ingsw.model.characters.PlayerCharacters;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.Player;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;
import java.util.Random;
import java.util.Vector;

/**
 * Game class is TODO
 */
public class Game {

  private final MainBoard mainBoard;
  private final StudentsBag studentsBag;
  private final Vector<CloudTile> cloudTiles;
  private final Player[] professorControlPlayer;  // TODO
  private final Vector<CharacterCard> purchasableCharacter = new Vector<>();

  private int playerNumber;
  private Vector<Team> teams;
  private int playerTowerNumber;
  private int studentAtEntrance;
  private int currentPlayerIndex;
  private int studentOnCloudTiles;
  private Vector<Player> gameOrder;
  private int influenceEqualProfessors = 0;

  /**
   * Constructor por Game class
   *
   */
  public Game(Boolean isExpert, Vector<Team> teams) throws IOException {

    this.teams = teams;
    this.gameOrder = new Vector<>();

    for (Team team : teams) {
      gameOrder.addAll(team.getPlayers());
    }

    initializeGameParameter();

    this.cloudTiles = new Vector<>();
    this.studentsBag = new StudentsBag();
    this.mainBoard = new MainBoard(this.studentsBag);
    // TODO add students to players
    this.professorControlPlayer = new Player[Constants.getNColors()];

    createClouds();
    fillClouds();

    if (isExpert) {
      initializePurchasableCharacter();
    }
  }

  /**
   *
   */
  public Player getPlayerFromOrder(int playerPosition) {
    return this.gameOrder.elementAt(playerPosition);
  }

  /**
   *
   */
  public void orderBasedOnAssistant() {
    // TODO
  }

  public void reverseOrderEndTurn() {
    Collections.reverse(this.gameOrder);
  }

  public void setCurrentPlayerIndex(int currentPlayerIndex) {
    this.currentPlayerIndex = currentPlayerIndex;
  }

  public Player getCurrentPlayer() {
    return this.gameOrder.elementAt(this.currentPlayerIndex);
  }

  /**
   * CreateClouds is a method that creates the clouds for the beginning of the game
   */
  public void createClouds() {
    for (int cloudIndex = 0; cloudIndex < playerNumber; cloudIndex++) {
      CloudTile cloudTile = new CloudTile(cloudIndex);
      this.cloudTiles.add(cloudTile);
    }
  }

  /**
   * FillCloud is a method that fill the cloud tiles with the students of students required by the
   * game
   */
  public void fillClouds() {
    for (CloudTile cloudTile : cloudTiles) {
      cloudTile.fillCloud(studentsBag.pickRandomStudents(studentOnCloudTiles));
    }
  }

  /**
   * Method to move mother nature, and it calculates the influence to realize the join between the
   * islands
   *
   * @param movement The exact number of steps that motherNature does
   */
  public void moveNature(int movement) {
    mainBoard.moveMotherNature(movement);

    Island islandMotherNatureIn = mainBoard.getIslands()
        .get(mainBoard.getMotherNature().getPosition());

    islandMotherNatureIn.setOwner(
        mainBoard.calculateInfluence(professorControlPlayer, teams, islandMotherNatureIn));

    // TODO : JOIN ISLAND
  }

  /**
   * Method used to take a cloud and add the students at the active player entrance
   *
   * @param activePlayer  To know who is the active player
   * @param idCloudToTake To know which cloud to take
   */
  public void takeCloud(Player activePlayer, int idCloudToTake) {
    CloudTile cloudTile = cloudTiles.get(idCloudToTake);
    int[] students = cloudTile.emptyCloud();

    for (int color : students) {
      for (int student = 0; student < color; student++) {
        activePlayer.moveToPlayerBoard(Places.ENTRANCE, color);
      }
    }
  }


  /**
   * Method used to create and set the character available fore this match
   */
  public void initializePurchasableCharacter() {

    Random random = new Random();
    Vector<Effects> cardEffects = new Vector<>();
    while (cardEffects.size() < 3) {
      Effects effect = Effects.values()[random.nextInt(Effects.values().length)];
      if (!cardEffects.contains(effect))
        cardEffects.add(effect);
    }

    for (Effects effect : cardEffects) {
      int[] cardStudents = studentsBag.pickRandomStudents(effect.getNOfStudents());
      switch (effect.getType()) {
        case GAME:
          purchasableCharacter.add(new GameCharacters(effect, effect.getCost(), cardStudents));
          break;
        case PLAYER:
          purchasableCharacter.add(new PlayerCharacters(effect, effect.getCost(), cardStudents));
          break;
        case MAINBOARD:
          purchasableCharacter.add(new MainBoardCharacters(effect, effect.getCost(), cardStudents));
          break;
        default:
          break;
      }
    }
  }


  /**
   * This method reads the json and set them into the class attributes
   */
  public void initializeGameParameter() throws IOException {

    /*JsonArray gameParameter = JsonParser
        .parseReader(new FileReader("src/main/resources/json/gameParameters.json"))
        .getAsJsonArray();

    for (Object o : gameParameter) {
      if (mode == jasonIndex) {
        JsonObject object = (JsonObject) o;
        this.playerNumber = object.get("PLAYER_NUMBER").getAsInt();
        this.playerTowerNumber = object.get("NUMBER_OF_TOWER_FOR_A_TEAM").getAsInt();
        this.studentAtEntrance = object.get("STUDENT_AT_THE_ENTRANCE").getAsInt();
        this.studentOnCloudTiles = object.get("STUDENTS_ON_EACH_CLOUD_TILE").getAsInt();
      }
      jasonIndex++;
    }*/
  }

  /**
   * Method used to move student from one place to another
   *
   * @param activePlayer To know which is the current player
   * @param placeToTake  Where the student is taken from
   * @param placeToAdd   Where the student is put int
   * @param color        Which is the color of the student
   * @param islandNumber To know on which island put the student
   */
  public void moveStudent(Player activePlayer, Places placeToTake, Places placeToAdd, int color,
      Optional<Integer> islandNumber) {

    if (placeToAdd == Places.DINING_ROOM && placeToTake == Places.ENTRANCE) {
      activePlayer.moveToPlayerBoard(Places.DINING_ROOM, color);
      activePlayer.getPlayerBoard().getEntrance().removeStudent(color);
      giveProfessorToPlayer(color);
    } else if (placeToAdd == Places.ISLAND && placeToTake == Places.ENTRANCE) {
      mainBoard.addToIsland(color, islandNumber.get());
      activePlayer.getPlayerBoard().getEntrance().removeStudent(color);
    }
  }

  /**
   * This method allows to play a certain assistant card
   *
   * @param activePlayer The player that is playing the card
   * @param assistantId  The id of the assistant that is played
   * @return The played assistant
   */
  public Assistant playAssistant(Player activePlayer, int assistantId) {
    activePlayer.playAssistant(assistantId);
    return activePlayer.getAssistant();
  }

  /**
   * This method allows to play a certain character card
   *
   * @param activePlayer The player that is playing the card
   * @param characterIndex The index of the character that is played
   */
  public void playCharacter(Player activePlayer, int characterIndex) {
    activePlayer.removeCoins(purchasableCharacter.elementAt(characterIndex).getCost());
    purchasableCharacter.elementAt(characterIndex).increaseCost();
  }

  /**
   * This method give the control of a professor to the player with the max number of students of
   * that color in his dining room
   *
   * @param color The color of the student added to a player dining room
   */
  public void giveProfessorToPlayer(int color) {
    int studentPerColor;
    int maxStudentColor = 0;
    Player playerTakesProfessor = null;

    if (professorControlPlayer[color] != null) {
      maxStudentColor = professorControlPlayer[color].getPlayerBoard().getDiningRoom()
          .getStudents()[color];
      playerTakesProfessor = professorControlPlayer[color];
    }

    for (Team team : teams) {
      for (Player player : team.getPlayers()) {

        studentPerColor = player.getPlayerBoard().getDiningRoom().getStudents()[color];
        // TODO: current player?
        // if (player.equals(this.pla)) {
        // studentPerColor += this.influenceEqualProfessors;
        //}

        if (studentPerColor > maxStudentColor) {
          playerTakesProfessor = player;
          maxStudentColor = studentPerColor;
        }

      }
    }

    this.professorControlPlayer[color] = playerTakesProfessor;
  }

  public Vector<CloudTile> getCloudTiles() {
    return cloudTiles;
  }

  public MainBoard getMainBoard() {
    return mainBoard;
  }

  public Vector<Team> getTeams() {
    return teams;
  }

  public Player[] getProfessorControlPlayer() {
    return professorControlPlayer;
  }

  public Vector<CharacterCard> getPurchasableCharacter() {
    return purchasableCharacter;
  }

  public int getPlayerTowerNumber() {
    return playerTowerNumber;
  }

  public int getStudentAtEntrance() {
    return studentAtEntrance;
  }

  public int getStudentOnCloudTiles() {
    return studentOnCloudTiles;
  }

  public int getPlayerNumber() {
    return playerNumber;
  }

  public StudentsBag getStudentsBag() {
    return studentsBag;
  }

  public void setInfluenceEqualProfessors(int influenceEqualProfessors) {
    this.influenceEqualProfessors = influenceEqualProfessors;
  }

  public int getInfluenceEqualProfessors() {
    return this.influenceEqualProfessors;
  }

}
