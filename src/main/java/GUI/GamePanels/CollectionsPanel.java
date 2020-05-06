package GUI.GamePanels;

import Cards.Card;
import Cards.CardButton;
import Cards.Deck;
import GUI.Events.*;
import GUI.Listeners.*;
import GameHandler.GameState;
import Logger.Logger;
import Places.*;
import Utilities.GrayscaleImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;

public class CollectionsPanel extends GamePanel {
    private Deck currentDeck;
    private String currentDeckPanelCard;
    private JPanel cardsPanel;
    private JPanel cardsOfDeckPanelAllCards;
    private JPanel cardsOfDeckPanelOptions;
    private JPanel listOfDecksPanelAllDecks;
    private JButton deckNameButton;
    private JPanel bottomButtonsPanel;
    private JPanel backAndExitButtonsPanel;
    private JPanel decksPanel;
    private JPanel listOfDecksPanel;
    private JPanel cardsOfDeckPanel;
    private CardLayout cardLayout;
    private JTextField searchField;
    private JRadioButton[] manaButtons;
    private JRadioButton[] heroesButtons;
    private JRadioButton[] doesOwnButtons;
    private JScrollPane cardsScrollPane;
    private ChangePlaceListener changePlaceListener;
    private FilterListener filterListener;
    private CreateDeckListener createDeckListener;
    private ChangeDeckHeroListener changeDeckHeroListener;
    private ActionListener collectionsFilterActionListener;
    private AddCardToDeckListener addCardToDeckListener;
    private RemoveCardFromDeckListener removeCardFromDeckListener;
    private RemoveDeckListener removeDeckListener;
    private DeckRenameListener deckRenameListener;
    private SetDeckAsSelectedListener setDeckAsSelectedListener;
    private ExitListener exitListener;
    private int cardWidth = 315;
    private int cardHeight = 435;
    private int buttonWidth = 200;
    private int deckButtonHeight = 75;
    private String[] heroesNames = {"all", "Neutral", "Mage", "Warlock", "Rogue", "Paladin", "Priest"};
    private String[] doesOwnButtonsNames = {"all", "owned", "not owned"};

    public CollectionsPanel(int screenWidth, int screenHeight) throws IOException {
        super(screenWidth, screenHeight);
        this.setLayout(new BorderLayout());
        initCollectionsFilterActionListener();
        initCardsPanel();
        initDecksPanel();
        initBottomButtons();
        initButtonsPanel();
    }

    private void initCollectionsFilterActionListener() {
        collectionsFilterActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                FilterEvent filterEvent = new FilterEvent
                        (this, getSelectedManaButton(), getSelectedHeroButton(),
                                searchField.getText(), getSelectedDoesOwnButton());
                if (filterListener != null) {
                    filterListener.filterOccurred(filterEvent);
                }
                try {
                    drawListOfCards(Collections.getCollections().getDisplayedCards());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private void initBottomButtons() {
        bottomButtonsPanel = new JPanel(new BorderLayout());
        initManaFilterPanel();
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

    private void initManaFilterPanel() {
        JPanel manaFilterPanel = new JPanel();
        manaFilterPanel.setLayout(new GridLayout(1, 0, 0, 0));

        JLabel manaFilterLabel = new JLabel("Filter by manas");

        manaButtons = new JRadioButton[12];
        ButtonGroup buttons = new ButtonGroup();

        manaButtons[11] = new JRadioButton("all");
        manaFilterPanel.add(manaButtons[11]);
        manaButtons[11].setSelected(true);
        buttons.add(manaButtons[11]);
        manaButtons[11].addActionListener(collectionsFilterActionListener);
        for (int i = 0; i <= 10; i++) {
            manaButtons[i] = new JRadioButton(String.valueOf(i));
            manaFilterPanel.add(manaButtons[i]);
            buttons.add(manaButtons[i]);
            manaButtons[i].addActionListener(collectionsFilterActionListener);
        }

        JPanel manaFilterContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        manaFilterContainer.add(manaFilterLabel);
        manaFilterContainer.add(manaFilterPanel);

        bottomButtonsPanel.add(manaFilterContainer, BorderLayout.WEST);
    }

    private void initButtonsPanel() {
        JPanel heroesButtonsPanel = new JPanel();
        heroesButtonsPanel.setLayout(new GridLayout(1, 0, 0, 0));

        JLabel buttonsFilterLabel = new JLabel("Filter by heroes");

        heroesButtons = new JRadioButton[heroesNames.length];

        ButtonGroup heroesButtonGroup = new ButtonGroup();

        for (int i = 0; i < heroesNames.length; i++) {
            heroesButtons[i] = new JRadioButton(heroesNames[i]);
            heroesButtonsPanel.add(heroesButtons[i]);
            heroesButtonGroup.add(heroesButtons[i]);
            heroesButtons[i].addActionListener(collectionsFilterActionListener);
        }
        heroesButtons[0].setSelected(true);

        JPanel heroesButtonsContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        heroesButtonsContainer.add(buttonsFilterLabel);
        heroesButtonsContainer.add(heroesButtonsPanel);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchField = new JTextField(20);
        JButton searchButton = new JButton("search");
        searchButton.addActionListener(collectionsFilterActionListener);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        JPanel doesOwnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        doesOwnButtons = new JRadioButton[3];
        ButtonGroup doesOwnButtonGroup = new ButtonGroup();
        for (int i = 0; i < 3; i++) {
            doesOwnButtons[i] = new JRadioButton(doesOwnButtonsNames[i]);
            doesOwnPanel.add(doesOwnButtons[i]);
            doesOwnButtonGroup.add(doesOwnButtons[i]);
            doesOwnButtons[i].addActionListener(collectionsFilterActionListener);
        }
        doesOwnButtons[0].setSelected(true);

        JPanel buttonsPanel = new JPanel(new BorderLayout());
        buttonsPanel.add(heroesButtonsContainer, BorderLayout.WEST);
        buttonsPanel.add(searchPanel, BorderLayout.CENTER);
        buttonsPanel.add(doesOwnPanel, BorderLayout.EAST);

        this.add(buttonsPanel, BorderLayout.NORTH);
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

        drawListOfCards(Collections.getCollections().getDisplayedCards());

        this.add(cardsScrollPane, BorderLayout.CENTER);
    }

    private void initDecksPanel() throws IOException {
        System.out.println("initDecksPanel");
        currentDeckPanelCard = "decks";
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

        JButton newDeck = new JButton("New Deck");
        newDeck.setPreferredSize(new Dimension(buttonWidth, 2 * deckButtonHeight / 3));
        newDeck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Logger.buttonPressLog(newDeck);
                CreateDeckEvent createDeckEvent = new CreateDeckEvent(this);
                if (createDeckListener != null) {
                    createDeckListener.CreateDeckOccurred(createDeckEvent);
                }
                try {
                    drawDecks();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                revalidate();
                repaint();
            }
        });
        listOfDecksPanelOptions.add(newDeck);
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
                    currentDeckPanelCard = "cards";
                    drawCardsOfDeck();
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
        cardsOfDeckPanelOptions = new JPanel(new GridLayout(0, 2, 5, 5));
        cardsOfDeckPanel.add(cardsOfDeckPanelAllCards, BorderLayout.NORTH);
        cardsOfDeckPanel.add(cardsOfDeckPanelOptions, BorderLayout.SOUTH);
        cardsOfDeckPanelAllCards.add(new JButton("TEST"));
        initCardsOfDeckPanelOptionButtons();
        drawCardsOfDeck();
    }

    private void drawCardsOfDeck() {
        cardsOfDeckPanelAllCards.removeAll();
        if (currentDeck == null) {
            System.out.println("NULL current deck!");
            return;
        }

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

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    Logger.buttonPressLog(button);
                    RemoveCardFromDeckEvent removeCardFromDeckEvent = new RemoveCardFromDeckEvent(this, card, currentDeck);
                    if (removeCardFromDeckListener != null) {
                        removeCardFromDeckListener.removeCardFromDeckOccurred(removeCardFromDeckEvent);
                    }
                    drawCardsOfDeck();
                    revalidate();
                    repaint();
                }
            });
            cardsOfDeckPanelAllCards.add(button);
        }
    }

    private void initCardsOfDeckPanelOptionButtons() {
        JButton backToDecksMenu = new JButton("Back");
        backToDecksMenu.setPreferredSize(new Dimension(buttonWidth / 2, 2 * deckButtonHeight / 3));
        backToDecksMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Logger.buttonPressLog(backToDecksMenu);
                cardLayout.show(decksPanel, "decks");
                currentDeckPanelCard = "decks";
                revalidate();
                repaint();
            }
        });
        JButton renameDeck = new JButton("<html><center> Rename <br /> Deck </center></html>");
        renameDeck.setPreferredSize(new Dimension(buttonWidth / 2, 2 * deckButtonHeight / 3));
        renameDeck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Logger.buttonPressLog(renameDeck);
                DeckRenameEvent deckRenameEvent = new DeckRenameEvent(this, currentDeck);
                if (deckRenameListener != null) {
                    deckRenameListener.deckRenameOccurred(deckRenameEvent);
                }
                try {
                    drawDecks();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                drawCardsOfDeck();
                revalidate();
                repaint();
            }
        });


        JButton changeHero = new JButton("<html><center> Change <br /> Hero </center></html>");
        changeHero.setPreferredSize(new Dimension(buttonWidth / 2, 2 * deckButtonHeight / 3));
        changeHero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Logger.buttonPressLog(changeHero);
                ChangeDeckHeroEvent changeDeckHeroEvent = new ChangeDeckHeroEvent(this, currentDeck);
                if (changeDeckHeroListener != null) {
                    changeDeckHeroListener.changeDeckHeroOccurred(changeDeckHeroEvent);
                }
                drawCardsOfDeck();
                try {
                    drawDecks();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                revalidate();
                repaint();
            }
        });


        JButton removeDeck = new JButton("<html><center> Remove <br /> Deck </center></html>");
        removeDeck.setPreferredSize(new Dimension(buttonWidth / 2, 2 * deckButtonHeight / 3));
        removeDeck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Logger.buttonPressLog(removeDeck);
                RemoveDeckEvent removeDeckEvent = new RemoveDeckEvent(this, currentDeck);
                if (removeDeckListener != null) {
                    removeDeckListener.removeDeckOccurred(removeDeckEvent);
                }
                cardLayout.show(decksPanel, "decks");
                try {
                    drawDecks();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        JButton setAsSelected = new JButton("<html><center> Set As <br /> Selected </center></html>");
        setAsSelected.setPreferredSize(new Dimension(buttonWidth / 2, 2 * deckButtonHeight / 3));
        setAsSelected.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Logger.buttonPressLog(setAsSelected);
                SetDeckAsSelectedEvent setDeckAsSelectedEvent = new SetDeckAsSelectedEvent(this, currentDeck);
                if (setDeckAsSelectedListener != null) {
                    setDeckAsSelectedListener.setDeckAsSelectedOccurred(setDeckAsSelectedEvent);
                }
                deckNameButton.setText(currentDeck.getName() + " : SELECTED");
                try {
                    drawDecks();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                revalidate();
                repaint();
            }
        });

        JButton Play = new JButton("Play");
        Play.setPreferredSize(new Dimension(buttonWidth / 2, 2 * deckButtonHeight / 3));
        Play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Logger.buttonPressLog(Play);
                SetDeckAsSelectedEvent setDeckAsSelectedEvent = new SetDeckAsSelectedEvent(this, currentDeck);
                if (setDeckAsSelectedListener != null) {
                    setDeckAsSelectedListener.setDeckAsSelectedOccurred(setDeckAsSelectedEvent);
                }
                deckNameButton.setText(currentDeck.getName() + " : SELECTED");
                try {
                    drawDecks();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                revalidate();
                repaint();

                ChangePlaceEvent changePlaceEvent = new ChangePlaceEvent(this, InfoPassive.getInfoPassive());
                if (changePlaceListener != null) {
                    try {
                        changePlaceListener.ChangePlaceOccurred(changePlaceEvent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        cardsOfDeckPanelOptions.add(backToDecksMenu);
        cardsOfDeckPanelOptions.add(renameDeck);
        cardsOfDeckPanelOptions.add(changeHero);
        cardsOfDeckPanelOptions.add(removeDeck);
        cardsOfDeckPanelOptions.add(setAsSelected);
        cardsOfDeckPanelOptions.add(Play);
    }


    void initBackToMainMenuButton() {
        JButton button = new JButton("Main Menu");
//        button.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
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


    private void drawListOfCards(ArrayList<Card> cards) throws IOException {
        if (cards == null) {
            System.out.println("null cards");
            return;
        }

        cardsPanel.removeAll();
        for (Card card : cards) {
            CardButton button = new CardButton(card);
            button.setPreferredSize(new Dimension(cardWidth, cardHeight));
            BufferedImage image = ImageIO.read(new File("Images/cards/" + button.getCard().getName() + ".png"));
            ImageIcon imageIcon;

            if (GameState.getGameState().getUser().hasCard(card))
                imageIcon = new ImageIcon(image);
            else
                imageIcon = new ImageIcon(GrayscaleImage.getGrayscaleImage(image));

            button.setIcon(imageIcon);
            button.setContentAreaFilled(false);
            button.setBorder(BorderFactory.createEmptyBorder());
            button.setToolTipText("<html><center> Price: " + card.getPrice() + " <br /> Rarity: " +
                    card.getRarity() + " </center></html>");

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    Logger.buttonPressLog(button);
                    AddCardToDeckEvent addCardToDeckEvent = new AddCardToDeckEvent(this, button.getCard(), currentDeck);
                    if (addCardToDeckListener != null) {
                        if (currentDeckPanelCard.equals("cards") && currentDeck != null) {
                            addCardToDeckListener.addCardToDeckOccurred(addCardToDeckEvent);
                        } else {
                            Store.getStore().sellOrBuyCard(button.getCard());
                        }
                    }
                    drawCardsOfDeck();
                    revalidate();
                    repaint();
                }
            });

            button.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    Image image = null;
                    try {
                        image = ImageIO.read(new File("Images/cards/" + button.getCard().getName() + ".png"));
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
        cardsScrollPane.getVerticalScrollBar().setValue(0);
        revalidate();
        repaint();
    }

    private int getSelectedManaButton() {
        for (int i = 0; i < 11; i++) {
            if (manaButtons[i].isSelected()) {
                return i;
            }
        }
        return -1;
    }

    private String getSelectedHeroButton() {
        int counter = 0;
        for (JRadioButton heroesButton : heroesButtons) {
            if (heroesButton.isSelected()) {
                return heroesNames[counter];
            }
            counter++;
        }
        return heroesNames[0];
    }

    private String getSelectedDoesOwnButton() {
        int counter = 0;
        for (JRadioButton doesOwnButton : doesOwnButtons) {
            if (doesOwnButton.isSelected()) {
                return doesOwnButtonsNames[counter];
            }
            counter++;
        }
        return doesOwnButtonsNames[0];
    }

    public void setChangePlaceListener(ChangePlaceListener changePlaceListener) {
        this.changePlaceListener = changePlaceListener;
    }

    public void setExitListener(ExitListener exitListener) {
        this.exitListener = exitListener;
    }

    public void setFilterListener(FilterListener filterListener) {
        this.filterListener = filterListener;
    }

    public void setCreateDeckListener(CreateDeckListener createDeckListener) {
        this.createDeckListener = createDeckListener;
    }

    public void setAddCardToDeckListener(AddCardToDeckListener addCardToDeckListener) {
        this.addCardToDeckListener = addCardToDeckListener;
    }

    public void setRemoveCardFromDeckListener(RemoveCardFromDeckListener removeCardFromDeckListener) {
        this.removeCardFromDeckListener = removeCardFromDeckListener;
    }

    public void setRemoveDeckListener(RemoveDeckListener removeDeckListener) {
        this.removeDeckListener = removeDeckListener;
    }

    public void setChangeDeckHeroListener(ChangeDeckHeroListener changeDeckHeroListener) {
        this.changeDeckHeroListener = changeDeckHeroListener;
    }

    public void setDeckRenameListener(DeckRenameListener deckRenameListener) {
        this.deckRenameListener = deckRenameListener;
    }

    public void setSetDeckAsSelectedListener(SetDeckAsSelectedListener setDeckAsSelectedListener) {
        this.setDeckAsSelectedListener = setDeckAsSelectedListener;
    }
}

