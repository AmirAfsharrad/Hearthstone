package GUI.Events;

import java.util.EventObject;

public class HeroPowerPressedEvent extends EventObject {
    int contestantIndex;

    public HeroPowerPressedEvent(Object source, int contestantIndex) {
        super(source);
        this.contestantIndex = contestantIndex;
    }

    public int getContestantIndex() {
        return contestantIndex;
    }
}
