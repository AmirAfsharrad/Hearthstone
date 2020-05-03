package GUI.GamePanels;

import Cards.Card;
import Cards.CardButton;
import GUI.Events.ChangePlaceEvent;
import GUI.Events.CollectionsFilterEvent;
import GUI.Events.ExitEvent;
import GUI.Listeners.ChangePlaceListener;
import GUI.Listeners.CollectionsFilterListener;
import GUI.Listeners.ExitListener;
import GameHandler.GameState;
import Places.Collections;
import Places.MainMenu;
import Places.Store;
import Utilities.GrayscaleImage;
import Utilities.ImageLoader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CollectionsPanel extends GamePanel {
    private JPanel cardsPanel;
    private JPanel buttonsPanel;
    private JPanel heroesButtonsPanel;
    private JPanel cardsContainer;
    private JPanel searchPanel;
    private JPanel doesOwnPanel;
    private JPanel buttonsContainer;
    private JPanel heroesButtonsContainer;
    private JPanel manaFilterPanel;
    private JPanel manaFilterContainer;
    private JPanel decksPanel;
    private JTextField searchField;
    private JRadioButton[] manaButtons;
    private JRadioButton[] heroesButtons;
    private JRadioButton[] doesOwnButtons;
    private JScrollPane cardsScrollPane;
    private JScrollPane decksScrollPane;
    private ChangePlaceListener changePlaceListener;
    private CollectionsFilterListener collectionsFilterListener;
    private ActionListener collectionsFilterActionListener;
    private ExitListener exitListener;
    private int cardWidth = 315;
    private int cardHeight = 435;
    private int buttonWidth = 150;
    private int buttonHeight = 75;
    private String[] heroesNames = {"all", "Neutral", "Mage", "Warlock", "Rogue", "Paladin", "Priest"};
    private String[] doesOwnButtonsNames = {"all", "owning", "notOwning"};

    public CollectionsPanel(int screenWidth, int screenHeight) throws IOException {
        super(screenWidth, screenHeight);
        this.setLayout(new BorderLayout());
        initCollectionsFilterActionListener();
        initCardsPanel();
        initDecksPanel();
        initManaFilterPanel();
        initButtonsPanel();
    }

    private void initCollectionsFilterActionListener() {
        collectionsFilterActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                CollectionsFilterEvent collectionsFilterEvent = new CollectionsFilterEvent
                        (this, getSelectedManaButton(), getSelectedHeroButton(),
                                searchField.getText(), getSelectedDoesOwnButton());
                if (collectionsFilterListener != null) {
                    collectionsFilterListener.CollectionsFilterOccurred(collectionsFilterEvent);
                }
                try {
                    drawListOfCards(Collections.getCollections().getDisplayedCards());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private void initManaFilterPanel() {
        manaFilterPanel = new JPanel();
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

        manaFilterContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        manaFilterContainer.add(manaFilterLabel);
        manaFilterContainer.add(manaFilterPanel);

        this.add(manaFilterContainer, BorderLayout.SOUTH);
    }

    private void initButtonsPanel() {
        heroesButtonsPanel = new JPanel();
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

        heroesButtonsContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        heroesButtonsContainer.add(buttonsFilterLabel);
        heroesButtonsContainer.add(heroesButtonsPanel);

        searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchField = new JTextField(20);
        JButton searchButton = new JButton("search");
        searchButton.addActionListener(collectionsFilterActionListener);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        doesOwnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        doesOwnButtons = new JRadioButton[3];
        ButtonGroup doesOwnButtonGroup = new ButtonGroup();
        for (int i = 0; i < 3; i++) {
            doesOwnButtons[i] = new JRadioButton(doesOwnButtonsNames[i]);
            doesOwnPanel.add(doesOwnButtons[i]);
            doesOwnButtonGroup.add(doesOwnButtons[i]);
            doesOwnButtons[i].addActionListener(collectionsFilterActionListener);
        }
        doesOwnButtons[0].setSelected(true);

        buttonsPanel = new JPanel(new BorderLayout());
        buttonsPanel.add(heroesButtonsContainer, BorderLayout.WEST);
        buttonsPanel.add(searchPanel, BorderLayout.CENTER);
        buttonsPanel.add(doesOwnPanel, BorderLayout.EAST);

        this.add(buttonsPanel, BorderLayout.NORTH);
    }

    private void initCardsPanel() throws IOException {
        cardsPanel = new JPanel();
        cardsPanel.setOpaque(false);
        cardsPanel.setLayout(new GridLayout(0, 3, 50, 50));
        cardsContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 100, 100));
        cardsContainer.add(cardsPanel);

        JViewport viewport = new JViewport()
        {
            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);

                BufferedImage image = ImageLoader.getInstance().loadImage("card list bg.jpg");
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };

        cardsContainer.setOpaque(false);
        cardsScrollPane = new JScrollPane();
        cardsScrollPane.setViewport(viewport);
        cardsScrollPane.setViewportView(cardsContainer);

        drawListOfCards(Collections.getCollections().getDisplayedCards());

        this.add(cardsScrollPane, BorderLayout.CENTER);
    }

    private void initDecksPanel() {
        buttonsPanel = new JPanel();
        buttonsPanel.setOpaque(false);
        buttonsPanel.setLayout(new GridLayout(0, 1, 0, 20));

        initSellCardsButton();
        initBuyCardsButton();
        initBackToMainMenuButton();
        initExitButton();
        initSellCardsButton();
        initBuyCardsButton();
        initBackToMainMenuButton();
        initExitButton();
        initSellCardsButton();
        initBuyCardsButton();
        initBackToMainMenuButton();
        initExitButton();

        buttonsContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 100, 100));
        buttonsContainer.add(buttonsPanel);
        buttonsContainer.setOpaque(false);
        decksScrollPane = new JScrollPane(buttonsContainer);

        this.add(decksScrollPane, BorderLayout.EAST);
    }

    void initSellCardsButton() {
        JButton button = new JButton("Sell a Card");
        button.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        button.setToolTipText("click to see the list of the cards you can sell");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    drawListOfCards(Store.getStore().sellCardsList());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        buttonsPanel.add(button);
    }

    void initBuyCardsButton() {
        JButton button = new JButton("Buy a Card");
        button.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        button.setToolTipText("click to see the list of the cards you can buy");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    drawListOfCards(Store.getStore().buyCardsList());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        buttonsPanel.add(button);
    }

    void initBackToMainMenuButton() {
        JButton button = new JButton("Main Menu");
        button.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
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
        buttonsPanel.add(button);
    }

    void initExitButton() {
        JButton button = new JButton("Exit");
        button.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ExitEvent exitEvent = new ExitEvent(this);
                if (exitListener != null) {
                    exitListener.exitEventOccurred(exitEvent);
                }
            }
        });
        buttonsPanel.add(button);
    }


    private void drawListOfCards(ArrayList<Card> cards) throws IOException {
        if (cards == null) {
            System.out.println("null cards");
            return;
        }

        cardsPanel.removeAll();
//        if (cards.size() == 0) {
//            cardsPanel.add(new CardButton);
//        }
        for (Card card : cards) {
            System.out.println(cardsPanel.getSize().width);
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
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    Store.getStore().sellOrBuyCard(button.getCard());
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

    public void setCollectionsFilterListener(CollectionsFilterListener collectionsFilterListener) {
        this.collectionsFilterListener = collectionsFilterListener;
    }

//        @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        Graphics2D g2d = (Graphics2D) g;
//        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//                RenderingHints.VALUE_ANTIALIAS_ON);
//        BufferedImage image = ImageLoader.getInstance().loadImage("mainmenu background.jpg");
//        g2d.drawImage(image, 0, 0, null);
//    }
}
