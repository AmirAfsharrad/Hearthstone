package GUI.Events;

import Cards.Card;

import java.util.EventObject;

public class PlantedCardPressedEvent extends EventObject {
    private Card card;

    public PlantedCardPressedEvent(Object source, Card card) {
        super(source);
        this.card = card;
    }

    public Card getCard() {
        return card;
    }
}
