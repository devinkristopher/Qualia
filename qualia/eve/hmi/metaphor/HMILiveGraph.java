import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

class HMILiveGraph extends Display {
  private double value = 0.0;
  private double minAngleRad = 0.0;
  private double maxAngleRad = 0.0;
  private double minValue = 0.0;
  private double maxValue = 0.0;
  double frameX;
  double frameY;
  double frameWidth;
  double frameHeight;
  double frameArcWidth;
  double frameArcHeight;
  ArrayList<Integer> xValues;
  ArrayList<Integer> yValues;

  void setValue(double value) {
    this.value = value;
    repaint();
  }

  void setRange(double minValue, double maxValue) {
    this.minValue = minValue;
    this.maxValue = maxValue;
    repaint();
  }

  public void getScreenSpace() {
    frameX = 0;
    frameY = 0;
    frameWidth = getWidth() - (getWidth() * .2);
    frameHeight = getHeight() - (getHeight() * .2);
    frameArcWidth = 15;
    frameArcHeight = 15;
  }

  void fillBlade(Graphics2D g) {
    Color fillColorStart = new Color(75, 75, 75);
    Color fillColorEnd = new Color(175, 175, 175);
    GradientPaint frameFillGradient = new GradientPaint(0, 0, fillColorStart, 100, 100, fillColorEnd);
    g.setColor(Color.GRAY);
    g.draw(new Rectangle2D.Double(frameX, frameY, frameWidth, frameHeight));
    g.setPaint(frameFillGradient);
    g.fill(new Rectangle2D.Double(frameX, frameY, frameWidth, frameHeight));
  }

  void getDataPoints() {
    xValues = new ArrayList<Integer>();
    xValues.add(0);
    xValues.add(1);
    xValues.add(2);
    xValues.add(3);
    xValues.add(4);
    xValues.add(5);
    yValues = new ArrayList<Integer>();
    yValues.add(3);
    yValues.add(2);
    yValues.add(4);
    yValues.add(1);
    yValues.add(3);
    yValues.add(5);
  }

  void drawLine(Graphics2D g, int graphX, int graphY) {
    g.setColor(Color.GREEN);
    int numPoints = 5;
    for (int i = 0; i < 6; i++) {
      int nextX = xValues.get(i);
      int nextY = yValues.get(i);
      nextX = (int) (((frameWidth / 5) * nextX));
      nextY = (int) (frameHeight - ((frameHeight / 5) * nextY));
      g.drawLine(graphX, graphY, nextX, nextY);
      graphX = nextX;
      graphY = nextY;
    }
  }

  void fillVolume(Graphics2D g) {
    getDataPoints();
    int graphX = (int) frameX;
    int graphY = (int) frameHeight;
    drawLine(g, graphX, graphY);
  }

  void drawFrame(Graphics2D g) {
    g.setColor(new Color(150, 150, 150));
    setBorder(new EmptyBorder(0, 0, 0, 0));
    g.setComposite(AlphaComposite.SrcOver.derive(0.5f));
    g.fillRect(0, 0, getWidth() / 40, getHeight());
    g.fillRect(0, 0, getWidth(), getHeight() / 25);
    g.fillRect(0, getHeight() - 5, getWidth(), getHeight() / 25);
    g.fillRect(getWidth() - 4, 0, getWidth() / 40, getHeight());
  }

  void cleanUp(Graphics2D g) {
    g.dispose();
}

  @Override
  protected void paintComponent(Graphics gr) {
    super.paintComponent(gr);
    Graphics2D g = (Graphics2D) gr;
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    getScreenSpace();
    fillBlade(g);
    fillVolume(g);
    drawFrame(g);
    super.cleanUp(g);
  }
}