/*
 * @author Devin Rogers
 * 
 * This desktop dashboard is designed for previewing Qualia content.
 * At its core, it's a JFrame.
 * 
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Main {
  static double mphValue;
  static double minAngle;
  static double maxAngle;
  static double minValue;
  static double maxValue;
  static double value;
  static double fillValue;

  public static void main(String[] args) throws IOException {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        createAndShowGUI();
      }
    });
  }

  private static void createAndShowGUI() {
    JFrame f = new JFrame();
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.getContentPane().setLayout(new BorderLayout());

    GaugePanel gaugePanel = new GaugePanel();
    f.getContentPane().add(gaugePanel, BorderLayout.CENTER);

    JPanel topBar = new JPanel();

  

    topBar.setLayout(new GridLayout(1, 5, 10, 10));
    Sacramento sacramento = new Sacramento(100, 100, 1);
    HMIFillBar fillBar = new HMIFillBar();
    HMILiveGraph graph = new HMILiveGraph();
    BladeStack blades = new BladeStack();
    topBar.add(blades);
    topBar.add(sacramento);
    topBar.add(fillBar);
    topBar.add(graph);
    sacramento.start();
    f.add(topBar, BorderLayout.NORTH);

    JPanel controlPanel = createControlPanel(gaugePanel, fillBar);
    f.getContentPane().add(controlPanel, BorderLayout.EAST);
    // f.setUndecorated(true);

    ////////////////////////////////////////////

    JLabel gauge = new JLabel();
    JLabel settings = new JLabel();
    JLabel power = new JLabel();
    JLabel maintenance = new JLabel();
    JLabel map = new JLabel();
    JLabel vehicle = new JLabel();
    JLabel phone = new JLabel();
    JLabel music = new JLabel();

    

    gauge.setIcon(new ImageIcon("resources/img/info1.png"));
    settings.setIcon(new ImageIcon("resources/img/info2.png"));
    power.setIcon(new ImageIcon("resources/img/info3.png"));
    maintenance.setIcon(new ImageIcon("resources/img/info1.png"));
    map.setIcon(new ImageIcon("resources/img/info2.png"));
    vehicle.setIcon(new ImageIcon("resources/img/info3.png"));
    phone.setIcon(new ImageIcon("resources/img/info1.png"));
    music.setIcon(new ImageIcon("resources/img/info2.png"));
    
   

    f.setSize(600, 800);
    // f.pack();
    f.setLocationRelativeTo(null);
    f.setVisible(true);

  }

  

  static JPanel createControlPanel(final GaugePanel gaugePanel, final HMIFillBar fillBar) {
    final JSlider mphSlider = new JSlider(0, 160, 0);
    final JSlider valueSlider = new JSlider(0, 100, 0);
    final JSlider fillSlider = new JSlider(0, 100, 0);
    fillValue = fillSlider.getValue();
    mphValue = mphSlider.getValue();
    minAngle = 0 / 100.0 * Math.PI * 2;
    maxAngle = 0 / 100.0 * Math.PI * 2;
    minValue = 0 / 100.0;
    maxValue = 100 / 100.0;
    value = valueSlider.getValue() / 100.0;

    JPanel controlPanel = new JPanel(new GridLayout(0, 2));
    controlPanel.add(new JLabel("MPH"));
    controlPanel.add(mphSlider);
    controlPanel.add(new JLabel("value"));
    controlPanel.add(valueSlider);
    controlPanel.add(new JLabel("Fill Value"));
    controlPanel.add(fillSlider);

    ChangeListener changeListener = new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        mphValue = mphSlider.getValue();
        minAngle = 0 / 100.0 * Math.PI * 2;
        maxAngle = 0 / 100.0 * Math.PI * 2;
        minValue = 0 / 100.0;
        maxValue = 100 / 100.0;
        value = valueSlider.getValue() / 100.0;
        fillValue = fillSlider.getValue();

        gaugePanel.setSpeed(mphValue);
        gaugePanel.setAngles(minAngle, maxAngle);
        gaugePanel.setRange(minValue, maxValue);
        gaugePanel.setValue(value);
        fillBar.setValue(fillValue);
      }
    };
    mphSlider.addChangeListener(changeListener);
    valueSlider.addChangeListener(changeListener);
    fillSlider.addChangeListener(changeListener);
    mphSlider.setValue(0);
    valueSlider.setValue(0);
    gaugePanel.setSpeed(mphValue);
    gaugePanel.setAngles(minAngle, maxAngle);
    gaugePanel.setRange(minValue, maxValue);
    gaugePanel.setValue(value);
    return controlPanel;
  }

}