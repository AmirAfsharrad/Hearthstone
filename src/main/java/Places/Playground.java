package Places;

import Cards.Card;
import Cards.Deck;
import GameHandler.GameState;
import Heroes.Hero;
import Logger.Logger;

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
        initHand();
        drawTwice = false;
        initPassiveProcess();
    }

    private void initHand() {
        int count = 0;
        for (Card card : deck) {
            if (card.getType().equals("Quest")) {
                if (count < 3) {
                    hand.add(card);
                    deck.remove(card);
                    count++;
                }
            }
        }
        for (int i = 0; i < Math.min(3 - count, deck.size()); i++) {
            System.out.println(i);
            hand.add(popRandomCard(deck));
        }
    }

    public void initPassiveProcess() {
        switch (passive) {
            case "zombie": {
                hero.setHeroPower("Zombie");
                break;
            }
            case "mana jump": {
                mana = 2;
                turnFullManas = 2;
                break;
            }
            case "draw twice": {
                drawTwice = true;
            }
        }
    }

    private void drawRandomCard() {
        if (hand.size() < 12) {
            if (deck.size() > 0) {
                hand.add(popRandomCard(deck));
            }
        }
    }

    public void stepOneTurn() {
        Logger.log("Next Turn", "");
        mana = Math.min(turnFullManas + 1, 10);
        turnFullManas = mana;
        drawRandomCard();
        if (drawTwice) {
            drawRandomCard();
        }
    }

    public void playCard(Card card) {
        if (planted.size() < 7) {
            if (card.getMana() <= mana) {
                Logger.log("Card Played", card.getName());
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
}
