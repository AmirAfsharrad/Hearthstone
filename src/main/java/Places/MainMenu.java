package Places;

import GameHandler.CLI.GetResponseFromUser;
import GameHandler.GameState;
import GameHandler.RespondToUser;
import Logger.Logger;
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

    public void logout() {
        Logger.log(GameState.getGameState().getUser(), "log out",
                "log out of " + GameState.getGameState().getUser().getName(), true);
        UserDataHandler.save(GameState.getGameState().getUser());
        RespondToUser.respond("Signed out of user " + GameState.getGameState().getUser().getName(),
                GameState.getGameState().getUser());
        GameState.getGameState().getUser().setLoggedIn(false);
        GameState.getGameState().setUser(null);
        GameState.getGameState().setCurrentPlace(SignInOrSignUp.getSignInOrSignUp());
    }

    public void exit() {
        Logger.log(GameState.getGameState().getUser(), "quit", "quit the game", true);
        UserDataHandler.save(GameState.getGameState().getUser());
    }

    @Override
    public String toString() {
        return "MainMenu";
    }

    @Override
    public void defaultResponse(){
        RespondToUser.respond("You are in main menu. Where to go?");
    }

    @Override
    public Place runCommand(String command, User user, Place currentPlace) {
        String lowerCaseCommand = command.toLowerCase();
        switch (lowerCaseCommand){
            case "hearthstone --help":{
                Logger.log(user, "help", "show list of instructions in " + currentPlace, true);
                for (String commandName :
                        getInstructions().keySet()) {
                    RespondToUser.respond(commandName + ": " + getInstructions().get(commandName), user);
                }
                return currentPlace;
            }
            case "exit -a":{
                Logger.log(user, "quit", "quit the game", true);
                UserDataHandler.save(user);
                RespondToUser.respond("Goodbye!", user);
                System.exit(0);
            }
            case "exit":{
                Logger.log(user, "log out", "log out of " + user.getName(), true);
                UserDataHandler.save(user);
                RespondToUser.respond("Signed out of user " + user.getName(), user);
                user.setLoggedIn(false);
                return SignInOrSignUp.getSignInOrSignUp();
            }
            case "delete-player":{
                Logger.log(user, "delete user", "removal of " + user.getName(), true);
                String password = GetResponseFromUser.getResponse("Please enter your password", user);
                if (SHA256Hash.getHashSHA256(password).equals(user.getPassword())){
                    RespondToUser.respond("Account " + user.getName() + " successfully removed!", user);
                    UserDataHandler.save(user);
                    UserDataHandler.remove(user);
                    user.setLoggedIn(false);
                    Logger.terminateUserLog(user);
                    return SignInOrSignUp.getSignInOrSignUp();
                } else {
                    RespondToUser.respond("The password is incorrect.", user);
                    return currentPlace;
                }
            }
            case "collections":{
                Logger.log(user, "navigate", "collections", true);
                return Collections.getCollections();
            }
            case "store":{
                Logger.log(user, "navigate", "store", true);
                return Store.getStore();
            }
        }
        RespondToUser.respond("Invalid Command!", user);
        return currentPlace;
    }
}
