package GUI.Listeners;

import GUI.Events.GetTextEvent;

import java.util.EventListener;

public interface GetTextListener extends EventListener {
    void getTextOccurred(GetTextEvent getTextEvent);
}
