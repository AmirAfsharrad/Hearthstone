package Cards;

import GameLogic.Interfaces.Damageable;
import GameLogic.Interfaces.HealthTaker;
import GameLogic.Visitors.DealDamageVisitor;
import GameLogic.Visitors.GiveHealthVisitor;
import UserHandle.User;

public class Minion extends Card implements Damageable, HealthTaker {
    private int hp;
    private int originalHp;
    private int attackPower;

    public Minion(int mana, String name, String rarity, String heroClass, String description, int attackPower, int hp) {
        super(mana, name, rarity, heroClass, description, "Minion");
        this.hp = hp;
        this.originalHp = hp;
        this.attackPower = attackPower;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public Card clone() {
        return new Minion(getMana(), getName(), getRarity(), getHeroClass(), getDescription(), getAttackPower(), getHp());
    }

    public int getOriginalHp() {
        return originalHp;
    }

    @Override
    public void acceptDamage(DealDamageVisitor dealDamageVisitor, int damageValue) {
        dealDamageVisitor.visit(this, damageValue);
    }

    @Override
    public void acceptHealth(GiveHealthVisitor giveHealthVisitor, int healthValue, boolean multiplicative, boolean restore) {
        giveHealthVisitor.visit(this, healthValue, multiplicative, restore);
    }
}
