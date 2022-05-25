package it.polimi.ingsw.network.client;


import com.google.gson.Gson;
import it.polimi.ingsw.helpers.MessageSecondary;
import it.polimi.ingsw.messages.ClientResponse;
import it.polimi.ingsw.messages.Message;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Class for ping the clients and detects the disconnections
 */
class Pinger implements Runnable {

  private static final int pingTime = 2000;
  private final Socket socketOut;
  private PrintWriter out;

  public Pinger(Socket socketOut) throws IOException {
    this.socketOut = socketOut;
    out = new PrintWriter(socketOut.getOutputStream(), true);
  }

  @Override
  public void run() {

    Message pingMessage = new ClientResponse(MessageSecondary.PING);

    while (true) {
      try {

        Thread.sleep(pingTime);
        out.println(toJson(pingMessage));

      } catch (InterruptedException e) {
        e.printStackTrace();
        break;
      }

    }
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
}