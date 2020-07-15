package GameLogic.Interfaces;

import GameLogic.Visitors.DealDamageVisitor;

public interface Damageable {
    public void acceptDamage(DealDamageVisitor dealDamageVisitor, int damageValue);
}
