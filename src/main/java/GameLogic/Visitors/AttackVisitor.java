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
        if (minion.isDivineShield())
            minion.setDivineShield(false);
        else {
            minion.acceptDamage(DealDamageVisitor.getInstance(), attackValue);
            if (minion.isLifesteal()) {
                minion.getContestant().getHero().acceptHealth(GiveHealthVisitor.getInstance(), attackValue,
                        false, true);
            }
        }
    }

    public void visit(Hero hero, int attackValue) {
        hero.acceptDamage(DealDamageVisitor.getInstance(), attackValue);
    }
}
