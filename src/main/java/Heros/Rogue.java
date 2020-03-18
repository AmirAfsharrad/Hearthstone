package Heros;

import Cards.Card;

import java.util.ArrayList;

public class Rogue extends Hero {
    public Rogue() {
        super();
    }

    public Rogue(int hp, Object heroPower, Object specialPower, ArrayList<Card> deck) {
        super(hp, heroPower, specialPower, deck);
    }

    @Override
    public String toString() {
        return "Rogue";
    }
}
