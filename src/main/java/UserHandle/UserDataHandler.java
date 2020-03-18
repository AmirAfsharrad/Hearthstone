package UserHandle;

import Heros.HeroCreator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class UserDataHandler {
    private static UserDataHandler userHandler = new UserDataHandler();
    private String path = "JSONs/users.json";

    private UserDataHandler() {
    }

    public static UserDataHandler getUserHandler() {
        return userHandler;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }

    public String getHashSHA256(String inputString){
        try {
            // Static getInstance method is called with hashing SHA
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // digest() method called to calculate message digest of an input and return array of byte
            byte[] hash = md.digest(inputString.getBytes(StandardCharsets.UTF_8));

            // Convert byte array into signum representation
            BigInteger number = new BigInteger(1, hash);

            // Convert message digest into hex value
            StringBuilder hexString = new StringBuilder(number.toString(16));

            // Pad with leading zeros
            while (hexString.length() < 32)
            {
                hexString.insert(0, '0');
            }

            return hexString.toString();

        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private void writeJsonArrayToFile(String path, JSONArray jsonArray){
        try (FileWriter file = new FileWriter(path)) {
            file.write(jsonArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean userMatchCheck(String userName, JSONObject user) {
        String tempUserName = (String) user.get("user name");
        String deleteTime = (String) user.get("delete time");

        if (userName.equals(tempUserName)) {
            return deleteTime.equals("none");
        }
        return false;
    }


    private int getUserIndexIfExists(String userName) {
        try (FileReader reader = new FileReader(this.getPath())) {

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

    public void add(User user) {
        try (FileReader reader = new FileReader(this.getPath())) {
            int userId = getUserIndexIfExists(user.getName());

            if (userId != -1) {
                System.out.println("There is another user registered as " + user.getName() + ". Please choose another name and try again.");
                return;
            }

            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(reader);
            JSONArray usersList = (JSONArray) obj;
            JSONObject userObj = new JSONObject();
            user.setId(usersList.size());

            userObj.put("user ID", user.getId());
            userObj.put("user name", user.getName());
            userObj.put("password", getHashSHA256(user.getPassword()));
            userObj.put("sign up time", LocalDateTime.now().toString());
            userObj.put("gold", user.getGold());
            userObj.put("delete time", "none");
            userObj.put("cards", user.getCardsString());
            userObj.put("heroes", user.getHeroesString());

            usersList.add(userObj);

            writeJsonArrayToFile(this.path, usersList);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        System.out.println("New user " + user.getName() + " created successfully.");
    }

    public User load(String userName, String password){
        int userId = getUserIndexIfExists(userName);

        if (userId == -1) {
            System.out.println("There is no user registered as " + userName + ". Please consider creating a new account!");
            return null;
        }

        User user = new User(userName, password);

        try (FileReader reader = new FileReader(this.getPath())) {

            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(reader);
            JSONArray usersList = (JSONArray) obj;
            JSONObject userObj;

            userObj = (JSONObject) usersList.get(userId);
            String truePassword = (String) userObj.get("password");

            if (truePassword.equals(getHashSHA256(password))) {
                Long gold =  (Long) userObj.get("gold");
                user.setGold(gold.intValue());

                ArrayList<String> heroesList = (ArrayList<String>) userObj.get("heroes");
                HeroCreator heroCreator = HeroCreator.getHeroCreator();
                for (String heroName :
                        heroesList) {
                    user.getHeroes().add(heroCreator.createHero(heroName));
                }


                return user;
            } else {
                System.out.println("Incorrect password!");
            }

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void save(User user){
        try (FileReader reader = new FileReader(this.getPath())) {

            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(reader);
            JSONArray usersList = (JSONArray) obj;
            JSONObject userObj;

            userObj = (JSONObject) usersList.get(user.getId());

            userObj.put("user name", user.getName());
            userObj.put("password", getHashSHA256(user.getPassword()));
            userObj.put("gold", user.getGold());
            userObj.put("cards", user.getCardsString());
            userObj.put("heroes", user.getHeroesString());

            writeJsonArrayToFile(this.path, usersList);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        System.out.println("Changes to " + user.getName() + " saved successfully.");
    }

    public void remove(User user){
        remove(user.getName(), user.getPassword());
    }

    public void remove(String userName, String password) {
        int userId = getUserIndexIfExists(userName);

        if (userId == -1) {
            System.out.println("There is no user registered as " + userName);
            return;
        }

        try (FileReader reader = new FileReader(this.getPath())) {

            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(reader);
            JSONArray usersList = (JSONArray) obj;
            JSONObject user;

            user = (JSONObject) usersList.get(userId);
            String truePassword = (String) user.get("password");

            if (truePassword.equals(getHashSHA256(password))) {
                user.put("delete time", LocalDateTime.now().toString());
                System.out.println(userName + " removed successfully");

                writeJsonArrayToFile(this.path, usersList);

            } else {
                System.out.println("Incorrect password!");
            }

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }
}


