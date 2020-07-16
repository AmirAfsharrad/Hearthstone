package GameLogic.Interfaces;

import GameLogic.Visitors.ModifyAttackVisitor;

public interface ModifiableAttack {
    void acceptAttackModification(ModifyAttackVisitor modifyAttackVisitor, int attackChangeValue);
}
