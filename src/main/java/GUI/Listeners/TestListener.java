package GUI.Listeners;

import GUI.Events.TestEvent;

import java.util.EventListener;

public interface TestListener extends EventListener {
    void testEventOccurred(TestEvent testEvent);
}
