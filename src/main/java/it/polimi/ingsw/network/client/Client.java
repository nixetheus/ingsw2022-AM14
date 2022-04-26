package it.polimi.ingsw.network.client;

import com.google.gson.Gson;
import it.polimi.ingsw.network.server.TestJson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Client class takes care of the communication with the server
 * TODO: clients leave the game or connection drop
 */
public class Client {

  private final int portNumber;
  private final String hostName;
  private String nickName;

  public Client(int portNumber, String hostName, String nickName) {
    this.portNumber = portNumber;
    this.hostName = hostName;
    this.nickName = nickName;
  }

  /**
   * This method creates a socket to manage communication with the server
   * and then takes care of data exchange with the server
   *
   * the while method is where the data exchange takes place
   */
  public void connect(){

    //object to test json
    TestJson test = new TestJson("giorgio", "bella@hotmail.it", 62, true);

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

      while ((userInput = stdIn.readLine()) != null) {
        //send to server
        out.println(toJson(test));
        //print the server response
        System.out.println("Server: " + in.readLine());
      }

    } catch (UnknownHostException e) {

      System.err.println("Don't know about host " + hostName);
      System.exit(1);

    } catch (IOException e) {

      System.err.println("Couldn't get I/O for the connection to " + hostName);
      System.exit(1);

    }
  }

  /**
   * toJon is used to parse a String to json format
   *
   * @param test Object to parse
   * @return String in json format
   */
  private String toJson (TestJson test){
    Gson gson = new Gson();
    return gson.toJson(test);
  }

}
