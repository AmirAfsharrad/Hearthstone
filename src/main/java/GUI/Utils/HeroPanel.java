package GUI.Utils;

import Cards.Card;
import Cards.Minion;
import Cards.Weapon;
import Heroes.Hero;
import Places.Playground;
import Utilities.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class HeroPanel extends JPanel {
    private String backgroundImagePath;
    private Hero hero;
    private int width;
    private int height;
    private double scaleFactorX = 1;
    private double scaleFactorY = 1;

//    public CardPanel(Card card, String backgroundImagePath) {
//        this.setOpaque(false);
//        this.backgroundImagePath = backgroundImagePath;
//        this.card = card;
//    }

    public HeroPanel(Hero hero) {
        this.setOpaque(false);
        this.backgroundImagePath = "heroes/" + hero.getType() + ".png";
        this.hero = hero;
    }

    public HeroPanel() {
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

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public void setDrawLocation(int x, int y, int width, int height) {
        this.width = width;
        this.height = height;
        int realWidth = (int) (scaleFactorX * width);
        this.setBounds(x - realWidth / 2, y, width, height);
    }

    public Point getAdjustedLocation(Point point) {
        return new Point((int) (point.x - width * scaleFactorX / 2), point.y - 100);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(new Font("Arial", Font.BOLD, 30));
        g2d.setColor(Color.GREEN);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        if (backgroundImagePath != null) {
            BufferedImage image = ImageLoader.getInstance().loadImage(backgroundImagePath);
            Image newImage = image.getScaledInstance((int) (image.getWidth() * scaleFactorX),
                    (int) (image.getHeight() * scaleFactorY), java.awt.Image.SCALE_SMOOTH);
            g2d.drawImage(newImage, 0, 0, null);
            if (hero != null) {
                drawHeroHP(g2d, (int) (image.getWidth() * scaleFactorX), (int) (image.getHeight() * scaleFactorY));
            }
        }
    }

    void drawHeroHP(Graphics2D g2d, int x, int y) {
        g2d.drawString("" + hero.getHp(), getProperRightXPosition(x, hero.getHp()), getProperDownYPosition(y));
    }

    int getProperRightXPosition(int x, int number) {
        if (number < 10) {
            return (int) (0.81 * x);
        }
        return (int) (0.77 * x);
    }

    int getProperDownYPosition(int y) {
        return (int) (0.72 * y);
    }

    int getProperUpYPosition(int y) {
        return (int) (0.2 * y);
    }

    public Hero getHero() {
        return hero;
    }
}
