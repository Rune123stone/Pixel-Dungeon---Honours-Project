package com.watabou.pixeldungeon.quests;

import com.watabou.pixeldungeon.actors.mobs.npcs.NPC;

import java.util.ArrayList;

public class Quest {

    public String questName; //name of the quest
    public String questGiver; //name of the quest giver NPC
    public String reward; //tier values for armor. From 0 to 6.
    public boolean given; //has the quest been given to the player
    public ArrayList<QuestObjective> questObjectives;
    public int curObjective;
    public boolean questCompleted; //is the quest completed?
    public int associatedAct; //which act is the quest in?

    public String QUEST_NOT_GIVEN_TEXT; //NPC dialogue if the quest hasn't been given to the player. (Quest Dialogue)
    public String QUEST_GIVEN_TEXT; //NPC dialogue if the quest has been given to the player.
    public String QUEST_COMPLETED_TEXT; //NPC dialogue if the quest has been completed.

    public Quest(String questName, String questGiver, String reward, int associatedAct, String QUEST_NOT_GIVEN_TEXT, String QUEST_GIVEN_TEXT, String QUEST_COMPLETED_TEXT) {
        this.questName = questName;
        this.questGiver = questGiver;
        this.reward = reward;
        questObjectives = new ArrayList<>();
        curObjective = 0;
        this.associatedAct = associatedAct;

        questCompleted = false;

        this.QUEST_NOT_GIVEN_TEXT = QUEST_NOT_GIVEN_TEXT;
        this.QUEST_GIVEN_TEXT = QUEST_GIVEN_TEXT;
        this.QUEST_COMPLETED_TEXT = QUEST_COMPLETED_TEXT;
    }

    public Quest(String questName, String questGiver, String reward, int associatedAct, String QUEST_GIVEN_TEXT) {
        this.questName = questName;
        this.questGiver = questGiver;
        this.reward = reward;
        questObjectives = new ArrayList<>();
        curObjective = 0;
        this.associatedAct = associatedAct;

        questCompleted = false;

        this.QUEST_GIVEN_TEXT = QUEST_GIVEN_TEXT;
    }

    public void addObjective(QuestObjective questObjective) {
        questObjectives.add(questObjective);
    }

    public void questComplete() {
        questCompleted = true;
    }






}
