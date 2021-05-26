# Qualia
Qualia is an open-source UX library for HCI and HMI solutions. Aside from offering a widget toolkit, Qualia inclusively offers integrated tools for interfacing with various devices, sensors, and critical components.  For example, CPU usage percentage will be an available metric in the near future.  Because Qualia is open-source, these tools can be optimized for your application.
![A demonstration of various Qualia components.](https://i.imgur.com/en8rNpp.png)

# GaugePanel
GaugePanel is a radial gauge that offers a fill level, text value, and classic needle for a metric.  It offers a redline zone that is always visible from the fill line.  Additionally, it can present a separate metric inside the frame.  One example of this usage is on modern vehicles where the instrument cluster shows the speedometer and the tachometer on the same display unit.
The GaugePanel is designed to be as scalable as possible to accomodate and adapt to any parent that can contain a JPanel.  Because the GaugePanel is drawn in ratios and proportions, it will automatically resize and scale with its parent container.
![A demonstration of a resized GaugePanel.](https://imgur.com/a/5Hv9h0B)

# Known Issues:
- General: CPU consumption due to repainting with Graphics2D.
- GaugePanel: RPM label may cut off part of the 'M' depending on the parent container the JPanel is in.
- GaugePanel: The MPH value does not scale properly at extremely large sizes (again, likely a parent container resizing issue).
