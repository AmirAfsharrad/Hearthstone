import CLI.CLI;
import Cards.Card;
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

        CardsDataCreator.cardsDataCreate();
/*
        MainMenu mainMenu = new MainMenu();

        System.out.println(mainMenu.getValidCommands());

 */
/*


        UserDataHandler userDataHandler = UserDataHandler.getUserHandler();

        PlacesDataCreator.initAllPlaces();

        User user = new User("Ali", "ali1234");
        ArrayList<Card> cards = new ArrayList<>();
        ArrayList<Hero> heroes = new ArrayList<>();
        Mage mage = new Mage();
        Warlock warlock = new Warlock();
        Rogue rogue = new Rogue();
        heroes.add(mage);
        heroes.add(warlock);
        heroes.add(rogue);
        user.setCards(cards);
        user.setCurrentHero(mage);
        user.setGold(50);
        user.setHeroes(heroes);

        userDataHandler.add(user);


        User user1 = userDataHandler.load("Taha", "taha1234");
        System.out.println(user1);

        User user2 = userDataHandler.load("Ehsan", "ehsan1234");
        user2.setGold(85);
        userDataHandler.save(user2);



*/



        //userDataHandler.remove("Ehsan", "ehsan1234");


    }

}
