package GameHandler;

import GUI.Events.YesNoEvent;
import GUI.Listeners.GeneralEventListener;
import GUI.Listeners.YesNoListener;
import GUI.MainFrame;
import GUI.ResponseFrame;
import GUI.YesNoResponseFrame;
import GameHandler.GameHandler;
import Logger.Logger;
import UserHandle.User;

public class RespondToUser {
    public static void yesNoRespond(String message, GeneralEventListener yesListener, GeneralEventListener noListener) {
        YesNoResponseFrame yesNoResponseFrame = new YesNoResponseFrame("", (String) message);
        yesNoResponseFrame.setYesListener(yesListener);
        yesNoResponseFrame.setNoListener(noListener);
    }

    public static void respond(Object message, boolean graphicalMessage){
        System.out.println(message);
        if (graphicalMessage) {
            new ResponseFrame("", (String) message);
        }
    }

    public static void respond(Object message, User user, boolean graphicalMessage){
        System.out.println(message);
        if (graphicalMessage) {
            new ResponseFrame("", (String) message);
        }
        if (user.isLoggedIn()){
            Logger.log(user, "system response", message.toString());
        }
    }

    public static void respond(Object message){
        System.out.println(message);
    }

    public static void respond(Object message, User user){
        System.out.println(message);
        if (user.isLoggedIn()){
            Logger.log(user, "system response", message.toString());
        }
    }
}
