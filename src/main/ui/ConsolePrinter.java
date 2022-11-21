package ui;

import model.Event;
import model.EventLog;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// Represents a console printer for printing event log to console
// Code partially based on Alarm System application presented in CPSC 210 as indicated for specific methods
// Alarm System: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
public class ConsolePrinter extends WindowAdapter {
    // EFFECTS: creates a new ConsolePrinter object
    public ConsolePrinter() {}

    // MODIFIES: this
    // EFFECTS: prints event log to console and exits credit card manager application
    // Code based on Alarm System application
    @Override
    public void windowClosing(WindowEvent e) {
        EventLog eventLog = EventLog.getInstance();
        for (Event event : eventLog) {
            System.out.println(event.toString());
        }
        System.exit(0);
    }
}
