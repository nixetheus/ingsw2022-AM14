package it.polimi.ingsw.view;

/**
 * Cli class used to prints the updates
 */
public class Cli implements View {

  /**
   * This method prints a string constructed by the controller in order to update the player
   *
   * @param clientResponse the string to be printed
   */
  public void printGameUpdate(String clientResponse) {
    if (clientResponse != null && !clientResponse.equals("")) {
      System.out.println(clientResponse);
    } else {
      System.out.println(
          "You made an illegal move, follow the correct round progress and check if you have the selected student");
    }
  }
}
