package it.polimi.ingsw.view;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.polimi.ingsw.guicontrollers.GameController;
import it.polimi.ingsw.guicontrollers.LoginController;
import it.polimi.ingsw.network.client.ClientServerOutputReader;
import it.polimi.ingsw.network.client.ClientUserInput;
import it.polimi.ingsw.network.client.Pinger;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewMain extends Application {

  static boolean isGUI;
  static Socket socket;
  private static int portNumber;
  private static String hostName;
  private static ServerParserGUI serverParserGUI;
  private GuiParser guiParser;

  public static void main(String[] args) throws IOException {

    setPortNumberFromJson();
    isGUI = Integer.parseInt(args[0]) > 0;

    //if server is down, print error message and terminate
    try {
      socket = new Socket(hostName, portNumber);
    } catch (Exception e) {
      System.out.println("Before starting clients, the server must be started!");
      System.exit(1);
    }

    // Thread for ping the server
    Thread pinger = new Thread(new Pinger(socket));
    pinger.start();

    if (isGUI) {
      launch();
    } else {
      // Thread for asynchronous communication: Server -> Client
      ClientServerOutputReader clientServerOutputReader = new ClientServerOutputReader(portNumber,
          hostName, socket, serverParserGUI, isGUI);
      clientServerOutputReader.start();
      // Thread for synchronous communication: CLI -> Server
      ClientUserInput clientUserInput = new ClientUserInput(portNumber, hostName, socket,
          clientServerOutputReader);
      clientUserInput.start();
    }
  }

  /**
   * setPortNumberFromJson method: Initialize port number and host name with the default value
   * contents in the file networkSettings.json
   *
   * @throws FileNotFoundException if file not found
   */
  private static void setPortNumberFromJson() throws IOException {

    String path =
        it.polimi.ingsw.network.server.FileReader.getPath("/json/networkSettings.json");
    Gson gson = new Gson();
    JsonArray list = gson
        .fromJson(path, JsonArray.class);
    JsonObject object = list.get(0).getAsJsonObject();
    portNumber = object.get("DEFAULT_PORT_NUMBER").getAsInt();
    hostName = object.get("DEFAULT_HOST").getAsString();

    System.out.println("Server IP: " + hostName
        + "\nDo you want to change it? (yes/no)");
    Scanner scanner = new Scanner(System.in);
    String insertIP = scanner.nextLine();

    if (insertIP.equals("yes") || insertIP.equals("YES") || insertIP.equals("Yes")
        || insertIP.equals("Y") || insertIP.equals("y")) {

      System.out.println("Insert new Server IP address");
      insertIP = scanner.nextLine();
      hostName = insertIP;
    }

    System.out.println(hostName + " " + portNumber);

  }

  @Override
  public void start(Stage stage) throws IOException {

    stage.setResizable(false);

    stage.setOnCloseRequest(event -> {
      Platform.exit();
      System.exit(0);
    });

    FXMLLoader loginFxmlLoader = new FXMLLoader(ViewMain.class.getResource("/login.fxml"));
    FXMLLoader loginParamsFxmlLoader = new FXMLLoader(
        ViewMain.class.getResource("/loginParams.fxml"));
    FXMLLoader loginLobbyFxmlLoader = new FXMLLoader(
        ViewMain.class.getResource("/loginLobby.fxml"));
    FXMLLoader gameFxmlLoader = new FXMLLoader(ViewMain.class.getResource("/game.fxml"));

    Scene login = new Scene(loginFxmlLoader.load());
    Scene loginParams = new Scene(loginParamsFxmlLoader.load());
    Scene loginLobby = new Scene(loginLobbyFxmlLoader.load());
    Scene game = new Scene(gameFxmlLoader.load());

    guiParser = new GuiParser(portNumber, hostName, socket, gameFxmlLoader.getController());
    serverParserGUI = new ServerParserGUI(stage, login, loginParams, loginLobby, game,
        gameFxmlLoader);

    LoginController loginController = loginFxmlLoader.getController();
    loginController.setParser(guiParser);

    LoginController loginParamsController = loginParamsFxmlLoader.getController();
    loginParamsController.setParser(guiParser);

    GameController gameController = gameFxmlLoader.getController();
    gameController.setGuiParser(guiParser);

    stage.setTitle("Eriantys");
    stage.show();

    // Thread for asynchronous communication: Server -> Client
    ClientServerOutputReader clientServerOutputReader = new ClientServerOutputReader(portNumber,
        hostName, socket, serverParserGUI, isGUI);
    clientServerOutputReader.start();

    stage.sizeToScene();
  }
}