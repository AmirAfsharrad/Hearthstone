package Places;

import CLI.RespondToUser;
import Cards.Card;
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

    }

    @Override
    public Place runCommand(String command, User user, Place currentPlace) {
        switch (command) {
            case "main menu":{
                return MainMenu.getMainMenu();
            }
            case "ls -a - heroes":{
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
            case "ls -a - cards":{
                RespondToUser.respond("Here is the list of your cards:");
                for (Card card :
                        user.getCards()) {
                    RespondToUser.respond(card);
                }
                return currentPlace;
            }
            case "ls -m - cards":{
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
                        RespondToUser.respond(card);
                    }
                }
                return currentPlace;
            }

        }

        if (TextProcessingTools.stringFirstWordMatch(command, "select")){
            String bracketedHeroName = command.replaceFirst("select ", "");
            if (TextProcessingTools.isInBrackets(bracketedHeroName)){
                String heroName = TextProcessingTools.unBracket(bracketedHeroName);
                for (Hero hero :
                        user.getHeroes()) {
                    if (hero.getClass().equals(heroName)){
                        user.setCurrentHero(hero);
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

        if (TextProcessingTools.stringFirstWordMatch(command, "select")){
            String bracketedHeroName = command.replaceFirst("select ", "");
            if (TextProcessingTools.isInBrackets(bracketedHeroName)){
                String heroName = TextProcessingTools.unBracket(bracketedHeroName);
                for (Hero hero :
                        user.getHeroes()) {
                    if (hero.getClass().equals(heroName)){
                        user.setCurrentHero(hero);
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



        return currentPlace;
    }
}
