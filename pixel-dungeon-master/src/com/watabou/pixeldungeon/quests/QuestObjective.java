package com.watabou.pixeldungeon.quests;

import com.watabou.pixeldungeon.actors.mobs.Mob;
import com.watabou.pixeldungeon.actors.mobs.npcs.NPC;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class QuestObjective {

    //general quest objectives variables
    public String questType;
    public String questObjectiveName;
    public String level;

    public String QUEST_NOT_GIVEN_TEXT;
    public String QUEST_GIVEN_TEXT;
    public String QUEST_COMPLETED_TEXT;

    //kill quest variables
    public int leftToKill;
    public String enemy;

    //fetch quest variables
    public String itemName;

    //speakTo quest variables
    public NPC speakToNPC;


    //kill quest constructor
    public QuestObjective(String questType, String questObjectiveName, String enemy, int leftToKill, String level) {
        this.questType = questType;
        this.questObjectiveName = questObjectiveName;
        this.enemy = enemy;
        this.leftToKill = leftToKill;
        this.level = level;
    }

    //fetch/collect quest constructor OR travel quest
    public QuestObjective(String questType, String questObjectiveName, String other) {

        if (questType.equals("travel")) { //travel
            this.questType = questType;
            this.questObjectiveName = questObjectiveName;
            level = other;
        } else { //collect
            this.questType = questType;
            this.questObjectiveName = questObjectiveName;
            itemName = other;
        }
    }

    //fetch/collect quest from NPC constructor OR speak to quest
    public QuestObjective(String questType, String questObjectiveName, String itemName, NPC speakToNPC) {
        this.questType = questType;
        this.questObjectiveName = questObjectiveName;
        this.itemName = itemName;
        this.speakToNPC = speakToNPC;
    }

    //speakTo quest constructor
    public QuestObjective(String questType, String questObjectiveName, NPC speakToNPC) {
        this.questType = questType;
        this.questObjectiveName = questObjectiveName;
        this.speakToNPC = speakToNPC;
    }

    //kill + fetch/collect constructor
    public QuestObjective(String questType, String questObjectiveName, String itemName, String enemy, int maxEnemies) {
        this.questType = questType;
        this.questObjectiveName = questObjectiveName;
        this.itemName = itemName;
        this.enemy = enemy;

        //will generate a random number of enemy's. When all of them are killed, the item will drop.
        Random random = new Random();

        leftToKill = random.nextInt((maxEnemies - 4) + 1) + 4;
    }


}
