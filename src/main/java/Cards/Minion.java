package Cards;

import UserHandle.User;

public class Minion extends Card {
    private int hp;
    private int attackPower;

    public Minion(int mana, String name, String rarity, String heroClass, String description, int attackPower, int hp) {
        super(mana, name, rarity, heroClass, description, "Minion");
        this.hp = hp;
        this.attackPower = attackPower;
    }

}
