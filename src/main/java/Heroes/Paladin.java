package Heroes;

public class Paladin extends Hero {
    public Paladin() {
        super(defaultHp, "Paladin", "The Silver Hand");
    }

    @Override
    public String toString() {
        return "Paladin";
    }
}
