import GameHandler.CLI.CLI;
import Initialization.PlacesDataCreator;

public class Hearthstone {
    public static void main(String[] args) {

        //PlacesDataCreator.initAllPlaces();

        CLI cli = CLI.getGetCLI();
        cli.run();

    }

}
