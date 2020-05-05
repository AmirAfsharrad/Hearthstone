package GUI.Dialogs;

import GameHandler.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResponseDialog {
    private JDialog dialog;

    public ResponseDialog(String title, String response) throws HeadlessException {
        dialog = new JDialog(GameState.getGameState().getMainFrame(), title, true);
        dialog.setTitle(title);
        dialog.setSize(400,100);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);

        JLabel message = new JLabel("<html>"+ response +"</html>", SwingConstants.CENTER);
        dialog.add(message);

        JButton okButton = new JButton("OK");
        dialog.add(okButton, BorderLayout.PAGE_END);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dialog.dispose();
            }
        });
        dialog.setVisible(true);
    }
}