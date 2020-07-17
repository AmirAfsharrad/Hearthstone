package GameLogic.Visitors;

import Cards.Minion;
import Heroes.Hero;

public class AttackVisitor {
    private static AttackVisitor attackVisitor = new AttackVisitor();

    private AttackVisitor() {
    }

    public static AttackVisitor getInstance() {
        return attackVisitor;
    }

    public void visit(Minion minion, int attackValue) {
        minion.acceptDamage(DealDamageVisitor.getInstance(), attackValue);
    }

    public void visit(Hero hero, int attackValue) {
        hero.acceptDamage(DealDamageVisitor.getInstance(), attackValue);
    }
}
