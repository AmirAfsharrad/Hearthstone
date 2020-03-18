package Places;

import UserHandle.User;
import Utilities.FileHandler;

import java.util.ArrayList;

public class Store extends Place {
    private static Store store = new Store();
    private Store(){
        validCommands = (ArrayList) FileHandler.readFileInList("Data/MainMenu Commands.txt");
    }

    public static Store getStore(){
        return store;
    }

    @Override
    public void defaultResponse() {

    }

    @Override
    public Place runCommand(String command, User user, Place currentPlace) {
        return MainMenu.getMainMenu();
    }
}
