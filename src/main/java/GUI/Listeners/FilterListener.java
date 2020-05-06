package GUI.Listeners;

import GUI.Events.FilterEvent;

import java.util.EventListener;

public interface FilterListener extends EventListener {
    void filterOccurred(FilterEvent filterEvent);
}
