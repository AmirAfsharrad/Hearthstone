package Cards;

import javax.swing.*;

public class CardButton extends JButton {
    private Card card;

    public CardButton(Card card) {
        this.card = card;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
