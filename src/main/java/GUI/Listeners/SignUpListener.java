package GUI.Listeners;

import GUI.Events.SignUpEvent;

import java.util.EventListener;

public interface SignUpListener extends EventListener {
    void signUpEventOccurred(SignUpEvent signUpEvent);
}
