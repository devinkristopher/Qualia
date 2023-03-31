/* @author Devin Rogers
 * 
 * Sacramento is a threaded Clock with a California-style design.
 * The time data is obtained through Java.util.Date and Java.util.Calendar.
 * 
 * Important notes:
 * If time is of the essence,
 * it's wise to set the thread priority in accordance with urgency.
 * If efficiency is of the essence,
 * it's wise to turn antialiasing OFF. 
 */

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.text.*;

public class Sacramento extends Display implements Runnable {
  private final int PANEL_WIDTH;
  private final int PANEL_HEIGHT;
  private final long THREAD_TIMER_MILLISECONDS = 1000;
  private final Color BACKGROUND_COLOR = new Color(28, 28, 30);
  private final int THREAD_PRIORITY;
  private int currentHours;
  private int currentMinutes;
  private int currentSeconds;
  private double clockAngle;
  Thread timerThread = null;
  boolean isStopped;


  Sacramento(int width, int height, int threadPriority) {
    this.PANEL_WIDTH = width;
    this.PANEL_HEIGHT = height;
    // minimum priority is 1, maximum priority is 10
    this.THREAD_PRIORITY = threadPriority;
    setPreferredSize(new Dimension(width, height));
    setBackground(BACKGROUND_COLOR);
  }

  public void start() {
    if (timerThread == null) {
      timerThread = new Thread(this);
      timerThread.setPriority(THREAD_PRIORITY);
      isStopped = false;
      timerThread.start();
    } else {
      if (isStopped) {
        isStopped = false;
        synchronized(this) {
          notify();
        }
      }
    }
  }

  public void stop() {
    isStopped = true;
  }

  public void setTime(Calendar cal) {
    currentHours = cal.get(Calendar.HOUR_OF_DAY);
    // converts 24-hour time to 12-hour time
    if (currentHours > 12) {
      currentHours -= 12;
    }
    currentMinutes = cal.get(Calendar.MINUTE);
    currentSeconds = cal.get(Calendar.SECOND);
  }


  public void run() {
    try {
      while (true) {
        Calendar cal = Calendar.getInstance();
        setTime(cal);
        if (isStopped) {
          synchronized (this) {
            while (isStopped) {
              wait();
            }
          }
        }
        repaint();
        timerThread.sleep(THREAD_TIMER_MILLISECONDS);
        // interval given in milliseconds
      }
    } catch (InterruptedException e) {
    }
  }

  void drawSecondsHand(double angle, int radius, Graphics g) {
    clockAngle -= 0.5 * Math.PI;
    int x = (int)(radius * Math.cos(angle));
    int y = (int)(radius * Math.sin(angle));
    Graphics2D g2 = (Graphics2D)g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setPaint(new Color(244, 182, 66));
    g2.setStroke(new BasicStroke(2.0f));
    g.drawLine(PANEL_WIDTH / 2, PANEL_HEIGHT / 2, PANEL_WIDTH / 2 + x, PANEL_HEIGHT / 2 + y);
  }

  void drawHands(double angle, int radius, Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    angle -= 0.5 * Math.PI;
    int x = (int)(radius * Math.cos(angle));
    int y = (int)(radius * Math.sin(angle));
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
    g.drawLine(PANEL_WIDTH / 2, PANEL_HEIGHT / 2, PANEL_WIDTH / 2 + x, PANEL_HEIGHT / 2 + y);

  }

  void fillBlade(Graphics2D g2, Graphics g) {
    g.setColor(Color.white);
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
    g2.drawOval((int) (PANEL_WIDTH / 2 - (PANEL_WIDTH * .5)), (int) (PANEL_HEIGHT / 2 - (PANEL_HEIGHT * .5)), (int) (PANEL_WIDTH), (int) (PANEL_HEIGHT));
    g2.setColor(new Color(242, 242, 247));
    g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10, new float[] { 1, 6 }, 3));
    g2.drawOval((int) (PANEL_WIDTH / 2 - (PANEL_WIDTH * .5)), (int) (PANEL_HEIGHT / 2 - (PANEL_HEIGHT * .5)), (int) (PANEL_WIDTH), (int) (PANEL_HEIGHT));
  }

  public void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    super.paintComponent(g);
    fillBlade(g2, g);
    drawHands(2 * Math.PI * currentHours / 12, PANEL_WIDTH / 5, g);
    drawHands(2 * Math.PI * currentMinutes / 60, PANEL_WIDTH / 3, g);
    drawSecondsHand(2 * Math.PI * currentSeconds / 60, PANEL_WIDTH / 2, g);
    super.cleanUp(g2);
  }

}