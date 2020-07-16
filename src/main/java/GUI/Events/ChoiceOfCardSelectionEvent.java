package GUI.Events;

import Cards.Card;

import java.util.EventObject;

public class ChoiceOfCardSelectionEvent extends EventObject {
    Card card;
    public ChoiceOfCardSelectionEvent(Object source, Card card) {
        super(source);
        this.card = card;
    }

    public Card getCard() {
        return card;
    }
}
