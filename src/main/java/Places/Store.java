package Places;

import Cards.Card;
import GUI.Listeners.GeneralEventListener;
import GUI.MainFrame;
import GameHandler.*;
import GameHandler.GameHandler;
import Heroes.Hero;
import Logger.Logger;
import UserHandle.User;
import Utilities.TextProcessingTools;

import java.util.ArrayList;
import java.util.EventObject;

public class Store extends Place {
    private static Store store = new Store();

    private Store() {
        setInstructionsPath("Data/Store Commands.json");
        loadInstructions();
    }

    public static Store getStore() {
        return store;
    }

    public ArrayList<Card> sellCardsList() {
        ArrayList<Card> sellCardsList = new ArrayList<>();
        for (Card card :
                GameState.getGameState().getUser().getCards()) {
            if (GameState.getGameState().getUser().isForSale(card)) {
                sellCardsList.add(card);
            }
        }
        return sellCardsList;
    }

    public ArrayList<Card> buyCardsList() {
        ArrayList<Card> buyCardsList = new ArrayList<>();
        for (String cardName :
                GameHandler.getGameHandler().getAllCardNames()) {
            if (!GameState.getGameState().getUser().hasCard(cardName)) {
                buyCardsList.add(GameHandler.getGameHandler().getCard(cardName));
            }
        }
        return buyCardsList;
    }

    public void sellOrBuyCard(Card card) {
        if (GameState.getGameState().getUser().hasCard(card)) {
            String message = "Sell " + card.getName() + " for " + card.getPrice() + " golds?";

            GeneralEventListener yesListener = new GeneralEventListener() {
                @Override
                public void eventOccurred(EventObject eventObject) {
                    sellCard(card);
                    GameState.getGameState().refreshMainFrame();
                }
            };

            GeneralEventListener noListener = new GeneralEventListener() {
                @Override
                public void eventOccurred(EventObject eventObject) {
                }
            };

            RespondToUser.yesNoRespond(message, yesListener, noListener);

        } else {
            String message = "Buy " + card.getName() + " for " + card.getPrice() + " golds?";

            GeneralEventListener yesListener = new GeneralEventListener() {
                @Override
                public void eventOccurred(EventObject eventObject) {
                    buyCard(card);
                    GameState.getGameState().refreshMainFrame();
                }
            };

            GeneralEventListener noListener = new GeneralEventListener() {
                @Override
                public void eventOccurred(EventObject eventObject) {
                }
            };

            RespondToUser.yesNoRespond(message, yesListener, noListener);
        }
    }

    private void buyCard(Card card) {
        if (card.getPrice() > GameState.getGameState().getUser().getGold()) {
            RespondToUser.respond("You don't have enough gold to buy " + card.getName() +
                    ".", GameState.getGameState().getUser(), true);
        }
        GameState.getGameState().getUser().setGold(GameState.getGameState().getUser().getGold() - card.getPrice());
        GameState.getGameState().getUser().addCard(card);
        RespondToUser.respond("You have successfully purchased " + card.getName() + ".",
                GameState.getGameState().getUser(), true);
    }

    private void sellCard(Card card) {

    }


    @Override
    public String toString() {
        return "Store{}";
    }

    @Override
    public void defaultResponse() {
        RespondToUser.respond("You are in the store. Run a command.");
    }

    @Override
    public Place runCommand(String command, User user, Place currentPlace) {
        String lowerCaseCommand = command.toLowerCase();
        switch (lowerCaseCommand) {
            case "hearthstone --help": {
                Logger.log(user, "help", "show list of instructions in " + currentPlace, true);
                for (String commandName :
                        getInstructions().keySet()) {
                    RespondToUser.respond(commandName + ": " + getInstructions().get(commandName), user);
                }
                return currentPlace;
            }
            case "main menu": {
                Logger.log(user, "navigate", "main menu", true);
                return MainMenu.getMainMenu();
            }
            case "wallet": {
                Logger.log(user, "wallet", "show wallet", true);
                RespondToUser.respond("You have " + user.getGold() + " golds.", user);
                return currentPlace;
            }
            case "ls -s": {
                Logger.log(user, "list", "cards available to sell", true);
                RespondToUser.respond("Here is the list of your cards, which could be sold:", user);
                for (Card card :
                        user.getCards()) {
                    if (user.isForSale(card)) {
                        RespondToUser.respond(card, user);
                    }
                }
                return currentPlace;
            }
            case "ls -b": {
                Logger.log(user, "list", "cards available to buy", true);
                RespondToUser.respond("Here is the list of available cards in the store for you:", user);
                for (String cardName :
                        GameHandler.getGameHandler().getAllCardNames()) {
                    if (!user.hasCard(cardName)) {
                        RespondToUser.respond(cardName, user);
                    }
                }
                return currentPlace;
            }
        }

        if (TextProcessingTools.stringFirstWordMatch(command, "sell")) {
            Logger.log(user, "sell", "sell a card", true);
            String bracketedCardName = command.replaceFirst("(?i)sell\\s*", "");
            if (TextProcessingTools.isInBrackets(bracketedCardName)) {
                String cardName = TextProcessingTools.unBracket(bracketedCardName);
                Card card = GameHandler.getGameHandler().getCard(cardName);
                if (card == null) {
                    RespondToUser.respond("There is no such card as " + cardName + ".", user);
                    return currentPlace;
                }
                if (user.hasCard(card)) {
                    for (Hero hero :
                            user.getHeroes()) {
                        if (hero.hasCard(card)) {
                            RespondToUser.respond("You have to remove " + cardName + " from the deck of all your heroes before selling it.", user);
                            return currentPlace;
                        }
                    }
                    user.setGold(user.getGold() + card.getPrice());
                    user.removeCard(card);
                    RespondToUser.respond("You have successfully sold " + cardName + ".", user);
                    return currentPlace;
                }
                RespondToUser.respond("You do not own " + cardName + " so you cannot sell it!", user);
                return currentPlace;
            }
            RespondToUser.respond("You have to enter your command in the form 'sell [card name]'", user);
            return currentPlace;
        }

        if (TextProcessingTools.stringFirstWordMatch(command, "buy")) {
            Logger.log(user, "sell", "buy a card", true);
            String bracketedCardName = command.replaceFirst("(?i)buy\\s+", "");
            if (TextProcessingTools.isInBrackets(bracketedCardName)) {
                String cardName = TextProcessingTools.unBracket(bracketedCardName);
                Card card = GameHandler.getGameHandler().getCard(cardName);
                if (card == null) {
                    RespondToUser.respond("There is no such card as " + cardName + ".", user);
                    return currentPlace;
                }
                if (user.hasCard(card)) {
                    RespondToUser.respond("You already have this card!", user);
                    return currentPlace;
                }
                if (card.getPrice() > user.getGold()) {
                    RespondToUser.respond("You don't have enough gold to buy " + cardName + ".", user);
                    return currentPlace;
                }
                user.setGold(user.getGold() - card.getPrice());
                user.addCard(card);
                RespondToUser.respond("You have successfully purchased " + cardName + ".", user);
                return currentPlace;
            }
            RespondToUser.respond("You have to enter your command in the form 'buy [card name]'", user);
            return currentPlace;
        }

        RespondToUser.respond("Invalid command!", user);
        return currentPlace;
    }
}
