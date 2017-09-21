package com.watabou.pixeldungeon.quests;

import com.watabou.pixeldungeon.actors.mobs.Mob;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;

import java.util.ArrayList;
import java.util.Collection;

public class Quest implements Bundlable{

    public String questName; //name of the quest
    public String questGiver; //name of the quest giver NPC
    public String reward; //tier values for armor. From 0 to 6
    public String questGiverLevel; //level that the quest giver NPC is in
    public boolean given; //has the quest been given to the player
    public ArrayList<QuestObjective> questObjectives;
    public int curObjective;
    public boolean questComplete; //is the quest completed?
    public int associatedAct; //which act is the quest in?

    public String questDescription; //the description of the quest that will be displayed in the quest journal.

    public String prerequisiteQuestName; //the name of the quest required to be completed before this quest can take place.

    public String QUEST_NOT_GIVEN_TEXT; //NPC dialogue if the quest hasn't been given to the player. (Quest Dialogue)
    public String QUEST_GIVEN_TEXT; //NPC dialogue if the quest has been given to the player.
    public String QUEST_COMPLETED_TEXT; //NPC dialogue if the quest has been completed.

    //Bundle TAGS
    public static final String QUESTNAME             = "questName";
    public static final String QUESTGIVBR            = "questGiver";
    public static final String QUESTREWARD           = "questReward";
    public static final String QUESTGIVERLEVEL       = "questGiverLevel";
    public static final String QUESTGIVEN            = "questGiven";
    public static final String CURRENTOBJECTIVE      = "currentObjective";
    public static final String QUESTCOMPLETE         = "questComplete";
    public static final String ASSOCIATEDACT         = "associatedAct";
    public static final String PREREQUISITEQUESTNAME = "prerequisiteQuestName";
    public static final String QUESTOBJECTIVES       = "questObjectives";

    public static final String QUESTNOTGIVENTEXT     = "questNotGivenText";
    public static final String QUESTGIVENTEXT        = "questGivenText";
    public static final String QUESTCOMPLETEDTEXT    = "questCompletedText";

    @Override
    public void storeInBundle( Bundle bundle ) {

        bundle.put( QUESTNAME, questName );
        bundle.put( QUESTGIVBR, questGiver );
        bundle.put( QUESTREWARD, reward );
        bundle.put( QUESTGIVERLEVEL, questGiverLevel );
        bundle.put( QUESTGIVEN, given );
        bundle.put( CURRENTOBJECTIVE, curObjective );
        bundle.put( QUESTCOMPLETE, questComplete );
        bundle.put( ASSOCIATEDACT, associatedAct );
        bundle.put( PREREQUISITEQUESTNAME, prerequisiteQuestName );
        bundle.put( QUESTOBJECTIVES, questObjectives);

        bundle.put( QUESTNOTGIVENTEXT, QUEST_NOT_GIVEN_TEXT);
        bundle.put( QUESTGIVENTEXT, QUEST_GIVEN_TEXT);
        bundle.put( QUESTCOMPLETEDTEXT, QUEST_COMPLETED_TEXT);
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {

        questName = bundle.getString(QUESTNAME);
        questGiver = bundle.getString(QUESTGIVBR);
        reward = bundle.getString(QUESTREWARD);
        questGiverLevel = bundle.getString(QUESTGIVERLEVEL);
        given = bundle.getBoolean(QUESTGIVEN);
        curObjective = bundle.getInt(CURRENTOBJECTIVE);
        questComplete = bundle.getBoolean(QUESTCOMPLETE);
        associatedAct = bundle.getInt(ASSOCIATEDACT);
        prerequisiteQuestName = bundle.getString(PREREQUISITEQUESTNAME);

        questObjectives = new ArrayList<>();

        for (Bundlable bundlable : bundle.getCollection( QUESTOBJECTIVES )) {
            System.out.println("wefijweifjiwjfiwejf");
            if (bundlable != null) {
                System.out.println("not null");
                questObjectives.add((QuestObjective)bundlable);
            }
        }

        if (questGiver.equals("none")) {
            QUEST_NOT_GIVEN_TEXT = bundle.getString(QUESTNOTGIVENTEXT);
        } else {
            QUEST_NOT_GIVEN_TEXT = bundle.getString(QUESTNOTGIVENTEXT);
            QUEST_GIVEN_TEXT = bundle.getString(QUESTGIVENTEXT);
            QUEST_COMPLETED_TEXT = bundle.getString(QUEST_COMPLETED_TEXT);
        }

    }

    public Quest() {
    }

    public Quest(String questName, String questGiver, String reward, String questGiverLevel, int associatedAct, String prerequisiteQuestName, String QUEST_NOT_GIVEN_TEXT, String QUEST_GIVEN_TEXT, String QUEST_COMPLETED_TEXT) {
        this.questName = questName;
        this.questGiver = questGiver;
        this.reward = reward;
        this.questGiverLevel = questGiverLevel;
        questObjectives = new ArrayList<>();
        curObjective = 0;
        this.associatedAct = associatedAct;
        this.prerequisiteQuestName = prerequisiteQuestName;

        given = false;
        questComplete = false;

        this.QUEST_NOT_GIVEN_TEXT = QUEST_NOT_GIVEN_TEXT;
        this.QUEST_GIVEN_TEXT = QUEST_GIVEN_TEXT;
        this.QUEST_COMPLETED_TEXT = QUEST_COMPLETED_TEXT;
    }

    public Quest(String questName, String questGiver, String reward, String questGiverLevel, int associatedAct, String prerequisiteQuestName, String QUEST_NOT_GIVEN_TEXT) {
        this.questName = questName;
        this.questGiver = questGiver;
        this.reward = reward;
        this.questGiverLevel = questGiverLevel;
        questObjectives = new ArrayList<>();
        curObjective = 0;
        this.associatedAct = associatedAct;
        this.prerequisiteQuestName = prerequisiteQuestName;

        given = false;
        questComplete = false;

        this.QUEST_NOT_GIVEN_TEXT = QUEST_NOT_GIVEN_TEXT;
    }

    public void addObjective(QuestObjective questObjective) {
        questObjectives.add(questObjective);
    }

    public void questCompleted() {
        questComplete = true;
    }

    public QuestObjective getCurObjective() {
        return questObjectives.get(curObjective);
    }

    public void displayQuest() {
        System.out.println(questGiver);
        System.out.println(questName);
        System.out.println(given);
    }

    public void displayObjectivesInSentence() {

        int i = 1;
        System.out.println("---QUEST OBJECTIVES---");
        for (QuestObjective questObjective: questObjectives) {

            switch (questObjective.questType) {
                case "speak":
                    System.out.println(i+") Speak to " +questObjective.speakToNPC+ " in the " +questObjective.level+ " zone.");
                    break;
                case "kill":
                    System.out.println(i+") Kill " +questObjective.leftToKill+ " " +questObjective.enemy+ " in the " +questObjective.level+ " zone.");
                    break;
                case "fetch":
                    System.out.println(i+") Collect " +questObjective.itemName+ " from somewhere in the " +questObjective.level+ " zone.");
                    break;
                case "kill_fetch":
                    System.out.println(i+") Collect " +questObjective.itemName+ " by killing " +questObjective.leftToKill+ " " +questObjective.enemy+ " in the " +questObjective.level+ " zone.");
                    break;
                case "rescue":
                    System.out.println(i+") Rescue " +questObjective.speakToNPC+ " from " +questObjective.enemy+ " in the " +questObjective.level+ " zone.");
                    break;
                case "use_item":
                    System.out.println(i+") Use the " +questObjective.itemName+ " in the " +questObjective.level+ " zone.");
                    break;
                case "travel":
                    System.out.println(i+") Travel to the " +questObjective.level+ " zone.");
                    break;
                case "speak_fetch":
                    System.out.println(i+") Collect " +questObjective.itemName+ " from " +questObjective.speakToNPC+ " in the " +questObjective.level+ " zone.");
                    break;
                case "fight":
                    System.out.println(i+") Fight " +questObjective.enemy+ " in the " +questObjective.level+ " zone.");
                    break;
            }
            i++;
        }
    }
}
