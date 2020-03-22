package Heroes;

import Cards.Card;

import java.util.ArrayList;

public class Rogue extends Hero {
    public Rogue() {
        super();
    }

    public Rogue(int hp, ArrayList<Card> deck) {
        super(hp, deck, "Rogue");
        super.setHeroPower("Steal a card from enemies hand for 3 manas.");
        super.setSpecialPower("Cards of other heroes type are worth 2 manas less than for other heroes.");
    }

    @Override
    public String toString() {
        return "Rogue";
    }
}
