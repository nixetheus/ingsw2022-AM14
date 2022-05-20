package it.polimi.ingsw.view;


public class Cli implements View {
  /**
   * This method prints a string constructed by the controller in order to update the player
   *
   * @param clientResponse the string to be printed
   */
  public void printGameUpdate(String clientResponse) {
    System.out.println(clientResponse);
  }
}
