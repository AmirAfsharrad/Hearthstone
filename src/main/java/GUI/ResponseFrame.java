package GUI;

import GUI.Events.LoginEvent;
import GUI.Events.SignUpEvent;
import GUI.GamePanels.LoginPanel;
import GUI.Listeners.LoginListener;
import GUI.Listeners.SignUpListener;
import Places.SignInOrSignUp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class ResponseFrame extends JFrame {
    private JPanel panelCards;
    CardLayout cardLayout = new CardLayout();

    public ResponseFrame(String title, String response) throws HeadlessException {
        this.setTitle(title);
        this.setSize(400,100);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);

        JLabel message = new JLabel("<html>"+ response +"</html>", SwingConstants.CENTER);
        this.add(message);

        JButton okButton = new JButton("OK");
        this.add(okButton, BorderLayout.PAGE_END);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                close();
            }
        });
    }

    void close() {
        this.dispose();
    }
}