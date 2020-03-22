import GameHandler.CLI.CLI;
import GameHandler.GameHandler;
import Initialization.CardsDataCreator;
import Initialization.DefaultUserCardsDataCreator;
import Initialization.PlacesDataCreator;

public class Hearthstone {
    public static void main(String[] args) {

        /*
        PlacesDataCreator.initAllPlaces();
        CardsDataCreator.createCardsData();
        DefaultUserCardsDataCreator.createDefaultUserCardsData();
        */

        GameHandler.getGameHandler().runGame();


    }

}
