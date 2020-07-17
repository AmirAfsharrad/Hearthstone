package GUI.Listeners;

import GUI.Events.WeaponPressedEvent;

import java.io.IOException;
import java.util.EventListener;

public interface WeaponPressedListener extends EventListener {
    void weaponPressedEventOccurred(WeaponPressedEvent weaponPressedEvent) throws IOException;
}
