package it.polimi.ingsw.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.ingsw.helpers.Color;
import it.polimi.ingsw.helpers.Constants;
import it.polimi.ingsw.helpers.Places;
import it.polimi.ingsw.model.board.Island;
import it.polimi.ingsw.model.board.MainBoard;
import it.polimi.ingsw.model.board.StudentsBag;
import it.polimi.ingsw.model.player.Player;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Game class is TODO
 */
public class Game {

  private final Vector<Team> teams = new Vector<>();
  private final StudentsBag studentsBag;
  private final MainBoard mainBoard;
  private final int mode;
  private final int gamePhase;
  private final Player[] professorControlPlayer;
  private final Vector<CloudTile> cloudTiles;
  private final CharacterCard[] purchasableCharacter;
  private int turnNumber;
  private int playerNumber;
  private int playerTowerNumber;
  private int studentAtEntrance;
  private int studentOnCloudTiles;

  /**
   * Constructor por Game class
   *
   * @param mode To know how many player will play
   */
  public Game(int mode) {
    this.gamePhase = 0;
    this.professorControlPlayer = new Player[Constants.getNColors()];
    this.studentsBag = new StudentsBag();
    this.mainBoard = new MainBoard(this.studentsBag);
    this.cloudTiles = new Vector<>();
    this.mode = mode;
    this.purchasableCharacter = new CharacterCard[3];
    // TODO LUCA: 3 = MAGIC NUMBER, metterlo in costanti
  }

  /**
   * CreateClouds is a method that creates and fill the clouds for the beginning of the game
   */
  public void createCloudsAndFill() {
    for (int cloudIndex = 0; cloudIndex < playerNumber; cloudIndex++) {
      CloudTile cloudTile = new CloudTile(cloudIndex);
      cloudTile.fillCloud(studentsBag.pickRandomStudents(studentOnCloudTiles));
      cloudTiles.add(cloudTile);
    }
  }

  /**
   * Method to move mother nature, and it calculates the influence to realize the join between the
   * islands
   *
   * @param movement The exact number of steps that motherNature does
   */
  public void moveNature(int movement) {
    mainBoard.getMotherNature()
        .move(movement, mainBoard.getIslands().size()); // TODO LUCA: Interface needed

    Island islandMotherNatureIn = mainBoard.getIslands()
        .get(mainBoard.getMotherNature().getPosition());

    islandMotherNatureIn.setOwner(
        mainBoard.calculateInfluence(professorControlPlayer, teams, islandMotherNatureIn));

    mainBoard.joinIsland(mainBoard.getMotherNature().getPosition());
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
      activePlayer.moveToPlayerBoard(Places.ENTRANCE, color);
    }
  }

  /**
   * Method used to create and set the character available fore this match
   */
  public void setPurchasableCharacter() throws FileNotFoundException {
    Vector<CharacterCard> characterCards = new Vector<>();
    JsonArray character = JsonParser
        .parseReader(new FileReader("src/main/resources/json/characters.json")).getAsJsonArray();
    for (Object o1 : character) {
      JsonObject object1 = (JsonObject) o1;
      int idCharacter = object1.get("ID").getAsInt();
      int characterCost = object1.get("COST").getAsInt();
      int studentOnCard = object1.get("CARDS_STUDENT").getAsInt();
      int effectNumber = object1.get("EFFECT_NUMBER").getAsInt();
      int effectClass = object1.get("EFFECT_CLASS").getAsInt();
      int noEntryTiles = object1.get("NO_ENTRY_TILE").getAsInt();
      boolean replaceStudent = object1.get("REPLACE_STUDENTS").getAsBoolean();
      CharacterCard characterCard = new CharacterCard(idCharacter, characterCost, studentOnCard,
          studentsBag, effectNumber, effectClass, noEntryTiles, replaceStudent);
      characterCards.add(characterCard);
    }

    Random random = new Random();
    List<Integer> randomNumbers = random.ints(0, 12).distinct().limit(3).boxed()
        .collect(Collectors.toList());
    for (int i = 0; i < 3; i++) {
      int finalI = i;
      Stream<CharacterCard> characterToAdd = characterCards.stream()
          .filter(characterCard -> characterCard.getId() == randomNumbers.get(finalI));
      this.purchasableCharacter[i] = characterToAdd.findAny().get();
    }
    // TODO LUCA: metodo troppo incasinato
  }


  /**
   * This method reads the json and set them into the class attributes
   */
  public void setGameParameter() throws FileNotFoundException {
    int i = 1;
    JsonArray gameParameter = JsonParser
        .parseReader(new FileReader("src/main/resources/json/gameParameters.json"))
        .getAsJsonArray();
    for (Object o : gameParameter) {
      if (mode == i) {
        JsonObject object = (JsonObject) o;
        this.playerNumber = object.get("PLAYER_NUMBER").getAsInt();
        this.playerTowerNumber = object.get("NUMBER_OF_TOWER_FOR_A_TEAM").getAsInt();
        this.studentAtEntrance = object.get("STUDENT_AT_THE_ENTRANCE").getAsInt();
        this.studentOnCloudTiles = object.get("STUDENTS_ON_EACH_CLOUD_TILE").getAsInt();
      }
      i++;
    }
    // TODO LUCA: i non è un bel nome
    // TODO LUCA: idFirstPlayer non viene usata
    int idFirstPlayer = new Random().nextInt(playerNumber);
  }

  /**
   * Method used to move student from one place to another
   *
   * @param activePlayer To know which is the current player
   * @param placeToTake  Where the student is taken from
   * @param placeToAdd   Where the student is put int
   * @param color        Which is the color of the student
   * @param islandNumber To know on which island put the student, if is not an island this value is
   *                     -1
   */
  public void moveStudent(Player activePlayer, Places placeToTake, Places placeToAdd, int color,
      int islandNumber) {

    if (placeToAdd == Places.DINING_ROOM && placeToTake == Places.ENTRANCE) {
      activePlayer.moveToPlayerBoard(Places.DINING_ROOM, color);
      activePlayer.getPlayerBoard().getEntrance().removeStudent(color);
    } else if (placeToAdd == Places.CLOUD_TILE && placeToTake == Places.ENTRANCE) {
      mainBoard.addToIsland(color, islandNumber);
      activePlayer.getPlayerBoard().getEntrance().removeStudent(color);
    }
    //TODO
    //for the character part
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

  public int getMode() {
    return mode;
  }

  public int getGamePhase() {
    return gamePhase;
  }

  public Player[] getProfessorControlPlayer() {
    return professorControlPlayer;
  }

  public CharacterCard[] getPurchasableCharacter() {
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

}
