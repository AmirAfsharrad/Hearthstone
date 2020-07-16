package GameLogic.Interfaces;

import GameLogic.Visitors.DealDamageVisitor;

public interface Damageable {
    void acceptDamage(DealDamageVisitor dealDamageVisitor, int damageValue);
}
