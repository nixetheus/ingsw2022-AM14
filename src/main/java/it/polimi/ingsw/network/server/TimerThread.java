package it.polimi.ingsw.network.server;

import java.util.Timer;
import java.util.TimerTask;

public class TimerThread implements Runnable {

  private final int timeOut = 1234565000;
  private final Timer pingTimer;
  private final TimerTask endGameTask = new TimerTask() {
    @Override
    public void run() {
      System.out.println("Client disconnect, GAME OVER");
    }
  };

  public TimerThread(Timer pingTimer) {
    this.pingTimer = pingTimer;
  }

  @Override
  public void run() {

    pingTimer.schedule(endGameTask, timeOut);

  }

}
