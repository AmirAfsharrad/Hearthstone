package UserHandle;

import Cards.Card;
import Heros.Hero;
import Heros.Mage;
import java.util.ArrayList;

public class User {
    private int id;
    private String name;
    private String password;
    private int gold;
    private ArrayList<Card> cards;
    private ArrayList<Hero> heroes;
    private Hero currentHero;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        cards = new ArrayList<Card>();
        heroes = new ArrayList<Hero>();
        currentHero = new Mage();
        heroes.add(currentHero);
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
        return currentHero;
    }

    public void setCurrentHero(Hero currentHero) {
        this.currentHero = currentHero;
    }

    public void copy (User user){
        this.setId(user.getId());
        this.setName(user.getName());
        this.setPassword(user.getPassword());
        this.setGold(user.getGold());
        this.setHeroes(user.getHeroes());
        this.setCurrentHero(user.getCurrentHero());
        this.setCards(user.getCards());
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

    public ArrayList<String> getHeroesString(){
        ArrayList<String> heroesString = new ArrayList<>();
        for (Hero hero :
                heroes) {
            heroesString.add(hero.toString());
        }
        return heroesString;
    }

    public ArrayList<String> getCardsString(){
        ArrayList<String> cardsString = new ArrayList<>();
        for (Card card :
                cards) {
            cardsString.add(card.toString());
        }
        return cardsString;
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
                ", currentHero=" + currentHero +
                '}';
    }
}
