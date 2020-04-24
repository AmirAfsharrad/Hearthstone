package GUI.Listeners;

import GUI.Events.TestEvent;

import java.util.EventListener;

public interface TestListener extends EventListener {
    public void testEventOccurred(TestEvent testEvent);
}
