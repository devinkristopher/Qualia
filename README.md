# Qualia
Qualia is an open-source UX library for HCI and HMI solutions. Aside from offering a widget toolkit, Qualia inclusively offers integrated tools for interfacing with various devices, sensors, and critical components.  For example, CPU usage percentage will be an available metric in the near future.  Because Qualia is open-source, these tools can be optimized for your application.
![A demonstration of various Qualia components.](https://i.imgur.com/en8rNpp.png)
# Known Issues:
- General: CPU consumption due to repainting with Graphics2D.
- GaugePanel: RPM label may cut off part of the 'M' depending on the parent container the JPanel is in.
- GaugePanel: The MPH value does not scale properly at extremely large sizes (again, likely a parent container resizing issue).
