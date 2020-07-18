package Cards;

public class Quest extends Card {
    private int completion = 0;

    public Quest(int mana, String name, String rarity, String heroClass, String description, int completion) {
        super(mana, name, rarity, heroClass, description, "Quest");
    }

    public Card clone() {
        return new Quest(getMana(), getName(), getRarity(), getHeroClass(), getDescription(), completion);
    }

    public int getCompletion() {
        return completion;
    }

    public void setCompletion(int completion) {
        this.completion = completion;
    }
}
