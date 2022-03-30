package it.polimi.ingsw.model;

import it.polimi.ingsw.helpers.Constants;
import it.polimi.ingsw.model.board.Island;
import it.polimi.ingsw.model.board.MainBoard;
import it.polimi.ingsw.model.board.StudentsBag;
import it.polimi.ingsw.model.player.Player;
import java.util.Random;
import java.util.Vector;

/**
 * Game class is TODO
 */
public class Game {

  private final Vector<Team> teams;
  private final StudentsBag studentsBag;
  private final MainBoard mainBoard;
  private final int mode;
  private final int gamePhase;
  private final int[] professorControlPlayer;
  private final Vector<CloudTile> cloudTiles;
  private final int idFirstPlayer;
  private int turnNumber;
  private CharacterCard[] purchasableCharacter;

  /**
   * Constructor por Game class
   *
   * @param teams to know the number of teams for this match
   * @param mode  To know how many player will play
   */
  public Game(Vector<Team> teams, int mode) {
    this.teams = teams;
    this.gamePhase = 0;
    this.professorControlPlayer = new int[Constants.getNColors()];
    //this.purchasableCharacter=
    this.studentsBag = new StudentsBag();
    this.mainBoard = new MainBoard();
    this.cloudTiles = new Vector<>();
    this.mode = mode;
    this.idFirstPlayer = new Random().nextInt(1, 4);
  }

  /**
   * CreateClouds is a method that create and fill the clouds for the beginning of the game
   */
  public void createClouds() {
    int i;
    for (i = 0; i > mode; i++) {
      CloudTile cloudTile = new CloudTile(i);
      cloudTile.fillCloud(studentsBag.pickRandomStudents(3));
      cloudTiles.add(cloudTile);
    }
  }

  /**
   * Method to move mother nature and it calculate the influence to realize the join between the
   * islands
   *
   * @param movement The exact number of steps that motherNature does
   */
  public void moveNature(int movement) {
    mainBoard.getMotherNature().move(movement, mainBoard.getIslands().size());
    Island islandMotherNatureIN = mainBoard.getIslands()
        .get(mainBoard.getMotherNature().getPosition());
    islandMotherNatureIN.setOwner(mainBoard
        .calculateInfluence());//make calculateInfluence return the player id that has the influence and takes the number of towers in the island to add for the influence
    mainBoard.joinIsland(mainBoard.getMotherNature().getPosition());
  }

  /**
   * Method used to take a cloud and add the students at the active player entrance
   *
   * @param activePlayer  To know the who is the active player
   * @param idCloudToTake To know which cloud to take
   */
  public void takeCloud(Player activePlayer, int idCloudToTake) {
    CloudTile cloudTile = cloudTiles.get(idCloudToTake);
    int[] students = cloudTile.getStudents();
    for (int i = 0; i < Constants.getNColors(); i++) {
      for (; students[i] > 0; students[i]--) {
        activePlayer.moveToPlayerBoard(0, i);
      }
    }
    cloudTile.emptyCloud();
  }

  public Vector<CloudTile> getCloudTiles() {
    return cloudTiles;
  }

  public MainBoard getMainBoard() {
    return mainBoard;
  }

}
