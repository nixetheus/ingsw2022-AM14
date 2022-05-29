package it.polimi.ingsw.network.client;

import it.polimi.ingsw.view.MessageParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * ClientUserInput class takes care of the SYNCHRONOUS communication with the server
 */
public class ClientUserInput extends Thread {

  //Attributes
  private final MessageParser messageParser;
  private final int portNumber;
  private final String hostName;
  private final Socket socket;
  private final ClientServerOutputReader clientServerOutputReader;
  private int playerId;

  public ClientUserInput(int portNumber, String hostName, Socket socket,
      ClientServerOutputReader clientServerOutputReader) {

    messageParser = new MessageParser();
    this.portNumber = portNumber;
    this.hostName = hostName;
    this.socket = socket;
    this.clientServerOutputReader = clientServerOutputReader;
  }


  /**
   * This method creates a socket to manage communication with the server and then takes care of
   * data exchange with the server the while method is where the data exchange takes place
   */
  public void run() {

    //setUp connection with Server
    try (

        //prints formatted representations of objects to a text-output stream
        PrintWriter out =
            new PrintWriter(socket.getOutputStream(), true);

        BufferedReader stdIn =
            new BufferedReader(
                new InputStreamReader(System.in))
    ) {

      String userInput = "";

      System.out.println("Communication starts");
      System.out.println("Enter a nickname");

      //read input from stdin
      userInput = stdIn.readLine();

      //processed input from controller, ready to be sent to the server
      String clientInputJson = messageParser.parser(userInput);

      //send to server
      out.println(clientInputJson);

      //set the player id

      //Communications with server
      while (!userInput.equals("quit")) {

        //read input from stdin
        userInput = stdIn.readLine();

        //set the player id
        this.playerId = clientServerOutputReader.getCliParser().getPlayerId();
        this.messageParser.setPlayerId(this.playerId);

        //processed input from controller, ready to be sent to the server
        clientInputJson = messageParser.parser(userInput);

        //send to server
        out.println(clientInputJson);

      }

    } catch (UnknownHostException e) {

      System.err.println("Don't know about host " + hostName + " " + portNumber);
      System.exit(1);

    } catch (IOException e) {

      System.err.println("Couldn't get I/O for the connection to " + hostName + " " + portNumber);
      System.exit(1);

    }
  }

}