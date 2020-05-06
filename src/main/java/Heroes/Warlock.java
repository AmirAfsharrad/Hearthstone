package Heroes;

import Cards.Card;

import java.util.ArrayList;

public class Warlock extends Hero {
    public Warlock() {
        super(defaultHp, "Warlock", "Sacrificer");
    }

    @Override
    public String toString() {
        return "Warlock";
    }
}
