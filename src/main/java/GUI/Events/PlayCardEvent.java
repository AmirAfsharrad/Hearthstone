package GUI.Events;

import Cards.Card;

import java.util.EventObject;

public class PlayCardEvent extends EventObject {
    private Card card;

    public PlayCardEvent(Object source, Card card) {
        super(source);
        this.card = card;
    }

    public Card getCard() {
        return card;
    }
}
