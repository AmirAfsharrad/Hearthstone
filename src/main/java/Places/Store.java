package Places;

import Cards.Card;
import Cards.Deck;
import GUI.Listeners.GeneralEventListener;
import GameHandler.*;
import GameHandler.GameHandler;
import Heroes.Hero;
import Logger.Logger;
import UserHandle.User;
import Utilities.TextProcessingTools;

import java.io.IOException;
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
                public void eventOccurred(EventObject eventObject) throws IOException {
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
                public void eventOccurred(EventObject eventObject) throws IOException {
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
            return;
        }
        GameState.getGameState().getUser().setGold(GameState.getGameState().getUser().getGold() - card.getPrice());
        GameState.getGameState().getUser().addCard(card);
        RespondToUser.respond("You have successfully purchased " + card.getName() + ".",
                GameState.getGameState().getUser(), true);
    }

    private void sellCard(Card card) {
        if (cardInvolvedInDecks(card)) {
            RespondToUser.respond("Remove " + card.getName() + " from all your decks in order to sell it.",
                    GameState.getGameState().getUser(), true);
        } else {
            GameState.getGameState().getUser().getCards().remove(card);
            GameState.getGameState().getUser().setGold(GameState.getGameState().getUser().getGold() + card.getPrice());
            RespondToUser.respond("You have successfully sold " + card.getName() + ".",
                    GameState.getGameState().getUser(), true);
        }
    }

    private boolean cardInvolvedInDecks(Card card) {
        for (Deck deck : GameState.getGameState().getUser().getDecks()) {
            if (deck.hasCard(card)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public String toString() {
        return "Store{}";
    }

    @Override
    public void defaultResponse() {
        RespondToUser.respond("You are in the store. Run a command.");
    }
}
