package GUI.Listeners;

import GUI.Events.TurnTimeFinishEvent;

import java.util.EventListener;

public interface TurnTimeFinishListener extends EventListener {
    void turnTimeFinishEventOccurred(TurnTimeFinishEvent turnTimeFinishEvent);
}
