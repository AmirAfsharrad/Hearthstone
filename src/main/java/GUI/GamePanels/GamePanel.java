package GUI.GamePanels;

import javax.swing.*;
import java.awt.*;

public abstract class GamePanel extends JPanel {
    int screenWidth;
    int screenHeight;

    public GamePanel(int screenWidth, int screenHeight) {
        this.setLayout(null);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }
}
