package GUI.GamePanels;

import Utilities.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BackgroundedPanel extends JPanel {
    private String backgroundImagePath;

    public BackgroundedPanel(String backgroundImagePath) {
        this.backgroundImagePath = backgroundImagePath;
    }

    public void setBackgroundImagePath(String backgroundImagePath) {
        this.backgroundImagePath = backgroundImagePath;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        BufferedImage image = ImageLoader.getInstance().loadImage(backgroundImagePath);
        g2d.drawImage(image, 0, 0, null);
    }
}
