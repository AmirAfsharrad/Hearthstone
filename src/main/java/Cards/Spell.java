package Cards;

public class Spell extends Card {
    private int[] target;
    private int[] damage;
    private int[] giveHealth;
    private int[] draw;
    private int[] destroy;
    private int[] setAbilities;
    private int[] modifyAttack;

    public Spell(int mana, String name, String rarity, String heroClass, String description, int[] target, int[] damage
    ,int[] giveHealth, int[] draw, int[] destroy, int[] setAbilities, int[] modifyAttack) {
        super(mana, name, rarity, heroClass, description, "Spell");
        this.target = target;
        this.damage = damage;
        this.giveHealth = giveHealth;
        this.draw = draw;
        this.destroy = destroy;
        this.setAbilities = setAbilities;
        this.modifyAttack = modifyAttack;
    }

    public Card clone() {
        return new Spell(getMana(), getName(), getRarity(), getHeroClass(), getDescription(), target, damage, giveHealth,
                draw, destroy, setAbilities, modifyAttack);
    }

    public int[] getTarget() {
        return target;
    }

    public void setTarget(int[] target) {
        this.target = target;
    }

    public int[] getDamage() {
        return damage;
    }

    public void setDamage(int[] damage) {
        this.damage = damage;
    }

    public int[] getGiveHealth() {
        return giveHealth;
    }

    public void setGiveHealth(int[] giveHealth) {
        this.giveHealth = giveHealth;
    }

    public int[] getDraw() {
        return draw;
    }

    public void setDraw(int[] draw) {
        this.draw = draw;
    }

    public int[] getDestroy() {
        return destroy;
    }

    public void setDestroy(int[] destroy) {
        this.destroy = destroy;
    }

    public int[] getSetAbilities() {
        return setAbilities;
    }

    public void setSetAbilities(int[] setAbilities) {
        this.setAbilities = setAbilities;
    }

    public int[] getModifyAttack() {
        return modifyAttack;
    }

    public void setModifyAttack(int[] modifyAttack) {
        this.modifyAttack = modifyAttack;
    }
}
