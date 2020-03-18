package Heros;

import Cards.Card;

import java.util.ArrayList;

public class Mage extends Hero {
    public Mage(){
        super();
    }

    public Mage(int hp, Object heroPower, Object specialPower, ArrayList<Card> deck) {
        super(hp, heroPower, specialPower, deck);
    }

    @Override
    public String toString() {
        return "Mage";
    }
}
