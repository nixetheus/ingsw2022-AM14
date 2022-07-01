package it.polimi.ingsw.helpers;

/**
 * Constants class used to have all the game constants in one place and make the code better
 */
public class Constants {

  private static final int N_COLORS = 5;
  private static final int MAX_TOWERS = 8;
  private static final int TOTAL_STUDENTS = 130;
  private static final int MAX_STUDENTS_DINING_ROOM = 10;
  private static final int STUDENTS_FOR_COLOR = TOTAL_STUDENTS / N_COLORS;
  private static final int INITIAL_NUM_ISLANDS = 12;
  private static final int MAX_NUMBER_OF_PLAYER_EFFECTS = 5;
  private static final int MAX_NUMBER_OF_MAIN_BOARD_EFFECTS = 6;
  private static final int MAX_PURCHASABLE_CHARACTER = 3;
  private static final int TWO_PLAYER_MODE = 1;
  private static final int THREE_PLAYER_MODE = 2;
  private static final int FOUR_PLAYER_MODE = 3;
  private static final int NUMBER_STUDENT_TO_COIN = 3;
  private static final int INITIAL_COIN_NUMBER = 1;
  private static final int MAX_PORT = 65535;
  private static final int MIN_PORT = 1024;
  private static final int PING_TIME = 2000;
  private static final int TIME_OUT = 5000;

  private Constants() {
  }

  public static int getNColors() {
    return N_COLORS;
  }

  public static int getMaxTowers() {
    return MAX_TOWERS;
  }

  public static int getStudentsForColor() {
    return STUDENTS_FOR_COLOR;
  }

  public static int getMaxStudentsDiningRoom() {
    return MAX_STUDENTS_DINING_ROOM;
  }

  public static int getInitialNumIslands() {
    return INITIAL_NUM_ISLANDS;
  }

  public static int getMaxNumberOfPlayerEffects() {
    return MAX_NUMBER_OF_PLAYER_EFFECTS;
  }

  public static int getMaxNumberOfMainBoardEffects() {
    return MAX_NUMBER_OF_MAIN_BOARD_EFFECTS;
  }

  public static int getMaxPurchasableCharacter() {
    return MAX_PURCHASABLE_CHARACTER;
  }

  public static int getTwoPlayerMode() {
    return TWO_PLAYER_MODE;
  }

  public static int getThreePlayerMode() {
    return THREE_PLAYER_MODE;
  }

  public static int getFourPlayerMode() {
    return FOUR_PLAYER_MODE;
  }

  public static int getNumberStudentToCoin() {
    return NUMBER_STUDENT_TO_COIN;
  }

  public static int getInitialCoinNumber() {
    return INITIAL_COIN_NUMBER;
  }

  public static int getMaxPort() {
    return MAX_PORT;
  }

  public static int getMinPort() {
    return MIN_PORT;
  }

  public static int getPingTime() {
    return PING_TIME;
  }

  public static int getTimeOut() {
    return TIME_OUT;
  }

}
