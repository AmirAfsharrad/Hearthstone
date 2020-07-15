package Initialization;

import Utilities.FileHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class CardsDataCreator {
    private static String path = "Data/All Cards.json";

    public static String getPath() {
        return path;
    }

    public static void setPath(String path) {
        CardsDataCreator.path = path;
    }

//    public static void createCardsData() {
//        try (FileReader reader = new FileReader(CardsDataCreator.getPath())) {
//
//            JSONParser jsonParser = new JSONParser();
//            Object obj = jsonParser.parse(reader);
//            JSONArray cardsList = (JSONArray) obj;
//            JSONObject cardObj;
//
//            cardObj = new JSONObject();
//            cardObj.put("mana", 1);
//            cardObj.put("name", "Goldshire Footman");
//            cardObj.put("rarity", "Common");
//            cardObj.put("heroClass", "Neutral");
//            cardObj.put("description", "Taunt");
//            cardObj.put("type", "Minion");
//            cardObj.put("hp", 2);
//            cardObj.put("attackPower", 1);
//            cardsList.add(cardObj);
//
//            cardObj = new JSONObject();
//            cardObj.put("mana", 1);
//            cardObj.put("name", "Stonetusk Boar");
//            cardObj.put("rarity", "Common");
//            cardObj.put("heroClass", "Neutral");
//            cardObj.put("description", "Charge");
//            cardObj.put("type", "Minion");
//            cardObj.put("hp", 1);
//            cardObj.put("attackPower", 1);
//            cardsList.add(cardObj);
//
//            cardObj = new JSONObject();
//            cardObj.put("mana", 1);
//            cardObj.put("name", "Shieldbearer");
//            cardObj.put("rarity", "Common");
//            cardObj.put("heroClass", "Neutral");
//            cardObj.put("description", "Taunt");
//            cardObj.put("type", "Minion");
//            cardObj.put("hp", 4);
//            cardObj.put("attackPower", 0);
//            cardsList.add(cardObj);
//
//            cardObj = new JSONObject();
//            cardObj.put("mana", 1);
//            cardObj.put("name", "Worgen Infiltrator");
//            cardObj.put("rarity", "Common");
//            cardObj.put("heroClass", "Neutral");
//            cardObj.put("description", "Stealth");
//            cardObj.put("type", "Minion");
//            cardObj.put("hp", 1);
//            cardObj.put("attackPower", 2);
//            cardsList.add(cardObj);
//
//            cardObj = new JSONObject();
//            cardObj.put("mana", 1);
//            cardObj.put("name", "Depth Charge");
//            cardObj.put("rarity", "Common");
//            cardObj.put("heroClass", "Neutral");
//            cardObj.put("description", "At the start of your turn, deal 5 damage to all minions");
//            cardObj.put("type", "Minion");
//            cardObj.put("hp", 5);
//            cardObj.put("attackPower", 0);
//            cardsList.add(cardObj);
//
//            cardObj = new JSONObject();
//            cardObj.put("mana", 2);
//            cardObj.put("name", "Bloodfen Raptor");
//            cardObj.put("rarity", "Common");
//            cardObj.put("heroClass", "Neutral");
//            cardObj.put("description", "");
//            cardObj.put("type", "Minion");
//            cardObj.put("hp", 2);
//            cardObj.put("attackPower", 3);
//            cardsList.add(cardObj);
//
//            cardObj = new JSONObject();
//            cardObj.put("mana", 2);
//            cardObj.put("name", "Novice Engineer");
//            cardObj.put("rarity", "Common");
//            cardObj.put("heroClass", "Neutral");
//            cardObj.put("description", "Battlecry: Draw a card");
//            cardObj.put("type", "Minion");
//            cardObj.put("hp", 1);
//            cardObj.put("attackPower", 1);
//            cardsList.add(cardObj);
//
//            cardObj = new JSONObject();
//            cardObj.put("mana", 3);
//            cardObj.put("name", "Raid Leader");
//            cardObj.put("rarity", "Common");
//            cardObj.put("heroClass", "Neutral");
//            cardObj.put("description", "Your other minions have +1 attack");
//            cardObj.put("type", "Minion");
//            cardObj.put("hp", 2);
//            cardObj.put("attackPower", 2);
//            cardsList.add(cardObj);
//
//            cardObj = new JSONObject();
//            cardObj.put("mana", 5);
//            cardObj.put("name", "Sunreaver Warmage");
//            cardObj.put("rarity", "Common");
//            cardObj.put("heroClass", "Neutral");
//            cardObj.put("description", "Battlecry: If you are holding a spell that costs 5 or more, deal 4 damage");
//            cardObj.put("type", "Minion");
//            cardObj.put("hp", 4);
//            cardObj.put("attackPower", 4);
//            cardsList.add(cardObj);
//
//            cardObj = new JSONObject();
//            cardObj.put("mana", 6);
//            cardObj.put("name", "Lord of the Arena");
//            cardObj.put("rarity", "Common");
//            cardObj.put("heroClass", "Neutral");
//            cardObj.put("description", "Taunt");
//            cardObj.put("type", "Minion");
//            cardObj.put("hp", 5);
//            cardObj.put("attackPower", 6);
//            cardsList.add(cardObj);
//
//            cardObj = new JSONObject();
//            cardObj.put("mana", 7);
//            cardObj.put("name", "Stormwind Champion");
//            cardObj.put("rarity", "Common");
//            cardObj.put("heroClass", "Neutral");
//            cardObj.put("description", "Your other minions have +1/+1");
//            cardObj.put("type", "Minion");
//            cardObj.put("hp", 6);
//            cardObj.put("attackPower", 6);
//            cardsList.add(cardObj);
//
//            cardObj = new JSONObject();
//            cardObj.put("mana", 8);
//            cardObj.put("name", "Mosh'gg Enforcer");
//            cardObj.put("rarity", "Common");
//            cardObj.put("heroClass", "Neutral");
//            cardObj.put("description", "Taunt\n Divine shield");
//            cardObj.put("type", "Minion");
//            cardObj.put("hp", 14);
//            cardObj.put("attackPower", 2);
//            cardsList.add(cardObj);
//
//            cardObj = new JSONObject();
//            cardObj.put("mana", 10);
//            cardObj.put("name", "Living Monument");
//            cardObj.put("rarity", "Common");
//            cardObj.put("heroClass", "Neutral");
//            cardObj.put("description", "Taunt");
//            cardObj.put("type", "Minion");
//            cardObj.put("hp", 10);
//            cardObj.put("attackPower", 10);
//            cardsList.add(cardObj);
//
//            cardObj = new JSONObject();
//            cardObj.put("mana", 3);
//            cardObj.put("name", "Regenerate");
//            cardObj.put("rarity", "Common");
//            cardObj.put("heroClass", "Neutral");
//            cardObj.put("description", "Restore 3 health");
//            cardObj.put("type", "Spell");
//            cardsList.add(cardObj);
//
//            cardObj = new JSONObject();
//            cardObj.put("mana", 3);
//            cardObj.put("name", "Divine Spirit");
//            cardObj.put("rarity", "Common");
//            cardObj.put("heroClass", "Neutral");
//            cardObj.put("description", "Double a minion's health");
//            cardObj.put("type", "Spell");
//            cardsList.add(cardObj);
//
//            cardObj = new JSONObject();
//            cardObj.put("mana", 6);
//            cardObj.put("name", "Holy Fire");
//            cardObj.put("rarity", "Rare");
//            cardObj.put("heroClass", "Neutral");
//            cardObj.put("description", "Deal 5 damage. Restore 5 health to your hero");
//            cardObj.put("type", "Spell");
//            cardsList.add(cardObj);
//
//            cardObj = new JSONObject();
//            cardObj.put("mana", 2);
//            cardObj.put("name", "Sap");
//            cardObj.put("rarity", "Common");
//            cardObj.put("heroClass", "Neutral");
//            cardObj.put("description", "Return an enemy's minion to their hand");
//            cardObj.put("type", "Spell");
//            cardsList.add(cardObj);
//
//            cardObj = new JSONObject();
//            cardObj.put("mana", 7);
//            cardObj.put("name", "Sprint");
//            cardObj.put("rarity", "Rare");
//            cardObj.put("heroClass", "Neutral");
//            cardObj.put("description", "Draw 4 cards");
//            cardObj.put("type", "Spell");
//            cardsList.add(cardObj);
//
//            cardObj = new JSONObject();
//            cardObj.put("mana", 2);
//            cardObj.put("name", "Arcane Missiles");
//            cardObj.put("rarity", "Common");
//            cardObj.put("heroClass", "Neutral");
//            cardObj.put("description", "Deal 3 damage to random enemies");
//            cardObj.put("type", "Spell");
//            cardsList.add(cardObj);
//
//            cardObj = new JSONObject();
//            cardObj.put("mana", 5);
//            cardObj.put("name", "Fireball");
//            cardObj.put("rarity", "Common");
//            cardObj.put("heroClass", "Neutral");
//            cardObj.put("description", "Deal 6 damage");
//            cardObj.put("type", "Spell");
//            cardsList.add(cardObj);
//
//            cardObj = new JSONObject();
//            cardObj.put("mana", 8);
//            cardObj.put("name", "Flamestike");
//            cardObj.put("rarity", "Epic");
//            cardObj.put("heroClass", "Neutral");
//            cardObj.put("description", "Deal 4 damage to all enemy minions");
//            cardObj.put("type", "Spell");
//            cardsList.add(cardObj);
//
//            cardObj = new JSONObject();
//            cardObj.put("mana", 4);
//            cardObj.put("name", "Polymorph");
//            cardObj.put("rarity", "Common");
//            cardObj.put("heroClass", "Mage");
//            cardObj.put("description", "Transform a minion into a 1/1 sheep");
//            cardObj.put("type", "Spell");
//            cardsList.add(cardObj);
//
//            cardObj = new JSONObject();
//            cardObj.put("mana", 3);
//            cardObj.put("name", "Twilight Flamecaller");
//            cardObj.put("rarity", "Common");
//            cardObj.put("heroClass", "Mage");
//            cardObj.put("description", "Battlecry: Deal 1 damage to all enemy minions");
//            cardObj.put("type", "Minion");
//            cardObj.put("hp", 2);
//            cardObj.put("attackPower", 2);
//            cardsList.add(cardObj);
//
//            cardObj = new JSONObject();
//            cardObj.put("mana", 1);
//            cardObj.put("name", "Friendly Smith");
//            cardObj.put("rarity", "Common");
//            cardObj.put("heroClass", "Rogue");
//            cardObj.put("description", "Discover a weapon from any class and add it to your deck with +2/+2");
//            cardObj.put("type", "Spell");
//            cardsList.add(cardObj);
//
//            cardObj = new JSONObject();
//            cardObj.put("mana", 4);
//            cardObj.put("name", "Plagubringer");
//            cardObj.put("rarity", "Rare");
//            cardObj.put("heroClass", "Rogue");
//            cardObj.put("description", "Battlecry: Give a friendly minion poisonous");
//            cardObj.put("type", "Minion");
//            cardObj.put("hp", 3);
//            cardObj.put("attackPower", 3);
//            cardsList.add(cardObj);
//
//            cardObj = new JSONObject();
//            cardObj.put("mana", 3);
//            cardObj.put("name", "Shadowblade");
//            cardObj.put("rarity", "Rare");
//            cardObj.put("heroClass", "Rogue");
//            cardObj.put("description", "Battlecry: Your hero is immune this turn");
//            cardObj.put("type", "Weapon");
//            cardObj.put("attackPower", 3);
//            cardObj.put("durability", 2);
//            cardsList.add(cardObj);
//
//            cardObj = new JSONObject();
//            cardObj.put("mana", 3);
//            cardObj.put("name", "Dreadscale");
//            cardObj.put("rarity", "Common");
//            cardObj.put("heroClass", "Warlock");
//            cardObj.put("description", "At the end of your turn, deal 1 damage to all other minions");
//            cardObj.put("type", "Minion");
//            cardObj.put("hp", 2);
//            cardObj.put("attackPower", 4);
//            cardsList.add(cardObj);
//
//            cardObj = new JSONObject();
//            cardObj.put("mana", 8);
//            cardObj.put("name", "Twisting Nether");
//            cardObj.put("rarity", "Rare");
//            cardObj.put("heroClass", "Warlock");
//            cardObj.put("description", "Destroy all minions");
//            cardObj.put("type", "Spell");
//            cardsList.add(cardObj);
//
//            cardObj = new JSONObject();
//            cardObj.put("mana", 5);
//            cardObj.put("name", "Skull of the Man'ari");
//            cardObj.put("rarity", "Common");
//            cardObj.put("heroClass", "Warlock");
//            cardObj.put("description", "At the start of your turn, summon a minion from your hand");
//            cardObj.put("type", "Weapon");
//            cardObj.put("attackPower", 0);
//            cardObj.put("durability", 3);
//            cardsList.add(cardObj);
//
//            FileHandler.writeJsonArrayToFile(cardsList, CardsDataCreator.getPath());
//
//        } catch (IOException | ParseException e) {
//            e.printStackTrace();
//        }
//    }
}