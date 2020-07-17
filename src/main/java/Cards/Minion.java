package Cards;

import GameLogic.Interfaces.*;
import GameLogic.Visitors.AttackVisitor;
import GameLogic.Visitors.DealDamageVisitor;
import GameLogic.Visitors.GiveHealthVisitor;
import GameLogic.Visitors.ModifyAttackVisitor;

public class Minion extends Card implements Damageable, HealthTaker, ModifiableAttack, Attacker, Attackable, Target {
    private int hp;
    private int originalHp;
    private int attackPower;
    private boolean taunt;
    private boolean charge;
    private boolean divineShield;
    private boolean rush;
    private boolean poisonous;
    private boolean stealth;
    private boolean windfury;
    private boolean lifesteal;
    private boolean reborn;
    private boolean battlecry;
    private int turnAttack = 1;
    private boolean justPlanted;
    private int[] turnStartDamage;
    private int[] turnEndDamage;
    private int[] drawEffect;

    public Minion(int mana, String name, String rarity, String heroClass, String description, int attackPower, int hp,
                  boolean battlecry, int[] turnStartDamage, int[] turnEndDamage, int[] drawEffect) {
        super(mana, name, rarity, heroClass, description, "Minion");
        this.hp = hp;
        this.originalHp = hp;
        this.attackPower = attackPower;
        this.battlecry = battlecry;
        this.turnStartDamage = turnStartDamage;
        this.turnEndDamage = turnEndDamage;
        this.drawEffect = drawEffect;
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
        Minion minion =  new Minion(getMana(), getName(), getRarity(), getHeroClass(), getDescription(), getAttackPower(), getHp(),
                battlecry, turnStartDamage, turnEndDamage, drawEffect);
        minion.setAbilities(this);
        return minion;
    }

    public int getOriginalHp() {
        return originalHp;
    }

    public boolean isTaunt() {
        return taunt;
    }

    public void setTaunt(boolean taunt) {
        this.taunt = taunt;
    }

    public boolean isCharge() {
        return charge;
    }

    public void setCharge(boolean charge) {
        this.charge = charge;
    }

    public boolean isDivineShield() {
        return divineShield;
    }

    public void setDivineShield(boolean divineShield) {
        this.divineShield = divineShield;
    }

    public boolean isRush() {
        return rush;
    }

    public void setRush(boolean rush) {
        this.rush = rush;
    }

    public boolean isPoisonous() {
        return poisonous;
    }

    public void setPoisonous(boolean poisonous) {
        this.poisonous = poisonous;
    }

    public boolean isStealth() {
        return stealth;
    }

    public void setStealth(boolean stealth) {
        this.stealth = stealth;
    }

    public boolean isWindfury() {
        return windfury;
    }

    public void setWindfury(boolean windfury) {
        this.windfury = windfury;
    }

    public boolean isLifesteal() {
        return lifesteal;
    }

    public void setLifesteal(boolean lifesteal) {
        this.lifesteal = lifesteal;
    }

    public boolean isReborn() {
        return reborn;
    }

    public void setReborn(boolean reborn) {
        this.reborn = reborn;
    }

    public int getTurnAttack() {
        return turnAttack;
    }

    public void setTurnAttack(int turnAttack) {
        this.turnAttack = turnAttack;
    }

    public boolean isJustPlanted() {
        return justPlanted;
    }

    public void setJustPlanted(boolean justPlanted) {
        this.justPlanted = justPlanted;
    }

    public int[] getTurnStartDamage() {
        return turnStartDamage;
    }

    public void setTurnStartDamage(int[] turnStartDamage) {
        this.turnStartDamage = turnStartDamage;
    }

    public int[] getTurnEndDamage() {
        return turnEndDamage;
    }

    public void setTurnEndDamage(int[] turnEndDamage) {
        this.turnEndDamage = turnEndDamage;
    }

    public int[] getDrawEffect() {
        return drawEffect;
    }

    public void setDrawEffect(int[] drawEffect) {
        this.drawEffect = drawEffect;
    }

    public void setAbilities(int[] abilities) {
        taunt = abilities[1] == 1;
        charge = abilities[2] == 1;
        divineShield = abilities[3] == 1;
        rush = abilities[4] == 1;
        poisonous = abilities[5] == 1;
        stealth = abilities[6] == 1;
        windfury = abilities[7] == 1;
        lifesteal = abilities[8] == 1;
        reborn = abilities[9] == 1;
    }

    public void setAbilities(Minion minion) {
        taunt = minion.taunt;
        charge = minion.charge;
        divineShield = minion.divineShield;
        rush = minion.rush;
        poisonous = minion.poisonous;
        stealth = minion.stealth;
        windfury = minion.windfury;
        lifesteal = minion.lifesteal;
        reborn = minion.reborn;
    }

    public void setOriginalHp(int originalHp) {
        this.originalHp = originalHp;
    }

    public boolean hasBattlecry() {
        return battlecry;
    }

    public void setBattlecry(boolean battlecry) {
        this.battlecry = battlecry;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    @Override
    public void acceptDamage(DealDamageVisitor dealDamageVisitor, int damageValue) {
        dealDamageVisitor.visit(this, damageValue);
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
    public void acceptAttack(int attackValue) {
        AttackVisitor.getInstance().visit(this, attackValue);
    }

    @Override
    public void attack(Attackable target, int attackValue) {
        if (turnAttack > 0) {
            target.acceptAttack(attackValue);
            turnAttack--;
        }
    }
}
