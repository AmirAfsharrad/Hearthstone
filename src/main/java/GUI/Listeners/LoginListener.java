package GUI.Listeners;

import GUI.Events.LoginEvent;
import GUI.Events.TestEvent;

import java.io.IOException;
import java.util.EventListener;

public interface LoginListener extends EventListener {
    void loginEventOccurred(LoginEvent loginEvent) throws IOException;
}
