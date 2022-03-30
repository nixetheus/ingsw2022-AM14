package it.polimi.ingsw.exceptions;

/**
 * InvalidMoveException class is thrown when a player do an invalid action that is not  allowed in
 * the game
 */
public class InvalidMoveException extends Exception {

  public String errorMessage;

  public InvalidMoveException(String errorMsg) {
    errorMessage = errorMsg;
  }

  /**
   * This method is used to know which error is happened and send to the client
   */
  public String getMessage() {
    return "Error: " + errorMessage;
  }
}