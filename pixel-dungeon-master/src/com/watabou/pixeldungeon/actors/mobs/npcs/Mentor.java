package com.watabou.pixeldungeon.actors.mobs.npcs;

import java.util.Random;

public class Mentor extends NPC {


    public static String name;
    public static String location;
    public static String kidnapLocation;


    public static void setMentorVariables() {
        Random random = new Random();

        int selector = random.nextInt(4 - 1) + 1;

        switch (selector) {
            case 1:
                name = "Cameron";
                break;
            case 2:
                name = "Tayla";
                break;
            case 3:
                name = "Mike";
                break;
        }

        selector = random.nextInt(4 - 1) + 1;

        switch (selector) {
            case 1:
                location = "Forest";
                break;
            case 2:
                location = "Forest";
                break;
            case 3:
                location = "Forest";
                break;
        }

        selector = random.nextInt(4 - 1) + 1;

        switch (selector) {
            case 1:
                kidnapLocation = "Caves";
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
