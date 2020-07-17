package GameLogic.Visitors;

import Cards.Minion;
import Cards.Weapon;
import Heroes.Hero;

public class DealDamageVisitor {
    private static DealDamageVisitor dealDamageVisitor = new DealDamageVisitor();

    private DealDamageVisitor() {
//        if (dealDamageVisitor == null)
//        dealDamageVisitor = new DealDamageVisitor();
    }

    public static DealDamageVisitor getInstance() {
        return dealDamageVisitor;
    }

    public void visit(Minion minion, int damageValue) {
        System.out.println("DAMAGE DEAL WITH VALUE " + damageValue + " ***********************************");
        minion.setHp(Math.max(minion.getHp() - damageValue, 0));
    }

    public void visit(Hero hero, int damageValue) {
        hero.setHp(Math.max(hero.getHp() - damageValue, 0));
    }
}
