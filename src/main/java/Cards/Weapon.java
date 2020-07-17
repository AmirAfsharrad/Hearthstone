package Cards;

import GameLogic.Interfaces.*;
import GameLogic.Visitors.GiveHealthVisitor;
import GameLogic.Visitors.ModifyAttackVisitor;

public class Weapon extends Card implements HealthTaker, ModifiableAttack, Attacker {
    private int durability;
    private int originialDurability;
    private int attackPower;
    private int turnAttack = 1;

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

    public int getTurnAttack() {
        return turnAttack;
    }

    public void setTurnAttack(int turnAttack) {
        this.turnAttack = turnAttack;
    }

    @Override
    public void acceptHealth(GiveHealthVisitor giveHealthVisitor, int healthValue, boolean multiplicative, boolean restore) {
        giveHealthVisitor.visit(this, healthValue, multiplicative, restore);
    }

    @Override
    public void acceptAttackModification(ModifyAttackVisitor modifyAttackVisitor, int attackChangeValue) {
        modifyAttackVisitor.visit(this, attackChangeValue);
    }

    @Override
    public void attack(Attackable target, int attackValue) {
        if (turnAttack > 0) {
            target.acceptAttack(attackValue);
            turnAttack--;
            durability--;
        }
    }
}
