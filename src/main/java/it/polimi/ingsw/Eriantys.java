package it.polimi.ingsw;

import it.polimi.ingsw.network.server.ServerMain;
import it.polimi.ingsw.view.ViewMain;
import java.io.IOException;
import java.util.Scanner;

public class Eriantys {

  public static void main(String[] args) throws IOException, InterruptedException {
    printEriantys();
    choise();
  }

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

  private static void choise() throws IOException, InterruptedException {
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

