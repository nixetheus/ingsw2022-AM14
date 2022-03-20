package it.polimi.ingsw.exceptions;

/**
 * TODO
 */
public class InvalidMoveException extends Exception {

  String errorMessage;

  public InvalidMoveException(String errorMsg) {
    errorMessage = errorMsg;
  }

  /**
   * TODO
   */
  @Override
  public String getMessage() {
    return "Error: " + errorMessage;
  }
}
