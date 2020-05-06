package Heroes;

public class Priest extends Hero {
    public Priest() {
        super(defaultHp, "Priest", "Heal");
    }

    @Override
    public String toString() {
        return "Priest";
    }
}
