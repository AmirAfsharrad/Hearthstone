package GUI.Utils;

import Places.Playground;
import Utilities.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BackgroundedPanel extends JPanel {
    private String backgroundImagePath;
    private double scaleFactorX = 1;
    private double scaleFactorY = 1;

    public BackgroundedPanel(String backgroundImagePath) {
        this.setOpaque(false);
        this.backgroundImagePath = backgroundImagePath;
    }

    public BackgroundedPanel() {
        this.setOpaque(false);
    }

    public void setScaleFactor(double scaleFactorX, double scaleFactorY) {
        this.scaleFactorX = scaleFactorX;
        this.scaleFactorY = scaleFactorY;
    }

    public void setScaleFactor(double scaleFactor) {
        this.scaleFactorX = scaleFactor;
        this.scaleFactorY = scaleFactor;
    }

    public void setBackgroundImagePath(String backgroundImagePath) {
        this.backgroundImagePath = backgroundImagePath;
    }

    public void setDrawLocation(int x, int y, int width, int height) {
        int realWidth = (int) (scaleFactorX * width);
        this.setBounds(x - realWidth / 2, y, width, height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        if (backgroundImagePath != null) {
            BufferedImage image = ImageLoader.getInstance().loadImage(backgroundImagePath);
            Image newImage = image.getScaledInstance((int) (image.getWidth() * scaleFactorX),
                    (int) (image.getHeight() * scaleFactorY), java.awt.Image.SCALE_SMOOTH);
            g2d.drawImage(newImage, 0, 0, null);
        }
//        double rotationRequired = Math.toRadians (45);
//        double locationX = image.getWidth() / 2;
//        double locationY = image.getHeight() / 2;
//        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
//        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
//        g2d.drawImage(op.filter(image, null), 0, 0, null);
    }
}
