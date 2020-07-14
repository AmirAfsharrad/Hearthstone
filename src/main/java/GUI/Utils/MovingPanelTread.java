package GUI.Utils;

import javax.swing.*;
import java.awt.*;

public class MovingPanelTread extends Thread {
    private CardPanel movingCard;
    private boolean finished;

    public MovingPanelTread(CardPanel cardPanel){
        movingCard = cardPanel;
        finished = false;
    }

    public void setFinished() {
        this.finished = true;
    }

    public Point finish() {
        setFinished();
        return MouseInfo.getPointerInfo().getLocation();
    }

//    public void setCard(String cardName){
//        movingCard.setImage(cardName);
//    }
//
//    public JPanel getPanel(){
//        return movingCard.getPanel();
//    }

    @Override
    public void run() {
        Point p;
        while (!finished) {
            p = MouseInfo.getPointerInfo().getLocation();
//            movingCard.setLocation(movingCard.getAdjustedLocation(p));
            movingCard.setLocation(p);
        }
        finished = false;
        movingCard.setVisible(false);
    }
}
