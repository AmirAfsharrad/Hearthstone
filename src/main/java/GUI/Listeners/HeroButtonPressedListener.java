package GUI.Listeners;

import GUI.Events.HeroButtonPressedEvent;

import java.util.EventListener;

public interface HeroButtonPressedListener extends EventListener {
    void heroButtonPressedEventOccurred(HeroButtonPressedEvent heroButtonPressedEvent);
}
