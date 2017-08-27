package com.watabou.pixeldungeon.story;

import java.util.ArrayList;
import java.util.Collections;

public class QuestGiver {

    public String NPC;
    public String name;

    public ArrayList<Motive> motives;

    public ArrayList<String> namesList;

    public int xmlID;

    public QuestGiver(String name, String...names) {

        namesList = new ArrayList<>();

        addQuestGiverNames(names);

        Collections.shuffle(namesList);

        NPC = namesList.get(0);

        this.name = name;

        motives = new ArrayList<>();
    }


    public String getRandomMotiveType(String storyPhase) {

        Collections.shuffle(motives);

        while (!motives.get(0).storyPhase.equals(storyPhase)) {
            Collections.shuffle(motives);
        }

        return motives.get(0).type;

    }

    public void addQuestGiverNames(String...names) {

        for (int i = 0; i < names.length; i++) {
            namesList.add(names[i]);
        }

    }

    public void createNewMotive(String type, String storyPhase) {

        Motive motive = new Motive(type, storyPhase);

        motives.add(motive);
        //System.out.println("added motive: " +type+ " with story phase " +storyPhase);
    }

    public void addMotiveObjectives(String type, String storyPhase, String...objectiveTypes) {

        for (Motive motive : motives) {

            if (motive.type.equals(type) && motive.storyPhase.equals(storyPhase)) {
                motive.populateObjectives(objectiveTypes);
            }
        }
    }

    public String getRandomObjective(String type, String storyPhase) {

        for (Motive motive : motives) {
            if (motive.type.equals(type) && motive.storyPhase.equals(storyPhase)) {
                return motive.getRandomObjective();
            }
        }

        return null;
    }

    public void addEnemies(String type, String storyPhase, String...enemies) {

        for (Motive motive : motives) {

            if (motive.type.equals(type) && motive.storyPhase.equals(storyPhase)) {
                motive.populateEnemies(enemies);
            }
        }
    }

    public void displayEnemies(String type, String storyPhase) {
        for (Motive motive : motives) {

            if (motive.type.equals(type) && motive.storyPhase.equals(storyPhase)) {
                for (String enemy : motive.enemies) {
                    System.out.println(enemy);
                }
            }

        }
    }

    public void addBossEnemies(String type, String storyPhase, String...bossEnemies) {

        for (Motive motive : motives) {

            if (motive.type.equals(type) && motive.storyPhase.equals(storyPhase)) {
                motive.populateBossEnemies(bossEnemies);
            }
        }
    }

    public void addItems(String type, String storyPhase, String...items) {

        for (Motive motive : motives) {

            if (motive.type.equals(type) && motive.storyPhase.equals(storyPhase)) {
                motive.populateItems(items);
            }
        }
    }

    public void addNPCs(String type, String storyPhase, String...npcs) {

        for (Motive motive : motives) {

            if (motive.type.equals(type) && motive.storyPhase.equals(storyPhase)) {
                motive.populateNPCs(npcs);
                System.out.println("adding npcs to motive type" +motive.type+ ", story phase " +motive.storyPhase);
            }
        }
    }

    public String getRandomEnemy(String type, String storyPhase) {

        for (Motive motive : motives) {
            if (motive.type.equals(type) && motive.storyPhase.equals(storyPhase)) {
                return motive.getRandomEnemy();
            }
        }

        return null;
    }

    public String getRandomBossEnemy(String type, String storyPhase) {

        for (Motive motive : motives) {
            if (motive.type.equals(type) && motive.storyPhase.equals(storyPhase)) {
                return motive.getRandomBossEnemmy();
            }
        }

        return null;
    }

    public String getRandomItem(String type, String storyPhase) {

        for (Motive motive : motives) {
            if (motive.type.equals(type) && motive.storyPhase.equals(storyPhase)) {
                return motive.getRandomItem();
            }
        }

        return null;
    }

    public String getRandomNPC(String type, String storyPhase) {

        for (Motive motive : motives) {
            if (motive.type.equals(type) && motive.storyPhase.equals(storyPhase)) {
                return motive.getRandomNPC();
            }
        }

        return null;
    }

    public void setXMLID(int xmlID) {
        this.xmlID = xmlID;
    }



    public void displayMotives() {

        for (Motive motive : motives) {

            System.out.println("Motive Type: " +motive.type);
            System.out.println("Motive Story Phase: " +motive.storyPhase);
            System.out.println("\n");

        }



    }


    public class Motive {


        public String storyPhase;

        public String type;
        public ArrayList<String> objectiveTypes;

        //lists of variables that can be used should the objective require it
        public ArrayList<String> enemies;
        public ArrayList<String> bossEnemies;

        public ArrayList<String> items;
        public ArrayList<String> npcs;

        public Motive(String type, String storyPhase) {
            this.type = type;
            this.storyPhase = storyPhase;

            objectiveTypes = new ArrayList<>();

            enemies = new ArrayList<>();
            bossEnemies = new ArrayList<>();
            items = new ArrayList<>();
            npcs = new ArrayList<>();
        }

        public void populateObjectives(String...values) {

            for (int i = 0; i < values.length; i++) {
                String curObjective = values[i];
                objectiveTypes.add(curObjective);
            }
        }

        public String getRandomObjective() {

            Collections.shuffle(objectiveTypes);
            return objectiveTypes.get(0);
        }

        public void populateEnemies(String...values) {

            for (int i = 0; i < values.length; i++) {
                String curEnemy = values[i];
                enemies.add(curEnemy);
            }
        }

        public String getRandomEnemy() {

            Collections.shuffle(enemies);
            return enemies.get(0);
        }

        public void populateBossEnemies(String...values) {

            for (int i = 0; i < values.length; i++) {
                String curBossEnemy = values[i];
                bossEnemies.add(curBossEnemy);
            }
        }

        public String getRandomBossEnemmy() {

            Collections.shuffle(bossEnemies);
            return bossEnemies.get(0);
        }

        public void populateItems(String...values) {

            for (int i = 0; i < values.length; i++) {
                String curItem = values[i];
                items.add(curItem);

            }
        }

        public String getRandomItem() {

            Collections.shuffle(items);
            return items.get(0);
        }

        public void populateNPCs(String...values) {

            for (int i = 0; i < values.length; i++) {
                String curNPC = values[i];
                npcs.add(curNPC);
            }
        }

        public String getRandomNPC() {

            Collections.shuffle(npcs);
            return npcs.get(0);
        }


    }




}
