package GUI.Listeners;

import GUI.Events.ChoiceOfCardSelectionEvent;

import java.util.EventListener;

public interface ChoiceOfWeaponListener extends EventListener {
    void choiceOfWeaponEventOccurred(ChoiceOfCardSelectionEvent choiceOfCardSelectionEvent);
}
