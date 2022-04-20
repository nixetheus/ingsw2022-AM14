package it.polimi.ingsw.network.server;

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
  private Socket socket;

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
      Scanner in = new Scanner(socket.getInputStream());
      PrintWriter out = new PrintWriter(socket.getOutputStream());

      while (true) {
        String line = in.nextLine();
        if (line.equals("quit")) {
          break;
        } else {
          System.out.println("Received: " + line + " from" + socket.toString());

          //Server response
          out.flush();
        }
      }

      //Communications and socket are closed
      in.close();
      out.close();
      socket.close();
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
  }
}
