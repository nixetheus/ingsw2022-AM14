package it.polimi.ingsw;

import it.polimi.ingsw.network.server.ServerMain;
import it.polimi.ingsw.view.ViewMain;
import java.io.IOException;
import java.util.Scanner;

/**
 * Eriantys is the main class of the project everything starts from here
 */
public class Eriantys {

  /**
   * Main method of Eriantys App
   * @param args args passed from command line
   * @throws IOException exception
   * @throws InterruptedException exception
   */
  public static void main(String[] args) throws IOException, InterruptedException {
    printEriantys();
    choice();
  }

  /**
   * Method used to print the initial page
   */
  private static void printEriantys() {
    System.out.println("███████╗██████╗ ██╗ █████╗ ███╗   ██╗████████╗██╗   ██╗███████╗");
    System.out.println("██╔════╝██╔══██╗██║██╔══██╗████╗  ██║╚══██╔══╝╚██╗ ██╔╝██╔════╝");
    System.out.println("█████╗  ██████╔╝██║███████║██╔██╗ ██║   ██║    ╚████╔╝ ███████╗");
    System.out.println("██╔══╝  ██╔══██╗██║██╔══██║██║╚██╗██║   ██║     ╚██╔╝  ╚════██║");
    System.out.println("███████╗██║  ██║██║██║  ██║██║ ╚████║   ██║      ██║   ███████║");
    System.out.println("╚══════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝   ╚═╝      ╚═╝   ╚══════╝");
    System.out.println("Insert:\n"
        + "1 to run Server\n"
        + "2 to run Client CLI\n"
        + "3 to run Client GUI");
  }

  /**
   * Method used to run what the user needs
   * 1) Server App
   * 2) Cli App
   * 3) Gui App
   */
  private static void choice() throws IOException, InterruptedException {
    Scanner choice = new Scanner(System.in);
    String choiceStr = choice.nextLine();  // Read user input

    while (!choiceStr.equals("1") && !choiceStr.equals("2") && !choiceStr.equals("3")) {
      System.out.println("Please choose a valid option");
      choice = new Scanner(System.in);
      choiceStr = choice.nextLine();
    }

    switch (choiceStr) {
      case "1": {

        String[] param = new String[0];
        ServerMain.main(param);

        break;
      }
      case "2": {

        String[] param = new String[1];
        param[0] = "0";
        ViewMain.main(param);

        break;
      }
      case "3": {

        String[] param = new String[1];
        param[0] = "1";
        ViewMain.main(param);

        break;
      }
    }
  }
}


