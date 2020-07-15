package GameLogic;

import Cards.Card;
import Cards.Minion;
import Cards.Spell;
import GUI.MainFrame;
import GameHandler.GameHandler;
import GameLogic.Interfaces.Damageable;
import GameLogic.Visitors.DealDamageVisitor;
import GameLogic.Visitors.GiveHealthVisitor;
import Places.Playground;
import UserHandle.Contestant;

import java.util.Random;

public class PlayCards {
    public static void playSpell(Spell spell) {
        Contestant contestant = Playground.getPlayground().getCurrentContestant();
        contestant.setCurrentSpell(spell);
        Damageable target = null;
        if (spell.getTarget()[0] == 1) {
            contestant.setWaitingForTarget(true);
            System.out.println("WAITING for target");
            while (contestant.getTarget() == null ||
                    (spell.getTarget()[1] == 0 && contestant.getTarget().getContestant() == contestant) ||
                    (spell.getTarget()[1] == 1 && contestant.getTarget().getContestant() != contestant)) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("target received");
            System.out.println("target = " + contestant.getTarget().getName());
            target = contestant.getTarget();
            contestant.setTarget(null);
            contestant.setWaitingForTarget(false);
        }

        if (spell.getDamage()[0] != 0) {
            int damageValue = spell.getDamage()[0];
            switch (spell.getDamage()[1]) {
                case 0:
                    if (target != null) {
                        target.acceptDamage(DealDamageVisitor.getInstance(), damageValue);
                    }
                    break;
                case 1:
                    for (Card card : Playground.getPlayground().getOpponentContestant().getPlanted()) {
                        ((Minion) card).acceptDamage(DealDamageVisitor.getInstance(), damageValue);
                    }
                    break;
                case 2:
                    for (int i = 0; i < damageValue; i++) {
                        Random random = new Random();
                        int rand = random.nextInt(Playground.getPlayground().getOpponentContestant().getPlanted().size());
                        ((Minion) Playground.getPlayground().getOpponentContestant().getPlanted().get(rand)).acceptDamage
                                (DealDamageVisitor.getInstance(), 1);
                    }
                    break;
            }
        }
    }
}
