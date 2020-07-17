package GUI.Events;

import Cards.Weapon;

import java.util.EventObject;

public class WeaponPressedEvent extends EventObject {
    Weapon weapon;

    public WeaponPressedEvent(Object source, Weapon weapon) {
        super(source);
        this.weapon = weapon;
    }

    public Weapon getWeapon() {
        return weapon;
    }
}
