package it.polimi.ingsw.network.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Server class is only concerned to instantiating the ServerSocket,
 * executing accept of the client connections
 * and creating the necessary threads to handle accepted connections
 */
public class Server {
  //Attributes
  private int portNumber;
  private String hostName;
  private ExecutorService executor;
  private ServerSocket serverSocket;

  /**
   * Constructor for Server
   *
   * @param portNumber
   * @param hostName
   */
  public Server(int portNumber, String hostName) {
    this.portNumber = portNumber;
    this.hostName = hostName;
  }

  /**
   * startServer takes care of instantiating the ServerSocket,
   * executing accept of the client and to create the threads
   * to manage the accepted connections
   *
   * It creates a thread when needed
   * but reuse existing ones if possible with a thread pool
   */
  public void startServer() {
    executor = Executors.newCachedThreadPool();
    try {
      serverSocket = new ServerSocket(portNumber);
    } catch (IOException e) {
      // Port not available
      System.err.println(e.getMessage());
      return;
    }

    System.out.println("Server ready");

    while (true) {
      try {
        Socket socket = serverSocket.accept();

        System.out.println("Accepted");

        executor.submit(new ClientHandler(socket));

      } catch(IOException e) {
        //if serverSocket is closed, this is done
        break;
      }
    }
    executor.shutdown();
  }
}
