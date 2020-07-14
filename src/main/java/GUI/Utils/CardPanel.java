package GUI.Utils;

import Cards.Card;
import Cards.Minion;
import Cards.Weapon;
import Places.Playground;
import Utilities.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class CardPanel extends JPanel {
    private String backgroundImagePath;
    private Card card;
    private int width;
    private int height;
    private double scaleFactorX = 1;
    private double scaleFactorY = 1;

    public CardPanel(Card card, String backgroundImagePath) {
        this.setOpaque(false);
        this.backgroundImagePath = backgroundImagePath;
        this.card = card;
    }

    public CardPanel() {
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

    public void setCard(Card card) {
        this.card = card;
    }

    public void setDrawLocation(int x, int y, int width, int height) {
        this.width = width;
        this.height = height;
        int realWidth = (int) (scaleFactorX * width);
        this.setBounds(x - realWidth / 2, y, width, height);
    }

//    @Override
//    public void setLocation(Point point) {
//        int realWidth = (int) (scaleFactorX * width);
//        this.setBounds(point.x - realWidth / 2 - 40, point.y - 75, width, height);
////        System.out.println("HELLO");
////        super.setLocation(new Point(point.x - 50, point.y - 75));
//    }

    public Point getAdjustedLocation(Point point) {
        return new Point((int) (point.x - width * scaleFactorX / 2), point.y - 100);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.setColor(Color.RED);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        if (backgroundImagePath != null) {
            BufferedImage image = ImageLoader.getInstance().loadImage(backgroundImagePath);
            Image newImage = image.getScaledInstance((int) (image.getWidth() * scaleFactorX),
                    (int) (image.getHeight() * scaleFactorY), java.awt.Image.SCALE_SMOOTH);
            g2d.drawImage(newImage, 0, 0, null);
            if (card != null) {
                drawCardStatistics(g2d, (int) (image.getWidth() * scaleFactorX), (int) (image.getHeight() * scaleFactorY));
            }
        }
    }

    void drawCardStatistics(Graphics2D g2d, int x, int y) {
        drawCardMana(g2d, x, y);
        if (card instanceof Minion) {
            drawMinionStatistics(g2d, x, y);
        } else if (card instanceof Weapon) {
            drawWeaponStatistics(g2d, x, y);
        }
    }

    void drawCardMana(Graphics2D g2d, int x, int y) {
        g2d.drawString("" + card.getMana(), getProperLeftXPosition(x, card.getMana()), getProperUpYPosition(y));
    }

    void drawMinionStatistics(Graphics2D g2d, int x, int y) {
        g2d.drawString("" + ((Minion) card).getHp(),
                getProperRightXPosition(x, ((Minion) card).getHp()), getProperDownYPosition(y));
        g2d.drawString("" + ((Minion) card).getAttackPower(),
                getProperLeftXPosition(x, ((Minion) card).getAttackPower()), getProperDownYPosition(y));
    }

    void drawWeaponStatistics(Graphics2D g2d, int x, int y) {
        g2d.drawString("" + ((Weapon) card).getDurability(),
                getProperRightXPosition(x, ((Weapon) card).getDurability()), getProperDownYPosition(y));
        g2d.drawString("" + ((Weapon) card).getAttackPower(),
                getProperLeftXPosition(x, ((Weapon) card).getAttackPower()), getProperDownYPosition(y));
    }

    int getProperLeftXPosition(int x, int number) {
        if (number < 10) {
            return (int) (0.1 * x);
        }
        return (int) (0.05 * x);
    }

    int getProperRightXPosition(int x, int number) {
        if (number < 10) {
            return (int) (0.82 * x);
        }
        return (int) (0.77 * x);
    }

    int getProperDownYPosition(int y) {
        return (int) (0.95 * y);
    }

    int getProperUpYPosition(int y) {
        return (int) (0.2 * y);
    }
}
