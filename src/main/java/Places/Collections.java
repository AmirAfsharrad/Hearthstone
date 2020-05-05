package Places;

import Cards.Card;
import Cards.Deck;
import GUI.Events.ChooseFromListEvent;
import GUI.Events.GetTextEvent;
import GUI.Listeners.ChooseFromListListener;
import GUI.Listeners.GetTextListener;
import GameHandler.*;
import Heroes.Hero;
import Heroes.HeroCreator;
import Logger.Logger;
import UserHandle.User;
import Utilities.TextProcessingTools;

import java.util.ArrayList;

public class Collections extends Place {
    private static Collections collections = new Collections();
    private ArrayList<Card> displayedCards;

    private Collections() {
//        displayedCards =
        setInstructionsPath("Data/Collections Commands.json");
        loadInstructions();
        displayedCards = new ArrayList<>();
        filterDisplayedCards(-1, "all", "", "all");
    }

    public static Collections getCollections() {
        return collections;
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
    }

    public void removeCardFromDeck(Card card, Deck deck) {
        for (Card deckCard : deck.getCards()) {
            if (card.equals(deckCard)) {
                deck.getCards().remove(card);
                return;
            }
        }
    }

    public void removeDeck(Deck deck) {
        for (Deck deck1 : GameState.getGameState().getUser().getDecks()) {
            if (deck.equals(deck1)) {
                System.out.println(GameState.getGameState().getUser().getDecks().size());
                GameState.getGameState().getUser().getDecks().remove(deck1);
                System.out.println(GameState.getGameState().getUser().getDecks().size());
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

    @Override
    public void defaultResponse() {
        RespondToUser.respond("You are going through your collections. Run a command.");
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
            case "ls -a -heroes": {
                Logger.log(user, "list", "all heroes", true);
                RespondToUser.respond("Here is the list of your available heroes:", user);
                for (Hero hero :
                        user.getHeroes()) {
                    RespondToUser.respond(hero, user);
                }
                return currentPlace;
            }
            case "ls -m -heroes": {
                Logger.log(user, "list", "current hero", true);
                RespondToUser.respond("Your current hero is " + user.getCurrentHero(), user);
                return currentPlace;
            }
            case "ls -a -cards": {
                Logger.log(user, "list", "all cards", true);
                RespondToUser.respond("Here is the list of your cards:", user);
                for (Card card :
                        user.getCards()) {
                    if (card.getHeroClass().equals("Neutral") || card.getHeroClass().equals(user.getCurrentHero().getType())) {
                        RespondToUser.respond(card, user);
                    }
                }
                return currentPlace;
            }
            case "ls -m -cards": {
                Logger.log(user, "list", user.getCurrentHero() + ": current deck", true);
                RespondToUser.respond("Here is the list of the cards in your deck:", user);
//                for (Card card :
//                        user.getCurrentHero().getDeck()) {
//                    RespondToUser.respond(card, user);
//                }
                return currentPlace;
            }
            case "ls -n -cards": {
                Logger.log(user, "list", user.getCurrentHero() + ": cards not in deck", true);
                RespondToUser.respond("Here is the list of the cards you can add to your deck:", user);
                for (Card card :
                        user.getCards()) {
//                    if (!user.getCurrentHero().getDeck().contains(card)){
//                        if (card.getHeroClass().equals("Neutral") || card.getHeroClass().equals(user.getCurrentHero().getType())) {
//                            if (user.getCurrentHero().getHowManyOfThisCardInDeck(card) < user.getCurrentHero().getMaxRepetitiveCardsInDeck())
//                            RespondToUser.respond(card, user);
//                        }
//                    }
                }
                return currentPlace;
            }

        }

        if (TextProcessingTools.stringFirstWordMatch(lowerCaseCommand, "select")) {
            Logger.log(user, "select", "select a hero", true);
            String bracketedHeroName = command.replaceFirst("(?i)select\\s*", "");
            if (TextProcessingTools.isInBrackets(bracketedHeroName)) {
                String heroName = TextProcessingTools.unBracket(bracketedHeroName);
                for (Hero hero :
                        user.getHeroes()) {
                    if (hero.getType().equals(heroName)) {
                        user.setCurrentHero(hero.getType());
                        RespondToUser.respond("You have selected " + hero + " as your hero.", user);
                        return currentPlace;
                    }
                }
                RespondToUser.respond("There is no such hero as " + heroName + " among your unlocked heroes.", user);
                return currentPlace;
            }
            RespondToUser.respond("You have to enter your command in the form 'select [hero name]'", user);
            return currentPlace;
        }

        if (TextProcessingTools.stringFirstWordMatch(command, "add")) {
            Logger.log(user, "add", "add card for " + user.getCurrentHero(), true);
            String bracketedCardName = command.replaceFirst("(?i)add\\s*", "");
            if (TextProcessingTools.isInBrackets(bracketedCardName)) {
//                if (user.getCurrentHero().getNumberOfCardsInDeck() >= user.getCurrentHero().getDeckCapacity()){
//                    RespondToUser.respond("There is no more capacity to add cards to deck.", user);
//                    return currentPlace;
//                }
                String cardName = TextProcessingTools.unBracket(bracketedCardName);
                Card card = GameHandler.getGameHandler().getCard(cardName);
                if (card == null) {
                    RespondToUser.respond("There is no such card as " + cardName + ".", user);
                    return currentPlace;
                }
                if (user.hasCard(card)) {
                    if (card.getHeroClass().equals("Neutral") || card.getHeroClass().equals(user.getCurrentHero().getType())) {
//                        if (user.getCurrentHero().getHowManyOfThisCardInDeck(card) >= user.getCurrentHero().getMaxRepetitiveCardsInDeck()){
//                            RespondToUser.respond("You already have " + user.getCurrentHero().getMaxRepetitiveCardsInDeck() + " of " + card + " in your deck. You cannot have more!", user);
//                            return currentPlace;
//                        }
//                        user.getCurrentHero().addToDeck(card);
                        RespondToUser.respond("Card " + cardName + " successfully added to your deck.", user);
                        return currentPlace;
                    }
                    RespondToUser.respond("You cannot use this card with " + user.getCurrentHero(), user);
                    return currentPlace;
                }
                RespondToUser.respond("You don't own " + cardName + " so you cannot add it to your deck.", user);
                return currentPlace;
            }
            RespondToUser.respond("You have to enter your command in the form 'add [card name]'", user);
            return currentPlace;
        }

        if (TextProcessingTools.stringFirstWordMatch(command, "remove")) {
            Logger.log(user, "remove", "remove card for " + user.getCurrentHero(), true);
            String bracketedHeroName = command.replaceFirst("(?i)remove\\s*", "");
            if (TextProcessingTools.isInBrackets(bracketedHeroName)) {
                String cardName = TextProcessingTools.unBracket(bracketedHeroName);
//                if (user.getCurrentHero().getDeckAsArrayOfString().contains(cardName)) {
//                    Card card = GameHandler.getGameHandler().getCard(cardName);
//                    user.getCurrentHero().removeFromDeck(card);
//                    RespondToUser.respond(cardName + " removed successfully from your deck", user);
//                } else {
//                    RespondToUser.respond("There is no card " + cardName + " in your deck.", user);
//                }
            } else {
                RespondToUser.respond("You have to enter your command in the form 'remove [card name]'", user);
            }
            return currentPlace;
        }
        RespondToUser.respond("Invalid command!", user);
        return currentPlace;
    }
}
