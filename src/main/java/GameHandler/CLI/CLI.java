package GameHandler.CLI;

import GameHandler.RespondToUser;
import Logger.Logger;
import Places.Place;
import Places.SignInOrSignUp;
import UserHandle.User;

import java.util.Arrays;

public class CLI {
    private static CLI CLI = new CLI();

    private CLI() {
    }

    public static CLI getGetCLI() {
        return CLI;
    }

    public void run() {
        RespondToUser.respond("Welcome to HEARTHSTONE!");
        User user = new User("", "");
        Place currentPlace = SignInOrSignUp.getSignInOrSignUp();
        String command;
        while (true) {
            try {
                command = GetResponseFromUser.getResponse(user);
//                currentPlace = currentPlace.runCommand(command, user, currentPlace);
            } catch (Exception e) {
                Logger.log(user, "error", Arrays.toString(e.getStackTrace()), true);
            }
        }
    }
}
