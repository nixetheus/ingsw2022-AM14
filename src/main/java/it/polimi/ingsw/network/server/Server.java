package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.MainController;
import it.polimi.ingsw.model.Game;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Server Class:
 * Once the server has been created with the parameters passed by the main server,
 * it listens at the port and accepts every client connection.
 * For each of them create a clientHandler class (executed by a thread)
 * and a socket that will take care of the client-server communication
 */
public class Server {

  //Attributes
  private final Game game;
  private final Semaphore sem;
  private final int portNumber;
  private final Vector<Socket> socketOut;
  private ExecutorService executor;
  private ServerSocket serverSocket;
  private final MainController mainController;
  private final Vector<ClientHandler> clientsHandler;

  /**
   * Constructor Server:
   *
   * @param portNumber portNumber
   */
  public Server(int portNumber) throws InterruptedException {
    this.game = new Game();
    this.portNumber = portNumber;
    this.sem = new Semaphore(1);

    socketOut = new Vector<>();
    clientsHandler = new Vector<>();
    mainController = new MainController();

    mainController.setGame(this.game);
    mainController.setServerSemaphore(this.sem);
  }

  /**
   * startServer Method:
   * This method creates the server,
   * accept clients and creates thread and clientHandler
   * In the end, it close the Server connection
   */
  public void startServer() throws IOException, InterruptedException {

    setUpServer();
    acceptClientsConnections(); //loop
    closeServer();

  }

  /**
   * setUpServer method:
   * It creates the server with parameters passed from ServerMain
   * It creates a Thread Poll for dynamic assignment of threads and reuse of them
   */
  private void setUpServer() throws SocketException {

    executor = Executors.newCachedThreadPool();
    try {
      serverSocket = new ServerSocket(portNumber);
    } catch (IOException e) {
      //Not available port
      System.err.println(e.getMessage());
      return;
    }

    System.out.println("Server ready!\nPort: " + portNumber + "\nIP address: ");

    Enumeration ip = NetworkInterface.getNetworkInterfaces();
    while(ip.hasMoreElements())
    {
      NetworkInterface netInterface = (NetworkInterface) ip.nextElement();
      Enumeration ipAddress = netInterface.getInetAddresses();
      while (ipAddress.hasMoreElements())
      {
        InetAddress singleIp = (InetAddress) ipAddress.nextElement();
        String ipToPrint = singleIp.getHostAddress();
        if(ipToPrint.charAt(0) == '1')
          System.out.println(ipToPrint);
      }
    }
  }

  /**
   * acceptClientsConnections method:
   * This method accepts every client connection,
   * create a socket and add it to socket out Vector,
   * create a ClientHandler class (Runnable class runs by the thread) and add it to
   * clientsHandler Vector
   */
  private void acceptClientsConnections() throws IOException, InterruptedException {

    // FIRST CLIENT
    Socket firstClientSocket;
    firstClientSocket = serverSocket.accept();
    System.out.println("accepted first client");

    socketOut.add(firstClientSocket);
    ClientHandler firstClientHandler = new ClientHandler(firstClientSocket, mainController, socketOut);
    executor.submit(firstClientHandler);
    clientsHandler.add(firstClientHandler);

    // STOP ACCEPTING CLIENTS IF PARAMS NOT GIVEN
    sem.acquire();

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
