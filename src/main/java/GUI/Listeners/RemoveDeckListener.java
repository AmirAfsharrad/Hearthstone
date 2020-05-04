package GUI.Listeners;

import GUI.Events.RemoveDeckEvent;

import java.util.EventListener;
import java.util.EventObject;

public interface RemoveDeckListener extends EventListener {
    void removeDeckOccurred(RemoveDeckEvent removeDeckEvent);
}
