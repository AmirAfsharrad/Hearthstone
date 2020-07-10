package Utilities;

import GUI.Events.TurnTimeFinishEvent;
import GUI.Listeners.TurnTimeFinishListener;
import GUI.MainFrame;
import GameHandler.GameState;
import Logger.Logger;
import Places.Playground;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TurnTimer extends Thread {
    private int waitingTime;
    private double leftRatio;
    private boolean flag;
    private long startTime;
    private TurnTimeFinishListener turnTimeFinishListener;
    private JProgressBar progressBar;

    public TurnTimer(int waitingTime, JProgressBar progressBar) {
        this.waitingTime = waitingTime;
        this.progressBar = progressBar;
    }

    @Override
    public void run() {
        flag = true;
        while (flag) {
            startTime = System.currentTimeMillis();
            progressBar.setForeground(Color.GREEN);

            while (System.currentTimeMillis() < startTime + waitingTime) {
                leftRatio = 1 - (System.currentTimeMillis() - startTime) * 1.0 / waitingTime;
                progressBar.setValue((int) (leftRatio * 100));
                if (leftRatio < 0.2) {
                    progressBar.setForeground(Color.RED);
                }
            }

            TurnTimeFinishEvent turnTimeFinishEvent = new TurnTimeFinishEvent(this);
            Logger.buttonPressLog("end turn by time");
            if (turnTimeFinishListener != null) {
                turnTimeFinishListener.turnTimeFinishEventOccurred(turnTimeFinishEvent);
            }
        }
    }

    public double getLeftRatio() {
        return leftRatio;
    }

    public void reset() {
        startTime = System.currentTimeMillis();
    }

    public void kill() {
        flag = false;
    }

    public void setTurnTimeFinishListener(TurnTimeFinishListener turnTimeFinishListener) {
        this.turnTimeFinishListener = turnTimeFinishListener;
    }
}
