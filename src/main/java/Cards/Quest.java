package Cards;

public class Quest extends Card {
    public Quest(int mana, String name, String rarity, String heroClass, String description) {
        super(mana, name, rarity, heroClass, description, "Quest");
    }

    public Card clone() {
        return new Quest(getMana(), getName(), getRarity(), getHeroClass(), getDescription());
    }
}
