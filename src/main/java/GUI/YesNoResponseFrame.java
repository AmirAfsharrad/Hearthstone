package GUI;

import GUI.Listeners.GeneralEventListener;
import GUI.Listeners.YesNoListener;
import sun.java2d.loops.FillRect;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.EventObject;

public class YesNoResponseFrame extends JFrame {
    private JPanel panelCards;
    CardLayout cardLayout = new CardLayout();
    GeneralEventListener yesListener;
    GeneralEventListener noListener;

    public YesNoResponseFrame(String title, String response) throws HeadlessException {
        this.setTitle(title);
        this.setSize(400, 100);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);

        JPanel buttons = new JPanel(new GridLayout(1,0, 30, 30));

        JLabel message = new JLabel("<html>" + response + "</html>", SwingConstants.CENTER);
        this.add(message, BorderLayout.CENTER);

        JButton yesButton = new JButton("YES");
        yesButton.setPreferredSize(new Dimension(50, 25));
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                EventObject eventObject = new EventObject(this);
                try {
                    yesListener.eventOccurred(eventObject);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                close();
            }
        });
        buttons.add(yesButton);

        JButton noButton = new JButton("NO");
        noButton.setPreferredSize(new Dimension(30, 15));
        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                EventObject eventObject = new EventObject(this);
                try {
                    noListener.eventOccurred(eventObject);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                close();
            }
        });
        buttons.add(noButton);

        this.add(buttons, BorderLayout.SOUTH);
    }

    void close() {
        this.dispose();
    }

    public void setYesListener(GeneralEventListener yesListener) {
        this.yesListener = yesListener;
    }

    public void setNoListener(GeneralEventListener noListener) {
        this.noListener = noListener;
    }
}
