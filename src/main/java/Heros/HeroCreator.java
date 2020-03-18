package Heros;

import CLI.RespondToUser;

public class HeroCreator {
    public static HeroCreator heroCreator = new HeroCreator();

    private HeroCreator(){}

    public static HeroCreator getHeroCreator() {
        return heroCreator;
    }

    public Hero createHero(String heroName){
        String heroNameLower = heroName.toLowerCase();
        switch (heroNameLower){
            case "mage": return new Mage();
            case "rogue": return new Rogue();
            case "warlock": return new Warlock();
        }
        RespondToUser.respond("There is no hero named " + heroName);
        return null;
    }
}
