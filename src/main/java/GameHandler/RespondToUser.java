package GameHandler;

import GUI.Dialogs.ChooseFromListDialog;
import GUI.Dialogs.GetTextDialog;
import GUI.Listeners.ChooseFromListListener;
import GUI.Listeners.GeneralEventListener;
import GUI.Dialogs.ResponseDialog;
import GUI.Dialogs.YesNoResponseDialog;
import GUI.Listeners.GetTextListener;
import Logger.Logger;
import UserHandle.User;

public class RespondToUser {
    public static void yesNoRespond(String message, GeneralEventListener yesListener, GeneralEventListener noListener) {
        new YesNoResponseDialog("", message, yesListener, noListener);
    }

    public static void chooseFromListResponse(String message, String[] listOfItems, ChooseFromListListener okListener) {
        new ChooseFromListDialog("", message, listOfItems, okListener);
    }

    public static void getTextResponse(String message, GetTextListener getTextListener) {
        new GetTextDialog("", message, getTextListener);
    }

    public static void respond(Object message, boolean graphicalMessage){
        System.out.println(message);
        if (graphicalMessage) {
            new ResponseDialog("", (String) message);
        }
    }

    public static void respond(Object message, User user, boolean graphicalMessage){
        System.out.println(message);
        if (graphicalMessage) {
            new ResponseDialog("", (String) message);
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
