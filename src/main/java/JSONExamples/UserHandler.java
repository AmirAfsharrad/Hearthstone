package JSONExamples;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class UserHandler {
    private static UserHandler userHandler = new UserHandler();
    private int initialWallet = 50;
    private String path = "JSONs/users.json";

    private UserHandler() {
    }

    public static UserHandler getUserHandler() {
        return userHandler;
    }

    public void setInitialWallet(int initialWallet) {
        this.initialWallet = initialWallet;
    }

    public int getInitialWallet() {
        return this.initialWallet;
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

    public void add(String userName, String password) {
        try (FileReader reader = new FileReader(this.getPath())) {

            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(reader);
            JSONArray usersList = (JSONArray) obj;
            JSONObject user = new JSONObject();

            user.put("user ID", usersList.size());
            user.put("user name", userName);
            user.put("password", password);
            user.put("sign up time", LocalDateTime.now().toString());
            user.put("wallet", this.getInitialWallet());
            user.put("delete time", "none");

            usersList.add(user);

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


