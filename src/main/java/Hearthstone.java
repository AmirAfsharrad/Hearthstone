//Imports are listed in full to show what's being used
//could just import javax.swing.* and java.awt.* etc..
import GUI.MainFrame;
import GameHandler.GameState;

import java.awt.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class Hearthstone {
    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
//                new Hearthstone();
//                MainFrame.getMainFrame();
//                new MainFrame();
                GameState.getGameState().init();
            }
        });
    }

    }
//}
