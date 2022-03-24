package it.polimi.ingsw.exceptions;

public class InvalidGameObjectException extends Exception {

  String errorMessage;

  public InvalidGameObjectException(String errorMsg) {
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
