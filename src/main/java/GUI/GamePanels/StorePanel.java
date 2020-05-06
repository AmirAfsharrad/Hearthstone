package GUI.GamePanels;

import Cards.Card;
import Cards.CardButton;
import GUI.Events.ChangePlaceEvent;
import GUI.Events.ExitEvent;
import GUI.Events.FilterEvent;
import GUI.Listeners.ChangePlaceListener;
import GUI.Listeners.ExitListener;
import GUI.Listeners.FilterListener;
import GameHandler.GameState;
import Logger.Logger;
import Places.MainMenu;
import Places.Store;
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

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;

public class StorePanel extends GamePanel {
    private JPanel cardsPanel;
    private JPanel bottomButtonsPanel;
    private JPanel backAndExitButtonsPanel;
    private JTextField searchField;
    private JRadioButton[] manaButtons;
    private JRadioButton[] heroesButtons;
    private JRadioButton[] doesOwnButtons;
    private JScrollPane cardsScrollPane;
    private ChangePlaceListener changePlaceListener;
    private FilterListener filterListener;
    private ActionListener storeFilterActionListener;
    private ExitListener exitListener;
    private int cardWidth = 315;
    private int cardHeight = 435;
    private String[] heroesNames = {"all", "Neutral", "Mage", "Warlock", "Rogue", "Paladin", "Priest"};
    private String[] doesOwnButtonsNames = {"all", "sellable", "buyable"};

    public StorePanel(int screenWidth, int screenHeight) throws IOException {
        super(screenWidth, screenHeight);
        this.setLayout(new BorderLayout());
        initStoreFilterActionListener();
        initCardsPanel();
        initBottomButtons();
        initButtonsPanel();
    }

    private void initStoreFilterActionListener() {
        storeFilterActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                FilterEvent filterEvent = new FilterEvent
                        (this, getSelectedManaButton(), getSelectedHeroButton(),
                                searchField.getText(), getSelectedDoesOwnButton());
                if (filterListener != null) {
                    filterListener.filterOccurred(filterEvent);
                }
                try {
                    drawListOfCards(Store.getStore().getDisplayedCards());
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
        manaButtons[11].addActionListener(storeFilterActionListener);
        for (int i = 0; i <= 10; i++) {
            manaButtons[i] = new JRadioButton(String.valueOf(i));
            manaFilterPanel.add(manaButtons[i]);
            buttons.add(manaButtons[i]);
            manaButtons[i].addActionListener(storeFilterActionListener);
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
            heroesButtons[i].addActionListener(storeFilterActionListener);
        }
        heroesButtons[0].setSelected(true);

        JPanel heroesButtonsContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        heroesButtonsContainer.add(buttonsFilterLabel);
        heroesButtonsContainer.add(heroesButtonsPanel);

        JPanel middlePanel = new JPanel(new BorderLayout());

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchField = new JTextField(20);
        JButton searchButton = new JButton("search");
        searchButton.addActionListener(storeFilterActionListener);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        JPanel goldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 200, 15));
        JLabel gold = new JLabel("Gold: " + GameState.getGameState().getUser().getGold());
        goldPanel.add(gold);

        middlePanel.add(searchPanel, BorderLayout.WEST);
        middlePanel.add(goldPanel, BorderLayout.CENTER);

        JPanel doesOwnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        doesOwnButtons = new JRadioButton[3];
        ButtonGroup doesOwnButtonGroup = new ButtonGroup();
        for (int i = 0; i < 3; i++) {
            doesOwnButtons[i] = new JRadioButton(doesOwnButtonsNames[i]);
            doesOwnPanel.add(doesOwnButtons[i]);
            doesOwnButtonGroup.add(doesOwnButtons[i]);
            doesOwnButtons[i].addActionListener(storeFilterActionListener);
        }
        doesOwnButtons[0].setSelected(true);

        JPanel buttonsPanel = new JPanel(new BorderLayout());
        buttonsPanel.add(heroesButtonsContainer, BorderLayout.WEST);
        buttonsPanel.add(middlePanel, BorderLayout.CENTER);
        buttonsPanel.add(doesOwnPanel, BorderLayout.EAST);

        this.add(buttonsPanel, BorderLayout.NORTH);
    }

    private void initCardsPanel() throws IOException {
        cardsPanel = new JPanel();
        cardsPanel.setOpaque(false);
        cardsPanel.setLayout(new GridLayout(0, 4, 50, 50));
        JPanel cardsContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 100, 100));
        cardsContainer.add(cardsPanel);

        JViewport viewport = new backgroundJViewport("card list bg.jpg");

        cardsContainer.setOpaque(false);
        cardsScrollPane = new JScrollPane();
        cardsScrollPane.setViewport(viewport);
        cardsScrollPane.setViewportView(cardsContainer);
        cardsScrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);


        drawListOfCards(Store.getStore().getDisplayedCards());

        this.add(cardsScrollPane, BorderLayout.CENTER);
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
//        button.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
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
                    card.getRarity() + " </center></html>"  + " <br /> Description: " +
                    card.getDescription() + " </center></html>");

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    Logger.buttonPressLog(button);
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

    public void setFilterListener(FilterListener filterListener) {
        this.filterListener = filterListener;
    }
}

