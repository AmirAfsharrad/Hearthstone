package GUI.Listeners;

import GUI.Events.PassiveChosenEvent;

import java.io.IOException;
import java.util.EventListener;

public interface PassiveChosenListener extends EventListener {
    void passiveChosenOccurred(PassiveChosenEvent passiveChosenEvent) throws IOException;
}
