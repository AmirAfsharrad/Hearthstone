import Cards.Card;
import Heros.Hero;
import Heros.Mage;
import UserHandle.User;
import UserHandle.UserDataHandler;
import org.json.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Hearthstone {
    public static void main(String[] args) {
        UserDataHandler userDataHandler = UserDataHandler.getUserHandler();

        User user = new User("Ehsan", "ehsan1234");
        ArrayList<Card> cards = new ArrayList<>();
        ArrayList<Hero> heroes = new ArrayList<>();
        Mage mage = new Mage();
        heroes.add(mage);
        //heroes.add(mage);
        user.setCards(cards);
        user.setCurrentHero(mage);
        user.setGold(50);
        user.setHeroes(heroes);

        userDataHandler.add(user);


        User user1 = userDataHandler.load("Ehsan", "ehsan1234");
        System.out.println(user1);



    }

}
