package Places;

import GameHandler.CLI.GetResponseFromUser;
import GameHandler.RespondToUser;
import GameHandler.GameState;
import Logger.Logger;
import UserHandle.User;
import UserHandle.UserDataHandler;

import java.io.IOException;

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

    public void createNewAccount(String username, String password) throws IOException {
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

    public void login(String username, String password) throws IOException {
        User newUser = UserDataHandler.load(username, password);
        System.out.println();
        if (newUser != null){
            newUser.copy(newUser);
            RespondToUser.respond("Signed in as " + username + " successfully.", newUser);
            newUser.setLoggedIn(true);
            Logger.log(newUser, "login", " as " + newUser.getName(), true);
            GameState.getGameState().setUser(newUser);
            GameState.getGameState().setCurrentPlace(MainMenu.getMainMenu());
        }
    }

}