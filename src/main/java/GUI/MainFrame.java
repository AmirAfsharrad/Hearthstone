package GUI;

import GUI.Events.*;
import GUI.GamePanels.LoginPanel;
import GUI.GamePanels.MainMenuPanel;
import GUI.GamePanels.StorePanel;
import GUI.Listeners.*;
import GameHandler.GameState;
import Places.MainMenu;
import Places.Place;
import Places.SignInOrSignUp;
import Places.Store;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private JPanel panelCards;
    CardLayout cardLayout = new CardLayout();
    private LoginPanel loginPanel;
    private MainMenuPanel mainMenuPanel;
    private StorePanel storePanel;
    private ChangePlaceListener changePlaceListener = new ChangePlaceListener() {
        @Override
        public void ChangePlaceOccurred(ChangePlaceEvent changePlaceEvent) {
            GameState.getGameState().setCurrentPlace(changePlaceEvent.getDestination());
            updatePage(changePlaceEvent.getDestination());
        }
    };
    private ExitListener exitListener = new ExitListener() {
        @Override
        public void exitEventOccurred(ExitEvent exitEvent) {
            MainMenu.getMainMenu().exit();
            close();
        }
    };

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

        try {
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                public void run() {
                    MainMenu.getMainMenu().exit();
                }
            }, "Shutdown-thread"));
        } catch (Exception e) {
            e.printStackTrace();
        }
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

        mainMenuPanel.setExitListener(exitListener);

        mainMenuPanel.setChangePlaceListener(changePlaceListener);

        panelCards.add(mainMenuPanel, "MainMenu");
    }

    void initStorePanel() {
        storePanel = new StorePanel(this.getSize().width, this.getSize().height);
        storePanel.setChangePlaceListener(changePlaceListener);
        storePanel.setExitListener(exitListener);

        panelCards.add(storePanel, "Store");
    }

    public void updatePage(Place place) {
        if (place instanceof SignInOrSignUp) {
            cardLayout.show(panelCards, "SignInOrSignUp");
        } else if (place instanceof MainMenu) {
            cardLayout.show(panelCards, "MainMenu");
        } else if (place instanceof Store) {
            initStorePanel();
            cardLayout.show(panelCards, "Store");
        }
    }

    private void close() {
        this.dispose();
    }

    public void refresh() {
        updatePage(GameState.getGameState().getCurrentPlace());
    }

}