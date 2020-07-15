package GameLogic.Visitors;

import Cards.Minion;
import Heroes.Hero;

public class GiveHealthVisitor {
    private static GiveHealthVisitor giveHealthVisitor = new GiveHealthVisitor();

    public GiveHealthVisitor() {
    }

    public static GiveHealthVisitor getInstance() {
        return giveHealthVisitor;
    }

    public void visit(Minion minion, int healthValue, boolean multiplicative, boolean restore) {
        int newHpInitial = multiplicative ? minion.getHp() * healthValue : minion.getHp() + healthValue;
        int newHp = restore ? Math.min(minion.getOriginalHp(), newHpInitial) : newHpInitial;
        minion.setHp(newHp);
    }

    public void visit(Hero hero, int healthValue, boolean multiplicative, boolean restore) {
        int newHpInitial = multiplicative ? hero.getHp() * healthValue : hero.getHp() + healthValue;
        int newHp = restore ? Math.min(hero.getOriginalHp(), newHpInitial) : newHpInitial;
        hero.setHp(newHp);
    }

}
