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
import com.google.gson.*;
import org.json.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Hearthstone {
    public static void main(String[] args) {





        //CLI cli = CLI.getGetCLI();
        //cli.run();

        //CardsDataCreator.cardsDataCreate();
        //Card card = CardCreator.createCard("Skull of the Man\u0027ari");
        //System.out.println(card);
/*
        MainMenu mainMenu = new MainMenu();

        System.out.println(mainMenu.getValidCommands());

 */



        //PlacesDataCreator.initAllPlaces();
/*
        User user = new User("amir0", "ehsan1234");
        ArrayList<Card> cards = new ArrayList<>();
        ArrayList<Card> cards1 = new ArrayList<>();
        ArrayList<Card> cards2 = new ArrayList<>();
        ArrayList<Hero> heroes = new ArrayList<>();

        cards.add(CardCreator.createCard("Holy Fire"));
        cards.add(CardCreator.createCard("Sap"));
        cards.add(CardCreator.createCard("Worgen Infiltrator"));

        cards1.add(CardCreator.createCard("Holy Fire"));
        cards1.add(CardCreator.createCard("Sap"));

        cards2.add(CardCreator.createCard("Holy Fire"));
        cards2.add(CardCreator.createCard("Worgen Infiltrator"));

        Mage mage = new Mage(10, cards1);
        Rogue rogue = new Rogue(20, cards2);

        heroes.add(mage);
        heroes.add(rogue);

        user.setCards(cards);
        user.setCurrentHero(mage);
        user.setGold(50);
        user.setHeroes(heroes);


        UserDataHandler.add(user);

 */


        User user1 = UserDataHandler.load("amir0", "ehsan1234");
        System.out.println("Sp:   " + user1.getHeroes().get(1).getHp());
        System.out.println(user1.getHeroes().get(1).getDeck());

        /*
        user1.addCard("Flamestike");
        user1.addCard("Stonetusk Boar");
        user1.addCard("Sap");
        UserDataHandler.save(user1);

         */



        /*
        User user2 = UserDataHandler.load("Ehsan", "ehsan1234");
        user2.setGold(85);
        UserDataHandler.save(user2);
         */

        //userDataHandler.remove("Ehsan", "ehsan1234");


    }

}
