package GUI.Events;

import Cards.Card;

import java.util.EventObject;

public class ChoiceOfWeaponEvent extends EventObject {
    Card card;

    public ChoiceOfWeaponEvent(Object source, Card card) {
        super(source);
        this.card = card;
    }

    public Card getCard() {
        return card;
    }
}
