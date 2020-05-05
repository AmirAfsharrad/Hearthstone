package GUI.Events;

import java.util.EventObject;

public class GetTextEvent extends EventObject {
    String text;

    public GetTextEvent(Object source, String text) {
        super(source);
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
