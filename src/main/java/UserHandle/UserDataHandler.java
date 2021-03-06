package UserHandle;

import Cards.Deck;
import GameHandler.GameHandler;
import GameHandler.RespondToUser;
import Heroes.*;
import Logger.Logger;
import Utilities.FileHandler;
import Utilities.SHA256Hash;
import com.google.gson.JsonObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class UserDataHandler {
    private static String path = "Data/users.json";

    public void setPath(String path) {
        path = path;
    }

    public static String getPath() {
        return path;
    }

    private static boolean userMatchCheck(String userName, JSONObject user) {
        String tempUserName = (String) user.get("user name");
        String deleteTime = (String) user.get("delete time");

        if (userName.equals(tempUserName)) {
            return deleteTime.equals("none");
        }
        return false;
    }


    private static int getUserIndexIfExists(String userName) {
        try (FileReader reader = new FileReader(UserDataHandler.getPath())) {

            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(reader);
            JSONArray usersList = (JSONArray) obj;

            int counter = 0;

            for (Object user :
                    usersList) {
                if (userMatchCheck(userName, (JSONObject) user)) {
                    return counter;
                }
                counter++;
            }

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static User add(User user) {
        try (FileReader reader = new FileReader(UserDataHandler.getPath())) {
            int userId = getUserIndexIfExists(user.getName());

            if (userId != -1) {
                RespondToUser.respond("There is another user registered as " + user.getName() +
                        ". Please choose another name and try again.", true);
                return null;
            }

            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(reader);
            JSONArray usersList = (JSONArray) obj;
            JSONObject userObj = new JSONObject();
            user.setId(usersList.size());
            user.setPassword(SHA256Hash.getHashSHA256(user.getPassword()));

            userObj.put("user ID", user.getId());
            userObj.put("user name", user.getName());
            userObj.put("password", user.getPassword());
            userObj.put("sign up time", LocalDateTime.now().toString());
            userObj.put("gold", user.getGold());
            userObj.put("delete time", "none");
            userObj.put("cards", user.getCardsAsArrayOfString());
            userObj.put("heroes", user.getHeroesJsonArray());
            userObj.put("current hero index", user.getCurrentHeroIndex());
            userObj.put("decks", user.getDecksJsonArray());

            usersList.add(userObj);

            FileHandler.writeJsonArrayToFile(usersList, path);

            Logger.InitializeNewUserLogFile(user);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }


        return user;
    }

    public static User load(String userName, String password){
        int userId = getUserIndexIfExists(userName);

        if (userId == -1) {
            RespondToUser.respond("There is no user registered as " + userName +"!", true);
            return null;
        }

        User user = new User(userName, password);
        user.setId(userId);

        try (FileReader reader = new FileReader(UserDataHandler.getPath())) {

            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(reader);
            JSONArray usersList = (JSONArray) obj;
            JSONObject userObj;

            userObj = (JSONObject) usersList.get(userId);
            String truePassword = (String) userObj.get("password");

            if (truePassword.equals(SHA256Hash.getHashSHA256(password))) {
                user.setPassword(truePassword);
                Long gold = (Long) userObj.get("gold");
                user.setGold(gold.intValue());

                JSONArray heroesListJsonArray = (JSONArray) userObj.get("heroes");
                for (Object heroJsonObject :
                        heroesListJsonArray) {
                    Hero hero = HeroCreator.createHeroFromJson((JSONObject) heroJsonObject);
                    user.addHero(hero);
                }

                JSONArray decksListJsonArray = (JSONArray) userObj.get("decks");
                for (Object deck : decksListJsonArray) {
                    String heroName = (String) ((JSONObject) deck).get("heroName");
                    Hero hero = HeroCreator.createHero(heroName);
                    String name = (String) ((JSONObject) deck).get("name");
                    JSONArray cards = (JSONArray) ((JSONObject) deck).get("cardsNames");

                    Deck newDeck = new Deck(name);
                    newDeck.setHero(hero);
                    for (Object card : cards) {
                        newDeck.getCards().add(GameHandler.getGameHandler().getCard((String) card));
                    }
                    user.addDeck(newDeck);
                }

                ArrayList<String> cardsList = (ArrayList<String>) userObj.get("cards");
                for (String cardName :
                        cardsList) {
                    user.addCard(cardName);
                }

                Long currentHeroIndex = (Long) userObj.get("current hero index");
                user.setCurrentHeroIndex(currentHeroIndex.intValue());
                //user.setCurrentHero(heroCreator.createHero(currentHero));

                return user;
            } else {
                RespondToUser.respond("Incorrect password!", true);
            }

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void save(User user){
        JSONArray usersList;

        try (FileReader reader = new FileReader(UserDataHandler.getPath())) {

            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(reader);
            usersList = (JSONArray) obj;
            JSONObject userObj;

            userObj = (JSONObject) usersList.get(user.getId());

            userObj.put("user name", user.getName());
            userObj.put("password", user.getPassword());
            userObj.put("gold", user.getGold());
            userObj.put("cards", user.getCardsAsArrayOfString());
            userObj.put("heroes", user.getHeroesJsonArray());
            userObj.put("current hero", user.getCurrentHeroIndex());
            userObj.put("decks", user.getDecksJsonArray());

            usersList.set(user.getId(), userObj);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
            RespondToUser.respond("Problem occurred during save process. Your recent modification data might have been lost.");
            return;
        }

        FileHandler.writeJsonArrayToFile(usersList, path);

        RespondToUser.respond("Changes to " + user.getName() + " saved successfully.");
    }

    public static void remove(User user){
        remove(user.getName(), user.getPassword());
    }

    public static void remove(String userName, String password) {
        int userId = getUserIndexIfExists(userName);

        if (userId == -1) {
            RespondToUser.respond("There is no user registered as " + userName);
            return;
        }

        try (FileReader reader = new FileReader(UserDataHandler.getPath())) {

            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(reader);
            JSONArray usersList = (JSONArray) obj;
            JSONObject user;

            user = (JSONObject) usersList.get(userId);
            String truePassword = (String) user.get("password");

            if (truePassword.equals(SHA256Hash.getHashSHA256(password)) || truePassword.equals(password)) {
                user.put("delete time", LocalDateTime.now().toString());
                RespondToUser.respond(userName + " removed successfully");

                FileHandler.writeJsonArrayToFile(usersList, path);

            } else {
                RespondToUser.respond("Incorrect password!");
            }

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }
}


