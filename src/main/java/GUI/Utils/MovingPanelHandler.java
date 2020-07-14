package GUI.Utils;

import javax.swing.*;
import java.awt.*;

public class MovingPanelHandler extends Thread {
    private CardPanel movingCard;
    private boolean finished;

    public MovingPanelHandler(CardPanel cardPanel){
        movingCard = cardPanel;
        finished = false;
    }

    public void setFinished() {
        this.finished = true;
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
            movingCard.setLocation(movingCard.getAdjustedLocation(p));
//            movingCard.setLocation(p);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        finished = false;
        movingCard.setVisible(false);
    }
}
