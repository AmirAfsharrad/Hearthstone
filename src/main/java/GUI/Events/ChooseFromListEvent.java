package GUI.Events;

import java.util.EventObject;

public class ChooseFromListEvent extends EventObject {
    private String choice;

    public ChooseFromListEvent(Object source, String choice) {
        super(source);
        this.choice = choice;
    }

    public String getChoice() {
        return choice;
    }
}
