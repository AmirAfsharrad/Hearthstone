package Heroes;

import Cards.Card;
import Cards.CardCreator;
import GameHandler.GameHandler;
import GameLogic.Interfaces.Damageable;
import GameLogic.Interfaces.HealthTaker;
import GameLogic.Visitors.DealDamageVisitor;
import GameLogic.Visitors.GiveHealthVisitor;
import UserHandle.Contestant;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public abstract class Hero implements Damageable, HealthTaker {
    protected static int defaultHp = 30;
    private int hp;
    private int originalHp;
    private String type;
    private String heroPower;
    private String specialPower;
    private int deckCapacity = 15;
    private int maxRepetitiveCardsInDeck = 2;
    private Contestant contestant;

    public Hero(){}

    public Hero(int hp, String type, String heroPower) {
        this.hp = hp;
        this.originalHp = hp;
        this.type = type;
        this.heroPower = heroPower;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public Contestant getContestant() {
        return contestant;
    }

    public void setContestant(Contestant contestant) {
        this.contestant = contestant;
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

    public int getOriginalHp() {
        return originalHp;
    }

    @Override
    public void acceptDamage(DealDamageVisitor dealDamageVisitor, int damageValue) {
        dealDamageVisitor.visit(this, damageValue);
    }

    @Override
    public void acceptHealth(GiveHealthVisitor giveHealthVisitor, int healthValue, boolean multiplicative, boolean restore) {
        giveHealthVisitor.visit(this, healthValue, multiplicative, restore);
    }
}
