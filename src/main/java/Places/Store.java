package Places;

import Cards.Card;
import Cards.CardCreator;
import GameHandler.CLI.RespondToUser;
import GameHandler.GameHandler;
import Heros.Hero;
import UserHandle.User;
import Utilities.TextProcessingTools;

public class Store extends Place {
    private static Store store = new Store();
    private Store(){
        setInstructionsPath("Data/Store Commands.json");
        loadInstructions();
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
            case "hearthstone --help":{
                for (String commandName :
                        getInstructions().keySet()) {
                    RespondToUser.respond(commandName + ": " + getInstructions().get(commandName));
                }
                return currentPlace;
            }
            case "main menu": {
                return MainMenu.getMainMenu();
            }
            case "wallet": {
                RespondToUser.respond("You have " + user.getGold() + " golds.");
                return currentPlace;
            }
            case "ls -s":{
                RespondToUser.respond("Here is the list of your cards, which could be sold:");
                for (Card card :
                        user.getCards()) {
                    if (user.isForSale(card)){
                        RespondToUser.respond(card);
                    }
                }
                return currentPlace;
            }
            case "ls -b":{
                RespondToUser.respond("Here is the list of available cards in the store for you:");
                for (String cardName :
                        GameHandler.getGameHandler().getAllCardNames()) {
                    if (!user.hasCard(cardName)){
                        RespondToUser.respond(cardName);
                    }
                }
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
