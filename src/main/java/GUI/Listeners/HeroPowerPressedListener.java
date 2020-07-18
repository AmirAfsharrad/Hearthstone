package GUI.Listeners;

import GUI.Events.HeroPowerPressedEvent;

import java.io.IOException;
import java.util.EventListener;

public interface HeroPowerPressedListener extends EventListener {
    void heroPowerPressedEventOccurred(HeroPowerPressedEvent heroPowerPressedEvent) throws IOException;
}
