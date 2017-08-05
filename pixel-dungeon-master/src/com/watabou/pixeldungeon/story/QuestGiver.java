package com.watabou.pixeldungeon.story;

import java.util.ArrayList;
import java.util.Collections;

public class QuestGiver {

    public String name;
    public ArrayList<Motive> motives;

    public ArrayList<String> namesList;

    public QuestGiver(String...names) {

        namesList = new ArrayList<>();

        addQuestGiverNames(names);

        Collections.shuffle(namesList);

        name = namesList.get(0);

        motives = new ArrayList<>();
    }


    public String getRandomMotiveType() {

        Collections.shuffle(motives);

        String motiveType = motives.get(0).type;

        return motiveType;

    }

    public void addQuestGiverNames(String...names) {

        for (int i = 0; i < names.length; i++) {
            namesList.add(names[i]);
        }

    }

    public void createNewMotive(String type) {

        Motive motive = new Motive(type);

        motives.add(motive);
    }

    public void addMotiveObjectives(String type, String...objectiveTypes) {

        for (Motive motive : motives) {

            if (motive.type.equals(type)) {
                motive.populateObjectives(objectiveTypes);
            }
            return;
        }
    }

    public String getRandomObjective(String type) {

        for (Motive motive : motives) {
            if (motive.type.equals(type)) {
                return motive.getRandomObjective();
            }
        }

        return null;
    }

    public void addEnemies(String type, String...enemies) {

        for (Motive motive : motives) {

            if (motive.type.equals(type)) {
                motive.populateEnemies(enemies);
            }
            return;
        }
    }

    public void addBossEnemies(String type, String...bossEnemies) {

        for (Motive motive : motives) {

            if (motive.type.equals(type)) {
                motive.populateBossEnemies(bossEnemies);
            }
            return;
        }
    }

    public void addItems(String type, String...items) {

        for (Motive motive : motives) {

            if (motive.type.equals(type)) {
                motive.populateItems(items);
            }
            return;
        }
    }

    public void addNPCs(String type, String...npcs) {

        for (Motive motive : motives) {

            if (motive.type.equals(type)) {
                motive.populateNPCs(npcs);
            }
            return;
        }
    }

    public String getRandomEnemy(String type) {

        for (Motive motive : motives) {
            if (motive.type.equals(type)) {
                return motive.getRandomEnemy();
            }
        }

        return null;
    }

    public String getRandomBossEnemy(String type) {

        for (Motive motive : motives) {
            if (motive.type.equals(type)) {
                return motive.getRandomBossEnemmy();
            }
        }

        return null;
    }

    public String getRandomItem(String type) {

        for (Motive motive : motives) {
            if (motive.type.equals(type)) {
                return motive.getRandomItem();
            }
        }

        return null;
    }

    public String getRandomNPC(String type) {

        for (Motive motive : motives) {
            if (motive.type.equals(type)) {
                return motive.getRandomNPC();
            }
        }

        return null;
    }


    public class Motive {

        public String type;
        public ArrayList<String> objectiveTypes;

        //lists of variables that can be used should the objective require it
        public ArrayList<String> enemies;
        public ArrayList<String> bossEnemies;

        public ArrayList<String> items;
        public ArrayList<String> npcs;

        public Motive(String type) {
            this.type = type;
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
