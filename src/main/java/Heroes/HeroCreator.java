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

        switch (type){
            case "Mage":{
                Hero hero = new Mage();
                return hero;
            }
            case "Rogue":{
                Hero hero = new Rogue();
                return hero;
            }
            case "Warlock":{
                Hero hero = new Warlock();
                return hero;
            }
            case "Paladin":{
                Hero hero = new Paladin();
                return hero;
            }
            case "Priest":{
                Hero hero = new Priest();
                return hero;
            }
        }
        return null;
    }
}
