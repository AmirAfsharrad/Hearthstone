package Places;

import Cards.Deck;
import Cards.Minion;
import Cards.Weapon;
import GUI.Dialogs.ResponseDialog;
import GUI.Events.HeroButtonPressedEvent;
import GUI.Events.HeroPowerPressedEvent;
import GUI.Events.PlantedCardPressedEvent;
import GUI.Events.WeaponPressedEvent;
import GameHandler.GameHandler;
import GameHandler.GameState;
import Logger.Logger;
import UserHandle.Contestant;
import Utilities.DeckReader;

import java.io.IOException;
import java.util.ArrayList;

public class Playground extends Place {
    private static Playground playground = new Playground();
    private ArrayList<String> gameLog;
    private Contestant contestant0;
    private Contestant contestant1;
    private boolean gameFinished;
    private Deck originalDeck;
    private int turn = 0;

    private Playground() {
        contestant0 = new Contestant(GameState.getGameState().getUser().getName());
        contestant1 = new Contestant("system");
    }

    public void changeTurn() {
        turn = 1 - turn;
    }

    public int getTurn() {
        return turn;
    }

    public void initGame(Deck inputDeck) {
        originalDeck = inputDeck;
        gameLog = new ArrayList<>();
        inputDeck.setTotalGamesPlayed(inputDeck.getTotalGamesPlayed() + 1);
        if (DeckReader.getInstance().isActive()) {
            contestant0.init(DeckReader.getInstance().getFriendly());
            contestant1.init(DeckReader.getInstance().getEnemy());
        } else {
            contestant0.init(inputDeck);
            contestant1.init(GameHandler.getGameHandler().getDefaultOpponentDeck());
        }
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

    public Contestant getOpponentContestant() {
        switch (turn) {
            case 0 : return contestant1;
            case 1 : return contestant0;
        }
        Logger.log("Turn Error", "turn value different from 0 or 1");
        return contestant0;
    }

    public void nextTurn() throws IOException {
//        turnTimer.reset();
        getCurrentContestant().endTurn();
        changeTurn();
        getCurrentContestant().startTurn();
    }

    public void manageSelectedPlantedCard(PlantedCardPressedEvent plantedCardPressedEvent) throws IOException {
        if (getCurrentContestant().isWaitingForTarget()) {
            System.out.println("manageSelectedPlanedCard method begin");
            getCurrentContestant().setTarget((Minion) plantedCardPressedEvent.getCard());
        } else {
            Minion minion = (Minion) plantedCardPressedEvent.getCard();
            if (minion.getContestant() != getCurrentContestant())
                return;
            getCurrentContestant().setWaitingForTarget(true);
            getCurrentContestant().initiateAttack(minion, minion.getAttackPower());
            Playground.getPlayground().getContestant0().checkForDeadMinions();
            Playground.getPlayground().getContestant1().checkForDeadMinions();
        }
    }

    public void manageWeaponPressed(WeaponPressedEvent weaponPressedEvent) throws IOException {
        if (getCurrentContestant().isWaitingForTarget())
            return;
        Weapon weapon = weaponPressedEvent.getWeapon();
        if (weapon.getContestant() != getCurrentContestant())
            return;
        getCurrentContestant().setWaitingForTarget(true);
        getCurrentContestant().initiateAttack(weapon, weapon.getAttackPower());
        Playground.getPlayground().getContestant0().checkForDeadMinions();
        Playground.getPlayground().getContestant1().checkForDeadMinions();
    }

    public void manageHeroPowerPressed(HeroPowerPressedEvent heroPowerPressedEvent) throws IOException {
        if (heroPowerPressedEvent.getContestantIndex() != turn)
            return;
        getCurrentContestant().runHeroPower();
    }

    public void checkForGameFinish() {
        if (contestant0.getHero().getHp() == 0) {
            new ResponseDialog("", contestant0.getName() + " has won!");
            originalDeck.setTotalWinning(originalDeck.getTotalWinning() + 1);
            gameFinished = true;
        }
        if (contestant1.getHero().getHp() == 0) {
            new ResponseDialog("", "player2 has won!");
            gameFinished = true;
        }
    }

    public void manageSelectedHero(HeroButtonPressedEvent heroButtonPressedEvent) {
        if (getCurrentContestant().isWaitingForTarget()) {
            getCurrentContestant().setTarget(heroButtonPressedEvent.getHero());
            System.out.println("SET HERO TO " + heroButtonPressedEvent.getHero().getName());
        }
    }

    public ArrayList<String> getGameLog() {
        return gameLog;
    }

    public boolean isGameFinished() {
        return gameFinished;
    }

    public void setGameFinished(boolean gameFinished) {
        this.gameFinished = gameFinished;
    }

    //
//    public void setPassive(String passive) {
//        this.passive = passive;
//    }
}
