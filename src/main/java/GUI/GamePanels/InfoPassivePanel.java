package GUI.GamePanels;

import GUI.Events.PassiveChosenEvent;
import GUI.Listeners.PassiveChosenListener;
import GUI.Utils.BackgroundedPanel;
import Logger.Logger;
import Places.InfoPassive;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class InfoPassivePanel extends GamePanel {
    private JPanel cardsPanel;
    private int cardWidth = 315;
    private int cardHeight = 435;
    private PassiveChosenListener passiveChosenListener;

    public InfoPassivePanel(int screenWidth, int screenHeight) throws IOException {
        super(screenWidth, screenHeight);
        this.setLayout(new BorderLayout());
        initCardsPanel();
    }


    private void initCardsPanel() throws IOException {
        cardsPanel = new JPanel();
        cardsPanel.setOpaque(false);
        cardsPanel.setLayout(new GridLayout(0, 3, 50, 50));
        JPanel cardsContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 100, 100));
        cardsContainer.setOpaque(false);

        drawListOfCards(InfoPassive.getInfoPassive().getPassives(3));

        Font font = new Font("Helvetica", Font.BOLD, 80);
        JLabel title = new JLabel("Choose a Passive!");
        title.setFont(font);
        title.setForeground(Color.WHITE);
        BackgroundedPanel container = new BackgroundedPanel("passive bg.jpg");
        container.setLayout(new GridLayout(0, 1, 0, 50));


        cardsContainer.add(title);
        cardsContainer.add(cardsPanel);
        container.add(cardsContainer);

        this.add(container, BorderLayout.CENTER);
    }


    private void drawListOfCards(ArrayList<String> passives) throws IOException {
        for (String passive : passives) {
            JButton button = new JButton(passive);
            button.setPreferredSize(new Dimension(cardWidth, cardHeight));
            BufferedImage image = ImageIO.read(new File("Images/passives/" + passive + ".png"));
            ImageIcon imageIcon;

            imageIcon = new ImageIcon(image);

            button.setIcon(imageIcon);
            button.setContentAreaFilled(false);
            button.setBorder(BorderFactory.createEmptyBorder());

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    Logger.buttonPressLog(button);
                    PassiveChosenEvent passiveChosenEvent = new PassiveChosenEvent(this, passive);
                    if (passiveChosenListener != null) {
                        try {
                            passiveChosenListener.passiveChosenOccurred(passiveChosenEvent);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            button.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    Image image = null;
                    try {
                        image = ImageIO.read(new File("Images/passives/" + passive + ".png"));
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

    public void setPassiveChosenListener(PassiveChosenListener passiveChosenListener) {
        this.passiveChosenListener = passiveChosenListener;
    }
}
