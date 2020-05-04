package Cards;

import Heroes.Hero;
import Heroes.Mage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Deck {
    private ArrayList<Card> cards;
    private Hero hero;
    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deck deck = (Deck) o;
        return Objects.equals(name, deck.name);
    }
}
