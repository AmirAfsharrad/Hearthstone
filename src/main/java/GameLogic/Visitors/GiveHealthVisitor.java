package GameLogic.Visitors;

import Cards.Minion;
import Heroes.Hero;

public class GiveHealthVisitor {
    public void visit(Minion minion, int healthValue, boolean multiplicative) {
        if (multiplicative)
            minion.setHp(minion.getHp() * healthValue);
        else
            minion.setHp(minion.getHp() + healthValue);

    }

    public void visit(Hero hero, int healthValue, boolean multiplicative) {
        if (multiplicative)
            hero.setHp(hero.getHp() * healthValue);
        else
            hero.setHp(hero.getHp() + healthValue);
    }

}
