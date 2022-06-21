package it.polimi.ingsw.network.server;

import it.polimi.ingsw.helpers.MessageSecondary;
import it.polimi.ingsw.messages.ClientResponse;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class handle a timer for each client,
 * when the timer is over a client disconnect with server
 * Then this class send a disconnection message to all the clients
 */
public class TimerThread implements Runnable {

  private final ClientHandler clientHandler;
  private final int timeOut = 5000;
  private final Timer pingTimer;

  //This task is run when timer ends
  private final TimerTask endGameTask = new TimerTask() {
    @Override
    public void run() {
      //create disconnection message
      ClientResponse disconnectionMessage = new ClientResponse(MessageSecondary.CLIENT_DISCONNECT);
      disconnectionMessage.setResponse("Client disconnect, GAME OVER!");

      //send it to all clients
      try {
        clientHandler.sendResponseToAllClients(disconnectionMessage);
      } catch (IOException e) {
        e.printStackTrace();
      }

    }
  };

  /**
   * Constructor class for TimerThread
   * @param pingTimer The timer for the ping message
   * @param clientHandler The client handler used to send the ping
   */
  public TimerThread(Timer pingTimer, ClientHandler clientHandler) {
    this.pingTimer = pingTimer;
    this.clientHandler = clientHandler;
  }

  /**
   * This method is used to schedule a ping message after a certain delay
   */
  @Override
  public void run() {

    pingTimer.schedule(endGameTask, timeOut);

  }

}
