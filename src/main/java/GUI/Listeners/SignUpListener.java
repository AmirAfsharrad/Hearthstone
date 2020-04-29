package GUI.Listeners;

import GUI.Events.SignUpEvent;

import java.util.EventListener;

public interface SignUpListener extends EventListener {
    public void signUpEventOccurred(SignUpEvent signUpEvent);
}
