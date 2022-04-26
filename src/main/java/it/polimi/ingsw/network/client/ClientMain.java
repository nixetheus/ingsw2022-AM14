package it.polimi.ingsw.network.client;

import it.polimi.ingsw.helpers.Constants;
import it.polimi.ingsw.network.server.TestJson;
import java.util.Scanner;


/**
 * ClientMain class creates a Client class and the connect method
 */
public class ClientMain {

  public static void main(String[] args) {

    //Parameters of connection
    String hostName = Constants.getDefaultHost();
    int portNumber = Constants.getDefaultPort();

    //set nicknames
    System.out.println("Enter username");
    Scanner nickNameIn = new Scanner(System.in);
    String nickName = nickNameIn.nextLine();

    Client client = new Client(portNumber, hostName, nickName);
    client.connect();
  }
}
