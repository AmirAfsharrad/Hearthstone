package Places;

import Cards.Card;
import Cards.Deck;
import GUI.Events.ChooseFromListEvent;
import GUI.Events.GetTextEvent;
import GUI.Listeners.ChooseFromListListener;
import GUI.Listeners.GetTextListener;
import GameHandler.GameHandler;
import GameHandler.GameState;
import GameHandler.RespondToUser;
import Heroes.Hero;
import Heroes.HeroCreator;
import Logger.Logger;

import java.util.ArrayList;

public class Collections extends Place {
    private static Collections collections = new Collections();
    private ArrayList<Card> displayedCards;

    private Collections() {
        setInstructionsPath("Data/Collections Commands.json");
        loadInstructions();
        displayedCards = new ArrayList<>();
        filterDisplayedCards(-1, "all", "", "all");
    }

    public static Collections getCollections() {
        return collections;
    }

    public void resetDisplayedCards() {
        filterDisplayedCards(-1, "all", "", "all");
    }

    public void createNewDeck() {
        String newDeckName = "New Deck 1";
        int count = 1;
        boolean flag = true;
        while (flag) {
            newDeckName = "New Deck " + count;
            flag = false;
            for (Deck deck : GameState.getGameState().getUser().getDecks()) {
                if (deck.getName().equals("New Deck " + count)) {
                    flag = true;
                }
            }
            count++;
        }
        GameState.getGameState().getUser().addDeck(newDeckName);
        Logger.log("Create New Deck", newDeckName);
    }

    public void setSelectedDeck(Deck deck) {
        GameState.getGameState().getUser().setSelectedDeck(deck);
    }

    public void renameDeck(Deck deck) {
        GetTextListener getTextListener = new GetTextListener() {
            @Override
            public void getTextOccurred(GetTextEvent getTextEvent) {
                String newName = getTextEvent.getText();
                for (Deck deck1 : GameState.getGameState().getUser().getDecks()) {
                    if (deck1.getName().equals(newName)) {
                        RespondToUser.respond("You already have a deck named " + newName, true);
                        return;
                    }
                }
                deck.setName(newName);
                Logger.log("Rename Deck", newName);
            }
        };

        RespondToUser.getTextResponse("Enter the new name of the selected deck:", getTextListener);
    }

    public void addCardToDeck(Card card, Deck deck) {
        if (!GameState.getGameState().getUser().hasCard(card)) {
            Store.getStore().sellOrBuyCard(card);
            return;
        }
        if (deck.getCards().size() >= 15) {
            RespondToUser.respond("You cannot add more than 15 cards to a deck!", true);
            return;
        }
        if (deck.getHowManyOfThisCardInDeck(card) >= 2) {
            RespondToUser.respond("You cannot add more than 2 instances" +
                    "of a single card in a deck!", true);
            return;
        }
        if (!card.getHeroClass().equals("Neutral") && !card.getHeroClass().equals(deck.getHero().getType())) {
            RespondToUser.respond(card + " is incompatible with " +
                    deck.getHero(), true);
            return;
        }
        deck.getCards().add(card);
        Logger.log("Add Card to Deck", card + " -> " + deck);
    }

    public void removeCardFromDeck(Card card, Deck deck) {
        for (Card deckCard : deck.getCards()) {
            if (card.equals(deckCard)) {
                deck.getCards().remove(card);
                Logger.log("Remove Card from Deck", card + " -> " + deck);
                return;
            }
        }
    }

    public void removeDeck(Deck deck) {
        for (Deck deck1 : GameState.getGameState().getUser().getDecks()) {
            if (deck.equals(deck1)) {
                GameState.getGameState().getUser().getDecks().remove(deck1);
                Logger.log("Remove Deck", deck.getName());
                return;
            }
        }
    }

    public void deckSetHero(Deck deck) {
        String[] listOfHeroes = {"Mage", "Warlock", "Rogue", "Paladin", "Priest"};
        ChooseFromListListener chooseFromListListener = new ChooseFromListListener() {
            @Override
            public void ChooseFromListOccurred(ChooseFromListEvent chooseFromListEvent) {
                Hero hero = HeroCreator.createHero(chooseFromListEvent.getChoice());
                for (Card card : deck.getCards()) {
                    if (!card.getHeroClass().equals("Neutral") && !card.getHeroClass().equals(hero)) {
                        RespondToUser.respond("Cannot use " + hero +
                                " with this set of cards!", true);
                        return;
                    }
                }
                deck.setHero(hero);
                Logger.log("Deck Set Hero", deck + "<-" + hero);
            }
        };
        RespondToUser.chooseFromListResponse("Choose a hero", listOfHeroes, chooseFromListListener);
    }

    public void filterDisplayedCards(int mana, String heroClass, String searchString, String doesOwn) {
        displayedCards.clear();
        for (String cardName : GameHandler.getGameHandler().getAllCardNames()) {
            Card card = GameHandler.getGameHandler().getCard(cardName);
            if (mana != -1 && card.getMana() != mana)
                continue;
            if (!heroClass.equals("all") && !card.getHeroClass().equals(heroClass)) {
                continue;
            }
            if (!card.getName().toLowerCase().contains(searchString.toLowerCase()))
                continue;
            if (doesOwn.equals("owned") && !GameState.getGameState().getUser().hasCard(card))
                continue;
            if (doesOwn.equals("not owned") && GameState.getGameState().getUser().hasCard(card))
                continue;
            displayedCards.add(card);
        }
    }

    public ArrayList<Card> getDisplayedCards() {
        return displayedCards;
    }

    @Override
    public String toString() {
        return "Collections";
    }
}
