package GUI.Events;

import java.util.EventObject;

public class YesNoEvent extends EventObject {
    private boolean value;

    public YesNoEvent(Object source, boolean value) {
        super(source);
        this.value = value;
    }

    public boolean isValue() {
        return value;
    }
}
