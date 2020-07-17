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
    private PlaygroundPanel playgroundPanel;
    private ChangePlaceListener changePlaceListener = new ChangePlaceListener() {
        @Override
        public void ChangePlaceOccurred(ChangePlaceEvent changePlaceEvent) throws IOException {
            GameState.getGameState().setCurrentPlace(changePlaceEvent.getDestination());
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
        LoginPanel loginPanel = new LoginPanel(this.getSize().width, this.getSize().height);
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
        MainMenuPanel mainMenuPanel = new MainMenuPanel(this.getSize().width, this.getSize().height);

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
        InfoPassivePanel infoPassivePanel = new InfoPassivePanel(this.getSize().width, this.getSize().height);
        panelCards.add(infoPassivePanel, "InfoPassive");
        infoPassivePanel.setPassiveChosenListener(new PassiveChosenListener() {
            @Override
            public void passiveChosenOccurred(PassiveChosenEvent passiveChosenEvent) throws IOException {
                Playground.getPlayground().getContestant0().setPassive(passiveChosenEvent.getPassive());
                GameState.getGameState().setCurrentPlace(Playground.getPlayground());
//                updatePage(Playground.getPlayground());
            }
        });
    }

    private void initStorePanel() throws IOException {
        StorePanel storePanel = new StorePanel(this.getSize().width, this.getSize().height);
        storePanel.setChangePlaceListener(changePlaceListener);
        storePanel.setExitListener(exitListener);
        storePanel.setFilterListener(new FilterListener() {
            @Override
            public void filterOccurred(FilterEvent filterEvent) {
                Store.getStore().filterDisplayedCards(filterEvent.getMana(),
                        filterEvent.getHeroClass(), filterEvent.getSearchString(),
                        filterEvent.getDoesOwn());
            }
        });

        panelCards.add(storePanel, "Store");
    }

    private void initCollectionsPanel() throws IOException {
        CollectionsPanel collectionsPanel = new CollectionsPanel(this.getSize().width, this.getSize().height);
        collectionsPanel.setChangePlaceListener(changePlaceListener);
        collectionsPanel.setExitListener(exitListener);
        collectionsPanel.setFilterListener(new FilterListener() {
            @Override
            public void filterOccurred(FilterEvent filterEvent) {
                Collections.getCollections().filterDisplayedCards(filterEvent.getMana(),
                        filterEvent.getHeroClass(), filterEvent.getSearchString(),
                        filterEvent.getDoesOwn());
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

        panelCards.add(collectionsPanel, "Collections");
    }

    private void initPlaygroundPanel() throws IOException {
        Playground.getPlayground().initGame(GameState.getGameState().getUser().getSelectedDeck());
        playgroundPanel = new PlaygroundPanel(this.getSize().width, this.getSize().height);
        panelCards.add(playgroundPanel, "Playground");
        playgroundPanel.setEndTurnListener(new EndTurnListener() {
            @Override
            public void endTurnOccurred(EndTurnEvent endTurnEvent) {
                Playground.getPlayground().nextTurn();
            }
        });
        playgroundPanel.setExitListener(exitListener);
        playgroundPanel.setChangePlaceListener(changePlaceListener);
        playgroundPanel.setPlayCardEventListener(new PlayCardListener() {
            @Override
            public void PlayCardOccurred(PlayCardEvent playCardEvent) {
                Playground.getPlayground().getCurrentContestant().playCard(playCardEvent.getCard(),
                        playCardEvent.getNumberOnLeft());
            }
        });
        playgroundPanel.setPlantedCardPressedListener(new PlantedCardPressedListener() {
            @Override
            public void plantedCardPressedEventOccurred(PlantedCardPressedEvent plantedCardPressedEvent) throws IOException {
                Playground.getPlayground().manageSelectedPlantedCard(plantedCardPressedEvent);
            }
        });
        playgroundPanel.setChoiceOfCardSelectionListener(new ChoiceOfCardSelectionListener() {
            @Override
            public void choiceOfCardSelectionEventOccurred(ChoiceOfCardSelectionEvent choiceOfCardSelectionEvent) throws IOException {
                Playground.getPlayground().getContestant0().initialHandModification(choiceOfCardSelectionEvent.getCard());
            }
        });
        playgroundPanel.setChoiceOfWeaponListener(new ChoiceOfWeaponListener() {
            @Override
            public void choiceOfWeaponEventOccurred(ChoiceOfCardSelectionEvent choiceOfCardSelectionEvent) {
                Playground.getPlayground().getCurrentContestant().setChoiceOfWeapon(choiceOfCardSelectionEvent.getCard());
            }
        });
    }

    private void initStatusPanel() throws IOException {
        StatusPanel statusPanel = new StatusPanel(this.getSize().width, this.getSize().height);
        statusPanel.setChangePlaceListener(changePlaceListener);
        statusPanel.setExitListener(exitListener);
        panelCards.add(statusPanel, "Status");

    }

    public void updatePage(Place place) throws IOException {
        if (place instanceof SignInOrSignUp) {
            cardLayout.show(panelCards, "SignInOrSignUp");
        } else if (place instanceof MainMenu) {
            cardLayout.show(panelCards, "MainMenu");
        } else if (place instanceof Store) {
            Store.getStore().resetDisplayedCards();
            initStorePanel();
            cardLayout.show(panelCards, "Store");
        } else if (place instanceof Collections) {
            Collections.getCollections().resetDisplayedCards();
            initCollectionsPanel();
            cardLayout.show(panelCards, "Collections");
        } else if (place instanceof Playground) {
            initPlaygroundPanel();
            cardLayout.show(panelCards, "Playground");
        } else if (place instanceof Status) {
            initStatusPanel();
            cardLayout.show(panelCards, "Status");
        } else if (place instanceof InfoPassive) {
            if (GameState.getGameState().getUser().getSelectedDeck() == null) {
                RespondToUser.respond("You have to select a deck to play!", true);
                GameState.getGameState().setCurrentPlace(Collections.getCollections());
                return;
            }
            initInfoPassivePanel();
            cardLayout.show(panelCards, "InfoPassive");
        }
    }

    public PlaygroundPanel getPlaygroundPanel() {
        return playgroundPanel;
    }

    private void close() {
        this.dispose();
    }

    public void refresh() throws IOException {
        if (GameState.getGameState().getCurrentPlace() == Playground.getPlayground()) {
            playgroundPanel.refresh();
        } else {
            updatePage(GameState.getGameState().getCurrentPlace());
        }
    }
}