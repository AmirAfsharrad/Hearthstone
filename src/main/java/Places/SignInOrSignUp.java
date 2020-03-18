package Places;

import CLI.GetResponseFromUser;
import CLI.RespondToUser;
import UserHandle.User;
import UserHandle.UserDataHandler;
import Utilities.FileHandler;
import java.util.ArrayList;

public class SignInOrSignUp extends Place {
    private static SignInOrSignUp signInOrSignUp = new SignInOrSignUp();
    private SignInOrSignUp(){
        validCommands = (ArrayList) FileHandler.readFileInList("Data/SignInOrSignUp Commands.txt");
    }

    public static SignInOrSignUp getSignInOrSignUp(){
        return signInOrSignUp;
    }

    @Override
    public void defaultResponse(){
        RespondToUser.respond("already have an account? (y/n)");
    }

    @Override
    public Place runCommand(String command, User user, Place currentPlace) {
        switch (command){
            case "exit -a":{
                System.out.println("Goodbye!");
                System.exit(0);
            }
            case "n":{
                String username = GetResponseFromUser.getResponse("Username:");
                String password = GetResponseFromUser.getResponse("Password:");
                User newUser = new User(username, password);
                newUser = UserDataHandler.add(newUser);
                if (newUser != null){
                    user.copy(newUser);
                    RespondToUser.respond("New user " + user.getName() + " created successfully.");
                    return MainMenu.getMainMenu();
                } else {
                    return currentPlace;
                }
            }
            case "y":{
                String username = GetResponseFromUser.getResponse("Username:");
                String password = GetResponseFromUser.getResponse("Password:");
                User newUser = UserDataHandler.load(username, password);
                if (newUser != null){
                    user.copy(newUser);
                    RespondToUser.respond("Signed in as " + username + " successfully.");
                    return MainMenu.getMainMenu();
                } else {
                    return currentPlace;
                }
            }
            case "hearthstone --help":{
                RespondToUser.respond("Sign in/Sign up page help");
            }
        }
        RespondToUser.respond("Invalid command!");
        return currentPlace;
    }
}