package Places;

import Cards.Card;
import Cards.Deck;
import GUI.Listeners.GeneralEventListener;
import GameHandler.GameHandler;
import GameHandler.GameState;
import GameHandler.RespondToUser;
import Logger.Logger;
import sun.rmi.runtime.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EventObject;

public class Store extends Place {
    private static Store store = new Store();
    private ArrayList<Card> displayedCards;

    private Store() {
        setInstructionsPath("Data/Store Commands.json");
        loadInstructions();
        displayedCards = new ArrayList<>();
        filterDisplayedCards(-1, "all", "", "all");
    }

    public static Store getStore() {
        return store;
    }

    public void resetDisplayedCards() {
        filterDisplayedCards(-1, "all", "", "all");
    }

    public ArrayList<Card> getDisplayedCards() {
        return displayedCards;
    }

    public void filterDisplayedCards(int mana, String heroClass, String searchString, String doesOwn) {
        displayedCards.clear();
        for (String cardName : GameHandler.getGameHandler().getAllCardNames()) {
            Card card = GameHandler.getGameHandler().getCard(cardName);
            if (mana != -1 && card.getMana() != mana)
                continue;
            if (!heroClass.equals("all") && !card.getHeroClass().equals(heroClass))
                continue;
            if (!card.getName().toLowerCase().contains(searchString.toLowerCase()))
                continue;
            if (doesOwn.equals("buyable") && GameState.getGameState().getUser().hasCard(card))
                continue;
            if (doesOwn.equals("sellable") && !GameState.getGameState().getUser().isForSale(card))
                continue;
            displayedCards.add(card);
        }
    }


    public void sellOrBuyCard(Card card) {
        if (GameState.getGameState().getUser().hasCard(card)) {
            String message = "Sell " + card.getName() + " for " + card.getPrice() + " golds?";

            GeneralEventListener yesListener = new GeneralEventListener() {
                @Override
                public void eventOccurred(EventObject eventObject) throws IOException {
                    Logger.log("Sell Card", card.getName());
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
                    Logger.log("Buy Card", card.getName());
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

}
