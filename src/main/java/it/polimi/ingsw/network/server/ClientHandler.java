package it.polimi.ingsw.network.server;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.MainController;
import it.polimi.ingsw.messages.ClientResponse;
import it.polimi.ingsw.messages.InfoRequestMessage;
import it.polimi.ingsw.messages.LoginMessage;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MoveMessage;
import it.polimi.ingsw.messages.PlayMessage;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;
import org.jetbrains.annotations.NotNull;


/**
 * ClientHandler class: It takes care of managing the communication with the associated client with
 * the assigned socket
 * <p>
 * It implements Runnable because each thread handles the communication with its client in parallel
 */
public class ClientHandler implements Runnable {

  //Attributes
  private final MainController mainController;
  private final Vector<Socket> socketOut;
  private final Socket socketClient;
  private final Scanner inputStream;

  /**
   * ClientHandler constructor:
   *
   * @param socketClient   socketInput associated client
   * @param mainController mainController to execute message
   * @param socketOut      Vector of all client socket
   * @throws IOException exception
   */
  public ClientHandler(Socket socketClient, MainController mainController, Vector<Socket> socketOut)
      throws IOException {
    this.socketClient = socketClient;
    this.mainController = mainController;
    this.socketOut = socketOut;
    inputStream = new Scanner(socketClient.getInputStream());
  }

  /**
   * run method: It handles the communication client-server Read input message from client, parse it
   * to a Message object from JSON and give it to mainController, which returns us a message object
   * that we will send to all clients
   */
  @Override
  public void run() {
    try {

      ClientResponse response;

      while (true) {

        response = readInputAndSendItToMainController();

        //TODO it is mandatory to put an exit condition, quit message
        if (response == null) {
          continue;
        }

        if (response.getResponse().equals("quit")) {
          break;
        }

        sendResponseToAllClients(response);
      }
      closeStreams();
    } catch (IOException e) {
      System.err.println(e.getMessage());
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * readInputAndSendItToMainController method: Read input, creates a message object parsing the
   * JSON string, Give this Message to mainController and return this
   *
   * @return message from mainController
   * @throws IOException            exception
   * @throws ClassNotFoundException exception
   */
  private ClientResponse readInputAndSendItToMainController()
      throws IOException, ClassNotFoundException {

    String input = inputStream.nextLine();

    if (input != null) {
      Message message = fromJson(input);
      ClientResponse clientResponse = mainController.elaborateMessage(message);

      System.out.println("Message sent to controller");
      return clientResponse;
    }
    return null;
  }

  /**
   * sendResponseToAllClients method: This method takes a message and send it to all clients
   * contained in the socketOut vector
   *
   * @param message message
   * @throws IOException exception
   */
  synchronized private void sendResponseToAllClients(ClientResponse message) throws IOException {
    for (Socket socketOut1 : socketOut) {
      sendSocketMessage(message.getResponse(), new PrintWriter(socketOut1.getOutputStream()));
    }
  }

  /**
   * sendSocketMessage method: It is used to send a server message response to one client, the
   * client associated to the PrintWriter passed from parameter
   *
   * @param serverAnswer String JSON to send to the client
   * @param outputStream PrintWriter associated to one client
   */
  private void sendSocketMessage(String serverAnswer, @NotNull PrintWriter outputStream) {
    outputStream.println(serverAnswer);
    outputStream.flush();
  }

  /**
   * closeStreams method: It closes the streams of communication
   *
   * @throws IOException exception
   */
  private void closeStreams() throws IOException {
    inputStream.close();
    socketClient.close();
  }

  /**
   * deserializeJson method: is used to parse a String to a Json object
   *
   * @param inputFromClient String to parse
   * @return object of testJon class
   */
  private Message fromJson(String inputFromClient) {
    Gson gson = new Gson();
    if (inputFromClient.contains("LOGIN")) {
      return gson.fromJson(inputFromClient, LoginMessage.class);
    }
    if (inputFromClient.contains("MOVE")) {
      return gson.fromJson(inputFromClient, MoveMessage.class);
    }
    if (inputFromClient.contains("PLAY")) {
      return gson.fromJson(inputFromClient, PlayMessage.class);
    }
    if (inputFromClient.contains("INFO")) {
      return gson.fromJson(inputFromClient, InfoRequestMessage.class);
    }

    return gson.fromJson(inputFromClient, Message.class);

  }

  /**
   * toJon method: is used to parse a String to json format
   *
   * @param test Object to parse
   * @return String in json format
   */
  private String toJson(Message test) {
    Gson gson = new Gson();
    return gson.toJson(test);
  }

}