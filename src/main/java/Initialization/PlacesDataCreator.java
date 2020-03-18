package Initialization;

import Utilities.FileHandler;

import java.util.ArrayList;
import java.util.List;

public class PlacesDataCreator {
    public static void initAllPlaces(){
        initSignInOrSignUp();
        initMainMenu();
        initCollections();
        initStore();
    }

    public static void initSignInOrSignUp(String... path){
        ArrayList<String> commands = new ArrayList();

        commands.add("y");
        commands.add("n");
        commands.add("exit");
        commands.add("exit -a");
        commands.add("hearthstone --help");

        if (path.length > 0){
            FileHandler.writeListIntoFile(commands, path[0]);
        }
        String defaultPath = "Data/SignInOrSignUp Commands.txt";
        FileHandler.writeListIntoFile(commands, defaultPath);



    }

    public static void initMainMenu(String... path){
        ArrayList<String> commands = new ArrayList();

        commands.add("delete-player");
        commands.add("exit");
        commands.add("exit -a");
        commands.add("collections");
        commands.add("store");
        commands.add("hearthstone --help");

        if (path.length > 0){
            FileHandler.writeListIntoFile(commands, path[0]);
        }
        String defaultPath = "Data/MainMenu Commands.txt";
        FileHandler.writeListIntoFile(commands, defaultPath);
    }

    public static void initCollections(String... path){
        ArrayList<String> commands = new ArrayList();

        commands.add("main menu");
        commands.add("ls -a - heroes");
        commands.add("ls -m -heroes");
        commands.add("select *");
        commands.add("ls -a -cards");
        commands.add("ls -m -cards");
        commands.add("ls -n -cards");
        commands.add("add *");
        commands.add("remove *");
        commands.add("hearthstone --help");


        if (path.length > 0){
            FileHandler.writeListIntoFile(commands, path[0]);
        }
        String defaultPath = "Data/Collections Commands.txt";
        FileHandler.writeListIntoFile(commands, defaultPath);
    }

    public static void initStore(String... path){
        ArrayList<String> commands = new ArrayList();

        commands.add("main menu");
        commands.add("buy *");
        commands.add("wallet");
        commands.add("sell *");
        commands.add("ls -s");
        commands.add("ls -b");
        commands.add("hearthstone --help");

        if (path.length > 0){
            FileHandler.writeListIntoFile(commands, path[0]);
        }
        String defaultPath = "Data/Store Commands.txt";
        FileHandler.writeListIntoFile(commands, defaultPath);
    }


}
