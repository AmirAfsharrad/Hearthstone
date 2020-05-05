package GUI.Dialogs;

import GUI.Events.ChooseFromListEvent;
import GUI.Events.GetTextEvent;
import GUI.Listeners.GetTextListener;
import GameHandler.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GetTextDialog {
    private JDialog dialog;
    private JTextField textField;

    public GetTextDialog(String title, String response, GetTextListener getTextListener) {
        dialog = new JDialog(GameState.getGameState().getMainFrame(), title, true);
        dialog.setTitle(title);
        dialog.setSize(400, 150);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);

        dialog.setLayout(new GridLayout(0, 1, 5, 5));
        JPanel buttons = new JPanel(new GridLayout(1, 0, 5, 5));

        textField = new JTextField(30);

        JLabel message = new JLabel("<html>" + response + "</html>", SwingConstants.CENTER);

        JButton yesButton = new JButton("OK");
        yesButton.setPreferredSize(new Dimension(50, 25));
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String text = textField.getText();
                GetTextEvent getTextEvent = new GetTextEvent(this, text);
                if (getTextListener != null) {
                    getTextListener.getTextOccurred(getTextEvent);
                }
                dialog.dispose();
            }
        });
        buttons.add(yesButton);

        JButton noButton = new JButton("Cancel");
        noButton.setPreferredSize(new Dimension(90, 15));
        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dialog.dispose();
            }
        });
        buttons.add(noButton);

        JPanel messageContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        messageContainer.add(message, BorderLayout.WEST);

        JPanel itemsContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        itemsContainer.add(textField);

        JPanel buttonsContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        buttonsContainer.add(buttons);

        dialog.add(messageContainer);
        dialog.add(itemsContainer);
        dialog.add(buttonsContainer);

        dialog.setVisible(true);
    }
}
