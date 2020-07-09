package GUI.GamePanels;

import Cards.Card;
import GUI.Constants.LowerHalfConstants;
import GUI.Constants.PlaygroundConstants;
import GUI.Constants.UpperHalfConstants;
import GUI.Events.*;
import GUI.Listeners.*;
import GUI.Utils.BackgroundedPanel;
import GUI.Utils.CardPanel;
import Logger.Logger;
import Places.MainMenu;
import Places.Playground;
import UserHandle.Contestant;
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
    private CardPanel[] playCardsOddUp;
    private CardPanel[] playCardsEvenUp;
    private CardPanel[] playCardsOddDown;
    private CardPanel[] playCardsEvenDown;
    private BackgroundedPanel[] manasUp;
    private BackgroundedPanel[] lostManasUp;
    private BackgroundedPanel[] manasDown;
    private BackgroundedPanel[] lostManasDown;
    private JButton backToMainMenuButton;
    private JButton exitButton;
    private ExitListener exitListener;
    private EndTurnListener endTurnListener;
    private ChangePlaceListener changePlaceListener;
    private PlayCardListener playCardEventListener;
    private PlaygroundConstants constants;
    private LowerHalfConstants lowerHalfConstants;
    private UpperHalfConstants upperHalfConstants;

    public PlaygroundPanel(int screenWidth, int screenHeight) {
        super(screenWidth, screenHeight);
        constants = new PlaygroundConstants();
        lowerHalfConstants = new LowerHalfConstants();
        upperHalfConstants = new UpperHalfConstants();
        playCardsOddUp = new CardPanel[7];
        playCardsOddDown = new CardPanel[7];
        playCardsEvenUp = new CardPanel[6];
        playCardsEvenDown = new CardPanel[6];
        manasUp = new BackgroundedPanel[10];
        lostManasUp = new BackgroundedPanel[10];
        manasDown = new BackgroundedPanel[10];
        lostManasDown = new BackgroundedPanel[10];
        draw();
    }

    private void draw() {
        initReturnButtons();

        drawGameLog();

        showNumberOfDeckCards(lowerHalfConstants, 0);
        showNumberOfDeckCards(upperHalfConstants, 1);

        drawHandPanels(lowerHalfConstants, 0);
        drawHandPanels(upperHalfConstants, 1);

        initPlayCardsOdd(lowerHalfConstants, playCardsOddDown);
        initPlayCardsOdd(upperHalfConstants, playCardsOddUp);

        initPlayCardsEven(lowerHalfConstants, playCardsEvenDown);
        initPlayCardsEven(upperHalfConstants, playCardsEvenUp);

        initHeroPanel(lowerHalfConstants, 0);
        initHeroPanel(upperHalfConstants, 1);

        initHeroPowerPanel(lowerHalfConstants, 0);
        initHeroPowerPanel(upperHalfConstants, 1);

        initManasPanel(lowerHalfConstants, manasDown, lostManasDown);
        initManasPanel(upperHalfConstants, manasUp, lostManasUp);

        drawPlantedCards(playCardsEvenDown, playCardsOddDown, 0);
        drawPlantedCards(playCardsEvenUp, playCardsOddUp, 1);

        drawManasPanel(manasDown, lostManasDown, 0);
        drawManasPanel(manasUp, lostManasUp, 1);

        initEndTrunButton();
    }

    void initEndTrunButton() {
        JButton endTurnButton = new JButton("");
        endTurnButton.setContentAreaFilled(false);
        endTurnButton.setBorder(BorderFactory.createEmptyBorder());
        endTurnButton.setBounds((int) (constants.END_BUTTON_X * screenWidth),
                (int) (constants.END_BUTTON_Y * screenHeight), constants.END_BUTTON_WIDTH, constants.END_BUTTON_HEIGHT);
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

    void initManasPanel(PlaygroundConstants constants, BackgroundedPanel[] manas, BackgroundedPanel[] lostManas) {
        for (int i = 0; i < 10; i++) {
            manas[i] = new BackgroundedPanel(("utils/mana.png"));
            manas[i].setScaleFactor(constants.MANA_SCALE_FACTOR);
            manas[i].setDrawLocation((int) ((constants.MANA_X_OFFSET + constants.MANA_X_DISTANCE * i) * screenWidth),
                    (int) (constants.MANA_Y * screenHeight), constants.MANA_WIDTH, constants.MANA_HEIGHT);

            lostManas[i] = new BackgroundedPanel(("utils/mana grayscale.png"));
            lostManas[i].setScaleFactor(constants.MANA_SCALE_FACTOR);
            lostManas[i].setDrawLocation((int) ((constants.MANA_X_OFFSET + constants.MANA_X_DISTANCE * i) * screenWidth),
                    (int) (constants.MANA_Y * screenHeight), constants.MANA_WIDTH, constants.MANA_HEIGHT);

            manas[i].setVisible(false);
            lostManas[i].setVisible(false);

            this.add(lostManas[i]);
            this.add(manas[i]);
        }
    }

    private void drawManasPanel(BackgroundedPanel[] manas, BackgroundedPanel[] lostManas, int index) {
        Contestant contestant = Playground.getPlayground().getContestant(index);
        int currentManas = contestant.getMana();
        int fullManas = contestant.getTurnFullManas();
        for (int i = 0; i < currentManas; i++) {
            manas[i].setVisible(true);
        }
        for (int i = currentManas; i < fullManas; i++) {
            lostManas[i].setVisible(true);
        }
    }

    private void initPlayCardsOdd(PlaygroundConstants constants, CardPanel[] playCardsOdd) {
        for (int i = 0; i < 7; i++) {
            playCardsOdd[i] = new CardPanel();
            playCardsOdd[i].setScaleFactor(constants.CARD_PANEL_SCALE_FACTOR);
            playCardsOdd[i].setDrawLocation((int) ((constants.CARD_PANEL_ODD_X_OFFSET
                            + constants.CARD_PANEL_X_DISTANCE * Math.pow(-1, i) * ((i + 1) / 2)) * screenWidth),
                    (int) (constants.CARD_PANEL_Y * screenHeight), constants.CARD_PANEL_WIDTH, constants.CARD_PANEL_HEIGHT);
            this.add(playCardsOdd[i]);
        }
    }

    void initPlayCardsEven(PlaygroundConstants constants, CardPanel[] playCardsEven) {
        for (int i = 0; i < 6; i++) {
            playCardsEven[i] = new CardPanel();
            playCardsEven[i].setScaleFactor(constants.CARD_PANEL_SCALE_FACTOR);
            playCardsEven[i].setDrawLocation((int) ((constants.CARD_PANEL_EVEN_X_OFFSET
                            + constants.CARD_PANEL_X_DISTANCE * Math.pow(-1, i) * ((i + 1) / 2)) * screenWidth),
                    (int) (constants.CARD_PANEL_Y * screenHeight), constants.CARD_PANEL_WIDTH, constants.CARD_PANEL_HEIGHT);
            this.add(playCardsEven[i]);
        }
    }

    void drawPlantedCards(CardPanel[] playCardsEven, CardPanel[] playCardsOdd, int index) {
        Contestant contestant = Playground.getPlayground().getContestant(index);
        ArrayList<Card> cards = contestant.getPlanted();

        if (cards.size() % 2 == 0) {
            for (int i = 0; i < cards.size(); i++) {
                playCardsEven[i].setCard(cards.get(i));
                playCardsEven[i].setBackgroundImagePath("cards/" + cards.get(i).getName() + ".png");
                playCardsEven[i].setVisible(true);
            }
        } else {
            for (int i = 0; i < cards.size(); i++) {
                playCardsOdd[i].setCard(cards.get(i));
                playCardsOdd[i].setBackgroundImagePath("cards/" + cards.get(i).getName() + ".png");
                playCardsOdd[i].setVisible(true);
            }
        }
    }

    private void drawHandPanels(PlaygroundConstants constants, int index) {
        Contestant contestant = Playground.getPlayground().getContestant(index);
        ArrayList<Card> cards = contestant.getHand();
        CardPanel[] handPanels = new CardPanel[cards.size()];
        CardPanel[] largerHandPanels = new CardPanel[cards.size()];

        int count = 0;
        for (Card card : cards) {
            drawHandLargerCard(card, cards.size() - count - 1, cards.size(), constants, largerHandPanels);
            count++;
        }

        count = 0;
        for (Card card : cards) {
            drawHandCard(card, cards.size() - count - 1, cards.size(), constants, handPanels, largerHandPanels, index);
            count++;
        }
    }

    private void showNumberOfDeckCards(PlaygroundConstants constants, int index) {
        Contestant contestant = Playground.getPlayground().getContestant(index);
        JPanel numberOfDeckCards = new JPanel();
        numberOfDeckCards.setBounds((int) (constants.NUMBER_OF_CARDS_X * screenWidth),
                (int) (constants.NUMBER_OF_CARDS_Y * screenHeight),
                constants.NUMBER_OF_CARDS_WIDTH, constants.NUMBER_OF_CARDS_HEIGHT);
        JLabel label = new JLabel(String.valueOf(contestant.getRemainingDeckSize()));
        label.setFont(new Font(constants.NUMBER_OF_CARDS_FONT, Font.BOLD, constants.NUMBER_OF_CARDS_FONT_SIZE));
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
        scrollPane.getViewport().setPreferredSize(new Dimension(constants.GAME_LOG_SCROLL_PANE_WIDTH,
                constants.GAME_LOG_SCROLL_PANE_HEIGHT));
        outer.setBounds((int) (constants.GAME_LOG_X * screenWidth), (int) (constants.GAME_LOG_Y * screenHeight),
                constants.GAME_LOG_WIDTH, constants.GAME_LOG_HEIGHT);
        outer.add(scrollPane);
        this.add(outer);
    }


    private void drawHandCard(Card card, int index, int totalCapacity, PlaygroundConstants constants,
                              CardPanel[] handPanels, CardPanel[] largerHandPanels, int turnIndex) {
        handPanels[index] = new CardPanel(card, "cards/" + card.getName() + ".png");
        handPanels[index].setScaleFactor(constants.HAND_PANEL_SCALE_FACTOR);
        if (totalCapacity == 1) {
            handPanels[index].setDrawLocation((int) (constants.HAND_PANEL_X * screenWidth),
                    (int) (constants.HAND_PANEL_Y * screenHeight), constants.HAND_PANEL_WIDTH, constants.HAND_PANEL_HEIGHT);
        } else {
            handPanels[index].setDrawLocation((int) ((constants.HAND_PANEL_X_OFFSET
                            + constants.HAND_PANEL_X_DISTANCE * index / (totalCapacity - 1)) * screenWidth),
                    (int) (constants.HAND_PANEL_Y * screenHeight), constants.HAND_PANEL_WIDTH, constants.HAND_PANEL_HEIGHT);
        }

        handPanels[index].addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                Logger.buttonPressLog(handPanels[index], card.getName());

                if (turnIndex != Playground.getPlayground().getTurn())
                    return;

                PlayCardEvent playCardEvent = new PlayCardEvent(this, card);
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
                if (turnIndex != Playground.getPlayground().getTurn())
                    return;

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

    private void drawHandLargerCard(Card card, int index, int totalCapacity, PlaygroundConstants constants,
                                    CardPanel[] largerHandPanels) {
        largerHandPanels[index] = new CardPanel(card, "cards/" + card.getName() + ".png");
        largerHandPanels[index].setScaleFactor(constants.LARGER_HAND_PANEL_SCALE_FACTOR);
        if (totalCapacity == 1) {
            largerHandPanels[index].setDrawLocation((int) (constants.LARGER_HAND_PANEL_X * screenWidth),
                    (int) (constants.LARGER_HAND_PANEL_Y * screenHeight), constants.LARGER_HAND_PANEL_WIDTH,
                    constants.LARGER_HAND_PANEL_HEIGHT);
        } else {
            largerHandPanels[index].setDrawLocation((int) ((constants.LARGER_HAND_PANEL_X_OFFSET
                            + constants.LARGER_HAND_PANEL_X_DISTANCE * index / (totalCapacity - 1)) * screenWidth),
                    (int) (constants.LARGER_HAND_PANEL_Y * screenHeight), constants.LARGER_HAND_PANEL_WIDTH,
                    constants.LARGER_HAND_PANEL_HEIGHT);
        }

        largerHandPanels[index].setVisible(false);
        this.add(largerHandPanels[index]);
    }


    private void initHeroPanel(PlaygroundConstants constants, int index) {
        Contestant contestant = Playground.getPlayground().getContestant(index);
        BackgroundedPanel heroPanel = new BackgroundedPanel("heroes/" + contestant.getHero() + ".png");
        heroPanel.setScaleFactor(constants.HERO_PANEL_X_SCALE_FACTOR, constants.HERO_PANEL_Y_SCALE_FACTOR);
        heroPanel.setDrawLocation((int) (constants.HERO_PANEL_X * screenWidth),
                (int) (constants.HERO_PANEL_Y * screenHeight), constants.HERO_PANEL_WIDTH, constants.HERO_PANEL_HEIGHT);
        this.add(heroPanel);
    }

    private void initHeroPowerPanel(PlaygroundConstants constants, int index) {
        Contestant contestant = Playground.getPlayground().getContestant(index);
        BackgroundedPanel heroPowerPanel = new BackgroundedPanel("hero powers/"
                + contestant.getHero().getHeroPower() + ".png");
        heroPowerPanel.setScaleFactor(constants.HERO_POWER_PANEL_SCALE_FACTOR);
        heroPowerPanel.setDrawLocation((int) (constants.HERO_POWER_PANEL_X * screenWidth),
                (int) (constants.HERO_POWER_PANEL_Y * screenHeight), constants.HERO_POWER_PANEL_WIDTH,
                constants.HERO_POWER_PANEL_HEIGHT);
        this.add(heroPowerPanel);
    }

    private void initReturnButtons() {
        initExitButton();
        initBackToMainMenuButton();
    }

    private void initExitButton() {
        exitButton = new JButton("Exit");
        exitButton.setBounds((int) (constants.EXIT_BUTTON_X * screenWidth), (int) (constants.EXIT_BUTTON_Y * screenHeight),
                constants.EXIT_BUTTON_WIDTH, constants.EXIT_BUTTON_HEIGHT);
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
        backToMainMenuButton.setBounds((int) (constants.MAIN_MENU_BUTTON_X * screenWidth),
                (int) (constants.MAIN_MENU_BUTTON_Y * screenHeight),
                constants.MAIN_MENU_BUTTON_WIDTH, constants.MAIN_MENU_BUTTON_HEIGHT);
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
