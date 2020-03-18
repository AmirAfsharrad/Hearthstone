package Heros;

import Cards.Card;

import java.util.ArrayList;

public class Warlock extends Hero {
    public Warlock() {
        super();
    }

    public Warlock(int hp, Object heroPower, Object specialPower, ArrayList<Card> deck) {
        super(hp, heroPower, specialPower, deck);
    }

    @Override
    public String toString() {
        return "Warlock";
    }
}
