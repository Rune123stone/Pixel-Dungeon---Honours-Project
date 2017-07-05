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
    public String speakToNPC;


    //kill quest constructor
    public QuestObjective(String questType, String questObjectiveName, String enemy, int leftToKill, String level) {
        this.questType = questType;
        this.questObjectiveName = questObjectiveName;
        this.enemy = enemy;
        this.leftToKill = leftToKill;
        this.level = level;
    }

    //travel quest
    public QuestObjective(String questType, String questObjectiveName, String other) {
        if (questType.equals("travel")) { //travel
            this.questType = questType;
            this.questObjectiveName = questObjectiveName;
            level = other;
        }
    }

    //speakTo + use item + fight + collect quest constructor
    public QuestObjective(String questType, String questObjectiveName, String varOne, String varTwo) {
        switch (questType) {
            case "speak":
                this.questType = questType;
                this.questObjectiveName = questObjectiveName;
                speakToNPC = varOne;
                level = varTwo;
                break;
            case "use_item":
                this.questType = questType;
                this.questObjectiveName = questObjectiveName;
                itemName = varOne;
                level = varTwo;
                break;
            case "fight":
                this.questType = questType;
                this.questObjectiveName = questObjectiveName;
                enemy = varOne;
                level = varTwo;
                break;
            case "collect":
                this.questType = questType;
                this.questObjectiveName = questObjectiveName;
                itemName = varOne;
                level = varTwo;
            }
        }

    //speakTo + Collect from NPC
    public QuestObjective(String questType, String questObjectiveName, String varOne, String varTwo, String varThree) {
        if (questType.equals("speak_collect")) { //assigns speak_collect variables
            this.questType = questType;
            this.questObjectiveName = questObjectiveName;
            speakToNPC = varOne;
            level = varTwo;
            itemName = varThree;
        } else { //assigns rescue variables
            this.questType = questType;
            this.questObjectiveName = questObjectiveName;
            enemy = varOne;
            speakToNPC = varTwo;
            level = varThree;
        }
    }

    //kill + fetch/collect constructor
    public QuestObjective(String questType, String questObjectiveName, String questItem, String enemy, int maxEnemies, String level) {
        this.questType = questType;
        this.questObjectiveName = questObjectiveName;
        itemName = questItem;
        this.enemy = enemy;
        this.level = level;

        //will generate a random number of enemy's. When all of them are killed, the item will drop.
        Random random = new Random();

        if (maxEnemies == 1) { //if item drops from boss type enemy
            leftToKill = 1;
        } else {
            leftToKill = random.nextInt((maxEnemies - 4) + 1) + 4;
        }
    }


}
