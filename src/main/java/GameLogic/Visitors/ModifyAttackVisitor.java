package GameLogic.Visitors;

import Cards.Minion;
import Cards.Weapon;

public class ModifyAttackVisitor {
    public static ModifyAttackVisitor modifyAttackVisitor = new ModifyAttackVisitor();

    public ModifyAttackVisitor() {
    }

    public static ModifyAttackVisitor getInstance() {
        return modifyAttackVisitor;
    }

    public void visit(Minion minion, int attackChangeValue) {
        minion.setAttackPower(minion.getAttackPower() + attackChangeValue);
    }

    public void visit(Weapon weapon, int attackChangeValue) {
        weapon.setAttackPower(weapon.getAttackPower() + attackChangeValue);
    }

}
