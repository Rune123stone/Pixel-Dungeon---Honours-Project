package com.watabou.pixeldungeon.quests;

import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.Journal;
import com.watabou.pixeldungeon.actors.Actor;
import com.watabou.pixeldungeon.actors.mobs.Mob;
import com.watabou.pixeldungeon.actors.mobs.npcs.NPC;
import com.watabou.pixeldungeon.items.Item;
import com.watabou.pixeldungeon.items.quest.DriedRose;
import com.watabou.pixeldungeon.levels.*;
import com.watabou.pixeldungeon.scenes.GameScene;
import com.watabou.pixeldungeon.windows.WndQuest;
import com.watabou.pixeldungeon.windows.WndQuestNPC;

public class QuestHandler {

    public static QuestObjective questObjective;

    public QuestHandler(QuestObjective questObjective) {
        this.questObjective = questObjective;
    }

    //handles interaction with NPC if the NPC has a quest for the player
    public void handleNPCInteraction(NPC npc, Quest quest) {
        String objectiveType = questObjective.questType;

        if (npc.speakToQuest) { //if the NPC being interacted with is part of a "speak" quest or "speak_fetch" quest, handle it using these interaction methods
            switch (objectiveType) {
                case "speak":
                    handleSpeakQuest(npc, quest);
                    break;
                case "speak_fetch":
                    handleSpeakFetchQuest(npc, quest);
                    break;
            }

        } else {

            switch (objectiveType) {
                case "kill":
                    killQuestInteract(npc, quest);
                    break;
                case "fetch":
                    fetchQuestInteract(npc, quest);
                    break;
                case "kill_fetch":
                    fetchQuestInteract(npc, quest);
                    break;
                case "speak":
                    speakQuestInteract(npc, quest);
                    break;
            }
        }
    }

    /**
     * FETCH quest methods & variables
     */
    //handles the interaction of FETCH quests. Works for KILL + FETCH quests as well.
    public void fetchQuestInteract(NPC npc, Quest quest) { //take in item as well.

        if (quest.given) {
            //Item item = Dungeon.hero.belongings.getItem(DriedRose.class); //checks if a hero has this item. DriedRose is used as an example in the mean time.

            Item item = null;

            // *** obtains item using reflection ***
            String itemPackage = "com.watabou.pixeldungeon.items.";
            String itemName = questObjective.itemName;
            String itemClassName = itemPackage.concat(itemName);

            try {
                Class<?> itemClass = Class.forName(itemClassName);
                Item questItem = (Item) itemClass.newInstance();

                // checks to see if item is in hero's inventory
                item = Dungeon.hero.belongings.getItem(questItem.getClass());
            } catch (Exception e) {
                System.out.println("Error in method: fetchQuestInteract, class: QuestHandler");
                e.printStackTrace();
            }

            if (item != null) {
                GameScene.show(new WndQuestNPC(npc, item, questObjective.QUEST_COMPLETED_TEXT));
                quest.curObjective++;
            } else {
                GameScene.show(new WndQuest(npc, questObjective.QUEST_GIVEN_TEXT));
            }
        } else {
            GameScene.show(new WndQuest(npc, questObjective.QUEST_NOT_GIVEN_TEXT));
            quest.given = true;
        }
    }

    //spawns quest item at random pos in a level. Used for items not dropped by enemies.
    public void spawnQuestItem(String itemName, Level level) {

        try {
            String itemPackage = "com.watabou.pixeldungeon.items.quest.";
            String itemClassName = itemPackage.concat(questObjective.itemName);
            Class<?> item = Class.forName(itemClassName);

            int randomPos = 0;

            switch (level.getClass().getSimpleName()) {
                case "SewerLevel":
                    randomPos = level.randomRespawnCell();
                    break;
                case "CavesLevel":
                    randomPos = CavesLevel.spawnPos();
                    break;
                case "ForestLevel":
                    randomPos = ForestLevel.spawnPos();
                    break;
                case "ShadowLandsLevel":
                    randomPos = ShadowLandsLevel.spawnPos();
                    break;
                case "FieldsLevel":
                    randomPos = FieldsLevel.spawnPos();
                    break;
                case "CityLevel":
                    randomPos = level.randomRespawnCell();
                    break;
            }

            Dungeon.level.drop((Item) item.newInstance(), randomPos);


        } catch (Exception e) {
            System.out.println("Problem in spawnQuestItem(): ");
            e.printStackTrace();
        }
    }

    /**
     * kill quest methods & variables
     */
    public boolean killedAll = false;

    //works for kill + fetch quests as well
    public void spawnKillQuestMobs(Level level) {
        try {

            //gets the enemy class
            String mobPackage = "com.watabou.pixeldungeon.actors.mobs.";
            String mobClassName = mobPackage.concat(questObjective.enemy);
            Class<?> enemy = Class.forName(mobClassName);
            System.out.println("Enemy type: " + enemy.getSimpleName());

            for (int i = 0; i < questObjective.leftToKill; i++) { //for all enemies that need to spawn
                Mob enemyMob = (Mob) enemy.newInstance(); //spawn new enemy
                enemyMob.partOfKillQuest = true; //used for handling the die() method in the mob's class.
                enemyMob.pos = level.randomRespawnCell(); //sets the enemy's position
                SewerLevel.mobs.add(enemyMob); //adds the enemy to the level
                Actor.occupyCell(enemyMob);

                System.out.println("spawned mob " + i + " at position " + enemyMob.pos);
            }
        } catch (Exception e) {
            System.out.println("spawnKillQuestMobs: ");
            e.printStackTrace();
        }
    }

    //gets called when quest enemy is killed
    public void handleKillQuest() {
        if (questObjective.leftToKill != 1) {
            questObjective.leftToKill--;
        } else {
            killedAll = true;
        }
    }

    //gets called when interacting with quest giver of Kill Quest
    public void killQuestInteract(NPC npc, Quest quest) {

        if (quest.given) {
            if (questObjective.leftToKill == 0) { //checks if a hero has killed all necessary enemies
                GameScene.show(new WndQuestNPC(npc, questObjective.QUEST_COMPLETED_TEXT));
                quest.curObjective++;
            } else {
                GameScene.show(new WndQuest(npc, questObjective.QUEST_GIVEN_TEXT));
            }
        } else {
            GameScene.show(new WndQuest(npc, questObjective.QUEST_NOT_GIVEN_TEXT));

            // **** Adds quest to the quest journal ***
            String journalEntry;

            if (questObjective.leftToKill > 1) {
                journalEntry = "Kill " +questObjective.leftToKill+ " " +questObjective.enemy+ "'s in the " +questObjective.level;
            } else {
                journalEntry = "Kill " +questObjective.leftToKill+ " " +questObjective.enemy+ " in the " +questObjective.level;
            }

            Journal.addQuestEntry(journalEntry);
            // **** ****

            quest.given = true;
        }
    }

    /**
     * SPECIAL CASE: kill + fetch quest
     */
    public void handleKillFetchQuest(Mob curMob) {

        try {
            String itemPackage = "com.watabou.pixeldungeon.items.quest.";
            String itemClassName = itemPackage.concat(questObjective.itemName);
            Class<?> item = Class.forName(itemClassName);

            //drops the item once all enemies spawned are killed
            if (questObjective.leftToKill != 1) {
                questObjective.leftToKill--;
            } else {
                Dungeon.level.drop((Item) item.newInstance(), curMob.pos).sprite.drop();
            }

        } catch (Exception e) {
            System.out.println("Error in handleKillFetchQuest: ");
            e.printStackTrace();
        }
    }

    /**
     * SpeakTo NPC quest
     */
    //gets called when speaking to an NPC you must speak to as part of the speak quest.
    public void handleSpeakQuest(NPC npc, Quest quest) {

        GameScene.show(new WndQuest(npc, questObjective.QUEST_COMPLETED_TEXT));
        quest.curObjective++;
        npc.assignSpeakToQuest(false);

    }

    //gets called when when interacting with quest giver, not the NPC you must speak to
    public void speakQuestInteract(NPC npc, Quest quest) {
        if (quest.given) {
            GameScene.show(new WndQuestNPC(npc, questObjective.QUEST_GIVEN_TEXT));
        } else {
            GameScene.show(new WndQuestNPC(npc, questObjective.QUEST_NOT_GIVEN_TEXT));

            // **** adds quest to quest journal ****
            String journalEntry = "Speak to " +npc.name+ " in the " +questObjective.level;

            Journal.addQuestEntry(journalEntry);
            // **** ****

            quest.given = true;
        }
    }

    /**
     * SPECIAL CASE: SpeakTo NPC + Fetch quest
     */
    //gets called when speaking to an NPC you must speak to as part of the speak_fetch quest. NPC will give you an item.
    public void handleSpeakFetchQuest(NPC npc, Quest quest) {

        GameScene.show(new WndQuest(npc, questObjective.QUEST_COMPLETED_TEXT));

        String itemPackage = "com.watabou.pixeldungeon.items.";
        String itemName = questObjective.itemName;
        String itemClassName = itemPackage.concat(itemName);

        try {
            Class<?> itemClass = Class.forName(itemClassName);

            Item questItem = (Item) itemClass.newInstance();

            questItem.doPickUp(Dungeon.hero);

        } catch (Exception e) {
            System.out.println("Error in method: handleSpeakFetchQuest, class: QuestHandler");
            e.printStackTrace();
        }

        quest.curObjective++;
        npc.assignSpeakToQuest(false);

    }






















    //other methods - thought of a better way to do this

    public void fetchQuestItemFromNPC(NPC npc) {
        try {

            String itemPackage = "com.watabou.pixeldungeon.items.quest.";
            String itemClassName = itemPackage.concat(questObjective.itemName);
            Class<?> item = Class.forName(itemClassName);

            questObjective.QUEST_GIVEN_TEXT = "Ey Boi, I got something 4 u..";

            if (npc.hasQuestItem) {
                GameScene.show(new WndQuest(npc, questObjective.QUEST_GIVEN_TEXT));

                Dungeon.level.drop((Item) item.newInstance(), Dungeon.hero.pos).sprite.drop();
                npc.assignQuestItem(false);
            }

        } catch (Exception e) {
            System.out.println("Error in method: fetchQuestItemFromNPC");
            e.printStackTrace();
        }
    }


}
