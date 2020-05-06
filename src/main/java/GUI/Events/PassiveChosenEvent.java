package GUI.Events;

import java.util.EventObject;

public class PassiveChosenEvent extends EventObject {
    private String passive;

    public PassiveChosenEvent(Object source, String passive) {
        super(source);
        this.passive = passive;
    }

    public String getPassive() {
        return passive;
    }
}
