package com.watabou.pixeldungeon.quests;

import com.watabou.pixeldungeon.actors.mobs.Mob;
import com.watabou.pixeldungeon.actors.mobs.npcs.NPC;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class QuestObjective implements Bundlable {

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
    public boolean questItemDropped;

    //speakTo quest variables
    public String speakToNPC;

    public boolean objectiveComplete;

    //Bundle TAGS
    public static final String QUESTTYPE          = "questType";
    public static final String QUESTOBJECTIVENAME = "questObjectiveName";
    public static final String LEVEL              = "level";
    public static final String LEFTTOKILL         = "leftToKill";
    public static final String ENEMY              = "enemy";
    public static final String ITEMNAME           = "itemName";
    public static final String SPEAKTONPC         = "speakToNPC";
    public static final String OBJECTIVECOMPLETE  = "objectiveComplete";

    public static final String QUESTNOTGIVENTEXT  = "questNotGivenText";
    public static final String QUESTGIVENTEXT     = "questGivenText";
    public static final String QUESTCOMPLETEDTEXT = "questCompletedText";



    @Override
    public void storeInBundle(Bundle bundle) {

        bundle.put(QUESTTYPE, questType);
        bundle.put(QUESTOBJECTIVENAME, questObjectiveName);
        bundle.put(LEVEL, level);

        switch (questType) {
            case "speak":
                bundle.put(SPEAKTONPC, speakToNPC);
                break;
            case "fetch":
            case "use_item":
                bundle.put(ITEMNAME, itemName);
                break;
            case "fight":
                bundle.put(ENEMY, enemy);
                break;
            case "kill":
                bundle.put(ENEMY, enemy);
                bundle.put(LEFTTOKILL, leftToKill);
                break;
            case "speak_fetch":
                bundle.put(SPEAKTONPC, speakToNPC);
                bundle.put(ITEMNAME, itemName);
                break;
            case "rescue":
                bundle.put(ENEMY, enemy);
                bundle.put(SPEAKTONPC, speakToNPC);
                break;
            case "kill_fetch":
                bundle.put(ENEMY, enemy);
                bundle.put(ITEMNAME, itemName);
                break;
        }

        bundle.put(QUESTNOTGIVENTEXT, QUEST_NOT_GIVEN_TEXT);
        bundle.put(QUESTGIVENTEXT, QUEST_GIVEN_TEXT);
        bundle.put(QUESTCOMPLETEDTEXT, QUEST_COMPLETED_TEXT);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {

        questType = bundle.getString(QUESTTYPE);
        questObjectiveName =  bundle.getString(QUESTOBJECTIVENAME);
        level = bundle.getString(LEVEL);

        switch (questType) {
            case "speak":
                speakToNPC = bundle.getString(SPEAKTONPC);
                break;
            case "fetch":
            case "use_item":
                itemName = bundle.getString(ITEMNAME);
                break;
            case "fight":
                enemy = bundle.getString(ENEMY);
                break;
            case "kill":
                enemy = bundle.getString(ENEMY);
                leftToKill = bundle.getInt(LEFTTOKILL);
                break;
            case "speak_fetch":
                speakToNPC = bundle.getString(SPEAKTONPC);
                itemName = bundle.getString(ITEMNAME);
                break;
            case "rescue":
                enemy = bundle.getString(ENEMY);
                speakToNPC = bundle.getString(SPEAKTONPC);
                break;
            case "kill_fetch":
                enemy = bundle.getString(ENEMY);
                itemName = bundle.getString(ITEMNAME);
                break;
        }

        QUEST_NOT_GIVEN_TEXT = bundle.getString(QUESTNOTGIVENTEXT);
        QUEST_GIVEN_TEXT = bundle.getString(QUESTGIVENTEXT);
        QUEST_COMPLETED_TEXT = bundle.getString(QUESTCOMPLETEDTEXT);
    }

    public QuestObjective() {

    }

    //kill quest constructor
    public QuestObjective(String questType, String questObjectiveName, String enemy, int leftToKill, String level) {
        this.questType = questType;
        this.questObjectiveName = questObjectiveName;
        this.enemy = enemy;
        this.leftToKill = leftToKill;
        this.level = level;

        objectiveComplete = false;
    }

    //travel quest
    public QuestObjective(String questType, String questObjectiveName, String other) {
        if (questType.equals("travel")) { //travel
            this.questType = questType;
            this.questObjectiveName = questObjectiveName;
            level = other;

            objectiveComplete = false;
        }
    }

    //speakTo + use item + fight + fetch quest constructor
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
            case "fetch":
                this.questType = questType;
                this.questObjectiveName = questObjectiveName;
                itemName = varOne;
                level = varTwo;
            }

        objectiveComplete = false;
        }

    //speack_fetch from NPC + rescue
    public QuestObjective(String questType, String questObjectiveName, String varOne, String varTwo, String varThree) {
        if (questType.equals("speak_fetch")) { //assigns speak_fetch variables
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

        objectiveComplete = false;
    }

    //kill_fetch constructor
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

        objectiveComplete = false;
    }

    public void objectiveCompleted() {
        objectiveComplete = true;
    }



}
