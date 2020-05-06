package GUI.Listeners;

import GUI.Events.PlayCardEvent;

import java.util.EventListener;

public interface PlayCardListener extends EventListener {
    void PlayCardOccurred(PlayCardEvent playCardEvent);
}
