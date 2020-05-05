package GUI.Listeners;

import GUI.Events.ChooseFromListEvent;

import java.util.EventListener;

public interface ChooseFromListListener extends EventListener {
    void ChooseFromListOccurred(ChooseFromListEvent chooseFromListEvent);
}
