package GUI.Listeners;

import GUI.Events.AddCardToDeckEvent;

import java.util.EventListener;

public interface AddCardToDeckListener extends EventListener {
    void addCardToDeckOccurred(AddCardToDeckEvent addCardToDeckEvent);
}
