package GUI.Events;

import Cards.Deck;

import java.util.EventObject;

public class DeckRenameEvent extends EventObject {
    private Deck deck;

    public DeckRenameEvent(Object source, Deck deck) {
        super(source);
        this.deck = deck;
    }

    public Deck getDeck() {
        return deck;
    }
}
