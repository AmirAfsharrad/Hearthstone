package GUI.Events;

import java.util.EventObject;

public class CollectionsFilterEvent extends EventObject {
    private int mana;
    private String heroClass;
    private String searchString;
    private String doesOwn;

    public CollectionsFilterEvent(Object source, int mana, String heroClass, String searchString, String doesOwn) {
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
