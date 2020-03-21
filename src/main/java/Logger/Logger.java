package Logger;

import UserHandle.User;
import Utilities.FileHandler;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Logger {
    private static String defaultPath = "Logs/";

    public static String getDefaultPath() {
        return defaultPath;
    }

    public static void setDefaultPath(String defaultPath) {
        Logger.defaultPath = defaultPath;
    }

    public static void InitializeNewUserLogFile(User user){
        try(FileWriter fileWriter = new FileWriter(defaultPath + user.getName() + "-" + user.getId() + ".log", false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter out = new PrintWriter(bufferedWriter))
        {
            out.println("USER: " + user.getName());
            out.println("CREATED_AT: " + LocalDateTime.now());
            out.println("PASSWORD: " + user.getPassword());
            out.println("DELETED_AT: ----------");
            out.println("");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void log(User user, String event, String eventDescription, boolean... systemLog) {
        if (user.isLoggedIn()) {
            try (FileWriter fileWriter = new FileWriter(defaultPath + user.getName() + "-" + user.getId() + ".log", true);
                 BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                 PrintWriter out = new PrintWriter(bufferedWriter)) {
                out.println(event + ", " + LocalDateTime.now() + ", " + eventDescription);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (systemLog.length > 0 && systemLog[0]){
            systemLog(event, eventDescription);
        }
    }

    public static void systemLog(String event, String eventDescription){
        try (FileWriter fileWriter = new FileWriter(defaultPath +  "hearthstone.log", true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter out = new PrintWriter(bufferedWriter)) {
            out.println(event + ", " + LocalDateTime.now() + ", " + eventDescription);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void terminateUserLog(User user){
        String path = defaultPath + user.getName() + "-" + user.getId() + ".log";
        ArrayList<String> userLogs = (ArrayList<String>) FileHandler.readFileInList(path);
        userLogs.set(3, "DELETED_AT: " + LocalDateTime.now());
        FileHandler.writeListIntoFile(userLogs, path);
    }
}

