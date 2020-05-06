package Heroes;

import Cards.Card;

import java.util.ArrayList;

public class Rogue extends Hero {
    public Rogue() {
        super(defaultHp, "Rogue", "Burgle");
    }

    @Override
    public String toString() {
        return "Rogue";
    }
}
