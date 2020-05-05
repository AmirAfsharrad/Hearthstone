//Imports are listed in full to show what's being used
//could just import javax.swing.* and java.awt.* etc..
import GameHandler.GameState;

import java.awt.*;
import java.io.IOException;

public class Hearthstone {
    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
//                new Hearthstone();
//                MainFrame.getMainFrame();
//                new MainFrame();
                try {
                    GameState.getGameState().init();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    }
//}
