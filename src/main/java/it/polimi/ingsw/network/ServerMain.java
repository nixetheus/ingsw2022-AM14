package it.polimi.ingsw.network;

import it.polimi.ingsw.helpers.Constants;
import it.polimi.ingsw.network.server.Server;

/**
 * Server App
 */
public class ServerMain {

  private static final int MAX_PORT = 65535;
  private static final int MIN_PORT = 1024;
  private static int portNumber;
  private static String hostName;

  /**
   * The main do 2 things:
   * 1) set up the port number of the server and any parameters passed on the command line
   * 2) Create a server Class and starts it
   *
   * @param args arguments passed from command line
   */
  public static void main(String[] args) {

    int portNumber = Constants.getDefaultPort();
    String hostname = Constants.getDefaultHost();

    System.out.println("Server started!");

    int argIndex = 0;
    int portNumberArg = 0;

    for (String arg : args) {

      //port number
      if (arg.equals("--port") || arg.equals("-p")) {
        portNumberArg = Integer.parseInt(args[argIndex + 1]);
        if (portNumberArg > MAX_PORT || portNumberArg < MIN_PORT) {
          System.out.println("Incorrect port number");
        } else {
          portNumber = portNumberArg;
        }
      }

      //host name
      if (arg.equals("--host") || arg.equals("-h")) {
        hostname = args[argIndex + 1];
      }

      argIndex += 2;
      }

      Server server = new Server(portNumber, hostname);
      server.startServer();

    }
  }
