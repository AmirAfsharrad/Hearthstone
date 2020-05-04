package GUI.Listeners;

import GUI.Events.CreateDeckEvent;

import java.util.EventListener;

public interface CreateDeckListener extends EventListener {
    void CreateDeckOccurred(CreateDeckEvent createDeckEvent);
}
