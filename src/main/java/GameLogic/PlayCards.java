package GameLogic;

import Cards.Card;
import Cards.Minion;
import Cards.Spell;
import GUI.MainFrame;
import GameHandler.GameHandler;
import GameLogic.Interfaces.Damageable;
import GameLogic.Interfaces.HealthTaker;
import GameLogic.Visitors.DealDamageVisitor;
import GameLogic.Visitors.GiveHealthVisitor;
import Heroes.Hero;
import Places.Playground;
import UserHandle.Contestant;

import java.util.Random;

public class PlayCards {
    public static void playSpell(Spell spell) {
        Contestant contestant = Playground.getPlayground().getCurrentContestant();
        contestant.setCurrentSpell(spell);
        Object target = null;
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
                        ((Damageable) target).acceptDamage(DealDamageVisitor.getInstance(), damageValue);
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

        if (spell.getGiveHealth()[0] != 0) {
            int healthValue = spell.getGiveHealth()[0];
            boolean multiplicative = spell.getGiveHealth()[2] == 1;
            boolean restore = spell.getGiveHealth()[3] == 0;
            switch (spell.getGiveHealth()[1]) {
                case 0 :
                    if (target != null) {
                        ((HealthTaker) target).acceptHealth(GiveHealthVisitor.getInstance(), healthValue, multiplicative, restore);
                    }
                    break;
                case 1:
                    Hero hero = Playground.getPlayground().getCurrentContestant().getHero();
                    hero.acceptHealth(GiveHealthVisitor.getInstance(), healthValue, multiplicative, restore);
                    break;
            }
        }
    }
}
