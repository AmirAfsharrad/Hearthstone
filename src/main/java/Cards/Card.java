package Cards;

public abstract class Card {
    protected int mana;
    protected String name;
    protected String rarity;
    protected String cardClass;
    protected String description;

    public Card(int mana, String name, String rarity, String cardClass, String description) {
        this.mana = mana;
        this.name = name;
        this.rarity = rarity;
        this.cardClass = cardClass;
        this.description = description;
    }

    @Override
    public String toString() {
        return name;
    }
}

