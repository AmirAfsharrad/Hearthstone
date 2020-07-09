package Cards;

import javax.swing.*;

public class CardButton extends JButton {
    private Card card;

    public CardButton(Card card) {
        this.card = card;
        this.setName(card.getName());
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
