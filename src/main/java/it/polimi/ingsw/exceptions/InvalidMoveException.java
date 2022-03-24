package it.polimi.ingsw.exceptions;

/**
 * TODO
 */
public class InvalidMoveException extends Exception {

  public String errorMessage;

  public InvalidMoveException(String errorMsg) {
    errorMessage = errorMsg;
  }

  /**
   * TODO
   */
  public String getMessage() {
    return "Error: " + errorMessage;
  }
}