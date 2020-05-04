package GUI.Listeners;

import GUI.Events.RemoveCardFromDeckEvent;

import java.util.EventListener;

public interface RemoveCardFromDeckListener extends EventListener {
    void removeCardFromDeckOccurred(RemoveCardFromDeckEvent removeCardFromDeckEvent);
}
