package GameHandler.CLI;

import Places.Place;
import Places.SignInOrSignUp;
import UserHandle.User;

public class CLI {
    private static CLI CLI = new CLI();

    private CLI() {
    }

    public static CLI getGetCLI() {
        return CLI;
    }

    public void run(){
        RespondToUser.respond("Welcome to HEARTHSTONE!");
        User user = new User("", "");
        Place currentPlace = SignInOrSignUp.getSignInOrSignUp();
        String command;
        while (true){
            currentPlace.defaultResponse();
            command = GetResponseFromUser.getResponse();
            currentPlace = currentPlace.runCommand(command, user, currentPlace);
        }
    }
}
