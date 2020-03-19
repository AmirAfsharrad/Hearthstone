package Cards;

public class Weapon extends Card {
    private int durability;
    private int attackPower;

    public Weapon(int mana, String name, String rarity, String heroClass, String description, int attackPower, int durability) {
        super(mana, name, rarity, heroClass, description, "Weapon");
        this.durability = durability;
        this.attackPower = attackPower;
    }
}
