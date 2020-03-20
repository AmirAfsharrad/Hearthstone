package Heros;

import Cards.Card;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public abstract class Hero {
    protected static int defaultHp = 30;
    private int hp;
    private String type;
    private String heroPower;
    private String specialPower;
    private ArrayList<Card> deck = new ArrayList<>();

    public Hero(){}

    public Hero(int hp, ArrayList<Card> deck, String type) {
        this.hp = hp;
        this.deck = deck;
        this.type = type;
    }

    public ArrayList<String> getDeckAsArrayOfString(){
        ArrayList<String> stringDeck = new ArrayList<>();
        for (Card card :
                deck) {
            stringDeck.add(card.getName());
        }
        return stringDeck;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public String getHeroPower() {
        return heroPower;
    }

    public void setHeroPower(String heroPower) {
        this.heroPower = heroPower;
    }

    public String getSpecialPower() {
        return specialPower;
    }

    public void setSpecialPower(String specialPower) {
        this.specialPower = specialPower;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public void setDeck(ArrayList<Card> deck) {
        this.deck = deck;
    }

    public String getType() {
        return type;
    }

    public void addToDeck(Card card){
        deck.add(card);
    }

    public void removeFromDeck(Card card){
        deck.remove(card);
    }

    public JSONObject getJson(){
        JSONObject heroObject = new JSONObject();

        heroObject.put("hp", hp);
        heroObject.put("type", type);
        heroObject.put("hero power", heroPower);
        heroObject.put("special power", specialPower);
        heroObject.put("stringDeck", getDeckAsArrayOfString());

        return heroObject;
    }
}
