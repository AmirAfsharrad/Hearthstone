package Initialization;

import Utilities.FileHandler;

import javax.swing.*;
import java.util.ArrayList;

public class DefaultUserCardsDataCreator {
    private static String path = "Data/Default User Cards.txt";

    public static String getPath() {
        return path;
    }

    public static void setPath(String path) {
        DefaultUserCardsDataCreator.path = path;
    }

    public static void createDefaultUserCardsData() {
        ArrayList<String> cards = new ArrayList();

        cards.add("Goldshire Footman");
        cards.add("Stonetusk Boar");
        cards.add("Raid Leader");
        cards.add("Lord of the Arena");
        cards.add("Mosh\u0027gg Enforcer");
        cards.add("Stormwind Champion");
        cards.add("Living Monument");
        cards.add("Sprint");
        cards.add("Sap");
        cards.add("Polymorph");

        FileHandler.writeListIntoFile(cards, path);

    }
}
