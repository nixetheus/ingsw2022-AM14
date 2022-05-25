package it.polimi.ingsw.network.client;


import it.polimi.ingsw.view.Cli;
import it.polimi.ingsw.view.CliParser;
import it.polimi.ingsw.view.View;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * ClientServerOutputReader class takes care of the ASYNCHRONOUS communication with the server
 */
public class ClientServerOutputReader extends Thread {

  //Attributes
  private final View view;
  private final CliParser cliParser;
  private final int portNumber;
  private final String hostName;
  private final Socket socket;

  public ClientServerOutputReader(int portNumber, String hostName, Socket socket) {

    view = new Cli();
    this.portNumber = portNumber;
    this.hostName = hostName;
    this.cliParser = new CliParser();
    this.socket = socket;

  }


  /**
   * This method creates a socket to manage communication with the server and then takes care of
   * data exchange with the server
   * the while method is where the data exchange takes place
   */
  public void run() {

    try (

        //creates the buffer for input stream
        BufferedReader in =
            new BufferedReader(
                new InputStreamReader(socket.getInputStream()))

    ) {

      //Communications with server
      String serverOutput = "";

      while (!serverOutput.equals("quit")) {

        //print the server response
        serverOutput = in.readLine();

        view.printGameUpdate(cliParser.fromJson(serverOutput));

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
