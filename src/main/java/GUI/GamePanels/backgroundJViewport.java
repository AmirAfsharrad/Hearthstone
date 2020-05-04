package GUI.GamePanels;

import Utilities.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class backgroundJViewport extends JViewport {
    String backgroundPath;

    public backgroundJViewport(String backgroundPath) {
        this.backgroundPath = backgroundPath;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        BufferedImage image = ImageLoader.getInstance().loadImage(backgroundPath);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
}
