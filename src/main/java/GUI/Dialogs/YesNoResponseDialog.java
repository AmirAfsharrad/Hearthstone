package GUI.Dialogs;

import GUI.Listeners.GeneralEventListener;
import GameHandler.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.EventObject;

public class YesNoResponseDialog {
    private JDialog dialog;

    public YesNoResponseDialog(String title, String response, GeneralEventListener yesListener,
                               GeneralEventListener noListener) throws HeadlessException {
        dialog = new JDialog(GameState.getGameState().getMainFrame(), title, true);
        dialog.setTitle(title);
        dialog.setSize(400, 100);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);

        JPanel buttons = new JPanel(new GridLayout(1,0, 30, 30));

        JLabel message = new JLabel("<html>" + response + "</html>", SwingConstants.CENTER);
        dialog.add(message, BorderLayout.CENTER);

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
                dialog.dispose();
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
                dialog.dispose();
            }
        });
        buttons.add(noButton);

        dialog.add(buttons, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
}
