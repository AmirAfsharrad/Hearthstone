package GUI.GamePanels;

import Cards.Card;
import Cards.CardButton;
import GUI.Events.ChangePlaceEvent;
import GUI.Events.ExitEvent;
import GUI.Listeners.ChangePlaceListener;
import GUI.Listeners.ExitListener;
import GameHandler.*;
import Places.MainMenu;
import Places.Store;
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

public class StorePanel extends GamePanel {
    private JPanel cardsPanel;
    private JPanel buttonsPanel;
    private JPanel cardsContainer;
    private JPanel buttonsContainer;
    private JScrollPane cardsScrollPane;
    private ChangePlaceListener changePlaceListener;
    private ExitListener exitListener;
    private int cardWidth = 315;
    private int cardHeight = 435;
    private int buttonWidth = 150;
    private int buttonHeight = 75;

    public StorePanel(int screenWidth, int screenHeight) {
        super(screenWidth, screenHeight);
        this.setLayout(new BorderLayout());
        initCardsPanel();
        initButtonsPanel();
    }

    private void initCardsPanel() {
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

        drawListOfCards(GameState.getGameState().getUser().getCards());

        this.add(cardsScrollPane, BorderLayout.CENTER);
    }

    private void initButtonsPanel() {
        buttonsPanel = new JPanel();
        buttonsPanel.setOpaque(false);
        buttonsPanel.setLayout(new GridLayout(0, 1, 0, 20));

        initSellCardsButton();
        initBuyCardsButton();
        initBackToMainMenuButton();
        initExitButton();

        buttonsContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 100, 100));
        buttonsContainer.add(buttonsPanel);
        buttonsContainer.setOpaque(false);
        this.add(buttonsContainer, BorderLayout.EAST);
    }

    void initSellCardsButton() {
        JButton button = new JButton("Sell a Card");
        button.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        button.setToolTipText("click to see the list of the cards you can sell");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                drawListOfCards(Store.getStore().sellCardsList());
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
                drawListOfCards(Store.getStore().buyCardsList());
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


    private void drawListOfCards(ArrayList<Card> cards) {
        if (cards == null) {
            System.out.println("null cards");
            return;
        }

        cardsPanel.removeAll();
        System.out.println("--------------------------------------------------");
        for (Card card : cards) {
            CardButton button = new CardButton(card);
            button.setPreferredSize(new Dimension(cardWidth, cardHeight));
            System.out.println(card.getName());
            ImageIcon imageIcon = new ImageIcon("Images/cards/" + card.getName() + ".png");
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

    public void setChangePlaceListener(ChangePlaceListener changePlaceListener) {
        this.changePlaceListener = changePlaceListener;
    }

    public void setExitListener(ExitListener exitListener) {
        this.exitListener = exitListener;
    }

//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        Graphics2D g2d = (Graphics2D) g;
//        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//                RenderingHints.VALUE_ANTIALIAS_ON);
//        BufferedImage image = ImageLoader.getInstance().loadImage("mainmenu background.jpg");
//        g2d.drawImage(image, 0, 0, null);
//    }
}