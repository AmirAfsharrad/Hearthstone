package GameHandler;

import GUI.MainFrame;
import GUI.ResponseFrame;
import GameHandler.GameHandler;
import Logger.Logger;
import UserHandle.User;

public class RespondToUser {
    public static void respond(Object message){
        System.out.println(message);
        new ResponseFrame("", (String) message);
    }

    public static void respond(Object message, User user){
        System.out.println(message);
        new ResponseFrame("", (String) message);
        if (user.isLoggedIn()){
            Logger.log(user, "system response", message.toString());
        }
    }
}
