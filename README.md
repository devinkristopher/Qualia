# Qualia
An open-source Java UI library featuring HCI/HMI elements and tools. Written in Java, it is primarily based on the existing AWT and Swing libraries.
![A demonstration of various Qualia components.](https://imgur.com/gl6moez.png)

# Elements

# GaugePanel
The GaugePanel is a radial gauge that offers a classic needle, a corresponding outer fill level, corresponding digital value off-gauge, and a secondary digital value on the top center of the gauge.  It offers a redline zone that is always visible from the fill line.  One example of this usage is on modern vehicles where the instrument cluster shows the speedometer and the tachometer on the same display unit.
The GaugePanel is designed to accomodate and adapt to any parent that can contain a JPanel.  Because the GaugePanel is drawn in ratios and proportions, it will automatically resize and scale with its parent container.
![A demonstration of a GaugePanel.](https://imgur.com/lStKij3.png)
![Another demonstration of a GaugePanel.](https://imgur.com/4PwZifj.png)

# Known Issues:
- General: CPU consumption due to repainting with Graphics2D.
- GaugePanel: RPM label may cut off part of the 'M' depending on the parent container the JPanel is in.
- GaugePanel: The MPH value does not scale properly at extremely large sizes (again, likely a parent container resizing issue).
