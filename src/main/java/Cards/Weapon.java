package Cards;

public class Weapon extends Card {
    private int durability;

    public Weapon(int mana, String name, String rarity, String cardClass, String description, int durability) {
        super(mana, name, rarity, cardClass, description);
        this.durability = durability;
    }
}
