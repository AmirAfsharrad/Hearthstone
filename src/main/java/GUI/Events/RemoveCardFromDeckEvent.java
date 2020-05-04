package GUI.Events;

import Cards.Card;
import Cards.Deck;

import java.util.EventObject;

public class RemoveCardFromDeckEvent extends EventObject {
    private Card card;
    private Deck deck;

    public RemoveCardFromDeckEvent(Object source, Card card, Deck deck) {
        super(source);
        this.card = card;
        this.deck = deck;
    }

    public Card getCard() {
        return card;
    }

    public Deck getDeck() {
        return deck;
    }
}
