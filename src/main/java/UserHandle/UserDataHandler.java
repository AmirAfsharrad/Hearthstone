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

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
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
            userObj.put("password", user.getPassword());
            userObj.put("sign up time", LocalDateTime.now().toString());
            userObj.put("gold", user.getGold());
            userObj.put("delete time", "none");
            userObj.put("cards", user.getCardsString());
            userObj.put("heroes", user.getHeroesString());

            usersList.add(userObj);

            try (FileWriter file = new FileWriter(this.getPath())) {
                file.write(usersList.toJSONString());
                file.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
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

            if (truePassword.equals(password)) {
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

            if (truePassword.equals(password)) {
                user.put("delete time", LocalDateTime.now().toString());
                System.out.println(userName + " removed successfully");
                try (FileWriter file = new FileWriter(this.getPath())) {
                    file.write(usersList.toJSONString());
                    file.flush();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            } else {
                System.out.println("Incorrect password!");
            }

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }
}


