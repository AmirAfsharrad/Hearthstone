package Cards;

public class Spell extends Card {
    public Spell(int mana, String name, String rarity, String heroClass, String description) {
        super(mana, name, rarity, heroClass, description, "Spell");
    }
}
