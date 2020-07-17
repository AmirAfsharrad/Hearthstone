package GUI.Listeners;

import GUI.Events.PlantedCardPressedEvent;

import java.io.IOException;
import java.util.EventListener;

public interface PlantedCardPressedListener extends EventListener {
    void plantedCardPressedEventOccurred(PlantedCardPressedEvent plantedCardPressedEvent) throws IOException;
}
