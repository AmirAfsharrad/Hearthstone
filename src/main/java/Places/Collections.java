package Places;

import UserHandle.User;
import Utilities.FileHandler;
import org.graalvm.compiler.lir.LIRInstruction;

import java.util.ArrayList;

public class Collections extends Place {
    private static Collections collections = new Collections();
    private Collections(){
        validCommands = (ArrayList) FileHandler.readFileInList("Data/Collections Commands.txt");
    }

    public static Collections getCollections(){
        return collections;
    }

    @Override
    public void defaultResponse() {

    }

    @Override
    public Place runCommand(String command, User user, Place currentPlace) {
        return MainMenu.getMainMenu();
    }
}
