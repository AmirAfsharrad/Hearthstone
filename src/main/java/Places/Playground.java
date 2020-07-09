package Places;

import Cards.Card;
import Cards.Deck;
import GameHandler.GameHandler;
import GameHandler.GameState;
import Heroes.Hero;
import Logger.Logger;
import UserHandle.Contestant;

import java.util.ArrayList;
import java.util.Random;

public class Playground extends Place {
    private static Playground playground = new Playground();
    private ArrayList<String> gameLog;
    private Contestant contestant0;
    private Contestant contestant1;
    private int turn = 0;

    private Playground() {
        contestant0 = new Contestant(GameState.getGameState().getUser().getName());
        contestant1 = new Contestant("system");
    }

    public void changeTurn() {
        turn = 1 - turn;
    }

    public void initGame(Deck inputDeck) {
        gameLog = new ArrayList<>();
        contestant0.init(inputDeck);
        contestant1.init(GameHandler.getGameHandler().getDefaultOpponentDeck());
    }

    public static Playground getPlayground() {
        return playground;
    }

    public Contestant getContestant(int index) {
        if (index == 0)
            return contestant0;
        if (index == 1)
            return contestant1;
        return null;
    }

    public Contestant getContestant0() {
        return contestant0;
    }

    public Contestant getContestant1() {
        return contestant1;
    }

    public Contestant getCurrentContestant() {
        switch (turn) {
            case 0 : return contestant0;
            case 1 : return contestant1;
        }
        Logger.log("Turn Error", "turn value different from 0 or 1");
        return contestant0;
    }

    public void nextTurn() {
        getCurrentContestant().endTurn();
        changeTurn();
        getCurrentContestant().startTrun();
    }

    //    private void initHand() {
//        int count = 0;
//        for (Card card : deck) {
//            if (card.getType().equals("Quest")) {
//                if (count < 3) {
//                    hand.add(card);
//                    deck.remove(card);
//                    count++;
//                }
//            }
//        }
//        for (int i = 0; i < Math.min(3 - count, deck.size()); i++) {
//            System.out.println(i);
//            hand.add(popRandomCard(deck));
//        }
//    }
//
//    public void initPassiveProcess() {
//        switch (passive) {
//            case "zombie": {
//                hero.setHeroPower("Zombie");
//                break;
//            }
//            case "mana jump": {
//                mana = 2;
//                turnFullManas = 2;
//                break;
//            }
//            case "draw twice": {
//                drawTwice = true;
//            }
//        }
//    }
//
//    private void drawRandomCard() {
//        if (hand.size() < 12) {
//            if (deck.size() > 0) {
//                hand.add(popRandomCard(deck));
//            }
//        }
//    }
//
//    public void stepOneTurn() {
//        Logger.log("Next Turn", "");
//        mana = Math.min(turnFullManas + 1, 10);
//        turnFullManas = mana;
//        drawRandomCard();
//        if (drawTwice) {
//            drawRandomCard();
//        }
//    }
//
//    public void playCard(Card card) {
//        if (planted.size() < 7) {
//            if (card.getMana() <= mana) {
//                Logger.log("Card Played", card.getName());
//                if (card.getType().equals("Minion")) {
//                    planted.add(card);
//                }
//                hand.remove(card);
//                mana -= card.getMana();
//                gameLog.add(GameState.getGameState().getUser().getName() + ": " + card);
//            }
//        }
//    }
//
//    private Card popRandomCard(ArrayList<Card> cards) {
//        if (!cards.isEmpty()) {
//            Random random = new Random();
//            int rand = random.nextInt(cards.size());
//            Card randomCard = cards.get(rand);
//            cards.remove(rand);
//            return randomCard;
//        } else {
//            return null;
//        }
//    }
//
//    public int getRemainingDeckSize() {
//        return deck.size();
//    }
//

//
//    public int getMana() {
//        return mana;
//    }
//
//    public int getTurnFullManas() {
//        return turnFullManas;
//    }
//
//    public ArrayList<Card> getPlanted() {
//        return planted;
//    }
//
//    public ArrayList<Card> getHand() {
//        return hand;
//    }
//
//    public Hero getHero() {
//        return hero;
//    }
//
    public ArrayList<String> getGameLog() {
        return gameLog;
    }
//
//    public void setPassive(String passive) {
//        this.passive = passive;
//    }
}
