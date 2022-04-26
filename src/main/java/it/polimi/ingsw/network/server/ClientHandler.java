package it.polimi.ingsw.network.server;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * ClientHandler class takes care of managing the communication
 * with the associated client with the assigned socket
 *
 * It implements Runnable because each thread handles
 * the communication with its client in parallel
 */
public class ClientHandler implements Runnable{
  private final Socket socket;

  /**
   * Constructor for clientHandler
   * @param socket socket to manage
   */
  public ClientHandler(Socket socket) {
    this.socket = socket;
  }

  /**
   * This method deals of the communication between client and socket
   *
   * It is executed until a special end-of-communication character is read
   */
  @Override
  public void run() {
    try {
      //input from client
      Scanner in = new Scanner(socket.getInputStream());
      //output to client
      PrintWriter out = new PrintWriter(socket.getOutputStream());

      TestJson testJson;

      while (true) {

        String line = in.nextLine();

        //deserialize JSON string
        testJson = deserializeJson(line);

        //check quit message
        if(testJson.getName().equals("quit")){
          break;
        } else {
          System.out.println(testJson);
        }

        //Server response
        out.println("OK");
        out.flush();
      }

      //Communications and socket are closed
      in.close();
      out.close();
      socket.close();
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
  }

  /**
   * deserializeJson is used to parse a String to a testJosn object
   *
   * @param inputFromClient String to parse
   * @return object of testJon class
   */
  private TestJson deserializeJson (String inputFromClient) {
    Gson gson = new Gson();
    return gson.fromJson(inputFromClient, TestJson.class);
  }

  /**
   * toJon is used to parse a String to json format
   *
   * @param test Object to parse
   * @return String in json format
   */
  private String toJson (TestJson test) {
    Gson gson = new Gson();
    return gson.toJson(test);
  }


}
