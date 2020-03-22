package Heroes;

import Cards.Card;

import java.util.ArrayList;

public class Warlock extends Hero {
    public Warlock() {
        super();
    }

    public Warlock(int hp, ArrayList<Card> deck) {
        super(hp, deck, "Warlock");
        super.setHeroPower("Loses 2 hps for a random choice of adding a card to current hand or adding an hp and an attackPower to a random minion.");
        super.setSpecialPower("Starts the game with hp = 35.");
    }

    @Override
    public String toString() {
        return "Warlock";
    }
}
