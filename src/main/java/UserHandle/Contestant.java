package UserHandle;

import Cards.*;
import GameHandler.ContestantState;
import GameHandler.GameHandler;
import GameHandler.GameState;
import GameLogic.Interfaces.Attackable;
import GameLogic.Interfaces.Target;
import GameLogic.PlayCards;
import GameLogic.Visitors.DealDamageVisitor;
import GameLogic.Visitors.GiveHealthVisitor;
import GameLogic.WaitingForTargetThread;
import Heroes.Hero;
import Logger.Logger;
import Places.Playground;

import java.io.IOException;
import java.lang.management.MemoryNotificationInfo;
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
    private Target target;
    private boolean[] initialHandModificationCheck = new boolean[3];
    private boolean immuneHero;

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
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
                drawEffectHandle(card);
                if (!card.getType().equals("Spell") || !noSpell)
                    hand.add(card);
            }
        }
    }

    private void drawEffectHandle(Card card) {
        for (Card card1 : planted) {
            Minion minion = (Minion) card1;
            if (card instanceof Minion) {
                ((Minion) card).setAttackPower(((Minion) card).getAttackPower() + minion.getDrawEffect()[0]);
                ((Minion) card).setHp(((Minion) card).getHp() + minion.getDrawEffect()[1]);
            }
            if (card instanceof Weapon) {
                ((Weapon) card).setAttackPower(((Weapon) card).getAttackPower() + minion.getDrawEffect()[0]);
                ((Weapon) card).setDurability(((Weapon) card).getDurability() + minion.getDrawEffect()[1]);
            }
            card.setMana(card.getMana() + minion.getDrawEffect()[2]);
        }
    }

    public void startTurn() throws IOException {
        state = ContestantState.Normal;
        Logger.log("Start turn", "start of " + name + "'s turn");
        mana = Math.min(turnFullManas + 1, 10);
        turnFullManas = mana;
        currentSpell = null;
        drawRandomCard(false);
        if (drawTwice) {
            drawRandomCard(false);
        }
        plantedAttackValuesInitiate();
        startTurnMinionEffectHandle();
        checkForDeadMinions();
        Playground.getPlayground().getOpponentContestant().checkForDeadMinions();
    }

    private void startTurnMinionEffectHandle() throws IOException {
        for (Card card : planted) {
            Minion minion = (Minion) card;
            if (minion.getTurnStartDamage()[0] > 0) {
                for (Card card1 : Playground.getPlayground().getOpponentContestant().getPlanted()) {
                    ((Minion) card1).acceptDamage(DealDamageVisitor.getInstance(), minion.getTurnStartDamage()[0]);
                }
            }
        }
        checkForDeadMinions();
        Playground.getPlayground().getOpponentContestant().checkForDeadMinions();
    }

    private void plantedAttackValuesInitiate() {
        for (Card card : planted) {
            ((Minion) card).setJustPlanted(false);
            if (((Minion) card).isWindfury())
                ((Minion) card).setTurnAttack(2);
            else
                ((Minion) card).setTurnAttack(1);

            Minion minion = (Minion) card;
            System.out.println(minion + " , Taunt: " + minion.isTaunt() + " , Charge: " + minion.isCharge() +
                    " , Rush: " + minion.isRush());

        }
    }

    public void endTurn() throws IOException {
        Logger.log("End turn", "end of " + name + "'s turn");
        immuneHero = false;
        waitingForTarget = false;
        endTurnMinionEffectHandle();
    }

    private void endTurnMinionEffectHandle() throws IOException {
        for (Card card : planted) {
            Minion minion = (Minion) card;
            if (minion.getTurnEndDamage()[0] > 0) {
                for (Card card1 : Playground.getPlayground().getOpponentContestant().getPlanted()) {
                    ((Minion) card1).acceptDamage(DealDamageVisitor.getInstance(), minion.getTurnEndDamage()[0]);
                }
            }
        }
        Playground.getPlayground().getOpponentContestant().checkForDeadMinions();
    }

    public void checkForDeadMinions() throws IOException {
        boolean flag = false;
        ArrayList<Integer> removedIndices = new ArrayList();
        for (int i = 0; i < planted.size(); i++) {
            if (((Minion) planted.get(i)).getHp() == 0) {
                if (((Minion) planted.get(i)).isReborn()) {
                    ((Minion) planted.get(i)).setReborn(false);
                    ((Minion) planted.get(i)).setHp(1);
                }
                removedIndices.add(0, i);
                flag = true;
            }
        }
        for (Integer removedIndex : removedIndices) {
            planted.remove((int) removedIndex);
        }
        if (flag) {
            GameState.getGameState().refreshMainFrame();
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
                            PlayCards.playMinion(card, numberOnLeft);
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
                if ((!opponentContestant.hasTaunt() || (target instanceof Minion && ((Minion) target).isTaunt()))
                        && !(target instanceof Minion && ((Minion) target).isStealth())
                        && !((target instanceof Hero) && minion.isRush() && minion.isJustPlanted())) {
                    minion.attack((Attackable) target, attackValue);
                    if (minion.isPoisonous() && target instanceof Card) {
                        safeRemove(opponentContestant.getDeck(), (Card) target);
                    }
                }
                try {
                    contestant.checkForDeadMinions();
                    opponentContestant.checkForDeadMinions();
                    GameState.getGameState().refreshMainFrame();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public int getNewPlantedCardIndex(int numberOnLeft) {
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

    public void summon(Card card) {
        if (planted.size() >= 7)
            return;
        for (Card card1 : planted) {
            if (card1.getName().equals("High Priest Amet")) {
                ((Minion) card).setHp(((Minion) card1).getHp());
            }
        }
        planted.add(card);
    }

    public void summon(int index, Card card) {
        if (planted.size() >= 7)
            return;
        for (Card card1 : planted) {
            if (card1.getName().equals("High Priest Amet")) {
                ((Minion) card).setHp(((Minion) card1).getHp());
            }
        }
        planted.add(index, card);
    }

    public Card hasPlantedCard(String cardName) {
        for (Card card : planted) {
            if (card.getName().equals(cardName))
                return card;
        }
        return null;
    }

}
