package it.polimi.ingsw.network.server;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.polimi.ingsw.helpers.Constants;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;

/**
 * ServerMain Class:
 * This class setup the parameters passed from command line
 * and creates the Server, if nothing is passed it takes the port number
 * and the host address from json file (networkSettings.json)
 */
public class ServerMain {

  //Attributes
  private static int portNumber;
  private static String hostName;

  /**
   * main:
   * set up port number and host from json file
   * if any parameters are passed from command line it overwrites port number and/or hostname
   * In the end creates the server and runs it
   * @param args arguments passed from command line
   */
  public static void main(String[] args) throws IOException, InterruptedException {

    //set network settings from networkSettings.json
    setPortNumberFromJson();

    //setUp parameters passed from command line
    setUpParametersCommandLine(args);

    Server server = new Server(portNumber, hostName);
    server.startServer();

  }

  /**
   * setPortNumberFromJson method:
   * Initialize port number and host name with the default value contents in
   * the file networkSettings.json
   * @throws FileNotFoundException if file not found
   */
  private static void setPortNumberFromJson() throws FileNotFoundException {

    Gson gson = new Gson();
    JsonArray list = gson
        .fromJson(new FileReader("src/main/resources/json/networkSettings.json"), JsonArray.class);
    JsonObject object = list.get(0).getAsJsonObject();
    portNumber = object.get("DEFAULT_PORT_NUMBER").getAsInt();
    hostName = object.get("DEFAULT_HOST").getAsString();

    System.out.println(portNumber + " " +  hostName);

  }

  /**
   * setUpParametersCommandLine method:
   * If any parameters are passed from command line it overwrites port number and/or hostname
   * port number --port || -p
   * hostname --host || -h
   * @param args arguments passed from command line
   */
  private static void setUpParametersCommandLine(String @NotNull [] args) {

    int argIndex = 0;
    int portNumberArg;

    for (String arg : args) {

      //port number
      if (arg.equals("--port") || arg.equals("-p")) {
        portNumberArg = Integer.parseInt(args[argIndex + 1]);

        if (portNumberArg > Constants.getMaxPort() || portNumberArg < Constants.getMinPort()) {
          System.out.println("Incorrect port number");
        } else {
          portNumber = portNumberArg;
        }

      }

      //host name
      if (arg.equals("--host") || arg.equals("-h")) {
        hostName = args[argIndex + 1];
      }

      argIndex += 2;
    }
  }

}
