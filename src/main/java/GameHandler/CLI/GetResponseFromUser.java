package GameHandler.CLI;

import Logger.Logger;
import Places.Place;
import UserHandle.User;

import java.util.Scanner;

public class GetResponseFromUser {
    public static String getResponse(Object message){
        RespondToUser.respond(message);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static String getResponse(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static String getResponse(User user){
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        if (user.isLoggedIn()){
            Logger.log(user, "user input", userInput);
        }
        return userInput;
    }

    public static String getResponse(Object message, User user){
        RespondToUser.respond(message, user);
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        if (user.isLoggedIn()){
            Logger.log(user, "user input", userInput);
        }
        return userInput;
    }
}
