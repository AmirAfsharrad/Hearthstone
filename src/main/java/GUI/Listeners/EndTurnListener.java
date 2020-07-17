package GUI.Listeners;

import GUI.Events.EndTurnEvent;

import java.io.IOException;
import java.util.EventListener;

public interface EndTurnListener extends EventListener {
    void endTurnOccurred(EndTurnEvent endTurnEvent) throws IOException;
}
