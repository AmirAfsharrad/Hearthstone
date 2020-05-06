package GUI.GamePanels;

import GUI.Events.ChangePlaceEvent;
import GUI.Events.ExitEvent;
import GUI.Events.LoginEvent;
import GUI.Events.LogoutEvent;
import GUI.Listeners.*;
import Places.Collections;
import Places.InfoPassive;
import Places.Playground;
import Places.Store;
import Utilities.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MainMenuPanel extends GamePanel {
    private JButton exit;
    private JButton logout;
    private JButton play;
    private JButton store;
    private JButton status;
    private JButton collections;
    private JButton setting;
    private int interButtonSpace = (screenHeight + screenWidth) / 100;
    private int standardButtonWidth = screenWidth / 10 - interButtonSpace / 2;
    private int standardButtonHeight = screenHeight / 15;
    private int xPositionOfButtons = screenWidth / 2 - screenWidth / 10;
    private LogoutListener logoutListener;
    private ExitListener exitListener;
    private ChangePlaceListener changePlaceListener;

    public MainMenuPanel(int screenWidth, int screenHeight) {
        super(screenWidth, screenHeight);
        initPlayButton();
        initCollectionButton();
        initStoreButton();
        initSettingButton();
        initStatusButton();
        initLogoutButton();
        initExitButton();
    }

    private void initPlayButton() {
        play = new JButton("Play");
        play.setBounds(xPositionOfButtons,
                screenHeight / 2 - screenHeight / 5,
                2 * screenWidth / 10, screenHeight / 10);

        this.add(play);

        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ChangePlaceEvent changePlaceEvent = new ChangePlaceEvent(this, InfoPassive.getInfoPassive());
                if (changePlaceListener != null) {
                    try {
                        changePlaceListener.ChangePlaceOccurred(changePlaceEvent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void initCollectionButton() {
        collections = new JButton("Collection");
        collections.setBounds(xPositionOfButtons,
                screenHeight / 2 - screenHeight / 5 + screenHeight / 10 + interButtonSpace,
                standardButtonWidth, standardButtonHeight);
        this.add(collections);

        collections.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ChangePlaceEvent changePlaceEvent = new ChangePlaceEvent(this, Collections.getCollections());
                if (changePlaceListener != null) {
                    try {
                        changePlaceListener.ChangePlaceOccurred(changePlaceEvent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void initStoreButton() {
        store = new JButton("Store");
        store.setBounds(xPositionOfButtons + standardButtonWidth + interButtonSpace,
                screenHeight / 2 - screenHeight / 5 + screenHeight / 10 + interButtonSpace,
                standardButtonWidth, standardButtonHeight);
        this.add(store);

        store.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ChangePlaceEvent changePlaceEvent = new ChangePlaceEvent(this, Store.getStore());
                if (changePlaceListener != null) {
                    try {
                        changePlaceListener.ChangePlaceOccurred(changePlaceEvent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void initSettingButton() {
        setting = new JButton("Setting");
        setting.setBounds(xPositionOfButtons,
                screenHeight / 2 - screenHeight / 5 + screenHeight / 10 + 2 * interButtonSpace + standardButtonHeight,
                standardButtonWidth, standardButtonHeight);
        this.add(setting);

        setting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
    }

    private void initStatusButton() {
        status = new JButton("Status");
        status.setBounds(xPositionOfButtons + standardButtonWidth + interButtonSpace,
                screenHeight / 2 - screenHeight / 5 + screenHeight / 10 + 2 * interButtonSpace + standardButtonHeight,
                standardButtonWidth, standardButtonHeight);
        this.add(status);

        status.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
    }

    private void initLogoutButton() {
        logout = new JButton("Log Out");
        logout.setBounds(xPositionOfButtons,
                screenHeight / 2 - screenHeight / 5 + screenHeight / 10 + 3 * interButtonSpace + 2 * standardButtonHeight,
                standardButtonWidth, standardButtonHeight);
        this.add(logout);

        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                LogoutEvent logoutEvent = new LogoutEvent(this);
                if (logoutListener != null) {
                    try {
                        logoutListener.logoutEventOccurred(logoutEvent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void initExitButton() {
        exit = new JButton("Exit");
        exit.setBounds(xPositionOfButtons + standardButtonWidth + interButtonSpace,
                screenHeight / 2 - screenHeight / 5 + screenHeight / 10 + 3 * interButtonSpace + 2 * standardButtonHeight,
                standardButtonWidth, standardButtonHeight);
        this.add(exit);

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ExitEvent exitEvent = new ExitEvent(this);
                if (exitListener != null) {
                    exitListener.exitEventOccurred(exitEvent);
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
        BufferedImage image = ImageLoader.getInstance().loadImage("mainmenu background.jpg");
        g2d.drawImage(image, 0, 0, null);
    }

    public void setLogoutListener(LogoutListener logoutListener) {
        this.logoutListener = logoutListener;
    }

    public void setExitListener(ExitListener exitListener) {
        this.exitListener = exitListener;
    }

    public void setChangePlaceListener(ChangePlaceListener changePlaceListener) {
        this.changePlaceListener = changePlaceListener;
    }
}


