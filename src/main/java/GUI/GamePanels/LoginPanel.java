package GUI.GamePanels;

import GUI.Events.TestEvent;
import GUI.Listeners.TestListener;
import GUI.MainFrame;
import Utilities.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;

public class LoginPanel extends GamePanel {
    TestListener testListener;
    JButton logInButton;
    JButton signUpButton;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;


    public LoginPanel(int screenWidth, int screenHeight) {
        super(screenWidth, screenHeight);

        usernameLabel = new JLabel("Username ");
        passwordLabel = new JLabel("Password ");
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        

        usernameField.setBounds(screenWidth / 2, screenHeight / 2 - 6 * screenHeight / 40
                , screenWidth / 8, screenHeight / 20);
        this.add(usernameField);

        passwordField.setBounds(screenWidth / 2, screenHeight / 2 - 3 * screenHeight / 40
                , screenWidth / 8, screenHeight / 20);
        this.add(passwordField);

        usernameLabel.setBounds(screenWidth / 2 - screenWidth / 20, screenHeight / 2 - 6 * screenHeight / 40
                , screenWidth / 8, screenHeight / 20);
        this.add(usernameLabel);

        passwordLabel.setBounds(screenWidth / 2 - screenWidth / 20, screenHeight / 2 - 3 * screenHeight / 40
                , screenWidth / 8, screenHeight / 20);
        this.add(passwordLabel);




        logInButton = new JButton("Log In");
        logInButton.setBounds(screenWidth / 2 + screenWidth / 20 - logInButton.getPreferredSize().width / 2,
                screenHeight / 2 + logInButton.getPreferredSize().height,
                logInButton.getPreferredSize().width, logInButton.getPreferredSize().height);
        this.add(logInButton);

        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String username = usernameField.getText();
//                TestEvent testEvent = new TestEvent(this, "test");
                System.out.println("Hello " + username);
            }
        });

        signUpButton = new JButton("Create New Account");
//        Dimension signUpSize = signUpButton.getPreferredSize();
        signUpButton.setBounds(screenWidth / 2 + screenWidth / 20 - signUpButton.getPreferredSize().width / 2,
                screenHeight / 2 + 5 * signUpButton.getPreferredSize().height / 2,
                signUpButton.getPreferredSize().width, signUpButton.getPreferredSize().height);
        this.add(signUpButton);

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String username = usernameField.getText();
//                TestEvent testEvent = new TestEvent(this, "test");
                System.out.println("Hello " + username);
            }
        });

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        BufferedImage image = ImageLoader.getInstance().loadImage("game background2.jpg");
//        g2d.drawImage(image, 0, 0, null);
    }

    public void setTestListener(TestListener testListener) {
        this.testListener = testListener;
    }
}
