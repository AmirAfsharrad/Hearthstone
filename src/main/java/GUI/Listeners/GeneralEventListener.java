package GUI.Listeners;

import java.io.IOException;
import java.util.EventListener;
import java.util.EventObject;

public interface GeneralEventListener extends EventListener {
    void eventOccurred(EventObject eventObject) throws IOException;
}
