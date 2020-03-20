package Places;

import CLI.RespondToUser;
import Cards.Card;
import Cards.CardCreator;
import Heros.Hero;
import UserHandle.User;
import Utilities.FileHandler;
import Utilities.TextProcessingTools;

import java.util.ArrayList;

public class Store extends Place {
    private static Store store = new Store();
    private Store(){
        validCommands = (ArrayList) FileHandler.readFileInList("Data/MainMenu Commands.txt");
    }

    public static Store getStore(){
        return store;
    }

    @Override
    public void defaultResponse() {
        RespondToUser.respond("You are in the store. Run a command.");
    }

    @Override
    public Place runCommand(String command, User user, Place currentPlace) {
        switch (command) {
            case "main menu": {
                return MainMenu.getMainMenu();
            }
            case "wallet": {
                RespondToUser.respond("You have " + user.getGold() + " golds.");
                return currentPlace;
            }
        }

        if (TextProcessingTools.stringFirstWordMatch(command, "sell")){
            String bracketedCardName = command.replaceFirst("sell\\s+", "");
            if (TextProcessingTools.isInBrackets(bracketedCardName)){
                String cardName = TextProcessingTools.unBracket(bracketedCardName);
                Card card = CardCreator.createCard(cardName);
                if (card == null){
                    RespondToUser.respond("There is no such card as " + cardName + ".");
                    return currentPlace;
                }
                if (user.hasCard(card)){
                    for (Hero hero :
                            user.getHeroes()) {
                        if (hero.hasCard(card)){
                            RespondToUser.respond("You have to remove " + cardName + " from the deck of all your heroes before selling it.");
                            return currentPlace;
                        }
                    }
                    user.setGold(user.getGold() + card.getPrice());
                    user.removeCard(card);
                    RespondToUser.respond("You have successfully sold " + cardName + ".");
                    return currentPlace;
                }
                RespondToUser.respond("You do not own " + cardName + " so you cannot sell it!");
                return currentPlace;
            }
            RespondToUser.respond("You have to enter your command in the form 'sell [card name]'");
            return currentPlace;
        }

        if (TextProcessingTools.stringFirstWordMatch(command, "buy")){
            String bracketedCardName = command.replaceFirst("buy\\s+", "");
            if (TextProcessingTools.isInBrackets(bracketedCardName)){
                String cardName = TextProcessingTools.unBracket(bracketedCardName);
                Card card = CardCreator.createCard(cardName);
                if (card == null){
                    RespondToUser.respond("There is no such card as " + cardName + ".");
                    return currentPlace;
                }
                if (user.hasCard(card)){
                    RespondToUser.respond("You already have this card!");
                    return currentPlace;
                }
                if (card.getPrice() > user.getGold()){
                    RespondToUser.respond("You don't have enough gold to buy " + cardName + ".");
                    return currentPlace;
                }
                user.setGold(user.getGold() - card.getPrice());
                user.addCard(card);
                RespondToUser.respond("You have successfully purchased " + cardName + ".");
                return currentPlace;
            }
            RespondToUser.respond("You have to enter your command in the form 'buy [card name]'");
            return currentPlace;
        }



        RespondToUser.respond("Invalid command!");
        return currentPlace;
    }
}
