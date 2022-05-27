package it.polimi.ingsw.network.client;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.polimi.ingsw.view.Eriantys;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import javafx.application.Application;


/**
 * ClientMain class: set up the parameters to connect the client to the server, ask and pass to
 * Client class the nickname and creates Client class and connect it
 */
public class ClientMain {

  private static int portNumber;
  private static String hostName;

  public static void main(String[] args) throws IOException {

    setPortNumberFromJson();
    //boolean isGUI = Integer.parseInt(args[1]) > 0;
    Socket socket = new Socket(hostName, portNumber);

    //thread for asynchronous communication
    //ClientServerOutputReader clientServerOutputReader = new ClientServerOutputReader(portNumber,
        //hostName, socket);

    //thread for synchronous communication
    ClientUserInput clientUserInput = new ClientUserInput(portNumber, hostName, socket);

    //thread for ping the server
    Thread pinger = new Thread(new Pinger(socket));

    //Let's start!
    clientUserInput.start();
    //clientServerOutputReader.start();
    pinger.start();
  }

  /**
   * setPortNumberFromJson method: Initialize port number and host name with the default value
   * contents in the file networkSettings.json
   *
   * @throws FileNotFoundException if file not found
   */
  private static void setPortNumberFromJson() throws FileNotFoundException {

    Gson gson = new Gson();
    JsonArray list = gson
        .fromJson(new FileReader("src/main/resources/json/networkSettings.json"), JsonArray.class);
    JsonObject object = list.get(0).getAsJsonObject();
    portNumber = object.get("DEFAULT_PORT_NUMBER").getAsInt();
    hostName = object.get("DEFAULT_HOST").getAsString();

    System.out.println(hostName + " " + portNumber);

  }
}
