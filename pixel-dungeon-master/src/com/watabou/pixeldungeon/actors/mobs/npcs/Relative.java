package com.watabou.pixeldungeon.actors.mobs.npcs;

import java.util.Random;

public class Relative extends NPC{

    public static String name;
    public static String location;
    public static String kidnapLocation;


    public static void setRelativeVariables() {
        Random random = new Random();

        int selector = random.nextInt(5 - 1) + 1;

        switch (selector) {
            case 1:
                name = "Mother";
                break;
            case 2:
                name = "Father";
                break;
            case 3:
                name = "Brother";
                break;
            case 4:
                name = "Sister";
        }

        selector = random.nextInt(3 - 1) + 1;

        switch (selector) {
            case 1:
                location = "Forest";
                break;
            case 2:
                location = "Town";
                break;
        }

        selector = random.nextInt(4 - 1) + 1;

        switch (selector) {
            case 1:
                kidnapLocation = "Cave";
                break;
            case 2:
                kidnapLocation = "Fields";
                break;
            case 3:
                kidnapLocation = "Castle";
                break;
        }

    }

    @Override
    public void interact() {

    }
}
