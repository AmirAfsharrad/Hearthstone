package GUI.Events;

import Cards.Card;

import java.util.EventObject;

public class PlayCardEvent extends EventObject {
    private Card card;
    private int numberOnLeft;

    public PlayCardEvent(Object source, Card card, int numberOnLeft) {
        super(source);
        this.card = card;
        this.numberOnLeft = numberOnLeft;
    }

    public Card getCard() {
        return card;
    }

    public int getNumberOnLeft() {
        return numberOnLeft;
    }
}
