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

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public Object getHeroPower() {
        return heroPower;
    }

    public void setHeroPower(Object heroPower) {
        this.heroPower = heroPower;
    }

    public Object getSpecialPower() {
        return specialPower;
    }

    public void setSpecialPower(Object specialPower) {
        this.specialPower = specialPower;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public void setDeck(ArrayList<Card> deck) {
        this.deck = deck;
    }
}
