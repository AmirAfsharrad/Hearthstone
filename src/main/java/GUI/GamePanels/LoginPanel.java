package GUI.GamePanels;

import GUI.Events.LoginEvent;
import GUI.Events.SignUpEvent;
import GUI.Events.TestEvent;
import GUI.Listeners.LoginListener;
import GUI.Listeners.SignUpListener;
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
    SignUpListener signUpListener;
    LoginListener loginListener;
    JButton logInButton;
    JButton signUpButton;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;


    public LoginPanel(int screenWidth, int screenHeight) {
        super(screenWidth, screenHeight);
        initUsernameLabel();
        initUsernameField();
        initPasswordLabel();
        initPasswordField();
        initLoginButton();
        initSignUpButton();
    }

    private void initUsernameLabel() {
        usernameLabel = new JLabel("Username ");

        usernameLabel.setBounds(screenWidth / 2 - screenWidth / 20, screenHeight / 2 - 6 * screenHeight / 40
                , screenWidth / 8, screenHeight / 20);
        this.add(usernameLabel);
    }

    private void initUsernameField() {
        usernameField = new JTextField(20);

        usernameField.setBounds(screenWidth / 2, screenHeight / 2 - 6 * screenHeight / 40
                , screenWidth / 8, screenHeight / 20);
        this.add(usernameField);
    }

    private void initPasswordLabel() {
        passwordLabel = new JLabel("Password ");

        passwordLabel.setBounds(screenWidth / 2 - screenWidth / 20, screenHeight / 2 - 3 * screenHeight / 40
                , screenWidth / 8, screenHeight / 20);
        this.add(passwordLabel);
    }

    private void initPasswordField() {
        passwordField = new JPasswordField(20);

        passwordField.setBounds(screenWidth / 2, screenHeight / 2 - 3 * screenHeight / 40
                , screenWidth / 8, screenHeight / 20);
        this.add(passwordField);
    }

    private void initLoginButton() {
        logInButton = new JButton("Log In");
        logInButton.setBounds(screenWidth / 2 + screenWidth / 20 - logInButton.getPreferredSize().width / 2,
                screenHeight / 2 + logInButton.getPreferredSize().height,
                logInButton.getPreferredSize().width, logInButton.getPreferredSize().height);
        this.add(logInButton);

        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String username = usernameField.getText();
                String password = passwordField.getText();

                LoginEvent loginEvent = new LoginEvent(this, username, password);
                if (loginListener != null) {
                    loginListener.loginEventOccurred(loginEvent);
                }
            }
        });
    }

    private void initSignUpButton() {
        signUpButton = new JButton("Create New Account");
        signUpButton.setBounds(screenWidth / 2 + screenWidth / 20 - signUpButton.getPreferredSize().width / 2,
                screenHeight / 2 + 5 * signUpButton.getPreferredSize().height / 2,
                signUpButton.getPreferredSize().width, signUpButton.getPreferredSize().height);
        this.add(signUpButton);

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String username = usernameField.getText();
                String password = passwordField.getText();

                SignUpEvent signUpEvent = new SignUpEvent(this, username, password);
                if (signUpListener != null) {
                    signUpListener.signUpEventOccurred(signUpEvent);
                }
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
        g2d.drawImage(image, 0, 0, null);
    }

    public void setSignUpListener (SignUpListener signUpListener) {
        this.signUpListener = signUpListener;
    }

    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }
}
