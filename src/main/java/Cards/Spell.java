package Cards;

public class Spell extends Card {
    private int[] target;
    private int[] damage;
    private int[] giveHealth;

    public Spell(int mana, String name, String rarity, String heroClass, String description, int[] target, int[] damage
    ,int[] giveHealth) {
        super(mana, name, rarity, heroClass, description, "Spell");
        this.target = target;
        this.damage = damage;
        this.giveHealth = giveHealth;
    }

    public Card clone() {
        return new Spell(getMana(), getName(), getRarity(), getHeroClass(), getDescription(), target, damage, giveHealth);
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
}
