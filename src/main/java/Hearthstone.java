import GameHandler.GameState;

import java.awt.*;
import java.io.IOException;

public class Hearthstone {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    GameState.getGameState().init();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

