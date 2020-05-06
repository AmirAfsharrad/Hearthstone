package Heroes;

import Cards.Card;
import Cards.CardCreator;
import GameHandler.GameHandler;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public abstract class Hero {
    protected static int defaultHp = 30;
    private int hp;
    private String type;
    private String heroPower;
    private String specialPower;
    private int deckCapacity = 15;
    private int maxRepetitiveCardsInDeck = 2;

    public Hero(){}

    public Hero(int hp, String type, String heroPower) {
        this.hp = hp;
        this.type = type;
        this.heroPower = heroPower;
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

    public String getType() {
        return type;
    }

    public int getMaxRepetitiveCardsInDeck() {
        return maxRepetitiveCardsInDeck;
    }

    public void setMaxRepetitiveCardsInDeck(int maxRepetitiveCardInDeck) {
        this.maxRepetitiveCardsInDeck = maxRepetitiveCardInDeck;
    }

    public int getDeckCapacity() {
        return deckCapacity;
    }

    public void setDeckCapacity(int deckCapacity) {
        this.deckCapacity = deckCapacity;
    }

    public JSONObject getJson(){
        JSONObject heroObject = new JSONObject();

        heroObject.put("hp", hp);
        heroObject.put("type", type);
        heroObject.put("hero power", heroPower);
        heroObject.put("special power", specialPower);

        return heroObject;
    }
}
