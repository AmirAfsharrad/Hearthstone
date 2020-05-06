package Places;

import GameHandler.CLI.GetResponseFromUser;
import GameHandler.GameState;
import GameHandler.RespondToUser;
import Logger.Logger;
import UserHandle.User;
import UserHandle.UserDataHandler;
import Utilities.SHA256Hash;

import java.io.IOException;

public class MainMenu extends Place {
    private static MainMenu mainMenu = new MainMenu();
    private MainMenu(){
        setInstructionsPath("Data/MainMenu Commands.json");
        loadInstructions();
    }

    public static MainMenu getMainMenu(){
        return mainMenu;
    }

    public void logout() throws IOException {
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

}
