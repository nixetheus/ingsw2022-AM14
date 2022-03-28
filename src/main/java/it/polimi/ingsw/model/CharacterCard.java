package it.polimi.ingsw.model;


import it.polimi.ingsw.exceptions.InvalidMoveException;
import it.polimi.ingsw.helpers.Constants;
import it.polimi.ingsw.model.board.StudentsBag;
import it.polimi.ingsw.model.effects.Effects;
import it.polimi.ingsw.model.effects.GameEffects;
import it.polimi.ingsw.model.effects.MainBoardEffects;
import it.polimi.ingsw.model.effects.PlayerEffects;
import java.util.Arrays;

/**
 * Class used to model the charter card in the expert version of the game
 */
public class CharacterCard {

  private final int id;
  private final int effectNumber;
  private final boolean replaceStudent;
  private int cost;
  private int[] students;
  private Effects effect;
  private int noEntryTile;

  /**
   * Constructor of CharacterCard class
   *
   * @param id           Used to identify the card
   * @param cost         Used to know the cost of the character
   * @param studentsBag  used to pick up the student to put on the card
   * @param effectNumber Used to know which effect will be activated
   * @param effectClass  Which effect class is related to the character
   * @param noEntryTile  To know if the card has some no entry tile available
   */
  public CharacterCard(int id, int cost, int studentsOnTheCard, StudentsBag studentsBag,
      int effectNumber, int effectClass, int noEntryTile, boolean replaceStudent) {
    this.id = id;
    this.cost = cost;
    this.students = new int[Constants.getNColors()];
    int[] pickRandom = studentsBag.pickRandomStudents(studentsOnTheCard);
    int i;
    for (i = 0; i < studentsOnTheCard; i++) {
      this.students[pickRandom[i]]++;
    }
    this.effectNumber = effectNumber;
    this.noEntryTile = noEntryTile;
    this.replaceStudent = replaceStudent;
    if (effectClass == 1) {
      this.setEffect(new GameEffects());//possibly wrong to create a new GameEffect
    }
    if (effectClass == 2) {
      this.setEffect(new MainBoardEffects());//possibly wrong to create a new MainBoardEffect
    }
    if (effectClass == 3) {
      this.setEffect(new PlayerEffects());//possibly wrong to create a new PlayerEffect
    }


  }

  /**
   * Method to increase the cost of a character after every purchase
   */
  public void increaseCost() {
    this.cost++;
  }

  /**
   * Used to activate the effect of a card
   */
  public void activateEffect() {
    effect.applyEffect(this.effectNumber);
  }

  /**
   * Used to reduce the number of no entry tile when used
   *
   * @throws InvalidMoveException If there are no entry tiles that can be removed
   */
  public void placeNoEntry() throws InvalidMoveException {
    if (noEntryTile > 0) {
      this.noEntryTile--;
    } else {
      throw new InvalidMoveException("TODO");
    }
  }


  /**
   * Used to remove a student from a CharacterCard
   *
   * @param color       The color of the student removed from the card
   * @param studentsBag To use the pickRandomStudent method
   */
  public void removeStudent(int color, StudentsBag studentsBag) {
    this.students[color]--;
    if (this.replaceStudent) {
      students[Arrays.stream(studentsBag.pickRandomStudents(1)).sum()]++;
    }
  }

  public int getId() {
    return id;
  }

  public int getCost() {
    return cost;
  }

  public int[] getStudents() {
    return students;
  }

  public void setStudents(int[] students) {
    this.students = students;
  }

  public Effects getEffect() {
    return effect;
  }

  public void setEffect(Effects effect) {
    this.effect = effect;
  }

  public int getNoEntryTile() {
    return noEntryTile;
  }
}