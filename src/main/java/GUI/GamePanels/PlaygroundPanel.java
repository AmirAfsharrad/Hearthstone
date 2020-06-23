package GUI.GamePanels;

import Cards.Card;
import GUI.Events.*;
import GUI.Listeners.*;
import GUI.Utils.BackgroundedPanel;
import GameHandler.GameHandler;
import Logger.Logger;
import Places.MainMenu;
import Places.Playground;
import Utilities.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class PlaygroundPanel extends GamePanel {
    private int cardCanGetLargeOwner = -1;
    private BackgroundedPanel[] handPanels;
    private BackgroundedPanel[] largerHandPanels;
    private BackgroundedPanel[] playCardsOdd;
    private BackgroundedPanel[] playCardsEven;
    private JButton backToMainMenuButton;
    private JButton exitButton;
    private BackgroundedPanel[] manas;
    private BackgroundedPanel[] lostManas;
    private ExitListener exitListener;
    private EndTurnListener endTurnListener;
    private ChangePlaceListener changePlaceListener;
    private PlayCardListener playCardEventListener;

    public PlaygroundPanel(int screenWidth, int screenHeight) {
        super(screenWidth, screenHeight);
        draw();
    }

    private void draw() {
        initReturnButtons();
        drawGameLog();
        showNumberOfDeckCards();
        drawHandPanels();
        initHeroPanel();
        initPlayCardsOdd();
        initPlayCardsEven();
        initManasPanel();
        initEndTrunButton();
        drawManasPanel();
        drawPlantedCards();
    }

    void initEndTrunButton() {
        JButton endTurnButton = new JButton("");
        endTurnButton.setContentAreaFilled(false);
        endTurnButton.setBorder(BorderFactory.createEmptyBorder());
        endTurnButton.setBounds((int) (0.85 * screenWidth),
                (int) (0.43 * screenHeight), 140, 50);
        endTurnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Logger.buttonPressLog("end turn");
                EndTurnEvent endTurnEvent = new EndTurnEvent(this);
                if (endTurnListener != null) {
                    endTurnListener.endTurnOccurred(endTurnEvent);
                }
                clear();
                draw();
                revalidate();
                repaint();
            }
        });
        this.add(endTurnButton);
    }

    void initManasPanel() {
        manas = new BackgroundedPanel[10];
        lostManas = new BackgroundedPanel[10];
        for (int i = 0; i < 10; i++) {
            manas[i] = new BackgroundedPanel(("utils/mana.png"));
            manas[i].setScaleFactor(0.28);
            manas[i].setDrawLocation((int) ((0.755 + 0.02 * i) * screenWidth),
                    (int) (0.915 * screenHeight), 200, 300);

            lostManas[i] = new BackgroundedPanel(("utils/mana grayscale.png"));
            lostManas[i].setScaleFactor(0.28);
            lostManas[i].setDrawLocation((int) ((0.755 + 0.02 * i) * screenWidth),
                    (int) (0.915 * screenHeight), 200, 300);

            manas[i].setVisible(false);
            lostManas[i].setVisible(false);

            this.add(lostManas[i]);
            this.add(manas[i]);
        }
    }

    private void drawManasPanel() {
        int currentManas = Playground.getPlayground().getMana();
        int fullManas = Playground.getPlayground().getTurnFullManas();
        for (int i = 0; i < currentManas; i++) {
            manas[i].setVisible(true);
        }
        for (int i = currentManas; i < fullManas; i++) {
            lostManas[i].setVisible(true);
        }
    }

    private void initPlayCardsOdd() {
        playCardsOdd = new BackgroundedPanel[7];
        for (int i = 0; i < 7; i++) {
            playCardsOdd[i] = new BackgroundedPanel();
            playCardsOdd[i].setScaleFactor(0.4);
            playCardsOdd[i].setDrawLocation((int) ((0.5 + 0.09 * Math.pow(-1, i) * ((i + 1) / 2)) * screenWidth),
                    (int) (0.46 * screenHeight), 200, 300);
            this.add(playCardsOdd[i]);
        }
    }

    void initPlayCardsEven() {
        playCardsEven = new BackgroundedPanel[6];
        for (int i = 0; i < 6; i++) {
            playCardsEven[i] = new BackgroundedPanel();
            playCardsEven[i].setScaleFactor(0.4);
            playCardsEven[i].setDrawLocation((int) ((0.545 + 0.09 * Math.pow(-1, i) * ((i + 1) / 2)) * screenWidth),
                    (int) (0.46 * screenHeight), 200, 300);
            this.add(playCardsEven[i]);
        }
    }

    void drawPlantedCards() {
        ArrayList<Card> cards = Playground.getPlayground().getPlanted();
        if (cards.size() % 2 == 0) {
            for (int i = 0; i < cards.size(); i++) {
                playCardsEven[i].setBackgroundImagePath("cards/" + cards.get(i).getName() + ".png");
                playCardsEven[i].setVisible(true);
            }
        } else {
            for (int i = 0; i < cards.size(); i++) {
                playCardsOdd[i].setBackgroundImagePath("cards/" + cards.get(i).getName() + ".png");
                playCardsOdd[i].setVisible(true);
            }
        }
    }

    private void drawHandPanels() {
        ArrayList<Card> cards = Playground.getPlayground().getHand();
        handPanels = new BackgroundedPanel[cards.size()];
        largerHandPanels = new BackgroundedPanel[cards.size()];

        int count = 0;
        for (Card card : cards) {
            drawHandLargerCard(card.getName(), cards.size() - count - 1, cards.size());
            count++;
        }

        count = 0;
        for (Card card : cards) {
            drawHandCard(card.getName(), cards.size() - count - 1, cards.size());
            count++;
        }
    }

    private void showNumberOfDeckCards() {
        JPanel numberOfDeckCards = new JPanel();
        numberOfDeckCards.setBounds((int) (0.92 * screenWidth), (int) (0.575 * screenHeight), 30, 30);
        JLabel label = new JLabel(String.valueOf(Playground.getPlayground().getRemainingDeckSize()));
        label.setFont(new Font("Arial", Font.BOLD, 15));
        label.setForeground(Color.WHITE);
        numberOfDeckCards.add(label);
        numberOfDeckCards.setOpaque(false);
        numberOfDeckCards.setForeground(Color.WHITE);
        this.add(numberOfDeckCards);
    }

    private void drawGameLog() {
        JPanel outer = new JPanel();
        JPanel gameLogContainer = new JPanel(new BorderLayout());
        JPanel gameLog = new JPanel(new GridLayout(0, 1, 0, 5));
        for (String log : Playground.getPlayground().getGameLog()) {
            gameLog.add(new JLabel(log));
        }


//        gameLogContainer.setBounds(5, 5, 80, 300);
        gameLogContainer.add(gameLog, BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(gameLogContainer);
        scrollPane.getViewport().setPreferredSize(new Dimension(70, 100));
        outer.setBounds(5, (int) (0.4 * screenHeight), 90, 120);
        outer.add(scrollPane);
        this.add(outer);
    }


    private void drawHandCard(String cardName, int index, int totalCapacity) {
        handPanels[index] = new BackgroundedPanel("cards/" + cardName + ".png");
        handPanels[index].setScaleFactor(0.35);
        if (totalCapacity == 1) {
            handPanels[index].setDrawLocation((int) (0.5 * screenWidth),
                    (int) (0.85 * screenHeight), 100, 200);
        } else {
            handPanels[index].setDrawLocation((int) ((0.35 + 0.25 * index / (totalCapacity - 1)) * screenWidth),
                    (int) (0.85 * screenHeight), 100, 200);
        }

        handPanels[index].addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                Logger.buttonPressLog(handPanels[index], cardName);
                PlayCardEvent playCardEvent = new PlayCardEvent(this, GameHandler.getGameHandler().getCard(cardName));
                if (playCardEventListener != null) {
                    playCardEventListener.PlayCardOccurred(playCardEvent);
                }
                clear();
                draw();
                revalidate();
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                if (cardCanGetLargeOwner == -1) {
                    cardCanGetLargeOwner = index;
                    handPanels[index].setVisible(false);
                    largerHandPanels[index].setVisible(true);
                    revalidate();
                    repaint();
                }
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                if (cardCanGetLargeOwner == index) {
                    cardCanGetLargeOwner = -1;
                    handPanels[index].setVisible(true);
                    largerHandPanels[index].setVisible(false);
                    revalidate();
                    repaint();
                }
            }
        });
        this.add(handPanels[index]);
    }

    private void drawHandLargerCard(String cardName, int index, int totalCapacity) {
        largerHandPanels[index] = new BackgroundedPanel("cards/" + cardName + ".png");
        largerHandPanels[index].setScaleFactor(0.45);
        if (totalCapacity == 1) {
            largerHandPanels[index].setDrawLocation((int) (0.5 * screenWidth),
                    (int) (0.8 * screenHeight), 120, 200);
        } else {
            largerHandPanels[index].setDrawLocation((int) ((0.35 + 0.25 * index / (totalCapacity - 1)) * screenWidth),
                    (int) (0.8 * screenHeight), 120, 200);
        }

        largerHandPanels[index].setVisible(false);
        this.add(largerHandPanels[index]);
    }


    private void initHeroPanel() {
        BackgroundedPanel heroPanel = new BackgroundedPanel("heroes/" + Playground.getPlayground().getHero() + ".png");
        heroPanel.setScaleFactor(0.75, 0.6);
        heroPanel.setDrawLocation((int) (0.491 * screenWidth), (int) (0.67 * screenHeight), 210, 210);
        this.add(heroPanel);

        BackgroundedPanel heroPowerPanel = new BackgroundedPanel("hero powers/" +
                Playground.getPlayground().getHero().getHeroPower() + ".png");
        heroPowerPanel.setScaleFactor(0.25, 0.25);
        heroPowerPanel.setDrawLocation((int) (0.6 * screenWidth), (int) (0.7 * screenHeight), 210, 210);
        this.add(heroPowerPanel);
    }

    private void initReturnButtons() {
        initExitButton();
        initBackToMainMenuButton();
    }

    private void initExitButton() {
        exitButton = new JButton("Exit");
        exitButton.setBounds(10, (int) (0.91 * screenHeight), 110, 30);
        this.add(exitButton);

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Logger.buttonPressLog(exitButton);
                ExitEvent exitEvent = new ExitEvent(this);
                if (exitListener != null) {
                    exitListener.exitEventOccurred(exitEvent);
                }
            }
        });
    }

    private void initBackToMainMenuButton() {
        backToMainMenuButton = new JButton("Main Menu");
        backToMainMenuButton.setBounds(10, (int) (0.87 * screenHeight), 110, 30);
        this.add(backToMainMenuButton);

        backToMainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Logger.buttonPressLog(backToMainMenuButton);
                ChangePlaceEvent changePlaceEvent = new ChangePlaceEvent(this, MainMenu.getMainMenu());
                if (changePlaceListener != null) {
                    try {
                        changePlaceListener.ChangePlaceOccurred(changePlaceEvent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        BufferedImage image = ImageLoader.getInstance().loadImage("game background2.jpg");
        g2d.drawImage(image, 0, 0, null);
    }

    private void clear() {
        cardCanGetLargeOwner = -1;
        this.removeAll();
    }

    public void setEndTurnListener(EndTurnListener endTurnListener) {
        this.endTurnListener = endTurnListener;
    }

    public void setExitListener(ExitListener exitListener) {
        this.exitListener = exitListener;
    }

    public void setChangePlaceListener(ChangePlaceListener changePlaceListener) {
        this.changePlaceListener = changePlaceListener;
    }

    public void setPlayCardEventListener(PlayCardListener playCardEventListener) {
        this.playCardEventListener = playCardEventListener;
    }
}
