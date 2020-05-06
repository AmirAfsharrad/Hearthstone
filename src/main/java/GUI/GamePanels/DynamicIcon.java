package GUI.GamePanels;

import javax.swing.*;
import java.awt.*;

public class DynamicIcon extends ImageIcon {
    Font font = new Font("Wide Latin", Font.BOLD, 14);
    private final static int DEFAULT_SIZE = 16;
    private int width = DEFAULT_SIZE;
    private int height = DEFAULT_SIZE;

    private String iconText;

    public DynamicIcon() {
    }


    @Override
    public int getIconHeight() {
        return height;
    }

    @Override
    public int getIconWidth() {
        return width;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(font);

//
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawImage(this.getImage(), 0, 0, null);
    }
}

