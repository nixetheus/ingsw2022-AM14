package it.polimi.ingsw.network.server;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.MainController;
import it.polimi.ingsw.helpers.MessageMain;
import it.polimi.ingsw.helpers.MessageSecondary;
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
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import org.jetbrains.annotations.NotNull;


/**
 * ClientHandler class: It takes care of managing the communication with the associated client with
 * the assigned socket
 * It implements Runnable because each thread handles the communication with its client in parallel
 */
public class ClientHandler implements Runnable {

  //Attributes
  private final MainController mainController;
  private final Vector<Socket> socketOut;
  private final Socket socketClient;
  private final Scanner inputStream;
  private Timer pingTimer;
  private Thread timer;

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

      Vector<Message> responses;

      pingThreadCreation();

      while (true) {
        responses = readInputAndSendItToMainController();

        //TODO put here a quit condition
        if (responses.get(0).toString().equals("exit_condition")) {
          break;
        }

        while (!responses.isEmpty() &&
            responses.get(0).getMessageSecondary() != MessageSecondary.PING) {

          Message response = responses.firstElement();
          if (response.getPlayerId() == -1) {
            sendResponseToAllClients(response);
          } else {
            //TODO check do not send to last client
            sendSocketMessage(toJson(response),
                new PrintWriter(socketOut.get(response.getPlayerId()).getOutputStream()));
          }
          responses.remove(response);
        }

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
  private Vector<Message> readInputAndSendItToMainController()
      throws IOException, ClassNotFoundException {

    //read input from client
    String input = inputStream.nextLine();
    if (input != null) {

      Message clientResponse = fromJson(input);

      if(clientResponse.getMessageMain() == MessageMain.INFO &&
          clientResponse.getMessageSecondary() == MessageSecondary.PING) {

        restartTimer();

        //if I don't return a Vector<Message> to run() method it doesn't work,
        //so I create a default Vector<Message> to return
        return PingResponse();
      }

      System.out.println("Message sent to controller");
      return mainController.elaborateMessage(clientResponse);

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
  synchronized private void sendResponseToAllClients(Message message) throws IOException {
    for (Socket socketOut : socketOut) {
      sendSocketMessage(toJson(message), new PrintWriter(socketOut.getOutputStream()));
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
   * @param msg Object to parse
   * @return String in json format
   */
  private String toJson(Message msg) {
    Gson gson = new Gson();
    return gson.toJson(msg);
  }

  /**
   * This method creates a timer and starts the TimerThread thread
   */
  private void pingThreadCreation() {
    pingTimer = new Timer();
    timer = new Thread(new TimerThread(pingTimer));
    timer.start();
  }

  /**
   * This method restarts the timer,
   * it is invoked when ping message arrived to the server
   */
  private void restartTimer() {
    pingTimer.cancel();
    timer.stop();

    pingTimer = new Timer();
    timer = new Thread(new TimerThread(pingTimer));
    timer.start();
  }

  /**
   * Create a default message to return to run()
   * @return default Vector<Message>
   */
  private Vector<Message> PingResponse() {
    Vector<Message> messages = new Vector<>();
    ClientResponse pingResponse = new ClientResponse(MessageSecondary.PING);
    messages.add(pingResponse);
    return messages;
  }

}