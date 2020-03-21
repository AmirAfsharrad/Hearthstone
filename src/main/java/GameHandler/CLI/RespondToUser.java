package GameHandler.CLI;

import Logger.Logger;
import UserHandle.User;

public class RespondToUser {
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
