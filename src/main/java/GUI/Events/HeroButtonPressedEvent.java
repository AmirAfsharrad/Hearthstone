package GUI.Events;

import Heroes.Hero;

import java.util.EventObject;

public class HeroButtonPressedEvent extends EventObject {
    Hero hero;

    public HeroButtonPressedEvent(Object source, Hero hero) {
        super(source);
        this.hero = hero;
    }

    public Hero getHero() {
        return hero;
    }
}
