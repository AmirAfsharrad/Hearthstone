package UserHandle;

import Cards.*;
import GameHandler.ContestantState;
import GameHandler.GameHandler;
import GameHandler.GameState;
import GameLogic.Interfaces.Attackable;
import GameLogic.PlayCards;
import GameLogic.Visitors.GiveHealthVisitor;
import GameLogic.WaitingForTargetThread;
import Heroes.Hero;
import Logger.Logger;
import Places.Playground;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class Contestant {
    private String name;
    private ArrayList<Card> planted;
    private ArrayList<Card> hand;
    private ArrayList<Card> deck;
    private Hero hero;
    private Weapon currentWeapon;
    private Spell currentSpell;
    private Card choiceOfWeapon;
    private boolean hasWeapon;
    private boolean waitingForTarget;
    private int mana;
    private int turnFullManas;
    private String passive;
    private boolean drawTwice;
    private ContestantState state;
    private Minion target;
    private boolean[] initialHandModificationCheck = new boolean[3];
    private boolean immuneHero;

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

    public void drawRandomCard(boolean noSpell) {
        if (hand.size() < 12) {
            if (deck.size() > 0) {
                Card card = popRandomCard(deck);
                if (!card.getType().equals("Spell") || !noSpell)
                    hand.add(card);
            }
        }
    }

    public void startTurn() {
        state = ContestantState.Normal;
        Logger.log("Start turn", "start of " + name + "'s turn");
        mana = Math.min(turnFullManas + 1, 10);
        turnFullManas = mana;
        drawRandomCard(false);
        if (drawTwice) {
            drawRandomCard(false);
        }
        plantedAttackValuesInitiate();
    }

    private void plantedAttackValuesInitiate() {
        for (Card card : planted) {
            ((Minion) card).setTurnAttack(1);
        }
    }

    public void endTurn() {
        Logger.log("End turn", "end of " + name + "'s turn");
        immuneHero = false;
        waitingForTarget = false;
    }

    public void checkForDeadMinions() throws IOException {
        boolean flag = false;
        ArrayList<Integer> removedIndices = new ArrayList();
        for (int i = 0; i < planted.size(); i++) {
            if (((Minion) planted.get(i)).getHp() == 0) {
                removedIndices.add(0, i);
                flag = true;
            }
        }
        for (Integer removedIndex : removedIndices) {
            planted.remove((int) removedIndex);
        }
        if (flag) {
//            GameState.getGameState().refreshMainFrame();
        }
    }

    public void playCard(Card card, int numberOnLeft) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (card.getMana() <= mana) {
                    waitingForTarget = false;
                    safeRemove(hand, card);
                    mana -= card.getMana();
                    Logger.log("Card Played", card.getName());
                    switch (card.getType()) {
                        case "Minion":
                            if (planted.size() < 7) {
                                ((Minion) card).setTurnAttack(0);
                                planted.add(getNewPlantedCardIndex(numberOnLeft), card);
                                System.out.println("card name = " + card.getName());
                                if (((Minion) card).hasBattlecry()) {
                                    try {
                                        Spell battlecry = (Spell) GameHandler.getGameHandler().getCard
                                                (card.getName() + " Battlecry");
                                        battlecry.setContestant(Playground.getPlayground().getCurrentContestant());
                                        PlayCards.playSpell(battlecry);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            break;
                        case "Weapon":
                            currentWeapon = (Weapon) card;
                            hasWeapon = true;
                            break;
                        case "Spell":
                            try {
                                PlayCards.playSpell((Spell) card);
                            } catch (IOException | InterruptedException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
//                    checkForDeadMinions();
                    Playground.getPlayground().getGameLog().add(name + ": " + card);
                }
            }
        }).start();
    }

    public void initiateAttack(Minion minion, int attackValue) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Contestant contestant = Playground.getPlayground().getCurrentContestant();
                Contestant opponentContestant = Playground.getPlayground().getOpponentContestant();
                Object target = null;

                System.out.println("WAITING for attack target");

                while (contestant.getTarget() == null || contestant.getTarget().getContestant() == contestant) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("target received");
                System.out.println("target = " + contestant.getTarget().getName());
                target = contestant.getTarget();
                contestant.setTarget(null);
                contestant.setWaitingForTarget(false);
                if (!opponentContestant.hasTaunt()) {
                    System.out.println("IF 1");
                    minion.attack((Attackable) target, attackValue);
                } else {
                    System.out.println("ELSE 1");
                    if (target instanceof Minion && ((Minion) target).isTaunt()) {
                        System.out.println("IF 2");
                        minion.attack((Attackable) target, attackValue);
                    }
                }
//                if (!opponentContestant.hasTaunt() || (target instanceof Minion && ((Minion) target).isTaunt()))
//                    minion.attack((Attackable) target, attackValue);
//                ((Attackable) target).acceptAttack(attackValue);
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

    public void initialHandModification(Card card) throws IOException {
        if (deck.size() <= 3)
            return;
        int index = 0;
        for (int i = 0; i < 3; i++) {
            if (hand.get(i) == card) {
                index = i;
                break;
            }
        }
        if (!initialHandModificationCheck[index]) {
            initialHandModificationCheck[index] = true;
            hand.remove(index);
            Card newCard = card;
            while (newCard == card) {
                newCard = popRandomCard(deck);
            }
            hand.add(index, newCard);
            GameState.getGameState().refreshMainFrame();
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
        for (Card card : planted) {
            if (((Minion) card).isTaunt())
                return true;
        }
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

    public Card getChoiceOfWeapon() {
        return choiceOfWeapon;
    }

    public void setChoiceOfWeapon(Card choiceOfWeapon) {
        this.choiceOfWeapon = choiceOfWeapon;
    }

    public <T> void safeRemove(ArrayList<T> arrayList, T item) {
        int index;
        boolean flag;
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i) == item) {
                arrayList.remove(i);
                break;
            }
        }
    }

    public boolean isImmuneHero() {
        return immuneHero;
    }

    public void setImmuneHero(boolean immuneHero) {
        this.immuneHero = immuneHero;
    }

    public int getPlantedCardIndex(Card card) {
        for (int i = 0; i < planted.size(); i++) {
            if (card == planted.get(i)) {
                return i;
            }
        }
        return -1;
    }

    public ArrayList<Card> getListOfWeapons() {
        ArrayList<Card> weapons = GameHandler.getGameHandler().getWeapons();
        int number = weapons.size();
        Random random = new Random();
        HashSet<Integer> numbers = new HashSet<>();
        while (numbers.size() < 3) {
            numbers.add(random.nextInt(number));
        }
        ArrayList<Card> result = new ArrayList<>();
        for (Integer integer : numbers) {
            result.add(weapons.get(integer));
        }
        return result;
    }

}
