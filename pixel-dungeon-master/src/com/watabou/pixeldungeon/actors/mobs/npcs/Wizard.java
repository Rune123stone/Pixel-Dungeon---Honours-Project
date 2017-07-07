package com.watabou.pixeldungeon.actors.mobs.npcs;


import java.util.Random;

public class Wizard extends NPC{

    public static String name;
    public static String location;

    public static void setWizardVariables() {
        Random random = new Random();

        int selector = random.nextInt(4 - 1) + 1;

        switch (selector) {
            case 1:
                name = "Dumbledoor";
                break;
            case 2:
                name = "Gandalf";
                break;
            case 3:
                name = "Kadgar";
                break;
        }

        selector = random.nextInt(3 - 1) + 1;

        switch (selector) {
            case 1:
                location = "Town";
                break;
            case 2:
                location = "Forest";
                break;
        }
    }

    @Override
    public void interact() {

    }
}
