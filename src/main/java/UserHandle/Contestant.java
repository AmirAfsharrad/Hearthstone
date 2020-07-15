package UserHandle;

import Cards.*;
import GameHandler.ContestantState;
import GameLogic.PlayCards;
import GameLogic.Visitors.GiveHealthVisitor;
import GameLogic.WaitingForTargetThread;
import Heroes.Hero;
import Logger.Logger;
import Places.Playground;

import java.util.ArrayList;
import java.util.Random;

public class Contestant {
    private String name;
    private ArrayList<Card> planted;
    private ArrayList<Card> hand;
    private ArrayList<Card> deck;
    private Hero hero;
    private Weapon currentWeapon;
    private Spell currentSpell;
    private boolean hasWeapon;
    private boolean waitingForTarget;
    private int mana;
    private int turnFullManas;
    private String passive;
    private boolean drawTwice;
    private ContestantState state;
    private Minion target;

    public Minion getTarget() {
        return target;
    }

    public void setTarget(Minion target) {
        this.target = target;
    }

    public Contestant(String name) {
        this.name = name;
        passive = "";
    }

    public void init(Deck inputDeck) {
        initDeck(inputDeck);
        initHero(inputDeck);
        mana = 1;
        turnFullManas = 1;
        planted = new ArrayList<>();
        hand = new ArrayList<>();
        initHand();
        drawTwice = false;
        initPassiveProcess();
    }

    private void initDeck(Deck inputDeck) {
        deck = new ArrayList<>();
        for (Card card : inputDeck.getCards()) {
            deck.add(card.clone());
        }
        for (Card card : deck) {
            card.setContestant(this);
        }
    }

    private void initHero(Deck inputDeck) {
        hero = inputDeck.getHero();
        hero.setContestant(this);
    }

    private void initHand() {
        int count = 0;
//        for (Card card : deck) {
//            if (card.getType().equals("Quest")) {
//                if (count < 3) {
//                    hand.add(card);
//                    deck.remove(card);
//                    count++;
//                }
//            }
//        }
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

    public void startTrun() {
        state = ContestantState.Normal;
        Logger.log("Start turn", "start of " + name + "'s turn");
        mana = Math.min(turnFullManas + 1, 10);
        turnFullManas = mana;
        drawRandomCard();
        if (drawTwice) {
            drawRandomCard();
        }
    }

    public void endTurn() {
        Logger.log("End turn", "end of " + name + "'s turn");
        waitingForTarget = false;
    }

    public void playCard(Card card, int numberOnLeft) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (card.getMana() <= mana) {
                    waitingForTarget = false;
                    hand.remove(card);
                    mana -= card.getMana();
                    Logger.log("Card Played", card.getName());
                    switch (card.getType()) {
                        case "Minion":
                            if (planted.size() < 7) {
                                planted.add(getNewPlantedCardIndex(numberOnLeft), card);
                                System.out.println("card name = " + card.getName());
                                System.out.println(card.getType());
                            }
                            break;
                        case "Weapon":
                            currentWeapon = (Weapon) card;
                            hasWeapon = true;
                            break;
                        case "Spell":
                            PlayCards.playSpell((Spell) card);
                            break;
                    }
                    Playground.getPlayground().getGameLog().add(name + ": " + card);
                }
            }
        }).start();
    }

    private int getNewPlantedCardIndex(int numberOnLeft) {
        int maxSize;
        if (planted.size() % 2 == 0) {
            maxSize = 6;
        } else {
            maxSize = 7;
        }
        return Math.min(Math.max(numberOnLeft - (maxSize - planted.size()) / 2, 0), planted.size());
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

    public void setPassive(String passive) {
        this.passive = passive;
    }

    public Weapon getCurrentWeapon() {
        return currentWeapon;
    }

    public boolean hasWeapon() {
        return hasWeapon;
    }

    public boolean isWaitingForTarget() {
        return waitingForTarget;
    }

    public Spell getCurrentSpell() {
        return currentSpell;
    }

    public ContestantState getState() {
        return state;
    }

    public boolean hasTaunt() {
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlanted(ArrayList<Card> planted) {
        this.planted = planted;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public void setDeck(ArrayList<Card> deck) {
        this.deck = deck;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public void setCurrentWeapon(Weapon currentWeapon) {
        this.currentWeapon = currentWeapon;
    }

    public void setCurrentSpell(Spell currentSpell) {
        this.currentSpell = currentSpell;
    }

    public boolean isHasWeapon() {
        return hasWeapon;
    }

    public void setHasWeapon(boolean hasWeapon) {
        this.hasWeapon = hasWeapon;
    }

    public void setWaitingForTarget(boolean waitingForTarget) {
        this.waitingForTarget = waitingForTarget;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void setTurnFullManas(int turnFullManas) {
        this.turnFullManas = turnFullManas;
    }

    public String getPassive() {
        return passive;
    }

    public boolean isDrawTwice() {
        return drawTwice;
    }

    public void setDrawTwice(boolean drawTwice) {
        this.drawTwice = drawTwice;
    }

    public void setState(ContestantState state) {
        this.state = state;
    }
}
