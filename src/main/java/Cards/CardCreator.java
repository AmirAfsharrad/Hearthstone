package Cards;

import com.google.gson.JsonArray;
import com.google.gson.internal.bind.util.ISO8601Utils;
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

    private static Minion createMinion(JSONObject cardObj) {
        Long mana = (Long) cardObj.get("mana");
        String name = (String) cardObj.get("name");
        String heroClass = (String) cardObj.get("heroClass");
        Long hp = (Long) cardObj.get("hp");
        String description = (String) cardObj.get("description");
        String rarity = (String) cardObj.get("rarity");
        Long attackPower = (Long) cardObj.get("attackPower");
        JSONArray battlecry = (JSONArray) cardObj.get("battlecry");
        boolean booleanBattlecry = jsonArrayToInt(battlecry)[0] == 1;
        JSONArray abilities = (JSONArray) cardObj.get("abilities");
        JSONArray turnStartDamage = (JSONArray) cardObj.get("turnStartDamage");
        JSONArray turnEndDamage = (JSONArray) cardObj.get("turnEndDamage");
        JSONArray drawEffect = (JSONArray) cardObj.get("drawEffect");

        Minion minion = new Minion(mana.intValue(), name, rarity, heroClass, description, attackPower.intValue(), hp.intValue(),
                booleanBattlecry, jsonArrayToInt(turnStartDamage), jsonArrayToInt(turnEndDamage), jsonArrayToInt(drawEffect));
        minion.setAbilities(jsonArrayToInt(abilities));

        return minion;
    }

    private static Spell createSpell(JSONObject cardObj) {
        Long mana = (Long) cardObj.get("mana");
        String name = (String) cardObj.get("name");
        String heroClass = (String) cardObj.get("heroClass");
        String description = (String) cardObj.get("description");
        String rarity = (String) cardObj.get("rarity");
        JSONArray target = (JSONArray) cardObj.get("target");
        JSONArray damage = (JSONArray) cardObj.get("damage");
        JSONArray giveHealth = (JSONArray) cardObj.get("giveHealth");
        JSONArray draw = (JSONArray) cardObj.get("draw");
        JSONArray destroy = (JSONArray) cardObj.get("destroy");
        JSONArray setAbilities = (JSONArray) cardObj.get("setAbilities");
        JSONArray modifyAttack = (JSONArray) cardObj.get("modifyAttack");

        Spell spell = new Spell(mana.intValue(), name, rarity, heroClass, description, jsonArrayToInt(target),
                jsonArrayToInt(damage), jsonArrayToInt(giveHealth), jsonArrayToInt(draw), jsonArrayToInt(destroy),
                jsonArrayToInt(setAbilities), jsonArrayToInt(modifyAttack));

        return spell;
    }

    private static Quest createQuest(JSONObject cardObj) {
        Long mana = (Long) cardObj.get("mana");
        String name = (String) cardObj.get("name");
        String heroClass = (String) cardObj.get("heroClass");
        String description = (String) cardObj.get("description");
        String rarity = (String) cardObj.get("rarity");

        Quest quest = new Quest(mana.intValue(), name, rarity, heroClass, description);

        return quest;
    }

    private static Weapon createWeapon(JSONObject cardObj) {
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
                        case "Quest": {
                        return createQuest(cardObj);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static int[] longArrayToInt(Long[] arr) {
        int[] result = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = arr[i].intValue();
        }
        return result;
    }

    private static int[] jsonArrayToInt(JSONArray jsonArray) {
        int[] result = new int[jsonArray.size()];

        for (int i = 0; i < jsonArray.size(); i++) {
            result[i] = Integer.parseInt(jsonArray.get(i).toString());
        }

        return result;
    }
}



