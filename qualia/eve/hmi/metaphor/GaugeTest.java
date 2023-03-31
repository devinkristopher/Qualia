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

public class GaugeTest {
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

    JPanel controlPanel = createControlPanel(gaugePanel);
    f.getContentPane().add(controlPanel, BorderLayout.EAST);
    // f.setUndecorated(true);

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

  

  static JPanel createControlPanel(final GaugePanel gaugePanel) {
    final JSlider mphSlider = new JSlider(0, 160, 0);
    final JSlider minAngleSlider = new JSlider(0, 100, 0);
    final JSlider maxAngleSlider = new JSlider(0, 100, 0);
    final JSlider minValueSlider = new JSlider(0, 100, 0);
    final JSlider maxValueSlider = new JSlider(0, 100, 0);
    final JSlider valueSlider = new JSlider(0, 100, 0);

    JPanel controlPanel = new JPanel(new GridLayout(0, 2));
    controlPanel.add(new JLabel("MPH"));
    controlPanel.add(mphSlider);
    controlPanel.add(new JLabel("minAngle"));
    controlPanel.add(minAngleSlider);
    controlPanel.add(new JLabel("maxAngle"));
    controlPanel.add(maxAngleSlider);
    controlPanel.add(new JLabel("minValue"));
    controlPanel.add(minValueSlider);
    controlPanel.add(new JLabel("maxValue"));
    controlPanel.add(maxValueSlider);
    controlPanel.add(new JLabel("value"));
    controlPanel.add(valueSlider);

    ChangeListener changeListener = new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        double mphValue = mphSlider.getValue();
        double minAngle = minAngleSlider.getValue() / 100.0 * Math.PI * 2;
        double maxAngle = maxAngleSlider.getValue() / 100.0 * Math.PI * 2;
        double minValue = minValueSlider.getValue() / 100.0;
        double maxValue = maxValueSlider.getValue() / 100.0;
        double value = valueSlider.getValue() / 100.0;

        gaugePanel.setSpeed(mphValue);
        gaugePanel.setAngles(minAngle, maxAngle);
        gaugePanel.setRange(minValue, maxValue);
        gaugePanel.setValue(value);
      }
    };
    mphSlider.addChangeListener(changeListener);
    minAngleSlider.addChangeListener(changeListener);
    maxAngleSlider.addChangeListener(changeListener);
    minValueSlider.addChangeListener(changeListener);
    maxValueSlider.addChangeListener(changeListener);
    valueSlider.addChangeListener(changeListener);

    mphSlider.setValue(0);
    minAngleSlider.setValue(0);
    maxAngleSlider.setValue(0);
    minValueSlider.setValue(0);
    maxValueSlider.setValue(100);
    valueSlider.setValue(0);

    return controlPanel;
  }

}