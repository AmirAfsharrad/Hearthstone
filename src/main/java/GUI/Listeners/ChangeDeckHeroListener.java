package GUI.Listeners;

import GUI.Events.ChangeDeckHeroEvent;

import java.util.EventListener;

public interface ChangeDeckHeroListener extends EventListener {
    void changeDeckHeroOccurred(ChangeDeckHeroEvent changeDeckHeroEvent);
}
