package Places;

import GameHandler.CLI.GetResponseFromUser;
import GameHandler.RespondToUser;
import GameHandler.GameState;
import Logger.Logger;
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
    public String toString() {
        return "SignInOrSignUp{}";
    }

    @Override
    public void defaultResponse(){
        RespondToUser.respond("already have an account? (y/n)");
    }

    public void createNewAccount(String username, String password) {
        User newUser = new User(username, password);
        newUser = UserDataHandler.add(newUser);
        if (newUser != null){
            newUser.initializeCardsAndHeroesAsDefault();
            RespondToUser.respond("New user " + newUser.getName() + " created successfully.", newUser);
            newUser.setLoggedIn(true);
            Logger.log(newUser, "sign up", newUser.getName() + " user created", true);
            GameState.getGameState().setUser(newUser);
            GameState.getGameState().setCurrentPlace(MainMenu.getMainMenu());
        }
    }

    public void login(String username, String password) {
        User newUser = UserDataHandler.load(username, password);
        if (newUser != null){
            newUser.copy(newUser);
            RespondToUser.respond("Signed in as " + username + " successfully.", newUser);
            newUser.setLoggedIn(true);
            Logger.log(newUser, "login", " as " + newUser.getName(), true);
            GameState.getGameState().setUser(newUser);
            GameState.getGameState().setCurrentPlace(MainMenu.getMainMenu());
        }
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
                RespondToUser.respond("Goodbye!", user);
                System.exit(0);
            }
            case "n":{
                String username = GetResponseFromUser.getResponse("Username:", user);
                String password = GetResponseFromUser.getResponse("Password:", user);
                User newUser = new User(username, password);
                newUser = UserDataHandler.add(newUser);
                if (newUser != null){
                    newUser.initializeCardsAndHeroesAsDefault();
                    user.copy(newUser);
                    RespondToUser.respond("New user " + user.getName() + " created successfully.", user);
                    user.setLoggedIn(true);
                    Logger.log(user, "sign up", user.getName() + " user created", true);
                    return MainMenu.getMainMenu();
                } else {
                    return currentPlace;
                }
            }
            case "y":{
                String username = GetResponseFromUser.getResponse("Username:", user);
                String password = GetResponseFromUser.getResponse("Password:", user);
                User newUser = UserDataHandler.load(username, password);
                if (newUser != null){
                    user.copy(newUser);
                    RespondToUser.respond("Signed in as " + username + " successfully.", user);
                    user.setLoggedIn(true);
                    Logger.log(user, "login", " as " + user.getName(), true);
                    return MainMenu.getMainMenu();
                } else {
                    return currentPlace;
                }
            }
        }
        RespondToUser.respond("Invalid command!", user);
        return currentPlace;
    }
}