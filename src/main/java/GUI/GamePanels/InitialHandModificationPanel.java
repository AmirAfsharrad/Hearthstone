package GUI.GamePanels;

import Cards.Card;
import GUI.Events.ChoiceOfCardSelectionEvent;
import GUI.Events.PassiveChosenEvent;
import GUI.Listeners.ChoiceOfCardSelectionListener;
import GUI.Listeners.PassiveChosenListener;
import GUI.Utils.BackgroundedPanel;
import Logger.Logger;
import Places.InfoPassive;
import Places.Playground;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class InitialHandModificationPanel extends GamePanel {
    private JPanel cardsPanel;
    private int cardWidth = (int) (315 * 0.8);
    private int cardHeight = (int) (435 * 0.8);
    private ChoiceOfCardSelectionListener choiceOfCardSelectionListener;
    private ArrayList<Card> cards;

    public InitialHandModificationPanel(int screenWidth, int screenHeight, ArrayList<Card> cards) throws IOException {
        super(screenWidth, screenHeight);
        this.setLayout(new BorderLayout());
        this.cards = cards;
        initCardsPanel();
    }


    private void initCardsPanel() throws IOException {
        cardsPanel = new JPanel();
        cardsPanel.setOpaque(false);
        cardsPanel.setLayout(new GridLayout(0, 3, 50, 50));
        JPanel cardsContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 100, 100));
        cardsContainer.setOpaque(false);

        drawListOfCards(cards);

        Font font = new Font("Helvetica", Font.BOLD, 80);
        JLabel title = new JLabel("Initial Hand");
        title.setFont(font);
        title.setForeground(Color.WHITE);
        BackgroundedPanel container = new BackgroundedPanel("passive bg.jpg");
        container.setLayout(new GridLayout(0, 1, 0, 50));


        cardsContainer.add(title);
        cardsContainer.add(cardsPanel);
        container.add(cardsContainer);

        this.add(container, BorderLayout.CENTER);
    }


    private void drawListOfCards(ArrayList<Card> hand) throws IOException {
        for (Card card : hand) {
            System.out.println(card.getName());
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(cardWidth, cardHeight));
            BufferedImage image = ImageIO.read(new File("Images/cards/" + card.getName() + ".png"));
            ImageIcon imageIcon;

            imageIcon = new ImageIcon(image);

            button.setIcon(imageIcon);
            button.setContentAreaFilled(false);
            button.setBorder(BorderFactory.createEmptyBorder());

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    Logger.buttonPressLog(button);
                    ChoiceOfCardSelectionEvent choiceOfCardSelectionEvent = new ChoiceOfCardSelectionEvent(this, card);
                    if (choiceOfCardSelectionListener != null) {
                        try {
                            choiceOfCardSelectionListener.choiceOfCardSelectionEventOccurred(choiceOfCardSelectionEvent);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        initCardsPanel();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            button.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    Image image = null;
                    try {
                        image = ImageIO.read(new File("Images/cards/" + card.getName() + ".png"));
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
        revalidate();
        repaint();
    }

    public void setChoiceOfCardSelectionListener(ChoiceOfCardSelectionListener choiceOfCardSelectionListener) {
        this.choiceOfCardSelectionListener = choiceOfCardSelectionListener;
    }
}
