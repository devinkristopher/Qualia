import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

class HMIFillBar extends Display {
    private double value;
    private final double MENISCUS_ARC_SIZE = 15;
    private Color frameColorStart;
    private Color frameColorEnd;
    private Color fillColor;
    private double frameX;
    private double frameY;
    private double frameWidth;
    private double frameHeight;
    private double fillY;
    private double fillHeight;
    private String strValue;
    private GradientPaint frameFillGradient;
    private Font labelFont;

    HMIFillBar() {
        value = 0.0;
        frameColorStart = new Color(75, 75, 75);
        frameColorEnd = new Color(175, 175, 175);
        fillColor = new Color(0, 255, 12);
        frameX = getWidth() / 3;
        frameY = getHeight() / 10;
        frameWidth = getWidth() * .3;
        frameHeight = getHeight() * .8;
        fillY = frameY + (100 - value);
        fillHeight = frameHeight - (100 - value);
        strValue = Double.toString(value);
        frameFillGradient = new GradientPaint(0, 0, frameColorStart, 100, 100, frameColorEnd);
        labelFont = new Font("Helvetica", Font.PLAIN, 18);
    }

    void setValue(double value) {
        this.value = value;
        strValue = Double.toString(value);
        strValue = strValue.substring(0, strValue.length() - 2);
        strValue = strValue + "%";
        repaint();
    }

    void setValue(int value) {
        this.value = (double) value;
        strValue = Double.toString(value);
        strValue = strValue.substring(0, strValue.length() - 2);
        strValue = strValue + "%";
        repaint();
    }

    Graphics2D createGraphics2D(Graphics gr) {
        super.paintComponent(gr);
        Graphics2D g = (Graphics2D) gr;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        return g;
    }

    // X, y, width, height, arc
    public void getScreenSpace() {
        frameX = getWidth() / 3;
        frameY = getHeight() / 10;
        frameWidth = getWidth() * .3;
        frameHeight = getHeight() * .8;
        fillHeight = frameHeight * (value / 100);
        fillY = frameY + (100 - value);
    }

    void drawFrame(Graphics2D g) {
        g.setColor(Color.GRAY);
        g.draw(new RoundRectangle2D.Double(frameX, frameY, frameWidth, frameHeight, MENISCUS_ARC_SIZE,
                MENISCUS_ARC_SIZE));
        g.setPaint(frameFillGradient);
        g.fill(new RoundRectangle2D.Double(frameX, frameY, frameWidth, frameHeight, MENISCUS_ARC_SIZE,
                MENISCUS_ARC_SIZE));
    }

    void fillFrame(Graphics2D g) {
        g.setPaint(fillColor);
        g.fill(new RoundRectangle2D.Double(frameX, fillY, frameWidth, fillHeight, MENISCUS_ARC_SIZE,
                MENISCUS_ARC_SIZE));

    }

    void drawLabel(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.setFont(labelFont);
        g.drawString(strValue, (int) (frameWidth * 2 + ((frameX + frameWidth) / 6)), (int) (frameY + frameHeight));
    }

    void drawBorder(Graphics2D g) {
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
        setValue(90);
        Graphics2D g = createGraphics2D(gr);
        getScreenSpace();
        drawFrame(g);
        fillFrame(g);
        drawLabel(g);
        drawBorder(g);
        super.cleanUp(g);
    }
}