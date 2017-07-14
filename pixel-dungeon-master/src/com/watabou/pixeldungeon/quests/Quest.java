package com.watabou.pixeldungeon.quests;

import java.util.ArrayList;

public class Quest {

    public String questName; //name of the quest
    public String questGiver; //name of the quest giver NPC
    public String reward; //tier values for armor. From 0 to 6
    public String questGiverLevel; //level that the quest giver NPC is in
    public boolean given; //has the quest been given to the player
    public ArrayList<QuestObjective> questObjectives;
    public int curObjective;
    public boolean questCompleted; //is the quest completed?
    public int associatedAct; //which act is the quest in?

    public String questDescription; //the description of the quest that will be displayed in the quest journal.

    public String prerequisiteQuestName; //the name of the quest required to be completed before this quest can take place.

    public String QUEST_NOT_GIVEN_TEXT; //NPC dialogue if the quest hasn't been given to the player. (Quest Dialogue)
    public String QUEST_GIVEN_TEXT; //NPC dialogue if the quest has been given to the player.
    public String QUEST_COMPLETED_TEXT; //NPC dialogue if the quest has been completed.

    public Quest(String questName, String questGiver, String reward, String questGiverLevel, int associatedAct, String prerequisiteQuestName, String QUEST_NOT_GIVEN_TEXT, String QUEST_GIVEN_TEXT, String QUEST_COMPLETED_TEXT) {
        this.questName = questName;
        this.questGiver = questGiver;
        this.reward = reward;
        this.questGiverLevel = questGiverLevel;
        questObjectives = new ArrayList<>();
        curObjective = 0;
        this.associatedAct = associatedAct;
        this.prerequisiteQuestName = prerequisiteQuestName;

        questCompleted = false;

        this.QUEST_NOT_GIVEN_TEXT = QUEST_NOT_GIVEN_TEXT;
        this.QUEST_GIVEN_TEXT = QUEST_GIVEN_TEXT;
        this.QUEST_COMPLETED_TEXT = QUEST_COMPLETED_TEXT;
    }

    public Quest(String questName, String questGiver, String reward, String questGiverLevel, int associatedAct, String prerequisiteQuestName, String QUEST_GIVEN_TEXT) {
        this.questName = questName;
        this.questGiver = questGiver;
        this.reward = reward;
        this.questGiverLevel = questGiverLevel;
        questObjectives = new ArrayList<>();
        curObjective = 0;
        this.associatedAct = associatedAct;
        this.prerequisiteQuestName = prerequisiteQuestName;

        questCompleted = false;

        this.QUEST_GIVEN_TEXT = QUEST_GIVEN_TEXT;
    }

    public void addObjective(QuestObjective questObjective) {
        questObjectives.add(questObjective);
    }

    public void questComplete() {
        questCompleted = true;
    }

    public void displayObjectives() {
        for (QuestObjective questObjective: questObjectives) {
            System.out.println("---QUEST OBJECTIVES---");
            System.out.println("Quest Type: " +questObjective.questType);
            System.out.println("Quest Name: " +questObjective.questObjectiveName);

            switch (questObjective.questType) {
                case "speak":
                    System.out.println("Speak To: " +questObjective.speakToNPC);
                    System.out.println("Level: " +questObjective.level);
                    break;
                case "kill":
                    System.out.println("Enemy: " +questObjective.enemy);
                    System.out.println("Amount to Kill: " +questObjective.leftToKill);
                    System.out.println("Level: " +questObjective.level);
                    break;
                case "collect":
                    System.out.println("Item to Collect: " +questObjective.itemName);
                    System.out.println("Level: " +questObjective.level);
                    break;
                case "kill_collect":
                    System.out.println("Enemy: " +questObjective.enemy);
                    System.out.println("Amount to Kill: " +questObjective.leftToKill);
                    System.out.println("Item to Collect: " +questObjective.itemName);
                    System.out.println("Level: " +questObjective.level);
                    break;
                case "rescue":
                    System.out.println("Enemy: " +questObjective.enemy);
                    System.out.println("Speak To: " +questObjective.speakToNPC);
                    System.out.println("Level: " +questObjective.level);
                    break;
                case "use_item":
                    System.out.println("Item to Use: " +questObjective.itemName);
                    System.out.println("Level: " +questObjective.level);
                    break;
                case "travel":
                    System.out.println("Level: " +questObjective.level);
                    break;
                case "speak_collect":
                    System.out.println("Speak To: " +questObjective.speakToNPC);
                    System.out.println("Level: " +questObjective.level);
                    System.out.println("Item to Collect: " +questObjective.itemName);
                    break;
                case "fight":
                    System.out.println("Fight: " +questObjective.enemy);
                    System.out.println("Level: " +questObjective.level);
                    break;
            }
            System.out.println("---END OF OBJECTIVE---");
        }
    }

    public void setDescription() {
        QuestObjective questObjective = questObjectives.get(curObjective);

        switch (questObjective.questType) {
            case "speak":
                questDescription = "Speak to " +questObjective.speakToNPC+ " in the " +questObjective.level+ " zone.";
                break;
            case "kill":
                questDescription = "Kill " +questObjective.leftToKill+ " " +questObjective.enemy+ " in the " +questObjective.level+ " zone.";
                break;
            case "fetch":
                questDescription = "Collect " +questObjective.itemName+ " from somewhere in the " +questObjective.level+ " zone.";
                break;
            case "kill_fetch":
                questDescription = "Collect " +questObjective.itemName+ " by killing " +questObjective.leftToKill+ " " +questObjective.enemy+ " in the " +questObjective.level+ " zone.";
                break;
            case "rescue":
                questDescription = "Rescue " +questObjective.speakToNPC+ " from " +questObjective.enemy+ " in the " +questObjective.level+ " zone.";
                break;
            case "use_item":
                questDescription = "Use the " +questObjective.itemName+ " in the " +questObjective.level+ " zone.";
                break;
            case "travel":
                questDescription = "Travel to the " +questObjective.level+ " zone.";
                break;
            case "speak_fetch":
                questDescription = "Collect " +questObjective.itemName+ " from " +questObjective.speakToNPC+ " in the " +questObjective.level+ " zone.";
                break;
            case "fight":
                questDescription = "Fight " +questObjective.enemy+ " in the " +questObjective.level+ " zone.";
                break;
        }

    }


    public void displayObjectivesInSentence() {

        int i = 1;
        System.out.println("---QUEST OBJECTIVES---");
        for (QuestObjective questObjective: questObjectives) {

//            System.out.println("Quest Type: " +questObjective.questType);
//            System.out.println("Quest Objective Name: " +questObjective.questObjectiveName);

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
