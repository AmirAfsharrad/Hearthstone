package GUI.Events;

import java.util.EventObject;

public class FilterEvent extends EventObject {
    private int mana;
    private String heroClass;
    private String searchString;
    private String doesOwn;

    public FilterEvent(Object source, int mana, String heroClass, String searchString, String doesOwn) {
        super(source);
        this.mana = mana;
        this.heroClass = heroClass;
        this.searchString = searchString;
        this.doesOwn = doesOwn;
    }

    public int getMana() {
        return mana;
    }

    public String getHeroClass() {
        return heroClass;
    }

    public String getSearchString() {
        return searchString;
    }

    public String getDoesOwn() {
        return doesOwn;
    }
}
