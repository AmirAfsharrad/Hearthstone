package GUI.Listeners;

import GUI.Events.ExitEvent;

import java.util.EventListener;

public interface ExitListener extends EventListener {
    void exitEventOccurred(ExitEvent exitEvent);
}
