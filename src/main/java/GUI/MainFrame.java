package GUI;

import GUI.Events.TestEvent;
import GUI.GamePanels.LoginPanel;
import GUI.Listeners.TestListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
//    private static MainFrame mainFrame;
    private JPanel panelCards;
    CardLayout cardLayout = new CardLayout();

//    public static MainFrame getMainFrame() {
//        if (mainFrame == null)
//            mainFrame = new MainFrame();
//        return mainFrame;
//    }

    public MainFrame() throws HeadlessException {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("CardLayout Example");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        int h = this.getSize().height;
        int w = this.getSize().width;

        LoginPanel loginPanel = new LoginPanel(this.getSize().width, this.getSize().height);
        loginPanel.setTestListener(new TestListener() {
            @Override
            public void testEventOccurred(TestEvent testEvent) {
//                System.out.println(h + " , " + w);
                nextPage();
            }
        });
        
        JPanel card2 = new JPanel();
//        loginPanel.setBackground(Color.BLUE);

//Create the panel that contains the "cards".

        panelCards = new JPanel(cardLayout);
        panelCards.add(loginPanel);
        panelCards.add(card2);

//        JButton b1 = new JButton("one");
//        Dimension size = b1.getPreferredSize();
//        b1.setBounds(w / 10, h / 10, size.width, size.height);
//        loginPanel.add(b1);
//
//        b1.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
//                cardLayout.next(cards);
//                System.out.println("Hello");
//            }
//        });

        JButton b2 = new JButton("two");
        Dimension size2 = b2.getPreferredSize();
        b2.setBounds(w / 10, h / 10, size2.width, size2.height);
        card2.add(b2);

        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                nextPage();
            }
        });

        this.add(panelCards);
    }

    public void nextPage() {
        cardLayout.next(panelCards);
    }

}

