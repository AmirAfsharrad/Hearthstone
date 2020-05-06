package GUI;

import GUI.Events.*;
import GUI.GamePanels.*;
import GUI.Listeners.*;
import GameHandler.GameState;
import GameHandler.RespondToUser;
import Places.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainFrame extends JFrame {
    private JPanel panelCards;
    private CardLayout cardLayout = new CardLayout();
    private LoginPanel loginPanel;
    private MainMenuPanel mainMenuPanel;
    private StorePanel storePanel;
    private CollectionsPanel collectionsPanel;
    private PlaygroundPanel playgroundPanel;
    private InfoPassivePanel infoPassivePanel;
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

    public MainFrame() throws HeadlessException, IOException {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Hearthstone");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        panelCards = new JPanel(cardLayout);

        initLoginPanel();
        initMainMenuPanel();

//        initPlaygroundPanel();

        this.add(panelCards);
        System.out.println(this.getSize().width+ "  " + this.getSize().height);

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

    private void initMainMenuPanel() throws IOException {
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

    private void initInfoPassivePanel() throws IOException {
        infoPassivePanel = new InfoPassivePanel(this.getSize().width, this.getSize().height);
        panelCards.add(infoPassivePanel, "InfoPassive");
        infoPassivePanel.setPassiveChosenListener(new PassiveChosenListener() {
            @Override
            public void passiveChosenOccurred(PassiveChosenEvent passiveChosenEvent) throws IOException {
                Playground.getPlayground().setPassive(passiveChosenEvent.getPassive());
                updatePage(Playground.getPlayground());
            }
        });
    }

    private void initStorePanel() throws IOException {
        storePanel = new StorePanel(this.getSize().width, this.getSize().height);
        storePanel.setChangePlaceListener(changePlaceListener);
        storePanel.setExitListener(exitListener);
        storePanel.setCollectionsFilterListener(new CollectionsFilterListener() {
            @Override
            public void CollectionsFilterOccurred(CollectionsFilterEvent collectionsFilterEvent) {
                Collections.getCollections().filterDisplayedCards(collectionsFilterEvent.getMana(),
                        collectionsFilterEvent.getHeroClass(), collectionsFilterEvent.getSearchString(),
                        collectionsFilterEvent.getDoesOwn());
            }
        });

        panelCards.add(storePanel, "Store");
    }

    private void initCollectionsPanel() throws IOException {
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
        collectionsPanel.setCreateDeckListener(new CreateDeckListener() {
            @Override
            public void CreateDeckOccurred(CreateDeckEvent createDeckEvent) {
                Collections.getCollections().createNewDeck();
            }
        });
        collectionsPanel.setAddCardToDeckListener(new AddCardToDeckListener() {
            @Override
            public void addCardToDeckOccurred(AddCardToDeckEvent addCardToDeckEvent) {
                Collections.getCollections().addCardToDeck(addCardToDeckEvent.getCard(), addCardToDeckEvent.getDeck());
            }
        });
        collectionsPanel.setRemoveCardFromDeckListener(new RemoveCardFromDeckListener() {
            @Override
            public void removeCardFromDeckOccurred(RemoveCardFromDeckEvent removeCardFromDeckEvent) {
                Collections.getCollections().removeCardFromDeck(removeCardFromDeckEvent.getCard(),
                        removeCardFromDeckEvent.getDeck());
            }
        });
        collectionsPanel.setRemoveDeckListener(new RemoveDeckListener() {
            @Override
            public void removeDeckOccurred(RemoveDeckEvent removeDeckEvent) {
                Collections.getCollections().removeDeck(removeDeckEvent.getDeck());
            }
        });
        collectionsPanel.setChangeDeckHeroListener(new ChangeDeckHeroListener() {
            @Override
            public void changeDeckHeroOccurred(ChangeDeckHeroEvent changeDeckHeroEvent) {
                Collections.getCollections().deckSetHero(changeDeckHeroEvent.getDeck());
            }
        });
        collectionsPanel.setDeckRenameListener(new DeckRenameListener() {
            @Override
            public void deckRenameOccurred(DeckRenameEvent deckRenameEvent) {
                Collections.getCollections().renameDeck(deckRenameEvent.getDeck());
            }
        });
        collectionsPanel.setSetDeckAsSelectedListener(new SetDeckAsSelectedListener() {
            @Override
            public void setDeckAsSelectedOccurred(SetDeckAsSelectedEvent setDeckAsSelectedEvent) {
                Collections.getCollections().setSelectedDeck(setDeckAsSelectedEvent.getDeck());
            }
        });
        collectionsPanel.setChangePlaceListener(changePlaceListener);

        panelCards.add(collectionsPanel, "Collections");
    }

    private void initPlaygroundPanel() {
        Playground.getPlayground().initGame(GameState.getGameState().getUser().getSelectedDeck());
        playgroundPanel = new PlaygroundPanel(this.getSize().width, this.getSize().height);
        panelCards.add(playgroundPanel, "Playground");
        playgroundPanel.setEndTurnListener(new EndTurnListener() {
            @Override
            public void endTurnOccurred(EndTurnEvent endTurnEvent) {
                Playground.getPlayground().stepOneTurn();
            }
        });
        playgroundPanel.setExitListener(exitListener);
        playgroundPanel.setChangePlaceListener(changePlaceListener);
        playgroundPanel.setPlayCardEventListener(new PlayCardListener() {
            @Override
            public void PlayCardOccurred(PlayCardEvent playCardEvent) {
                Playground.getPlayground().playCard(playCardEvent.getCard());
            }
        });
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
        } else if (place instanceof Playground) {
            initPlaygroundPanel();
            cardLayout.show(panelCards, "Playground");
        } else if (place instanceof InfoPassive) {
            if (GameState.getGameState().getUser().getSelectedDeck() == null) {
                RespondToUser.respond("You have to select a deck to play!", true);
                updatePage(Collections.getCollections());
                return;
            }
            initInfoPassivePanel();
            cardLayout.show(panelCards, "InfoPassive");
        }
    }

    private void close() {
        this.dispose();
    }

    public void refresh() throws IOException {
        updatePage(GameState.getGameState().getCurrentPlace());
    }
}