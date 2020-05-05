package GUI.Listeners;

import GUI.Events.DeckRenameEvent;

import java.util.EventListener;

public interface DeckRenameListener extends EventListener {
    void deckRenameOccurred(DeckRenameEvent deckRenameEvent);
}
