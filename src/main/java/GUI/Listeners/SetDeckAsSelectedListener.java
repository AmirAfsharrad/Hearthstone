package GUI.Listeners;

import GUI.Events.SetDeckAsSelectedEvent;

import java.util.EventListener;

public interface SetDeckAsSelectedListener extends EventListener {
    void setDeckAsSelectedOccurred(SetDeckAsSelectedEvent setDeckAsSelectedEvent);
}
