package it.polimi.ingsw.controller;

import it.polimi.ingsw.helpers.MessageMain;
import it.polimi.ingsw.helpers.MessageSecondary;


/**
 * Turn manager class control and manage the turn of a player
 */
public class TurnManager {

  private int currentState;
  private MessageMain mainGamePhase;
  private MessageSecondary secondaryPhase;

  // Game variables
  private int numberOfUsers;
  private int numberStudentsFromEntrance;

  // Progress trackers
  private int currentNumberOfUsers;
  private int currentNumberOfPlayedAssistants;
  private int currentNumberOfStudentsFromEntrance;
  private int currentNumberOfUsersPlayedActionPhase;

  /**
   * Constructor method for TurnManager class
   */
  public TurnManager() {

    currentState = 0;
    mainGamePhase = MessageMain.LOGIN;
    secondaryPhase = MessageSecondary.PLAYER_PARAMS;

    currentNumberOfUsers = 0;
    currentNumberOfPlayedAssistants = 0;
    currentNumberOfStudentsFromEntrance = 0;
    currentNumberOfUsersPlayedActionPhase = 0;
  }

  //TODO update message what to do next
  public void changeState() {

    int nextState = currentState;

    switch (currentState) {
      case 0:  // GET FIRST USER
        nextState++;
        break;
      case 1:  // GET PARAMS
        nextState++;
        break;
      case 2:  // CONNECT USERS
        if (currentNumberOfUsers == numberOfUsers) {
          nextState++;
        }
        break;
      case 3:  // PLAY ASSISTANT
        if (currentNumberOfPlayedAssistants == numberOfUsers) {
          nextState += 1;
        }
        break;
      case 4:  // MOVE STUDENTS FROM ENTRANCE
        if (currentNumberOfStudentsFromEntrance == numberStudentsFromEntrance) {
          nextState++;
        }
        break;
      case 5:  // MOVE MOTHER NATURE
        nextState++;
        break;
      case 6:
        if (currentNumberOfUsersPlayedActionPhase == numberOfUsers) {
          nextState = 3;
        } else {
          nextState = 4;
        }
        break;
      default:
        break;
    }
    currentState = nextState;
    setStateParameters();
  }

  /**
   *
   */
  public void updateCounters() {
    switch (currentState) {
      case 0:
        currentNumberOfUsers++;
        break;
      case 1:
        break;
      case 2:
        currentNumberOfUsers++;
        break;
      case 3:
        currentNumberOfPlayedAssistants++;
        break;
      case 4:
        currentNumberOfStudentsFromEntrance++;
        break;
      case 5:
        break;
      case 6:
        currentNumberOfUsersPlayedActionPhase++;
        break;
      default:
        break;
    }
  }

  /**
   *
   */
  private void setStateParameters() {
    switch (currentState) {
      case 0:
        mainGamePhase = MessageMain.LOGIN;
        secondaryPhase = MessageSecondary.PLAYER_PARAMS;
        break;
      case 1:
        mainGamePhase = MessageMain.LOGIN;
        secondaryPhase = MessageSecondary.GAME_PARAMS;
        break;
      case 2:
        mainGamePhase = MessageMain.LOGIN;
        secondaryPhase = MessageSecondary.PLAYER_PARAMS;
        break;
      case 3:
        //currentNumberOfPlayedAssistants = 0;
        currentNumberOfUsersPlayedActionPhase = 0;
        mainGamePhase = MessageMain.PLAY;
        secondaryPhase = MessageSecondary.ASSISTANT;
        break;
      case 4:
        //currentNumberOfPlayedAssistants = 0;
        mainGamePhase = MessageMain.MOVE;
        secondaryPhase = MessageSecondary.ENTRANCE;
        break;
      case 5:
        currentNumberOfPlayedAssistants = 0;
        mainGamePhase = MessageMain.MOVE;
        secondaryPhase = MessageSecondary.MOVE_MN;
        break;
      case 6:
        currentNumberOfStudentsFromEntrance = 0;
        mainGamePhase = MessageMain.MOVE;
        secondaryPhase = MessageSecondary.CLOUD_TILE;
        break;
      default:
        break;
    }
  }

  public void setNumberOfUsers(int numberOfUsers) {
    this.numberOfUsers = numberOfUsers;
  }

  public void setNumberStudentsFromEntrance(int numberStudentsFromEntrance) {
    this.numberStudentsFromEntrance = numberStudentsFromEntrance;
  }

  public MessageMain getMainGamePhase() {
    return mainGamePhase;
  }

  public MessageSecondary getSecondaryPhase() {
    return secondaryPhase;
  }

  public int getCurrentState() {
    return currentState;
  }

  public int getCurrentNumberOfUsers() {
    return currentNumberOfUsers;
  }

  public int getCurrentNumberOfPlayedAssistants() {
    return currentNumberOfPlayedAssistants;
  }

  public int getCurrentNumberOfStudentsFromEntrance() {
    return currentNumberOfStudentsFromEntrance;
  }

  public int getCurrentNumberOfUsersPlayedActionPhase() {
    return currentNumberOfUsersPlayedActionPhase;
  }

  public int getNumberStudentsFromEntrance() {
    return numberStudentsFromEntrance;
  }

  public void finishGame() {
    secondaryPhase = MessageSecondary.WINNER;
  }
}
