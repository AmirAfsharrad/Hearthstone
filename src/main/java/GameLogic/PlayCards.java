package GameLogic;

import Cards.*;
import GUI.MainFrame;
import GameHandler.GameHandler;
import GameHandler.GameState;
import GameLogic.Interfaces.Attackable;
import GameLogic.Interfaces.Damageable;
import GameLogic.Interfaces.HealthTaker;
import GameLogic.Visitors.DealDamageVisitor;
import GameLogic.Visitors.GiveHealthVisitor;
import GameLogic.Visitors.ModifyAttackVisitor;
import Heroes.Hero;
import Heroes.Priest;
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
                    (spell.getTarget()[1] == 1 && contestant.getTarget().getContestant() != contestant) ||
                    (spell.getTarget()[1] == 0 && target instanceof Minion && ((Minion) contestant.getTarget()).isStealth())) {
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
            if (contestant.getHero() instanceof Priest) {
                healthValue *= 2;
            }
            boolean multiplicative = spell.getGiveHealth()[2] == 1;
            boolean restore = spell.getGiveHealth()[3] == 0;
            switch (spell.getGiveHealth()[1]) {
                case 0:
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
            opponentContestant.summon(index, card);
        }

        if (spell.getName().equals("Swarm of locusts")) {
            int limit = opponentContestant.getPlanted().size();
            for (int i = 0; i < 7 - limit; i++) {
                System.out.println("Locust i = " + i);
                Card card = GameHandler.getGameHandler().getCard("Locust");
                card.setContestant(contestant);
                contestant.summon(card);
            }
        }

        if (spell.getName().equals("Shadowblade Battlecry")) {
            contestant.setImmuneHero(true);
        }

        if (spell.getName().equals("Tomb warden Battlecry")) {
            if (contestant.getPlanted().size() < 7)
                contestant.summon(((Card) target).clone());
        }

        if (spell.getName().equals("Sathrovarr Battlecry")) {
            contestant.getHand().add(((Card) target).clone());
            contestant.getDeck().add(((Card) target).clone());
            if (contestant.getPlanted().size() < 7)
                contestant.summon(((Card) target).clone());
        }

        if (spell.getName().equals("Friendly Smith")) {
            GameState.getGameState().getMainFrame().getPlaygroundPanel().selectWeapon();
            while (contestant.getChoiceOfWeapon() == null) {
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

    public static void playMinion(Card card, int numberOnLeft) {
        Contestant contestant = card.getContestant();
        if (contestant.getPlanted().size() < 7) {
            if (!((Minion) card).isCharge() && !((Minion) card).isRush())
                ((Minion) card).setTurnAttack(0);
//                                planted.add(getNewPlantedCardIndex(numberOnLeft), card);
            contestant.summon(contestant.getNewPlantedCardIndex(numberOnLeft), card);
            ((Minion) card).setJustPlanted(true);
            System.out.println("card name = " + card.getName());
            if (((Minion) card).hasBattlecry()) {
                try {
                    Spell battlecry = (Spell) GameHandler.getGameHandler().getCard
                            (card.getName() + " Battlecry");
                    battlecry.setContestant(Playground.getPlayground().getCurrentContestant());
                    PlayCards.playSpell(battlecry);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        Card card1 = Playground.getPlayground().getOpponentContestant().hasPlantedCard("Swamp King Dred");
        if (card1 != null) {
            System.out.println("WE DO HAVE THAT SHIT!");
            ((Minion) card1).setTurnAttack(((Minion) card1).getTurnAttack() + 1);
            ((Minion) card1).attack((Attackable) card, ((Minion) card1).getAttackPower());
            try {
                contestant.checkForDeadMinions();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void playWeapon(Weapon weapon) {
        Contestant contestant = weapon.getContestant();
        contestant.setCurrentWeapon(weapon);
        contestant.setHasWeapon(true);
        if (weapon.getName().equals("Shadowblade")) {
            contestant.setImmuneHero(true);
        }
    }

    public static void playQuest(Quest quest) {
        Contestant contestant = quest.getContestant();
        contestant.setActiveQuest(quest);
    }
}
