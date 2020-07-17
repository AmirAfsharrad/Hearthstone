package Cards;

import GameLogic.Interfaces.Damageable;
import GameLogic.Interfaces.HealthTaker;
import UserHandle.Contestant;

import java.util.Objects;
import java.util.Random;

public abstract class Card {
    private int mana;
    private String name;
    private String rarity;
    private String heroClass;
    private String description;
    private String type;
    private Contestant contestant;

    public Card(int mana, String name, String rarity, String heroClass, String description, String type) {
        this.mana = mana;
        this.name = name;
        this.rarity = rarity;
        this.heroClass = heroClass;
        this.description = description;
        this.type = type;
    }

    public int getMana() {
        return mana;
    }

    public String getName() {
        return name;
    }

    public String getRarity() {
        return rarity;
    }

    public String getHeroClass() {
        return heroClass;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public void setContestant(Contestant contestant) {
        this.contestant = contestant;
    }

    public Contestant getContestant() {
        return contestant;
    }

    public int getPrice(){
        switch (rarity){
            case "Common": return 10;
            case "Rare": return 20;
            case "Epic": return 30;
            case "Legendary": return 40;
        }
        return 0;
    }

    public abstract Card clone();

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Card card = (Card) object;
        return Objects.equals(name, card.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

