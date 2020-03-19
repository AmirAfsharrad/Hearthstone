package CLI;

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
}
