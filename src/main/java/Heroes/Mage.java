package Heroes;

import Cards.Card;

import java.util.ArrayList;

public class Mage extends Hero {
    public Mage(){
        super(defaultHp, "Mage", "Fireblast");
    }

    @Override
    public String toString() {
        return "Mage";
    }
}
