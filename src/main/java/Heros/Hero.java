package Heros;

import CLI.RespondToUser;
import Cards.Card;
import UserHandle.UserDataHandler;
import Utilities.FileHandler;
import Utilities.SHA256Hash;
import com.google.gson.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Hero {
    private int hp;
    private String type;
    private String heroPower;
    private String specialPower;
    private ArrayList<Card> deck = new ArrayList<>();

    public Hero(){}

    public Hero(int hp, ArrayList<Card> deck, String type) {
        this.hp = hp;
        this.deck = deck;
        this.type = type;
    }

    public ArrayList<String> getDeckAsArrayOfString(){
        ArrayList<String> stringDeck = new ArrayList<>();
        for (Card card :
                deck) {
            stringDeck.add(card.getName());
        }
        return stringDeck;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public String getHeroPower() {
        return heroPower;
    }

    public void setHeroPower(String heroPower) {
        this.heroPower = heroPower;
    }

    public String getSpecialPower() {
        return specialPower;
    }

    public void setSpecialPower(String specialPower) {
        this.specialPower = specialPower;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public void setDeck(ArrayList<Card> deck) {
        this.deck = deck;
    }

    public JSONObject getJson(){
        JSONObject heroObject = new JSONObject();

        System.out.println(getDeckAsArrayOfString());

        heroObject.put("hp", hp);
        heroObject.put("type", type);
        heroObject.put("hero power", heroPower);
        heroObject.put("special power", specialPower);
        heroObject.put("stringDeck", getDeckAsArrayOfString());

        return heroObject;
    }


}
