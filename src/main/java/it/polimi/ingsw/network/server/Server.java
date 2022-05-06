package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.MainController;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Server Class:
 * Once the server has been created with the parameters passed by the main server,
 * it listens at the port and accepts every client connection.
 * For each of them create a clientHandler class (executed by a thread)
 * and a socket that will take care of the client-server communication
 */
public class Server {

  //Attributes
  //private final Game game;
  private final MainController mainController;
  private final Vector<ClientHandler> clientsHandler;
  private Vector<Socket> socketOut;
  private final int portNumber;
  private final String hostName;
  private ExecutorService executor;
  private ServerSocket serverSocket;

  /**
   * Constructor Server:
   *
   * @param portNumber portNumber
   * @param hostName hostName
   */
  public Server(int portNumber, String hostName) {
    this.portNumber = portNumber;
    this.hostName = hostName;

    mainController = new MainController();
    clientsHandler = new Vector<>();
    socketOut = new Vector<>();
  }

  /**
   * startServer Method:
   * This method creates the server,
   * accept clients and creates thread and clientHandler
   * In the end, it close the Server connection
   */
  public void startServer() {

    setUpServer();
    acceptClientsConnections();
    closeServer();

  }

  /**
   * setUpServer method:
   * It creates the server with parameters passed from ServerMain
   * It creates a Thread Poll for dynamic assignment of threads and reuse of them
   */
  private void setUpServer() {

    executor = Executors.newCachedThreadPool();
    try {
      serverSocket = new ServerSocket(portNumber);
    } catch (IOException e) {
      //Not available port
      System.err.println(e.getMessage());
      return;
    }

    System.out.println("Server ready");
  }

  /**
   * acceptClientsConnections method:
   * This method accepts every client connection,
   * create a socket and add it to socket out Vector,
   * create a ClientHandler class (Runnable class runs by the thread) and add it to
   * clientsHandler Vector
   */
  private void acceptClientsConnections() {

    while (true) {
      try {
        Socket newClientSocket = serverSocket.accept();
        socketOut.add(newClientSocket);
        System.out.println("accepted");

        ClientHandler newClientHandler = new ClientHandler(newClientSocket, mainController, socketOut);
        executor.submit(newClientHandler);


        clientsHandler.add(newClientHandler);

      } catch (IOException e) {
        //if serverSocket is closed, this is done
        System.out.println("Server closed");
        break;
      }
    }

  }

  /**
   * closeServer method:
   * close the executor and server connection
   */
  private void closeServer() {
    System.out.println("Server closed");
    executor.shutdown();
  }

}
