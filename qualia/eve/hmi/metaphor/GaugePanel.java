/* @author Devin Rogers
 * 
 * The custom JPanel class for the GaugePanel radial gauge.
 * 
 * The interface design of this gauge is vehicle-oriented.
 * To display information in an effective manner, this gauge displays multiple data points:
 * - a numeric value to no decimal points (e.g., MPH)
 * - a numeric value to correspond to the perimeter of the circle (e.g., RPM)
 * 
 * MPH:
 * - has a threshold that, once passed, will change the text color to orange
 * 
 * RPM:
 * - has a 'redline' zone that is displayed within the circle's border
 * - has a 'fill' zone that acts as the current progress
 * - a needle is offered, but not mandatory
 * - a label is offered, but not mandatory
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.Font;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.Point;
import javax.swing.JLabel;
import javax.swing.JPanel;

class GaugePanel extends JPanel {
  // the MPH value
  private double mphValue = 0.0;
  // the minimum angle in radians
  private double minAngleRad = 0.0;
  // the maximum angle in radians
  private double maxAngleRad = 0.0;
  // the minimum value
  private double minValue = 0.0;
  // the maximum value
  private double maxValue = 0.0;
  // the current value
  private double value = 0.0;
  // these are all used for the MPH value;
  // effectively, these are digits of the entire speed value.
  // should not need more than 3!
  private JLabel speedometer1 = new JLabel();
  private JLabel speedometer2 = new JLabel();
  private JLabel speedometer3 = new JLabel();
  private Arc2D arc;
  Arc2D redLine;
  Ellipse2D circle;
  Arc2D fillRPM;
  private double alpha;
  private double angleRad;
  private double length;
  private double x0;
  private double x1;
  private double y0;
  private double y1;
  private double x2;
  private double y2;
  private Color trailColor = new Color(224, 255, 234);
  private double circlew;
  private double circleh;
  private int intSpeedometer;
  private String speedometer;
  private double speedometerOffset;
  private double speedometerBaseline;

  // This method sets the minimum and maximum angles for the needle.
  void setAngles(double minAngleRad, double maxAngleRad) {
    // 270 degrees (6:00) w/ radian/deg conversion factor
    this.minAngleRad = 270 * (Math.PI / 180);
    this.maxAngleRad = -40 * (Math.PI / 180);
    repaint();
  }

  void setSpeed(double mphValue) {
    this.mphValue = mphValue;
  }

  void setRange(double minValue, double maxValue) {
    this.minValue = minValue;
    this.maxValue = maxValue;
    repaint();
  }

  void setValue(double value) {
    this.value = value;
    repaint();
  }

  void drawBase() {

  }

  // This method creates the inner part of the Gauge Face. It is overlaid on top
  // of the track.
  Ellipse2D createGaugeFace(Arc2D arc, Graphics2D g) {
    g.setColor(new Color(25, 25, 25));
    x2 = x0 - getWidth() / 2.0;
    y2 = y0 - getHeight() / 2.0;
    circlew = arc.getWidth() * .8;
    circleh = arc.getHeight() * .8;
    Ellipse2D circle = new Ellipse2D.Double(x2, y2, circlew, circleh);
    return circle;
  }

  // This method creates the gauge track. The visible portion is the outside of
  // the circle (until the redline).
  Arc2D createGaugeTrack(Graphics2D g) {
    g.setColor(trailColor);
    Arc2D.Double arc = new Arc2D.Double(x0 - length, y0 - length, length + length, length + length,
        Math.toDegrees(minAngleRad), Math.toDegrees(maxAngleRad - minAngleRad), Arc2D.PIE);

    g.fill(arc);
    g.setColor(new Color(175, 175, 175));
    g.draw(arc);
    return arc;
  }

  Arc2D createRedLineZone(Graphics2D g) {
    g.setColor(new Color(255, 0, 0));
    Arc2D.Double redLine = new Arc2D.Double((x0 - length), (y0 - length), length + length, length + length, 13.5, -55.0,
        Arc2D.PIE);
    return redLine;

  }

  Arc2D createFillZone(Graphics2D g) {
    g.setColor(new Color(15, 220, 15));
    Arc2D.Double fillRPM = new Arc2D.Double((x0 - length), (y0 - length), length + length, length + length, -90,
        -(value * (360 - 50)), Arc2D.PIE);
    return fillRPM;
  }

  Line2D createNeedle(Graphics2D g) {
    g.setStroke(new BasicStroke((float) (getWidth() * .025)));
    g.setColor(Color.RED);
    Line2D needle = new Line2D.Double(x0, y0, x1, y1);
    // (x, y)A to (x, y)B
    return needle;
  }

  void drawGaugeDesignerArea(Graphics2D g, int fontSize) {
    int ty = 20;
    g.setColor(Color.BLACK);
    g.drawString("minAngle " + Math.toDegrees(minAngleRad), 20, ty += 20);
    g.drawString("maxAngle " + Math.toDegrees(maxAngleRad), 20, ty += 20);
    g.drawString("minValue " + minValue, 20, ty += 20);
    g.drawString("maxValue " + maxValue, 20, ty += 20);
    g.drawString("value " + value, 20, ty += 20);
    g.drawString("Speed: " + mphValue, 20, ty += 20);
  }

  void drawSpeedometerValue(Graphics2D g, Font stringFont) {
    g.setColor(new Color(255, 255, 255));
    speedometer1.setForeground(Color.WHITE);
    speedometer2.setForeground(Color.WHITE);
    speedometer3.setForeground(Color.WHITE);
    speedometer1.setFont(stringFont);
    speedometer2.setFont(stringFont);
    speedometer3.setFont(stringFont);
    intSpeedometer = (int) mphValue;
    speedometer = Integer.toString(intSpeedometer);
    speedometerOffset = .11;
    speedometerBaseline = .34;
    repaint();
    revalidate();
    removeAll();

    switch (speedometer.length()) {
      case 1:
        speedometer2.setText(speedometer);
        speedometer2.setLocation((int) (getWidth() * (speedometerBaseline + speedometerOffset)),
            (int) (y0 - length * .7));
        add(speedometer2);
        break;
      case 2:
        speedometer1.setText(speedometer.substring(0, 1));
        speedometer2.setText(speedometer.substring(1, 2));
        if (mphValue >= 75) {
          speedometer1.setForeground(Color.ORANGE);
          speedometer2.setForeground(Color.ORANGE);
        }
        speedometer1.setLocation((int) (getWidth() * (speedometerBaseline + .05)), (int) (y0 - length * .7));
        speedometer2.setLocation((int) (getWidth() * ((speedometerBaseline + .05) + speedometerOffset)),
            (int) (y0 - length * .7));
        add(speedometer1);
        add(speedometer2);
        break;
      case 3:
        speedometer1.setText(speedometer.substring(0, 1));
        speedometer2.setText(speedometer.substring(1, 2));
        speedometer3.setText(speedometer.substring(2, 3));
        if (mphValue >= 75) {
          speedometer1.setForeground(Color.ORANGE);
          speedometer2.setForeground(Color.ORANGE);
          speedometer3.setForeground(Color.ORANGE);
        }
        speedometer1.setLocation((int) (getWidth() * speedometerBaseline), (int) (y0 - length * .7));
        speedometer2.setLocation((int) (getWidth() * (speedometerBaseline + speedometerOffset)),
            (int) (y0 - length * .7));
        speedometer3.setLocation((int) (getWidth() * (speedometerBaseline + (speedometerOffset * 2))),
            (int) (y0 - length * .7));
        add(speedometer1);
        add(speedometer2);
        add(speedometer3);
        break;
      default:
        ;
    }

  }

  void drawTachometerValue(Graphics2D g, Font stringFont) {
    g.setFont(stringFont);
    g.drawString(value * 8000 + " RPM", getWidth() / 2, (int) (y0 - length * .6));
  }

  static void drawGaugeComponents(Graphics2D g, Arc2D redLine, Arc2D fillRPM) {
    g.setColor(new Color(255, 0, 0));
    g.draw(redLine);
    g.fill(redLine);
    g.setColor(new Color(15, 220, 15));
    g.draw(fillRPM);
    g.fill(fillRPM);
  }

  void calculateScreenSpace() {
    // this is why they make you take precalc!
    alpha = (value - minValue) / (maxValue - minValue);
    angleRad = minAngleRad + alpha * (maxAngleRad - minAngleRad);
    length = Math.min(getWidth(), getHeight()) / 3.0;
    x0 = getWidth() / 2.0;
    y0 = getHeight() / 2.0;
    x1 = x0 + Math.cos(angleRad) * length;
    y1 = y0 - Math.sin(angleRad) * length;
  }

  @Override
  protected void paintComponent(Graphics gr) {
    calculateScreenSpace();
    super.paintComponent(gr);
    Graphics2D g = (Graphics2D) gr;
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    int fontSize = (int) (getWidth() * .2);
    drawGaugeDesignerArea(g, fontSize);
    arc = createGaugeTrack(g);
    Arc2D redLine = createRedLineZone(g);
    Ellipse2D circle = createGaugeFace(arc, g);
    Arc2D fillRPM = createFillZone(g);
    drawGaugeComponents(g, redLine, fillRPM);

    arc.setFrameFromCenter(new Point(0, 0), new Point(120, 120));
    circle.setFrameFromCenter(x0, y0, x0 + length * .85, y0 + length * .85);
    redLine.setFrameFromCenter(new Point(0, 0), new Point(120, 120));
    fillRPM.setFrameFromCenter(new Point(0, 0), new Point(120, 120));

    g.setColor(new Color(25, 25, 25));
    g.draw(circle);
    g.fill(circle);

    Line2D needle = createNeedle(g);
    g.draw(needle);

    Font stringFont = new Font("SansSerif", Font.BOLD, fontSize);

    drawSpeedometerValue(g, stringFont);
    g.setColor(Color.RED);
    drawTachometerValue(g, stringFont);

  }

}