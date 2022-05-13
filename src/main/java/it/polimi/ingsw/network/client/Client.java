package it.polimi.ingsw.network.client;

import com.google.gson.Gson;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.view.Cli;
import it.polimi.ingsw.view.MessageParser;
import it.polimi.ingsw.view.View;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Client class takes care of the communication with the server
 * TODO: clients leave the game or connection drop
 */
public class Client {

  private View view;
  private final MessageParser messageParser;
  private final int portNumber;
  private final String hostName;

  public Client(int portNumber, String hostName, String nickName) {
    //select type of view
    boolean choice = cliOrGui();
    if(choice)
      view = new Cli();
    messageParser = new MessageParser();
    this.portNumber = portNumber;
    this.hostName = hostName;

  }

  private boolean cliOrGui() {
    System.out.println("Type '1' for the CLI or '2' for the GUI");
    Scanner typeOfViewIn = new Scanner(System.in);
    String choice = typeOfViewIn.nextLine();
    //true => cli
    //false => gui
    return (choice.equals("1"));
  }

  /**
   * This method creates a socket to manage communication with the server
   * and then takes care of data exchange with the server
   *
   * the while method is where the data exchange takes place
   */
  public void connect(){

    //setUp connection with Server
    try (
        //creates a socket to handle communication with Server
        Socket socket = new Socket(hostName, portNumber);

        //prints formatted representations of objects to a text-output stream
        PrintWriter out =
            new PrintWriter(socket.getOutputStream(), true);

        //reads text from a character-input stream, buffering characters so as to
        // provide for the efficient reading of characters, arrays, and lines.
        BufferedReader in =
            new BufferedReader(
                new InputStreamReader(socket.getInputStream()));

        BufferedReader stdIn =
            new BufferedReader(
                new InputStreamReader(System.in))
    ){

      String userInput;

      System.out.println("Communication starts");

      //Communications with server
      while (!(userInput = stdIn.readLine()).equals("quit")) {
        //send to server
        String str = messageParser.parser(userInput);
        out.println(str);

        //print the server response
        view.printGameUpdate(in.readLine());
      }

    } catch (UnknownHostException e) {

      System.err.println("Don't know about host " + hostName);
      System.exit(1);

    } catch (IOException e) {

      System.err.println("Couldn't get I/O for the connection to " + hostName);
      System.exit(1);

    }
  }
}
