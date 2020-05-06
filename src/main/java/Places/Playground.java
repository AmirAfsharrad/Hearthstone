package Places;

import Cards.Card;
import Cards.Deck;
import Cards.Minion;
import GameHandler.GameState;
import Heroes.Hero;

import javax.swing.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Random;

public class Playground extends Place {
    private static Playground playground = new Playground();
    private ArrayList<Card> planted;
    private ArrayList<Card> hand;
    private ArrayList<Card> deck;
    private Hero hero;
    private int mana;
    private int turnFullManas;
    private ArrayList<String> gameLog;
    private String passive;
    private boolean drawTwice;

    private Playground() {
    }

    public void initGame(Deck inputDeck) {
        gameLog = new ArrayList<>();
        deck = new ArrayList<>();
        deck.addAll(inputDeck.getCards());
        hero = inputDeck.getHero();
        mana = 1;
        turnFullManas = 1;
        planted = new ArrayList<>();
        hand = new ArrayList<>();
        int deckInitialSize = deck.size();
        for (int i = 0; i < Math.min(3, deckInitialSize); i++) {
            System.out.println(i);
            hand.add(popRandomCard(deck));
        }
        drawTwice = false;
        initPassiveProcess();
    }

    public void initPassiveProcess() {
        switch (passive) {
            case "zombie" : {
                hero.setHeroPower("Zombie");
                break;
            }
            case "mana jump" : {
                mana = 2;
                turnFullManas = 2;
                break;
            }
            case "draw twice" : {
                drawTwice = true;
            }
        }
    }

    public void stepOneTurn() {
        mana = Math.min(turnFullManas + 1, 10);
        turnFullManas = mana;
        if (hand.size() < 12) {
            if (deck.size() > 0) {
                hand.add(popRandomCard(deck));
                if (drawTwice) {
                    if (hand.size() < 12) {
                        if (deck.size() > 0) {
                            hand.add(popRandomCard(deck));
                        }
                    }
                }
            }
        }
    }

    public void playCard(Card card) {
        if (planted.size() < 7) {
            if (card.getMana() <= mana) {
                if (card.getType().equals("Minion")) {
                    planted.add(card);
                }
                hand.remove(card);
                mana -= card.getMana();
                gameLog.add(GameState.getGameState().getUser().getName() + ": " + card);
            }
        }
    }

    private Card popRandomCard(ArrayList<Card> cards) {
        if (!cards.isEmpty()) {
            Random random = new Random();
            int rand = random.nextInt(cards.size());
            Card randomCard = cards.get(rand);
            cards.remove(rand);
            return randomCard;
        } else {
            return null;
        }
    }

    public int getRemainingDeckSize() {
        return deck.size();
    }

    public static Playground getPlayground() {
        return playground;
    }

    public int getMana() {
        return mana;
    }

    public int getTurnFullManas() {
        return turnFullManas;
    }

    public ArrayList<Card> getPlanted() {
        return planted;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public Hero getHero() {
        return hero;
    }

    public ArrayList<String> getGameLog() {
        return gameLog;
    }

    public void setPassive(String passive) {
        this.passive = passive;
    }

    @Override
    public void defaultResponse() {

    }


}
