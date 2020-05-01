package GUI.Listeners;

import GUI.Events.YesNoEvent;

import java.util.EventListener;

public interface YesNoListener extends EventListener {
    void YesNoEventOccurred(YesNoEvent yesNoEvent);
}
