package GUI.Listeners;

import GUI.Events.PlantedCardPressedEvent;

import java.util.EventListener;

public interface PlantedCardPressedListener extends EventListener {
    void plantedCardPressedEventOccurred(PlantedCardPressedEvent plantedCardPressedEvent);
}
