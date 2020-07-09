package GameHandler;

import Cards.Card;
import Cards.CardCreator;
import Cards.Deck;
import GameHandler.CLI.CLI;
import Heroes.Mage;
import Initialization.DefaultUserCardsDataCreator;
import Utilities.FileHandler;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

public class GameHandler {
    private static GameHandler gameHandler = new GameHandler();
    private HashMap<String, Card> allCards;
    private ArrayList<String> allCardNames;
    private String path = "Data/All Cards.json";

    GameHandler() {
        refreshCardsDatabase();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ArrayList<String> getAllCardNames() {
        return allCardNames;
    }

    public void refreshCardsDatabase() {
        allCards = new HashMap();
        allCardNames = new ArrayList<>();
        JSONArray cardsJsonList = FileHandler.getJsonArrayFromFile(path);
        org.json.simple.JSONObject cardObject;

        for (Object object :
                cardsJsonList) {
            cardObject = (org.json.simple.JSONObject) object;
            allCardNames.add((String) cardObject.get("name"));
        }

        for (String cardName :
                allCardNames) {
            allCards.put(cardName, CardCreator.createCard(cardName));
        }
    }

    public static GameHandler getGameHandler() {
        return gameHandler;
    }

    public Card getCard(String cardName) {
        return allCards.get(cardName).clone();
    }

    public Deck getDefaultOpponentDeck() {
        Deck deck = new Deck("system");
        ArrayList<String> stringListOfCards = (ArrayList) FileHandler.readFileInList("Data/Default Opponent Deck Cards.txt");

        for (String cardName :
                stringListOfCards) {
            deck.getCards().add(getCard(cardName));
        }
        return deck;
    }

    public static void runGame() {
        CLI cli = CLI.getGetCLI();
        cli.run();
    }
}
