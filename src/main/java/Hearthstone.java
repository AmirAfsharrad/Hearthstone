import CLI.CLI;
import Cards.Card;
import Cards.CardCreator;
import Heros.Hero;
import Heros.Mage;
import Heros.Rogue;
import Heros.Warlock;
import Initialization.CardsDataCreator;
import Initialization.PlacesDataCreator;
import Places.MainMenu;
import UserHandle.User;
import UserHandle.UserDataHandler;
import org.json.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Hearthstone {
    public static void main(String[] args) {
        //PrettyJson.prettyJson("Data/users.json");
        //PlacesDataCreator.initAllPlaces();

        //CLI cli = CLI.getGetCLI();
        //cli.run();

        //CardsDataCreator.cardsDataCreate();
        //Card card = CardCreator.createCard("Skull of the Man\u0027ari");
        //System.out.println(card);
/*
        MainMenu mainMenu = new MainMenu();

        System.out.println(mainMenu.getValidCommands());

 */



        PlacesDataCreator.initAllPlaces();

        User user = new User("Ehsan", "ehsan1234");
        ArrayList<Card> cards = new ArrayList<>();
        ArrayList<Hero> heroes = new ArrayList<>();
        Mage mage = new Mage();
        Warlock warlock = new Warlock();
        Rogue rogue = new Rogue();
        heroes.add(warlock);
        heroes.add(rogue);
        user.setCards(cards);
        user.setCurrentHero(mage);
        user.setGold(50);
        user.setHeroes(heroes);


        UserDataHandler.add(user);


        User user1 = UserDataHandler.load("Ehsan", "ehsan1234");
        System.out.println(user1);
        user1.addCard("Flamestike");
        user1.addCard("Stonetusk Boar");
        user1.addCard("Sap");
        UserDataHandler.save(user1);

        /*
        User user2 = UserDataHandler.load("Ehsan", "ehsan1234");
        user2.setGold(85);
        UserDataHandler.save(user2);

         */



        //userDataHandler.remove("Ehsan", "ehsan1234");


    }

}
