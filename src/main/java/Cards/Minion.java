package Cards;

public class Minion extends Card {
    private int hp;
    private int attack;

    public Minion(int mana, String name, String rarity, String cardClass, String description, int hp, int attack) {
        super(mana, name, rarity, cardClass, description);
        this.hp = hp;
        this.attack = attack;
    }
}
