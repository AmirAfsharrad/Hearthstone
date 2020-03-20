package Places;

import CLI.RespondToUser;
import Cards.Card;
import Cards.CardCreator;
import Heros.Hero;
import UserHandle.User;
import Utilities.FileHandler;
import Utilities.TextProcessingTools;

import java.util.ArrayList;

public class Collections extends Place {
    private static Collections collections = new Collections();
    private Collections(){
        validCommands = (ArrayList) FileHandler.readFileInList("Data/Collections Commands.txt");
    }

    public static Collections getCollections(){
        return collections;
    }

    @Override
    public void defaultResponse() {
        RespondToUser.respond("You are going through your collections. Run a command.");
    }

    @Override
    public Place runCommand(String command, User user, Place currentPlace) {
        switch (command) {
            case "main menu":{
                return MainMenu.getMainMenu();
            }
            case "ls -a -heroes":{
                RespondToUser.respond("Here is the list of your available heroes:");
                for (Hero hero :
                        user.getHeroes()) {
                    RespondToUser.respond(hero);
                }
                return currentPlace;
            }
            case "ls -m -heroes":{
                RespondToUser.respond("Your current hero is " + user.getCurrentHero());
                return currentPlace;
            }
            case "ls -a -cards": {
                RespondToUser.respond("Here is the list of your cards:");
                for (Card card :
                        user.getCards()) {
                    if (card.getHeroClass().equals("Neutral") || card.getHeroClass().equals(user.getCurrentHero().getType())) {
                        RespondToUser.respond(card);
                    }
                }
                return currentPlace;
            }
            case "ls -m -cards":{
                RespondToUser.respond("Here is the list of the cards in your deck:");
                for (Card card :
                        user.getCurrentHero().getDeck()) {
                    RespondToUser.respond(card);
                }
                return currentPlace;
            }
            case "ls -n -cards":{
                RespondToUser.respond("Here is the list of the cards you can add to your deck:");
                for (Card card :
                        user.getCards()) {
                    if (!user.getCurrentHero().getDeck().contains(card)){
                        if (card.getHeroClass().equals("Neutral") || card.getHeroClass().equals(user.getCurrentHero().getType())) {
                            RespondToUser.respond(card);
                        }
                    }
                }
                return currentPlace;
            }

        }

        if (TextProcessingTools.stringFirstWordMatch(command, "select")){
            String bracketedHeroName = command.replaceFirst("select\\s+", "");
            if (TextProcessingTools.isInBrackets(bracketedHeroName)){
                String heroName = TextProcessingTools.unBracket(bracketedHeroName);
                for (Hero hero :
                        user.getHeroes()) {
                    if (hero.getType().equals(heroName)){
                        user.setCurrentHero(hero.getType());
                        RespondToUser.respond("You have selected " + hero + " as your hero.");
                        return currentPlace;
                    }
                }
                RespondToUser.respond("There is no such hero as " + heroName + " among your unlocked heroes.");
                return currentPlace;
            }
            RespondToUser.respond("You have to enter your command in the form 'select [hero name]'");
            return currentPlace;
        }

        if (TextProcessingTools.stringFirstWordMatch(command, "add")) {
            String bracketedCardName = command.replaceFirst("add\\s+", "");
            if (TextProcessingTools.isInBrackets(bracketedCardName)) {
                String cardName = TextProcessingTools.unBracket(bracketedCardName);
                if (user.getCardsAsArrayOfString().contains(cardName)) {
                    Card card = CardCreator.createCard(cardName);
                    if (card.getHeroClass().equals("Neutral") || card.getHeroClass().equals(user.getCurrentHero().getType())) {
                        user.getCurrentHero().addToDeck(card);
                        RespondToUser.respond("Card " + cardName + " successfully added to your deck.");
                    } else {
                        RespondToUser.respond("You cannot use this card with " + user.getCurrentHero());
                    }
                } else {
                    RespondToUser.respond("You don't own a card named " + cardName);
                }
            } else {
                RespondToUser.respond("You have to enter your command in the form 'add [card name]'");
            }
            return currentPlace;
        }

        if (TextProcessingTools.stringFirstWordMatch(command, "remove")) {
            String bracketedHeroName = command.replaceFirst("remove\\s+", "");
            if (TextProcessingTools.isInBrackets(bracketedHeroName)) {
                String cardName = TextProcessingTools.unBracket(bracketedHeroName);
                if (user.getCurrentHero().getDeckAsArrayOfString().contains(cardName)) {
                    Card card = CardCreator.createCard(cardName);
                    user.getCurrentHero().removeFromDeck(card);
                    RespondToUser.respond(cardName + " removed successfully from your deck");
                } else {
                    RespondToUser.respond("There is no card " + cardName + " in your deck.");
                }
            } else {
                RespondToUser.respond("You have to enter your command in the form 'remove [card name]'");
            }
            return currentPlace;
        }
        RespondToUser.respond("Invalid command!");
        return currentPlace;
    }
}
