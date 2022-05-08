package it.polimi.ingsw.network.client;

import java.util.Scanner;


/**
 * ClientMain class: TODO
 */
public class ClientMain {

  public static void main(String[] args) {

    //Parameters of connection
    String hostName = "127.0.0.1";
    int portNumber = 1234;

    //set nicknames
    System.out.println("Enter username");
    Scanner nickNameIn = new Scanner(System.in);
    String nickName = nickNameIn.nextLine();

    Client client = new Client(portNumber, hostName, nickName);
    client.connect();
  }
}
