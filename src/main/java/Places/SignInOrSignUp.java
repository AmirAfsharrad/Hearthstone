package Places;

import GameHandler.CLI.GetResponseFromUser;
import GameHandler.CLI.RespondToUser;
import UserHandle.User;
import UserHandle.UserDataHandler;

public class SignInOrSignUp extends Place {
    private static SignInOrSignUp signInOrSignUp = new SignInOrSignUp();
    private SignInOrSignUp(){
        setInstructionsPath("Data/SignInOrSignUp Commands.json");
        loadInstructions();
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
            case "hearthstone --help":{
                for (String commandName :
                        getInstructions().keySet()) {
                    RespondToUser.respond(commandName + ": " + getInstructions().get(commandName));
                }
                return currentPlace;
            }
            case "exit -a":{
                RespondToUser.respond("Goodbye!");
                System.exit(0);
            }
            case "n":{
                String username = GetResponseFromUser.getResponse("Username:");
                String password = GetResponseFromUser.getResponse("Password:");
                User newUser = new User(username, password);
                newUser = UserDataHandler.add(newUser);
                if (newUser != null){
                    newUser.initializeCardsAndHeroesAsDefault();
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
        }
        RespondToUser.respond("Invalid command!");
        return currentPlace;
    }
}