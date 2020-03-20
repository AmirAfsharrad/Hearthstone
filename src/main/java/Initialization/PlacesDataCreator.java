package Initialization;

import Utilities.FileHandler;
import com.google.gson.JsonObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PlacesDataCreator {
    public static void initAllPlaces() {
        initSignInOrSignUp();
        initMainMenu();
        initCollections();
        initStore();
    }

    public static void initSignInOrSignUp(String... path) {
        JSONObject instructionsJsonObject = new JSONObject();

        instructionsJsonObject.put("y\t", "enter if you already have an account");
        instructionsJsonObject.put("n\t", "enter if you want to create a new account");
        instructionsJsonObject.put("exit\t", "enter if you want sign out of your current account");
        instructionsJsonObject.put("exit -a\t", "enter if you want to quit the game");

        if (path.length > 0) {
            FileHandler.writeJsonObjectToFile(instructionsJsonObject, path[0]);
        }
        String defaultPath = "Data/SignInOrSignUp Commands.json";
        FileHandler.writeJsonObjectToFile(instructionsJsonObject, defaultPath);
    }

    public static void initMainMenu(String... path) {
        JSONObject instructionsJsonObject = new JSONObject();

        instructionsJsonObject.put("delete-player\t", "enter if you already have an account");
        instructionsJsonObject.put("exit\t", "enter if you want sign out of your current account");
        instructionsJsonObject.put("exit -a\t", "enter if you want to quit the game");
        instructionsJsonObject.put("collections\t", "enter collections to check your heroes and cards");
        instructionsJsonObject.put("store\t", "enter store to sell or buy cards");
        instructionsJsonObject.put("hearthstone --help\t", "a list of valid commands and instructions");

        if (path.length > 0) {
            FileHandler.writeJsonObjectToFile(instructionsJsonObject, path[0]);
        }
        String defaultPath = "Data/MainMenu Commands.json";
        FileHandler.writeJsonObjectToFile(instructionsJsonObject, defaultPath);
    }

    public static void initCollections(String... path) {
        JSONObject instructionsJsonObject = new JSONObject();

        instructionsJsonObject.put("main menu\t", "back to main menu");
        instructionsJsonObject.put("ls -a - heroes\t", "list of all your heroes");
        instructionsJsonObject.put("ls -m -heroes\t", "your current hero");
        instructionsJsonObject.put("select [hero name]\t", "select a hero");
        instructionsJsonObject.put("ls -a -cards\t", "list of all your cards");
        instructionsJsonObject.put("ls -m -cards\t", "list of the cards in your current deck");
        instructionsJsonObject.put("ls -n -cards\t", "list of the cards you can add to your current deck");
        instructionsJsonObject.put("add [card name]\t", "add a card to your current deck");
        instructionsJsonObject.put("remove [card name]\t", "remove a card from your current deck");
        instructionsJsonObject.put("hearthstone --help\t", "a list of valid commands and instructions");

        if (path.length > 0) {
            FileHandler.writeJsonObjectToFile(instructionsJsonObject, path[0]);
        }
        String defaultPath = "Data/Collections Commands.json";
        FileHandler.writeJsonObjectToFile(instructionsJsonObject, defaultPath);
    }

    public static void initStore(String... path) {
        JSONObject instructionsJsonObject = new JSONObject();

        instructionsJsonObject.put("main menu\t", "back to main menu");
        instructionsJsonObject.put("buy [card name]\t", "buy a card");
        instructionsJsonObject.put("sell [card name]\t", "sell a card");
        instructionsJsonObject.put("wallet\t", "check the amount of gold you have");
        instructionsJsonObject.put("ls -s\t", "list of the cards you can sell");
        instructionsJsonObject.put("ls -b\t", "list of the cards you can buy (if you have enough gold)");
        instructionsJsonObject.put("hearthstone --help\t", "a list of valid commands and instructions");

        if (path.length > 0) {
            FileHandler.writeJsonObjectToFile(instructionsJsonObject, path[0]);
        }
        String defaultPath = "Data/Store Commands.json";
        FileHandler.writeJsonObjectToFile(instructionsJsonObject, defaultPath);
    }
}

