package Utilities;

import Cards.Card;
import Cards.Deck;
import GameHandler.GameHandler;
import Heroes.Mage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class DeckReader {
    private static DeckReader deckReader = new DeckReader();
    private boolean active;
    private Deck friendly = new Deck();
    private Deck enemy = new Deck();
    private Card questReward;

    private DeckReader() {
        JSONObject jsonObject = FileHandler.getJsonObjectFromFile("Data/Deck Reader.json");
        if ((Long) jsonObject.get("active") == 0) {
            active = false;
            return;
        }
        active = true;
        JSONArray friendlyJsonArray = (JSONArray) jsonObject.get("friend");
        for (Object o : friendlyJsonArray) {
            String cardName = (String) o;
            if (cardName.contains("->")) {
                String[] temp = cardName.split("->");
                friendly.getCards().add(GameHandler.getGameHandler().getCard(temp[0]));
                questReward = GameHandler.getGameHandler().getCard(temp[1]);
            } else {
                friendly.getCards().add(GameHandler.getGameHandler().getCard(cardName));
            }
        }

        JSONArray enemyJsonArray = (JSONArray) jsonObject.get("enemy");
        for (Object o : enemyJsonArray) {
            String cardName = (String) o;
            if (cardName.contains("->")) {
                String[] temp = cardName.split("->");
                enemy.getCards().add(GameHandler.getGameHandler().getCard(temp[0]));
                questReward = GameHandler.getGameHandler().getCard(temp[1]);
            } else {
                enemy.getCards().add(GameHandler.getGameHandler().getCard(cardName));
            }
        }

        friendly.setHero(new Mage());
        enemy.setHero(new Mage());
    }

    public static DeckReader getInstance() {
        return deckReader;
    }

    public boolean isActive() {
        return active;
    }

    public Deck getFriendly() {
        return friendly;
    }

    public Deck getEnemy() {
        return enemy;
    }

    public Card getQuestReward() {
        return questReward;
    }
}
