package GUI.Events;

import Cards.Card;
import Cards.Deck;

import java.util.EventObject;

public class AddCardToDeckEvent extends EventObject {
    private Card card;
    private Deck deck;

    public AddCardToDeckEvent(Object source, Card card, Deck deck) {
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
