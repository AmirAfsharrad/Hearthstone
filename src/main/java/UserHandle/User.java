package UserHandle;

import Cards.Card;
import Cards.CardCreator;
import Heros.Hero;
import Heros.Mage;
import Initialization.CardsDataCreator;
import Initialization.DefaultUserCardsDataCreator;
import Utilities.FileHandler;
import org.json.simple.JSONArray;

import java.util.ArrayList;

public class User{
    private int id;
    private String name;
    private String password;
    private int gold;
    private ArrayList<Card> cards;
    private ArrayList<Hero> heroes;
    private int currentHeroIndex;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        cards = new ArrayList<Card>();
        heroes = new ArrayList<Hero>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public ArrayList<Hero> getHeroes() {
        return heroes;
    }

    public void setHeroes(ArrayList<Hero> heroes) {
        this.heroes = heroes;
    }

    public Hero getCurrentHero() {
        return heroes.get(currentHeroIndex);
    }

    public int getCurrentHeroIndex() {
        return currentHeroIndex;
    }

    public void setCurrentHeroIndex(int currentHeroIndex){
        this.currentHeroIndex = currentHeroIndex;
    }

    public void setCurrentHero(String heroName) {
        int counter = 0;
        for (Hero hero :
                heroes) {
            if (hero.getType().equals(heroName)) {
                currentHeroIndex = counter;
                break;
            }
            counter++;
        }
    }

    public void copy (User user){
        this.setId(user.id);
        this.setName(user.name);
        this.setPassword(user.password);
        this.setGold(user.gold);
        this.setHeroes(user.heroes);
        this.setCurrentHeroIndex(user.currentHeroIndex);
        this.setCards(user.cards);
    }

    public boolean hasHero(Hero hero){
        return hasHero(hero.toString());
    }

    public boolean hasHero(String hero){
        for (Hero userHero :
             this.heroes) {
            if (userHero.getClass().equals(hero)){
                return true;
            }
        }
        return false;
    }

    public boolean hasCard(String cardName){
        return hasCard(CardCreator.createCard(cardName));
    }

    public boolean hasCard(Card card){
        return  (cards.contains(card));
    }

    public void addHero(Hero hero){
        heroes.add(hero);
    }

    public ArrayList<String> getHeroesString(){
        ArrayList<String> heroesString = new ArrayList<>();
        for (Hero hero :
                heroes) {
            heroesString.add(hero.toString());
        }
        return heroesString;
    }

    public void initializeCardsAndHeroesAsDefault(){
        currentHeroIndex = 0;
        heroes.add(new Mage());

        ArrayList<String> stringListOfCards = (ArrayList) FileHandler.readFileInList(DefaultUserCardsDataCreator.getPath());

        for (String cardName :
                stringListOfCards) {
            addCard(cardName);
        }
    }

    public ArrayList<String> getCardsAsArrayOfString(){
        ArrayList<String> cardsString = new ArrayList<>();
        for (Card card :
                cards) {
            cardsString.add(card.toString());
        }
        return cardsString;
    }

    public void addCard(String cardName){
        addCard(CardCreator.createCard(cardName));
    }

    public void addCard(Card card){
        cards.add(card);
    }

    public void removeCard(String cardName){
        removeCard(CardCreator.createCard(cardName));
    }

    public void removeCard(Card card){
        cards.remove(card);
    }

    public JSONArray getHerosJsonArray(){
        JSONArray heroesJsonArray = new JSONArray();
        for (Hero hero :
                heroes) {
            heroesJsonArray.add(hero.getJson());
        }
        return heroesJsonArray;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", gold=" + gold +
                ", cards=" + cards +
                ", heroes=" + heroes +
                ", currentHero=" + heroes.get(currentHeroIndex) +
                '}';
    }
}
