package UserHandle;

import CLI.RespondToUser;
import Heros.HeroCreator;
import Utilities.FileHandler;
import Utilities.SHA256Hash;
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
                RespondToUser.respond("There is another user registered as " + user.getName() + ". Please choose another name and try again.");
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
            userObj.put("cards", user.getCardsString());
            userObj.put("heroes", user.getHeroesString());
            userObj.put("current hero", user.getCurrentHero());

            usersList.add(userObj);

            FileHandler.writeJsonArrayToFile(path, usersList);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static User load(String userName, String password){
        int userId = getUserIndexIfExists(userName);

        if (userId == -1) {
            RespondToUser.respond("There is no user registered as " + userName +"!");
            return null;
        }

        User user = new User(userName, password);

        try (FileReader reader = new FileReader(UserDataHandler.getPath())) {

            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(reader);
            JSONArray usersList = (JSONArray) obj;
            JSONObject userObj;

            userObj = (JSONObject) usersList.get(userId);
            String truePassword = (String) userObj.get("password");

            if (truePassword.equals(SHA256Hash.getHashSHA256(password))) {
                Long gold =  (Long) userObj.get("gold");
                user.setGold(gold.intValue());

                ArrayList<String> heroesList = (ArrayList<String>) userObj.get("heroes");
                HeroCreator heroCreator = HeroCreator.getHeroCreator();
                for (String heroName :
                        heroesList) {
                    user.getHeroes().add(heroCreator.createHero(heroName));
                }

                String currentHero = (String) userObj.get("current hero");
                user.setCurrentHero(heroCreator.createHero(currentHero));

                return user;
            } else {
                RespondToUser.respond("Incorrect password!");
            }

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void save(User user){
        try (FileReader reader = new FileReader(UserDataHandler.getPath())) {

            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(reader);
            JSONArray usersList = (JSONArray) obj;
            JSONObject userObj;

            userObj = (JSONObject) usersList.get(user.getId());

            userObj.put("user name", user.getName());
            userObj.put("password", SHA256Hash.getHashSHA256(user.getPassword()));
            userObj.put("gold", user.getGold());
            userObj.put("cards", user.getCardsString());
            userObj.put("heroes", user.getHeroesString());
            userObj.put("current hero", user.getCurrentHero());

            FileHandler.writeJsonArrayToFile(path, usersList);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        System.out.println("Changes to " + user.getName() + " saved successfully.");
    }

    public static void remove(User user){
        remove(user.getName(), user.getPassword());
    }

    public static void remove(String userName, String password) {
        int userId = getUserIndexIfExists(userName);

        if (userId == -1) {
            System.out.println("There is no user registered as " + userName);
            return;
        }

        try (FileReader reader = new FileReader(UserDataHandler.getPath())) {

            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(reader);
            JSONArray usersList = (JSONArray) obj;
            JSONObject user;

            user = (JSONObject) usersList.get(userId);
            String truePassword = (String) user.get("password");

            if (truePassword.equals(SHA256Hash.getHashSHA256(password))) {
                user.put("delete time", LocalDateTime.now().toString());
                System.out.println(userName + " removed successfully");

                FileHandler.writeJsonArrayToFile(path, usersList);

            } else {
                System.out.println("Incorrect password!");
            }

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }
}


