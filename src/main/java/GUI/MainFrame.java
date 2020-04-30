package GUI;

import GUI.Events.*;
import GUI.GamePanels.LoginPanel;
import GUI.GamePanels.MainMenuPanel;
import GUI.Listeners.*;
import GameHandler.GameState;
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
//        1550 x 878
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

        mainMenuPanel.setLogoutListener(new LogoutListener() {
            @Override
            public void logoutEventOccurred(LogoutEvent logoutEvent) {
                MainMenu.getMainMenu().logout();
            }
        });

        mainMenuPanel.setExitListener(new ExitListener() {
            @Override
            public void exitEventOccurred(ExitEvent exitEvent) {
                MainMenu.getMainMenu().exit();
                close();
            }
        });

        mainMenuPanel.setChangePlaceListener(new ChangePlaceListener() {
            @Override
            public void ChangePlaceOccurred(ChangePlaceEvent changePlaceEvent) {
                updatePage(changePlaceEvent.getDestination());
            }
        });

        panelCards.add(mainMenuPanel, "MainMenu");
    }

    public void updatePage(Place place) {
        if (place instanceof SignInOrSignUp) {
            cardLayout.show(panelCards, "SignInOrSignUp");
        } else if (place instanceof MainMenu) {
            cardLayout.show(panelCards, "MainMenu");
        }
    }

    private void close() {
        this.dispose();
    }

}