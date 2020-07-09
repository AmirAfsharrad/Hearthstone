package GUI.GamePanels;

import Cards.Card;
import Cards.Deck;
import GUI.Events.ChangePlaceEvent;
import GUI.Events.ExitEvent;
import GUI.Listeners.ChangePlaceListener;
import GUI.Listeners.ExitListener;
import GUI.Utils.DynamicIcon;
import GUI.Utils.backgroundJViewport;
import GameHandler.GameState;
import Heroes.Hero;
import Logger.Logger;
import Places.MainMenu;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;

public class StatusPanel extends GamePanel {
    private Deck currentDeck;
    private JPanel cardsPanel;
    private JPanel cardsOfDeckPanelAllCards;
    private JPanel cardsOfDeckPanelOptions;
    private JPanel listOfDecksPanelAllDecks;
    private JPanel bottomButtonsPanel;
    private JPanel backAndExitButtonsPanel;
    private JPanel decksPanel;
    private JPanel listOfDecksPanel;
    private JPanel cardsOfDeckPanel;
    private CardLayout cardLayout;
    private JScrollPane cardsScrollPane;
    private ChangePlaceListener changePlaceListener;
    private ExitListener exitListener;
    private int cardWidth = 315;
    private int cardHeight = 435;
    private int buttonWidth = 200;
    private int deckButtonHeight = 75;

    public StatusPanel(int screenWidth, int screenHeight) throws IOException {
        super(screenWidth, screenHeight);
        this.setLayout(new BorderLayout());
        initCardsPanel();
        initDecksPanel();
        initBottomButtons();
    }


    private void initBottomButtons() {
        bottomButtonsPanel = new JPanel(new BorderLayout());
        initBackAndExitButtonsPanel();
        this.add(bottomButtonsPanel, BorderLayout.SOUTH);
    }

    private void initBackAndExitButtonsPanel() {
        backAndExitButtonsPanel = new JPanel();
        backAndExitButtonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        initExitButton();
        initBackToMainMenuButton();
        bottomButtonsPanel.add(backAndExitButtonsPanel, BorderLayout.EAST);
    }


    private void initCardsPanel() throws IOException {
        cardsPanel = new JPanel();
        cardsPanel.setOpaque(false);
        cardsPanel.setLayout(new GridLayout(0, 3, 50, 50));
        JPanel cardsContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 100, 100));
        cardsContainer.add(cardsPanel);

        JViewport viewport = new backgroundJViewport("card list bg.jpg");

        cardsContainer.setOpaque(false);
        cardsScrollPane = new JScrollPane();
        cardsScrollPane.setViewport(viewport);
        cardsScrollPane.setViewportView(cardsContainer);
        cardsScrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
//        drawStats();

        this.add(cardsScrollPane, BorderLayout.CENTER);
    }

    private void initDecksPanel() throws IOException {
        System.out.println("initDecksPanel");
        cardLayout = new CardLayout();
        decksPanel = new JPanel(cardLayout);
        initListOfDecksPanel();
        initCardsOfDeckPanel();
        decksPanel.add(listOfDecksPanel, "decks");
        decksPanel.add(cardsOfDeckPanel, "cards");
        cardLayout.show(decksPanel, "decks");

        this.add(decksPanel, BorderLayout.EAST);
    }

    private void initListOfDecksPanel() throws IOException {
        System.out.println("initListOfDecksPanel");
        listOfDecksPanel = new JPanel(new BorderLayout());
        JPanel listOfDecksPanelOptions = new JPanel(new GridLayout(0, 1, 0, 5));
        listOfDecksPanelAllDecks = new JPanel(new GridLayout(0, 1, 0, 5));
        JPanel listOfDecksPanelAllDecksContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        listOfDecksPanelAllDecksContainer.add(listOfDecksPanelAllDecks);
        JScrollPane listOfDecksPanelAllDecksScrollPane = new JScrollPane(listOfDecksPanelAllDecksContainer);
        listOfDecksPanelAllDecksScrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        listOfDecksPanel.add(listOfDecksPanelAllDecksScrollPane, BorderLayout.CENTER);
        listOfDecksPanel.add(listOfDecksPanelOptions, BorderLayout.SOUTH);

        drawDecks();
    }

    private void drawDecks() throws IOException {
        System.out.println("drawDecks");
        listOfDecksPanelAllDecks.removeAll();
        for (Deck deck : GameState.getGameState().getUser().getDecks()) {
            JButton button = new JButton(deck.getName());
            button.setForeground(Color.YELLOW);
            button.setHorizontalAlignment(SwingConstants.LEFT);
            button.setVerticalAlignment(SwingConstants.BOTTOM);
            button.setPreferredSize(new Dimension(buttonWidth, deckButtonHeight));
            BufferedImage image = ImageIO.read(new File("Images/decks/" + deck.getHero() + "DeckBackground.jpg"));
            DynamicIcon imageIcon = new DynamicIcon();
            imageIcon.setImage(image);
            button.setIcon(imageIcon);

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    Logger.buttonPressLog(button);
                    currentDeck = deck;
                    cardLayout.show(decksPanel, "cards");
                    drawCardsOfDeck();
                    try {
                        drawStats(currentDeck);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    revalidate();
                    repaint();
                }
            });
            listOfDecksPanelAllDecks.add(button);
        }
    }

    private void initCardsOfDeckPanel() {
        System.out.println("initCardsOfDeckPanel");
        cardsOfDeckPanel = new JPanel(new BorderLayout());
        cardsOfDeckPanelAllCards = new JPanel(new GridLayout(0, 1, 0, 0));
        cardsOfDeckPanelOptions = new JPanel(new GridLayout(0, 1, 5, 5));
        cardsOfDeckPanel.add(cardsOfDeckPanelAllCards, BorderLayout.NORTH);
        cardsOfDeckPanel.add(cardsOfDeckPanelOptions, BorderLayout.SOUTH);
        cardsOfDeckPanelAllCards.add(new JButton("TEST"));
        initBackButton();
        drawCardsOfDeck();
    }

    private void drawCardsOfDeck() {
        cardsOfDeckPanelAllCards.removeAll();
        if (currentDeck == null) {
            System.out.println("NULL current deck!");
            return;
        }

        JButton deckNameButton;
        if (GameState.getGameState().getUser().getSelectedDeck() == currentDeck) {
            deckNameButton = new JButton(currentDeck.getName() + " : SELECTED");
        } else {
            deckNameButton = new JButton(currentDeck.getName());
        }

        deckNameButton.setContentAreaFilled(false);
        deckNameButton.setBorder(BorderFactory.createEmptyBorder());
        cardsOfDeckPanelAllCards.add(deckNameButton);

        JButton heroNameButton = new JButton("Hero: " + currentDeck.getHero());
        heroNameButton.setContentAreaFilled(false);
        heroNameButton.setBorder(BorderFactory.createEmptyBorder());
        cardsOfDeckPanelAllCards.add(heroNameButton);

        HashMap<Card, Integer> hashMapOfCards = currentDeck.getHashMapOfCards();
        for (Card card : hashMapOfCards.keySet()) {
            JButton button;
            int numberOfCardInstances = hashMapOfCards.get(card);
            if (numberOfCardInstances > 1) {
                button = new JButton(card.getName() + " x" + numberOfCardInstances);
            } else {
                button = new JButton(card.getName());
            }
            cardsOfDeckPanelAllCards.add(button);
        }
    }

    void initBackToMainMenuButton() {
        JButton button = new JButton("Main Menu");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Logger.buttonPressLog(button);
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
        backAndExitButtonsPanel.add(button);
    }

    void initExitButton() {
        JButton button = new JButton("Exit");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Logger.buttonPressLog(button);
                ExitEvent exitEvent = new ExitEvent(this);
                if (exitListener != null) {
                    exitListener.exitEventOccurred(exitEvent);
                }
            }
        });
        backAndExitButtonsPanel.add(button);
    }

    private void addElementToStatsPanel(Object object) throws IOException {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(cardWidth, cardHeight));
        BufferedImage image = null;
        if (object instanceof Card) {
            image = ImageIO.read(new File("Images/cards/" + ((Card) object).getName() + ".png"));
        } else if (object instanceof Hero) {
            image = ImageIO.read(new File("Images/heroes/" + ((Hero) object).getType() + ".png"));
        }
        ImageIcon imageIcon;
        imageIcon = new ImageIcon(image);
        button.setIcon(imageIcon);
        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Image image = null;
                try {
                    if (object instanceof Card) {
                        image = ImageIO.read(new File("Images/cards/" + ((Card) object).getName() + ".png"));
                    } else if (object instanceof Hero) {
                        image = ImageIO.read(new File("Images/heroes/" + ((Hero) object).getType() + ".png"));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Image newImage = image.getScaledInstance(cardWidth, cardHeight, java.awt.Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(newImage));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setIcon(imageIcon);
            }
        });

        cardsPanel.add(button);
    }

    private void addStatistics(Deck deck) {
        JPanel statistics = new JPanel(new GridLayout(0, 1, 5, 30));
        JPanel container = new JPanel(new BorderLayout());
        statistics.setOpaque(false);
        container.setOpaque(false);

        Font font = new Font("Helvetica", Font.BOLD, 20);

        JLabel name = new JLabel("Name: " + deck.getName());
        name.setFont(font);
        name.setForeground(Color.WHITE);

        JLabel winRatio = new JLabel("Win ration: " + 0);
        winRatio.setFont(font);
        winRatio.setForeground(Color.WHITE);

        JLabel totalWin = new JLabel("Total winning: " + 0);
        totalWin.setFont(font);
        totalWin.setForeground(Color.WHITE);

        JLabel totalPlayed = new JLabel("Total games played: " + 0);
        totalPlayed.setFont(font);
        totalPlayed.setForeground(Color.WHITE);

        JLabel averageCardCost = new JLabel("Average card cost: " + deck.getAverageCost());
        averageCardCost.setFont(font);
        averageCardCost.setForeground(Color.WHITE);

        statistics.add(name);
        statistics.add(winRatio);
        statistics.add(totalWin);
        statistics.add(totalPlayed);
        statistics.add(averageCardCost);
        container.add(statistics, BorderLayout.NORTH);
        cardsPanel.add(container);
    }


    private void drawStats(Deck deck) throws IOException {
        cardsPanel.removeAll();
        addElementToStatsPanel(deck.getCards().get(0));
        addElementToStatsPanel(deck.getHero());
        addStatistics(deck);

        cardsScrollPane.getVerticalScrollBar().setValue(0);
        revalidate();
        repaint();
    }

    private void initBackButton() {
        JButton backToDecksMenu = new JButton("Back");
        backToDecksMenu.setPreferredSize(new Dimension(buttonWidth / 2, 2 * deckButtonHeight / 3));
        backToDecksMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Logger.buttonPressLog(backToDecksMenu);
                cardLayout.show(decksPanel, "decks");
                cardsPanel.removeAll();
                revalidate();
                repaint();
            }
        });
        cardsOfDeckPanelOptions.add(backToDecksMenu);
    }


    public void setChangePlaceListener(ChangePlaceListener changePlaceListener) {
        this.changePlaceListener = changePlaceListener;
    }

    public void setExitListener(ExitListener exitListener) {
        this.exitListener = exitListener;
    }
}
