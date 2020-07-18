package GameLogic.Visitors;

import Cards.Minion;
import Cards.Weapon;
import GameHandler.GameHandler;
import Heroes.Hero;
import Places.Playground;

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
        minion.setHp(Math.max(minion.getHp() - damageValue, 0));
        if (minion.getName().equals("Security Rover")) {
            Minion minion1 = (Minion) GameHandler.getGameHandler().getCard("Terminator");
            minion1.setContestant(minion.getContestant());
            minion.getContestant().summon(minion1);
        }
    }

    public void visit(Hero hero, int damageValue) {
        if (!hero.getContestant().isImmuneHero())
            hero.setHp(Math.max(hero.getHp() - damageValue, 0));
        Playground.getPlayground().checkForGameFinish();
    }
}
