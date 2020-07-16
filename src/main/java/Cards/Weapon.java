package Cards;

import GameLogic.Interfaces.Damageable;
import GameLogic.Interfaces.HealthTaker;
import GameLogic.Interfaces.ModifiableAttack;
import GameLogic.Visitors.GiveHealthVisitor;
import GameLogic.Visitors.ModifyAttackVisitor;

public class Weapon extends Card implements HealthTaker, ModifiableAttack {
    private int durability;
    private int originialDurability;
    private int attackPower;

    public Weapon(int mana, String name, String rarity, String heroClass, String description, int attackPower, int durability) {
        super(mana, name, rarity, heroClass, description, "Weapon");
        this.durability = durability;
        this.attackPower = attackPower;
        this.originialDurability = durability;
    }

    public int getDurability() {
        return durability;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public int getOriginialDurability() {
        return originialDurability;
    }

    public void setOriginialDurability(int originialDurability) {
        this.originialDurability = originialDurability;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public Card clone() {
        return new Weapon(getMana(), getName(), getRarity(), getHeroClass(), getDescription(), getAttackPower(), getDurability());
    }

    @Override
    public void acceptHealth(GiveHealthVisitor giveHealthVisitor, int healthValue, boolean multiplicative, boolean restore) {
        giveHealthVisitor.visit(this, healthValue, multiplicative, restore);
    }

    @Override
    public void acceptAttackModification(ModifyAttackVisitor modifyAttackVisitor, int attackChangeValue) {
        modifyAttackVisitor.visit(this, attackChangeValue);
    }
}
