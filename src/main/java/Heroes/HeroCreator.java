package Heroes;

import Cards.Card;
import Cards.CardCreator;
import GameHandler.GameHandler;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class HeroCreator {

    public static Hero createHeroFromJson(JSONObject heroJsonObject){
        String type = (String) heroJsonObject.get("type");
        Long hp = (Long) heroJsonObject.get("hp");
        ArrayList<String> deckStringArray = (ArrayList<String>) heroJsonObject.get("stringDeck");

        ArrayList<Card> deck = new ArrayList<>();
        for (String card :
                deckStringArray) {
            deck.add(GameHandler.getGameHandler().getCard(card));
        }

        switch (type){
            case "Mage":{
                Hero hero = new Mage(hp.intValue(), deck);
                return hero;
            }
            case "Rogue":{
                Hero hero = new Rogue(hp.intValue(), deck);
                return hero;
            }
            case "Warlock":{
                Hero hero = new Warlock(hp.intValue(), deck);
                return hero;
            }
        }
        return null;
    }
}
