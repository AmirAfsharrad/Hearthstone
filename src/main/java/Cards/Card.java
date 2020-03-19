package Cards;

public abstract class Card {
    private int mana;
    private String name;
    private String rarity;
    private String heroClass;
    private String description;
    private String type;

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

    @Override
    public String toString() {
        return name;
    }
}

