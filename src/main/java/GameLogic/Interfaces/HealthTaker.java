package GameLogic.Interfaces;

import GameLogic.Visitors.GiveHealthVisitor;

public interface HealthTaker {
    void acceptHealth(GiveHealthVisitor giveHealthVisitor, int healthValue, boolean multiplicative, boolean restore);
}
