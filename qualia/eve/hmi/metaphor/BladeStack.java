import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.Font;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.awt.geom.RoundRectangle2D;
import java.awt.GridLayout;
import java.awt.GradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.io.IOException;
import java.awt.Point;

import javax.swing.border.EmptyBorder;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class BladeStack extends Display {
    private double value = 0.0;
    private double minValue = 0.0;
    private double maxValue = 0.0;
    double frameX;
    double frameY;
    double frameWidth;
    double frameHeight;
    double frameArcWidth;
    double frameArcHeight;
    Color fillColorStart;
    Color fillColorEnd;

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
        frameX = getWidth() / 6;
        frameY = getHeight() / 10;
        frameWidth = getWidth() * .7;
        frameHeight = getHeight() * .3;
        frameArcWidth = 15;
        frameArcHeight = 15;
    }

    void fillBlade(Graphics2D g) {
        fillColorStart = new Color(75, 75, 75);
        fillColorEnd = new Color(175, 175, 175);
        GradientPaint frameFillGradient = new GradientPaint(0, 0, fillColorStart, 100, 100, fillColorEnd);
        g.setColor(Color.GRAY);
        g.draw(new RoundRectangle2D.Double(frameX, frameY, frameWidth, frameHeight, frameArcWidth, frameArcHeight));
        g.setPaint(frameFillGradient);
        g.fill(new RoundRectangle2D.Double(frameX, frameY, frameWidth, frameHeight, frameArcWidth, frameArcHeight));

        g.setColor(Color.GRAY);
        g.draw(new RoundRectangle2D.Double(frameX, frameY + frameHeight + frameY, frameWidth, frameHeight, frameArcWidth, frameArcHeight));
        g.setPaint(frameFillGradient);
        g.fill(new RoundRectangle2D.Double(frameX, frameY + frameHeight + frameY, frameWidth, frameHeight, frameArcWidth, frameArcHeight));

        g.setColor(Color.GRAY);
        g.draw(new RoundRectangle2D.Double(frameX, frameY + frameHeight + frameY + frameHeight + frameY, frameWidth, frameHeight, frameArcWidth, frameArcHeight));
        g.setPaint(frameFillGradient);
        g.fill(new RoundRectangle2D.Double(frameX, frameY + frameHeight + frameY + frameHeight + frameY, frameWidth, frameHeight, frameArcWidth, frameArcHeight));

    }

    void fillVolume(Graphics2D g) {
        value = 90;
        double fillY = frameY;
        double fillHeight = frameHeight - (100 - value);
        Color fillColor = new Color(242, 242, 247);
        Color fillColor1 = new Color(50, 215, 75);
        g.setPaint(fillColor);
        g.fill(new RoundRectangle2D.Double(frameX, frameY, frameWidth, frameHeight, frameArcWidth, frameArcHeight));
        Color fillColor2 = new Color(255, 149, 0);
        g.setPaint(fillColor);
        g.fill(new RoundRectangle2D.Double(frameX, frameY + frameHeight + frameY, frameWidth, frameHeight,
                frameArcWidth, frameArcHeight));
        Color fillColor3 = new Color(255, 59, 48);
        g.setPaint(fillColor);
        g.fill(new RoundRectangle2D.Double(frameX, frameY + frameHeight + frameY + frameHeight + frameY, frameWidth,
                frameHeight, frameArcWidth, frameArcHeight));
    }

    void drawLabel(Graphics2D g) {
        g.setColor(Color.BLACK);
        String strValue = Double.toString(value);
        strValue = strValue.substring(0, strValue.length() - 2);
        strValue = strValue + "%";
        g.setFont(new Font("Helvetica", Font.PLAIN, 18));
        g.drawString(strValue, (int) (frameWidth * 2 + ((frameX + frameWidth) / 6)), (int) (frameY + frameHeight));
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

    @Override
    protected void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        Graphics2D g = (Graphics2D) gr;
        g.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        getScreenSpace();
        fillBlade(g);
        fillVolume(g);
        drawLabel(g);
        drawFrame(g);
        super.cleanUp(g);
    }
}