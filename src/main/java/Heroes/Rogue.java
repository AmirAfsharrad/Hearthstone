package Heroes;

import Cards.Card;

import java.util.ArrayList;

public class Rogue extends Hero {
    boolean upgradedHeroPower;

    public Rogue() {
        super(defaultHp, "Rogue", "Burgle");
    }

    @Override
    public String toString() {
        return "Rogue";
    }

    public boolean isUpgradedHeroPower() {
        return upgradedHeroPower;
    }

    public void setUpgradedHeroPower(boolean upgradedHeroPower) {
        this.upgradedHeroPower = upgradedHeroPower;
    }

    public void initUpgradedHeroPower() {
        if (getContestant().hasWeapon()) {
            upgradedHeroPower = true;
        } else {
            upgradedHeroPower = false;
        }
    }
}
