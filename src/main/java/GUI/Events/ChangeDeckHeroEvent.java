package GUI.Events;

import Cards.Deck;

import java.util.EventObject;

public class ChangeDeckHeroEvent extends EventObject {
    private Deck deck;

    public ChangeDeckHeroEvent(Object source, Deck deck) {
        super(source);
        this.deck = deck;
    }

    public Deck getDeck() {
        return deck;
    }
}
