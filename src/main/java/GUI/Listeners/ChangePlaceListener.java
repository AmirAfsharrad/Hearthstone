package GUI.Listeners;

import GUI.Events.ChangePlaceEvent;

import java.util.EventListener;

public interface ChangePlaceListener extends EventListener {
    void ChangePlaceOccurred(ChangePlaceEvent changePlaceEvent);
}
