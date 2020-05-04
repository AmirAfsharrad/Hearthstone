package GUI.Events;

import Cards.Deck;

import java.util.EventListener;
import java.util.EventObject;

public class RemoveDeckEvent extends EventObject {
    private Deck deck;

    public RemoveDeckEvent(Object source, Deck deck) {
        super(source);
        this.deck = deck;
    }

    public Deck getDeck() {
        return deck;
    }
}
