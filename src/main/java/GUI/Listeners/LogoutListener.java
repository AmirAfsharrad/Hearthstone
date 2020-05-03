package GUI.Listeners;

import GUI.Events.LoginEvent;
import GUI.Events.LogoutEvent;

import java.io.IOException;
import java.util.EventListener;

public interface LogoutListener extends EventListener {
    void logoutEventOccurred(LogoutEvent logoutEvent) throws IOException;
}
