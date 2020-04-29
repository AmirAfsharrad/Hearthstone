package GUI.Listeners;

import GUI.Events.LoginEvent;
import GUI.Events.TestEvent;

import java.util.EventListener;

public interface LoginListener extends EventListener {
    public void loginEventOccurred(LoginEvent loginEvent);
}
