package Places;

import UserHandle.User;

import java.util.ArrayList;

public abstract class Place {
    protected ArrayList validCommands;

    public ArrayList getValidCommands() {
        return validCommands;
    }

    public abstract Place runCommand(String command, User user, Place currentPlace);
    public abstract void defaultResponse();
}
