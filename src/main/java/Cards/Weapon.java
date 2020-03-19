package Cards;

public class Weapon extends Card {
    private int durability;

    public Weapon(int mana, String name, String rarity, String heroClass, String description, int durability) {
        super(mana, name, rarity, heroClass, description, "Weapon");
        this.durability = durability;
    }
}
