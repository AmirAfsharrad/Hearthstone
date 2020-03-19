package Heros;

import Cards.Card;

import java.util.ArrayList;

public class Mage extends Hero {
    public Mage(){
        super();
    }

    public Mage(int hp, ArrayList<Card> deck) {
        super(hp, deck, "Mage");
        super.setHeroPower("Can attack any enemy with 2 manas and do a one-hp harm to them.");
        super.setSpecialPower("Spells are worth 2 manas less than for other heroes.");
    }

    @Override
    public String toString() {
        return "Mage";
    }
}
