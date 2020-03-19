package Cards;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class CardCreator {
    public static String getPath() {
        return path;
    }

    public static void setPath(String path) {
        CardCreator.path = path;
    }

    private static String path = "Data/All Cards.json";

    public static Minion createMinion(JSONObject cardObj) {
        Long mana = (Long) cardObj.get("mana");
        String name = (String) cardObj.get("name");
        String heroClass = (String) cardObj.get("heroClass");
        Long hp = (Long) cardObj.get("hp");
        String description = (String) cardObj.get("description");
        String rarity = (String) cardObj.get("rarity");
        Long attackPower = (Long) cardObj.get("attackPower");

        Minion minion = new Minion(mana.intValue(), name, rarity, heroClass, description, attackPower.intValue(), hp.intValue());

        return minion;
    }

    public static Spell createSpell(JSONObject cardObj) {
        Long mana = (Long) cardObj.get("mana");
        String name = (String) cardObj.get("name");
        String heroClass = (String) cardObj.get("heroClass");
        String description = (String) cardObj.get("description");
        String rarity = (String) cardObj.get("rarity");

        Spell spell = new Spell(mana.intValue(), name, rarity, heroClass, description);

        return spell;
    }

    public static Weapon createWeapon(JSONObject cardObj) {
        Long mana = (Long) cardObj.get("mana");
        String name = (String) cardObj.get("name");
        String heroClass = (String) cardObj.get("heroClass");
        Long durability = (Long) cardObj.get("durability");
        String description = (String) cardObj.get("description");
        String rarity = (String) cardObj.get("rarity");
        Long attackPower = (Long) cardObj.get("attackPower");

        Weapon weapon = new Weapon(mana.intValue(), name, rarity, heroClass, description, attackPower.intValue(), durability.intValue());

        return weapon;
    }

    public static Card createCard(String cardName) {
        try (FileReader reader = new FileReader(CardCreator.getPath())) {

            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(reader);
            JSONArray cardsList = (JSONArray) obj;
            JSONObject userObj;

            for (Object object :
                    cardsList) {
                JSONObject cardObj = (JSONObject) object;
                if (cardObj.get("name").equals(cardName)) {
                    switch ((String) cardObj.get("type")) {
                        case "Minion": {
                            return createMinion(cardObj);
                        }
                        case "Spell": {
                            return createSpell(cardObj);
                        }
                        case "Weapon": {
                            return createWeapon(cardObj);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}



