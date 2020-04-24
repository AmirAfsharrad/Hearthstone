package GUI.Events;

import java.util.EventObject;

public class TestEvent extends EventObject {
    String name;

    public TestEvent(Object source) {
        super(source);
    }

    public TestEvent(Object source, String name) {
        super(source);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}