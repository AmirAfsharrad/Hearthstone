package GUI;

import GUI.Events.LoginEvent;
import GUI.Events.SignUpEvent;
import GUI.GamePanels.LoginPanel;
import GUI.GamePanels.MainMenuPanel;
import GUI.Listeners.LoginListener;
import GUI.Listeners.SignUpListener;
import Places.MainMenu;
import Places.Place;
import Places.SignInOrSignUp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private JPanel panelCards;
    CardLayout cardLayout = new CardLayout();
    private LoginPanel loginPanel;
    private MainMenuPanel mainMenuPanel;

    public MainFrame() throws HeadlessException {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("CardLayout Example");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        panelCards = new JPanel(cardLayout);

        initLoginPanel();
        initMainMenuPanel();

        this.add(panelCards);
    }

    private void initLoginPanel() {
        loginPanel = new LoginPanel(this.getSize().width, this.getSize().height);
        loginPanel.setSignUpListener(new SignUpListener() {
            @Override
            public void signUpEventOccurred(SignUpEvent signUpEvent) {
                SignInOrSignUp.getSignInOrSignUp().createNewAccount(signUpEvent.getUsername(), signUpEvent.getPassword());
            }
        });

        loginPanel.setLoginListener(new LoginListener() {
            @Override
            public void loginEventOccurred(LoginEvent loginEvent) {
                SignInOrSignUp.getSignInOrSignUp().login(loginEvent.getUsername(), loginEvent.getPassword());
            }
        });

        panelCards.add(loginPanel, "SignInOrSignUp");
    }

    private void initMainMenuPanel() {
        mainMenuPanel = new MainMenuPanel(this.getSize().width, this.getSize().height);

        panelCards.add(mainMenuPanel, "MainMenu");
    }

    public void updatePage(Place place) {
        if (place instanceof SignInOrSignUp) {
            cardLayout.show(panelCards, "SignInOrSignUp");
        } else if (place instanceof MainMenu) {
            cardLayout.show(panelCards, "MainMenu");
        }

    }

}