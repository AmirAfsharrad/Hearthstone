package GameHandler;

import GUI.MainFrame;
import Places.Place;
import Places.SignInOrSignUp;
import UserHandle.User;

import java.io.IOException;

public class GameState {
    public static GameState gameState = new GameState();
    private User user;
    private Place currentPlace;
    private MainFrame mainFrame;

    private GameState() {
    }

    public static GameState getGameState() {
        return gameState;
    }

    public void init() throws IOException {
        mainFrame = new MainFrame();
        currentPlace = SignInOrSignUp.getSignInOrSignUp();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Place getCurrentPlace() {
        return currentPlace;
    }

    public void setCurrentPlace(Place currentPlace) throws IOException {
        this.currentPlace = currentPlace;
        mainFrame.updatePage(currentPlace);
    }

    public void refreshMainFrame() throws IOException {
        mainFrame.refresh();
    }
}
