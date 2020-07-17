package GameLogic;

import Cards.Card;
import Cards.Minion;
import Cards.Spell;
import Cards.Weapon;
import GUI.MainFrame;
import GameHandler.GameHandler;
import GameHandler.GameState;
import GameLogic.Interfaces.Damageable;
import GameLogic.Interfaces.HealthTaker;
import GameLogic.Visitors.DealDamageVisitor;
import GameLogic.Visitors.GiveHealthVisitor;
import GameLogic.Visitors.ModifyAttackVisitor;
import Heroes.Hero;
import Places.Playground;
import UserHandle.Contestant;

import java.io.IOException;
import java.util.Random;

public class PlayCards {
    public static void playSpell(Spell spell) throws IOException, InterruptedException {
        Contestant contestant = Playground.getPlayground().getCurrentContestant();
        Contestant opponentContestant = Playground.getPlayground().getOpponentContestant();
        contestant.setCurrentSpell(spell);
        Object target = null;

        if (spell.getName().equals("Sunreaver Warmage Battlecry")) {
            boolean flag = false;
            for (Card card : contestant.getHand()) {
                if (card.getType().equals("Spell") && card.getMana() >= 5) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                contestant.setWaitingForTarget(false);
                contestant.setCurrentSpell(null);
                return;
            }
        }

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

        Playground.getPlayground().getContestant0().checkForDeadMinions();
        Playground.getPlayground().getContestant1().checkForDeadMinions();

        if (spell.getDraw()[0] != 0) {
            int drawValue = spell.getDraw()[0];
            boolean noSpell = spell.getDraw()[1] == 1;
            for (int i = 0; i < drawValue; i++) {
                contestant.drawRandomCard(noSpell);
            }
        }

        if (spell.getName().equals("Polymorph")) {
            int index = opponentContestant.getPlantedCardIndex((Card) target);
            opponentContestant.getPlanted().remove(index);
            Card card = GameHandler.getGameHandler().getCard("Sheep");
            card.setContestant(opponentContestant);
            opponentContestant.getPlanted().add(index, card);
        }

        if (spell.getName().equals("Swarm of locusts")) {
            int limit = opponentContestant.getPlanted().size();
            for (int i = 0; i < 7 - limit; i++) {
                System.out.println("Locust i = " + i);
                Card card = GameHandler.getGameHandler().getCard("Locust");
                card.setContestant(opponentContestant);
                opponentContestant.getPlanted().add(card);
            }
        }

        if (spell.getName().equals("Shadowblade Battlecry")) {
            contestant.setImmuneHero(true);
        }

        if (spell.getName().equals("Tomb warden Battlecry")) {
            if (contestant.getPlanted().size() < 7)
                contestant.getPlanted().add(((Card) target).clone());
        }

        if (spell.getName().equals("Sathrovarr Battlecry")) {
            contestant.getHand().add(((Card) target).clone());
            contestant.getDeck().add(((Card) target).clone());
            if (contestant.getPlanted().size() < 7)
                contestant.getPlanted().add(((Card) target).clone());
        }

        if (spell.getName().equals("Friendly Smith")) {
            GameState.getGameState().getMainFrame().getPlaygroundPanel().selectWeapon();
            while (contestant.getChoiceOfWeapon() == null){
                Thread.sleep(100);
            }
            Weapon weapon = (Weapon) contestant.getChoiceOfWeapon();
            contestant.setChoiceOfWeapon(null);
            weapon.acceptHealth(GiveHealthVisitor.getInstance(), 2, false, false);
            weapon.acceptAttackModification(ModifyAttackVisitor.getInstance(), 2);
            weapon.setContestant(contestant);
            contestant.getDeck().add(weapon);
        }

        if (spell.getDestroy()[0] != 0) {
            if (spell.getDestroy()[1] == 0) {
                int index = opponentContestant.getPlantedCardIndex((Card) target);
                opponentContestant.getPlanted().remove(index);
            } else {
                opponentContestant.getPlanted().clear();
            }
        }

        if (spell.getSetAbilities()[0] != 0) {
            ((Minion) target).setAbilities(spell.getSetAbilities());
        }

        if (spell.getModifyAttack()[0] != 0) {
            int attackChangeValue = spell.getModifyAttack()[0];
            ((Minion) target).acceptAttackModification(ModifyAttackVisitor.getInstance(), attackChangeValue);
        }

        contestant.setCurrentSpell(null);
        GameState.getGameState().refreshMainFrame();
    }
}
