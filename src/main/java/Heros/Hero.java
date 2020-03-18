package Heros;

import Cards.Card;

import java.util.ArrayList;

public abstract class Hero {
    private int hp;
    private Object heroPower;
    private Object specialPower;
    private ArrayList<Card> deck;

    public Hero(){}

    public Hero(int hp, Object heroPower, Object specialPower, ArrayList<Card> deck) {
        this.hp = hp;
        this.heroPower = heroPower;
        this.specialPower = specialPower;
        this.deck = deck;
    }
}
