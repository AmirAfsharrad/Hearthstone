package Utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class FileHandler {

    public static List<String> readFileInList(String path) {
        List<String> lines = Collections.emptyList();
        try {
            lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static String readFileInString(String path) {
        try {
            String text = Files.readString(Paths.get(path), StandardCharsets.UTF_8);
            return text;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeListIntoFile(List list, String path){
        try {
            Files.write(Paths.get(path), list);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void writeStringIntoFile(String text, String path){
        try (PrintWriter out = new PrintWriter(path)) {
            out.println(text);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void writeJsonObjectToFile(JSONObject jsonObject, String path){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(jsonObject.toJSONString());
        String prettyJsonString = gson.toJson(jsonElement);

        try (FileWriter file = new FileWriter(path)) {
            file.write(prettyJsonString);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeJsonArrayToFile(JSONArray jsonArray, String path){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(jsonArray.toJSONString());
        String prettyJsonString = gson.toJson(jsonElement);

        try (FileWriter file = new FileWriter(path)) {
            file.write(prettyJsonString);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JSONArray getJsonArrayFromFile(String path){
        try (FileReader reader = new FileReader(path)) {

            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(reader);
            JSONArray jsonArray = (JSONArray) obj;

            return jsonArray;

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject getJsonObjectFromFile(String path){
        try (FileReader reader = new FileReader(path)) {

            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(reader);
            JSONObject jsonObject = (JSONObject) obj;

            return jsonObject;

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
