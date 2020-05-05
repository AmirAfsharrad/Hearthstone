package GUI.Dialogs;

import GUI.Events.ChooseFromListEvent;
import GUI.Listeners.ChooseFromListListener;
import GameHandler.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChooseFromListDialog {
    private JComboBox itemsBox;
    private JDialog dialog;


    public ChooseFromListDialog(String title, String response, String[] listOfItems,
                                ChooseFromListListener okListener) throws HeadlessException {
        dialog = new JDialog(GameState.getGameState().getMainFrame(), title, true);
        dialog.setTitle(title);
        dialog.setSize(400, 150);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);
//        dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

        dialog.setLayout(new GridLayout(0, 1, 5, 5));
        JPanel buttons = new JPanel(new GridLayout(1, 0, 5, 5));

        itemsBox = new JComboBox(listOfItems);

        JLabel message = new JLabel("<html>" + response + "</html>", SwingConstants.CENTER);

        JButton yesButton = new JButton("OK");
        yesButton.setPreferredSize(new Dimension(50, 25));
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String choice = (String) itemsBox.getSelectedItem();
                ChooseFromListEvent chooseFromListEvent = new ChooseFromListEvent(this, choice);
                if (okListener != null) {
                    okListener.ChooseFromListOccurred(chooseFromListEvent);
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
        itemsContainer.add(itemsBox);

        JPanel buttonsContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        buttonsContainer.add(buttons);

        dialog.add(messageContainer);
        dialog.add(itemsContainer);
        dialog.add(buttonsContainer);

        dialog.setVisible(true);
    }
}
