package Places;

import UserHandle.User;
import Utilities.FileHandler;
import org.json.simple.JSONObject;

import java.util.HashMap;

public abstract class Place{
    private HashMap<String, String> instructions = new HashMap<>();
    private String instructionsPath;

    public void loadInstructions(){
        JSONObject instructionsJsonObject = FileHandler.getJsonObjectFromFile(instructionsPath);
        for (Object commandObject :
                instructionsJsonObject.keySet()) {
            String command = (String) commandObject;
            instructions.put(command, (String) instructionsJsonObject.get(command));
        }
    }

    public String getInstructionsPath() {
        return instructionsPath;
    }

    public void setInstructionsPath(String instructionsPath) {
        this.instructionsPath = instructionsPath;
    }

    public HashMap<String, String> getInstructions() {
        return instructions;
    }

    public abstract Place runCommand(String command, User user, Place currentPlace);
    public abstract void defaultResponse();
}