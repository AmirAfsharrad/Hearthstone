package GUI.Listeners;

import GUI.Events.ChoiceOfCardSelectionEvent;

import java.io.IOException;
import java.util.EventListener;

public interface ChoiceOfCardSelectionListener extends EventListener {
    void choiceOfCardSelectionEventOccurred(ChoiceOfCardSelectionEvent choiceOfCardSelectionEvent) throws IOException;
}
