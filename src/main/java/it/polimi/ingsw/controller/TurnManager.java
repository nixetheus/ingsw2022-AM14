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
    secondaryPhase = MessageSecondary.GAME_PARAMS;

    currentNumberOfUsers = 0;
    currentNumberOfPlayedAssistants = 0;
    currentNumberOfStudentsFromEntrance = 0;
    currentNumberOfUsersPlayedActionPhase = 0;
  }

  public void changeState() {

    int nextState = currentState;
    switch (currentState) {
      case 0:
        nextState++;
        break;
      case 1:
        if (currentNumberOfUsers == numberOfUsers) {
          nextState++;
        }
        break;
      case 2:
        if (currentNumberOfPlayedAssistants == numberOfUsers) {
          nextState++;
        }
        break;
      case 3:
        if (currentNumberOfStudentsFromEntrance == numberStudentsFromEntrance) {
          nextState++;
        }
        break;
      case 4:
        nextState++;
        break;
      case 5:
        if (currentNumberOfUsersPlayedActionPhase == numberOfUsers) {
          nextState = 2;
        } else {
          nextState = 3;
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
        break;
      case 1:
        currentNumberOfUsers++;
        break;
      case 2:
        currentNumberOfPlayedAssistants++;
        break;
      case 3:
        currentNumberOfStudentsFromEntrance++;
        break;
      case 4:
        break;
      case 5:
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
        currentNumberOfUsers = 0;
        mainGamePhase = MessageMain.LOGIN;
        secondaryPhase = MessageSecondary.GAME_PARAMS;
        break;
      case 1:
        mainGamePhase = MessageMain.LOGIN;
        secondaryPhase = MessageSecondary.PLAYER_PARAMS;
        break;
      case 2:
        currentNumberOfUsersPlayedActionPhase = 0;
        mainGamePhase = MessageMain.PLAY;
        secondaryPhase = MessageSecondary.ASSISTANT;
        break;
      case 3:
        currentNumberOfPlayedAssistants = 0;
        mainGamePhase = MessageMain.MOVE;
        secondaryPhase = MessageSecondary.ENTRANCE;
        break;
      case 4:
        currentNumberOfStudentsFromEntrance = 0;
        mainGamePhase = MessageMain.MOVE;
        secondaryPhase = MessageSecondary.MOTHER_NATURE;
        break;
      case 5:
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
}
