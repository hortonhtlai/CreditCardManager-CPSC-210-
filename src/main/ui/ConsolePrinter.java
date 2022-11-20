package ui;

import model.Event;
import model.EventLog;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;

public class ConsolePrinter extends WindowAdapter {
    public ConsolePrinter() {

    }

    // todo
    @Override
    public void windowClosing(WindowEvent e) {
        EventLog eventLog = EventLog.getInstance();
        Iterator<Event> eventIterator = eventLog.iterator();
        while (eventIterator.hasNext()) {
            Event event = eventIterator.next();
            System.out.println(event.toString());
        }
        System.exit(0);
    }
}
