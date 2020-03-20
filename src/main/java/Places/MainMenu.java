package Places;

import GameHandler.CLI.GetResponseFromUser;
import GameHandler.CLI.RespondToUser;
import UserHandle.User;
import UserHandle.UserDataHandler;
import Utilities.SHA256Hash;

public class MainMenu extends Place {
    private static MainMenu mainMenu = new MainMenu();
    private MainMenu(){
        setInstructionsPath("Data/MainMenu Commands.json");
        loadInstructions();
    }

    public static MainMenu getMainMenu(){
        return mainMenu;
    }

    @Override
    public void defaultResponse(){
        RespondToUser.respond("You are in main menu. Where to go?");
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
                UserDataHandler.save(user);
                RespondToUser.respond("Goodbye!");
                System.exit(0);
            }
            case "exit":{
                UserDataHandler.save(user);
                RespondToUser.respond("Signed out of user " + user.getName());
                return SignInOrSignUp.getSignInOrSignUp();
            }
            case "delete-user":{
                String password = GetResponseFromUser.getResponse("Please enter your password");
                if (SHA256Hash.getHashSHA256(password).equals(user.getPassword())){
                    RespondToUser.respond("Account " + user.getName() + " successfully removed!");
                    UserDataHandler.remove(user);
                    return SignInOrSignUp.getSignInOrSignUp();
                } else {
                    RespondToUser.respond("The password is incorrect.");
                    return currentPlace;
                }
            }
            case "collections":{
                return Collections.getCollections();
            }
            case "store":{
                return Store.getStore();
            }
        }
        RespondToUser.respond("Invalid Command!");
        return currentPlace;
    }
}
