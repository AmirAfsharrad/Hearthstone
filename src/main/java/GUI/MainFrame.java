package GUI;

import GUI.Events.*;
import GUI.GamePanels.CollectionsPanel;
import GUI.GamePanels.LoginPanel;
import GUI.GamePanels.MainMenuPanel;
import GUI.GamePanels.StorePanel;
import GUI.Listeners.*;
import GameHandler.GameState;
import Places.*;
import com.google.gson.internal.bind.util.ISO8601Utils;
import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainFrame extends JFrame {
    private JPanel panelCards;
    CardLayout cardLayout = new CardLayout();
    private LoginPanel loginPanel;
    private MainMenuPanel mainMenuPanel;
    private StorePanel storePanel;
    private CollectionsPanel collectionsPanel;
    private ChangePlaceListener changePlaceListener = new ChangePlaceListener() {
        @Override
        public void ChangePlaceOccurred(ChangePlaceEvent changePlaceEvent) throws IOException {
            GameState.getGameState().setCurrentPlace(changePlaceEvent.getDestination());
//            updatePage(changePlaceEvent.getDestination());
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
        this.setTitle("Hearthstone");
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
            public void signUpEventOccurred(SignUpEvent signUpEvent) throws IOException {
                SignInOrSignUp.getSignInOrSignUp().createNewAccount(signUpEvent.getUsername(), signUpEvent.getPassword());
            }
        });

        loginPanel.setLoginListener(new LoginListener() {
            @Override
            public void loginEventOccurred(LoginEvent loginEvent) throws IOException {
                SignInOrSignUp.getSignInOrSignUp().login(loginEvent.getUsername(), loginEvent.getPassword());
            }
        });

        panelCards.add(loginPanel, "SignInOrSignUp");
    }

    private void initMainMenuPanel() {
        mainMenuPanel = new MainMenuPanel(this.getSize().width, this.getSize().height);

        mainMenuPanel.setLogoutListener(new LogoutListener() {
            @Override
            public void logoutEventOccurred(LogoutEvent logoutEvent) throws IOException {
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

    void initCollectionsPanel() throws IOException {
        collectionsPanel = new CollectionsPanel(this.getSize().width, this.getSize().height);
        collectionsPanel.setChangePlaceListener(changePlaceListener);
        collectionsPanel.setExitListener(exitListener);
        collectionsPanel.setCollectionsFilterListener(new CollectionsFilterListener() {
            @Override
            public void CollectionsFilterOccurred(CollectionsFilterEvent collectionsFilterEvent) {
                Collections.getCollections().filterDisplayedCards(collectionsFilterEvent.getMana(),
                        collectionsFilterEvent.getHeroClass(), collectionsFilterEvent.getSearchString(),
                        collectionsFilterEvent.getDoesOwn());
            }
        });

        panelCards.add(collectionsPanel, "Collections");
    }

    public void updatePage(Place place) throws IOException {
        if (place instanceof SignInOrSignUp) {
            cardLayout.show(panelCards, "SignInOrSignUp");
        } else if (place instanceof MainMenu) {
            cardLayout.show(panelCards, "MainMenu");
        } else if (place instanceof Store) {
            initStorePanel();
            cardLayout.show(panelCards, "Store");
        } else if (place instanceof Collections) {
            initCollectionsPanel();
            cardLayout.show(panelCards, "Collections");
        }
    }

    private void close() {
        this.dispose();
    }

    public void refresh() throws IOException {
        updatePage(GameState.getGameState().getCurrentPlace());
    }

}