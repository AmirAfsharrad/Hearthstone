package Cards;

import Heroes.Hero;
import Heroes.Mage;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Deck {
    private ArrayList<Card> cards;
    private Hero hero;
    private String name;
    private int totalWinning;
    private int totalGamesPlayed;

    public Deck() {
        cards = new ArrayList<>();
    }

    public Deck(String name) {
        cards = new ArrayList<>();
        this.name = name;
        hero = new Mage();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public double getAverageCost() {
        double totalCost = 0;
        for (Card card : cards) {
            totalCost += card.getMana();
        }
        return Math.floor(10 * totalCost / cards.size()) / 10.0;
    }

    public int getHowManyOfThisCardInDeck(Card card) {
        int count = 0;
        for (Card card1 : cards) {
            if (card.equals(card1))
                count++;
        }
        return count;
    }

    public boolean hasCard(Card card) {
        return cards.contains(card);
    }

    public HashMap<Card, Integer> getHashMapOfCards() {
        HashMap<Card, Integer> hashMapOfCards = new HashMap<>();
        for (Card card : cards) {
            if (hashMapOfCards.containsKey(card)) {
                hashMapOfCards.put(card, hashMapOfCards.get(card) + 1);
            } else {
                hashMapOfCards.put(card, 1);
            }
        }
        return hashMapOfCards;
    }

    public int getTotalWinning() {
        return totalWinning;
    }

    public void setTotalWinning(int totalWinning) {
        this.totalWinning = totalWinning;
    }

    public int getTotalGamesPlayed() {
        return totalGamesPlayed;
    }

    public void setTotalGamesPlayed(int totalGamesPlayed) {
        this.totalGamesPlayed = totalGamesPlayed;
    }

    public int getWinningRatio () {
        return totalWinning * 100 / (totalGamesPlayed == 0 ? 1 : totalGamesPlayed);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deck deck = (Deck) o;
        return Objects.equals(name, deck.name);
    }

    public JSONObject getJson(){
        JSONObject deckObject = new JSONObject();
        ArrayList<String> cardsNames = new ArrayList<>();

        for (Card card : cards) {
            cardsNames.add(card.getName());
        }

        deckObject.put("name", name);
        deckObject.put("cardsNames", cardsNames);
        deckObject.put("heroName", hero.getType());
        deckObject.put("totalGames", totalGamesPlayed);
        deckObject.put("totalWin", totalWinning);

        return deckObject;
    }

    @Override
    public String toString() {
        return name;
    }
}
