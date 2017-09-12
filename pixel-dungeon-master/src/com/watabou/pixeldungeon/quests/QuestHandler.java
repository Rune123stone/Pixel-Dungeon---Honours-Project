package com.watabou.pixeldungeon.quests;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.Journal;
import com.watabou.pixeldungeon.actors.Actor;
import com.watabou.pixeldungeon.actors.mobs.Mob;
import com.watabou.pixeldungeon.actors.mobs.npcs.NPC;
import com.watabou.pixeldungeon.items.Item;
import com.watabou.pixeldungeon.items.quest.DriedRose;
import com.watabou.pixeldungeon.levels.*;
import com.watabou.pixeldungeon.scenes.GameScene;
import com.watabou.pixeldungeon.scenes.InterlevelScene;
import com.watabou.pixeldungeon.scenes.OverworldScene;
import com.watabou.pixeldungeon.sprites.BlacksmithSprite;
import com.watabou.pixeldungeon.sprites.CharSprite;
import com.watabou.pixeldungeon.story.DataHandler;
import com.watabou.pixeldungeon.windows.WndMessage;
import com.watabou.pixeldungeon.windows.WndQuest;
import com.watabou.pixeldungeon.windows.WndQuestNPC;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Iterator;

public class QuestHandler {

    public static QuestObjective questObjective;

    public QuestHandler(QuestObjective questObjective) {
        this.questObjective = questObjective;
    }



    /**
     *  handles interaction with NPC if the NPC has a quest for the player
     */

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

        } else if (npc.questGiver) {

            switch (objectiveType) {
                case "kill":
                    killQuestInteract(npc, quest);
                    break;
                case "fetch":
                    fetchQuestInteract(npc, quest);
                    break;
                case "kill_fetch":
                    killFetchInteract(npc, quest);
                    break;
                case "speak":
                    speakQuestInteract(npc, quest);
                    break;
                case "speak_fetch":
                    speakQuestInteract(npc, quest);
                    break;
            }
        }

    }



    /**
     * use_item objective logic
     */

    public static void useQuestItem(Quest quest) {

        try {

            completeQuest(null, quest);

        } catch (Exception e) {
            System.out.println("Error in useQuestITem");
            e.printStackTrace();
        }
    }

    public static void setUseItemQuest(Item item) {
        for (Quest quest : DataHandler.getInstance().questList) {

            for (QuestObjective questObjective : quest.questObjectives) {

                if (questObjective.questType.equals("use_item") || questObjective.questType.equals("fetch")) {

                    String questItemName = questObjective.itemName;

                    if (item.getClass().getSimpleName().equals(questItemName)) {

                        item.assignQuest(quest);
                        System.out.println("assigned quest to item");

                    }

                }
            }

        }
    }




    /**
     * fetch objective logic
     */

    //handles the interaction of FETCH quests. Works for KILL + FETCH quests as well.
    public void fetchQuestInteract(NPC npc, Quest quest) {

        if (quest.given) {

            Item item = null;

            // *** obtains item using reflection ***
            String itemPackage = "com.watabou.pixeldungeon.items.quest.";
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
                //GameScene.show(new WndQuestNPC(npc, item, questObjective.QUEST_COMPLETED_TEXT));
                GameScene.show(new WndQuest(npc, quest.QUEST_COMPLETED_TEXT));

                completeQuest(npc, quest);

            } else {
                GameScene.show(new WndQuest(npc, quest.QUEST_GIVEN_TEXT));
            }
        } else {
            GameScene.show(new WndQuest(npc, quest.QUEST_NOT_GIVEN_TEXT));

            //**** START if the quest giver is in the same level as the quest item, spawn the item in the level when the quest is given to the player ***
            String questGiverLevel = quest.questGiverLevel;

            if (questGiverLevel.equals("Castle")) { //prevents nullPointer error - Castle uses the CityLevel class, there is no "CastleLevel" class.
                questGiverLevel = "City";
            }

            if (questGiverLevel.equals("Dungeon")) { //prevents nullPointer error - Dungeon uses the SewerLevel class, there is no "DungeonLevel" class.
                questGiverLevel = "Sewer";
            }

            if (questGiverLevel.equals("Shadow Lands")) { //prevents nullPointer error - Shadow Lands uses the ShadowLandsLevel class, there is no "Shadow LandsLevel" class.
                questGiverLevel = "ShadowLands";
            }

            String questGiverLevelClassName = questGiverLevel.concat("Level");

            if (questObjective.level.equals("Castle")) { //prevents nullPointer error - Castle uses the CityLevel class, there is no "CastleLevel" class.
                questObjective.level = "City";
            }

            if (questObjective.level.equals("Dungeon")) { //prevents nullPointer error - Dungeon uses the SewerLevel class, there is no "DungeonLevel" class.
                questObjective.level = "Sewer";
            }

            if (questObjective.level.equals("Shadow Lands")) { //prevents nullPointer error - Shadow Lands uses the ShadowLandsLevel class, there is no "Shadow LandsLevel" class.
                questObjective.level = "ShadowLands";
            }


            String questObjectiveLevelClassName = questObjective.level.concat("Level");

            System.out.println("questGiverClass name: " +questGiverLevelClassName);
            System.out.println("objectiveClassName: " +questObjectiveLevelClassName);

            if (questObjectiveLevelClassName.equals(questGiverLevelClassName)) {
                spawnQuestItem(questObjective.itemName, Dungeon.level, quest);
            }
            //**** END ****

            addToQuestJournal(quest);

            setQuestGiven(DataHandler.getInstance().questList, quest.questName);
            quest.given = true;
        }
    }

    //spawns quest item at random pos in a level. Used for items not dropped by enemies.
    public static void spawnQuestItem(String itemName, Level level, Quest quest) {

        try {
            String itemPackage = "com.watabou.pixeldungeon.items.quest.";
            //String itemClassName = itemPackage.concat(questObjective.itemName);
            String itemClassName = itemPackage.concat(itemName);
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

            Item questItem = (Item)item.newInstance();

            System.out.println("spawned item at pos: " +randomPos);
            //level.drop((Item) item.newInstance(), randomPos);
            level.drop(questItem, randomPos);


            setUseItemQuest(questItem);

            quest.getCurObjective().questItemDropped = true;

        } catch (Exception e) {
            System.out.println("Problem in spawnQuestItem(): ");
            e.printStackTrace();
        }
    }



    /**
     * kill & kill_fetch objective logic
     */

    //spawns mobs for kill and kill_fetch quest types if quest giver is in a different level to that of quest_location.
    public static void spawnKillQuestMobs(Quest quest, Level level) {

        try {

            String mobPackage = "com.watabou.pixeldungeon.actors.mobs.";
            String mobClassName = mobPackage.concat(quest.getCurObjective().enemy);
            Class<?> enemy = Class.forName(mobClassName);

            for (int i = 0; i < quest.getCurObjective().leftToKill; i++) { //for all enemies that need to spawn
                Mob enemyMob = (Mob) enemy.newInstance(); //spawn new enemy

                enemyMob.assignQuest(quest);

                enemyMob.pos = level.randomRespawnCell(); //sets the enemy's position
                level.mobs.add(enemyMob); //adds the enemy to the level
                Actor.occupyCell(enemyMob);

                System.out.println("spawned mob " + i + " at position " + enemyMob.pos);
            }

        } catch (Exception e) {
            System.out.println("error in spawnKillQuestMobs: ");
            e.printStackTrace();
        }
    }

    //spawns mobs for kill and kill_fetch quest types if quest giver is in the same level as quest_location.
    public static void spawnKillQuestMobsInSameLevelAsHero(Quest quest, Level level) {

        try {

            String mobPackage = "com.watabou.pixeldungeon.actors.mobs.";
            String mobClassName = mobPackage.concat(quest.getCurObjective().enemy);
            Class<?> enemy = Class.forName(mobClassName);

            for (int i = 0; i < quest.getCurObjective().leftToKill; i++) { //for all enemies that need to spawn
                Mob enemyMob = (Mob) enemy.newInstance(); //spawn new enemy

                enemyMob.assignQuest(quest);

                enemyMob.pos = level.randomRespawnCell(); //sets the enemy's position
                GameScene.add(enemyMob);

                System.out.println("spawned mob " + i + " at position " + enemyMob.pos);
            }
        } catch (Exception e) {
            System.out.println("error in spawnKillQuestMobs: ");
            e.printStackTrace();
        }
    }

    //gets called when quest enemy is killed
    public void handleKillQuest(Quest quest) {
        if (questObjective.leftToKill != 1) {

            removeFromQuestJournal(quest);

            questObjective.leftToKill--;

            addToQuestJournal(quest);
        } else {

            completeQuest(null, quest);
            System.out.println("completing kill quest");
        }
    }

    //gets called when interacting with quest giver of Kill Quest
    public void killQuestInteract(NPC npc, Quest quest) {

        if (quest.given) {
            if (questObjective.leftToKill == 0) { //checks if a hero has killed all necessary enemies
                GameScene.show(new WndQuest(npc, quest.QUEST_COMPLETED_TEXT));

                completeQuest(npc, quest);

            } else {
                GameScene.show(new WndQuest(npc, quest.QUEST_GIVEN_TEXT));
            }
        } else {
            GameScene.show(new WndQuest(npc, quest.QUEST_NOT_GIVEN_TEXT));

            //**** START if the quest giver is in the same level as the kill enemies, spawn the enemies in the level when the quest is given to the player ***
            QuestObjective curObjective = quest.getCurObjective();

            String objectiveLevel = curObjective.level;

            if (objectiveLevel.equals("Castle")) { //prevents nullPointer error - Castle uses the CityLevel class, there is no "CastleLevel" class.
                objectiveLevel = "City";
            }

            if (objectiveLevel.equals("Dungeon")) { //prevents nullPointer error - Dungeon uses the SewerLevel class, there is no "DungeonLevel" class.
                objectiveLevel = "Sewer";
            }

            if (objectiveLevel.equals("Shadow Lands")) { //prevents nullPointer error - Shadow Lands uses the ShadowLandsLevel class, there is no "Shadow LandsLevel" class.
                objectiveLevel = "ShadowLands";
            }

            String questObjectiveLevelClassName = objectiveLevel.concat("Level");

            if (Dungeon.level.getClass().getSimpleName().equals(questObjectiveLevelClassName)) {
                spawnKillQuestMobsInSameLevelAsHero(quest, Dungeon.level);
                //spawnKillQuestMobs(quest, Dungeon.level);
            }
            //**** END ****

            // **** Adds quest to the quest journal ***
            addToQuestJournal(quest);
            // **** ****

            setQuestGiven(DataHandler.getInstance().questList, quest.questName);
            quest.given = true;
        }
    }

    public void handleKillFetchQuest(Mob curMob, Quest quest) {

        try {

            String itemPackage = "com.watabou.pixeldungeon.items.quest.";
            String itemClassName = itemPackage.concat(questObjective.itemName);
            Class<?> item = Class.forName(itemClassName);

            Item questItem = (Item)item.newInstance();

            //drops the item once all enemies spawned are killed
            if (questObjective.leftToKill != 1) {
                questObjective.leftToKill--;
            } else {
                Dungeon.level.drop(questItem, curMob.pos).sprite.drop();
//                setUseItemQuest(questItem);
                questItem.assignQuest(quest);

                //completeQuest(null, quest);
            }

        } catch (Exception e) {
            System.out.println("Error in handleKillFetchQuest: ");
            e.printStackTrace();
        }
    }

    public void killFetchInteract(NPC npc, Quest quest) {

        if (quest.given) {

            Item item = null;

            // *** obtains item using reflection ***
            String itemPackage = "com.watabou.pixeldungeon.items.quest.";
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
                //GameScene.show(new WndQuestNPC(npc, item, questObjective.QUEST_COMPLETED_TEXT));
                GameScene.show(new WndQuest(npc, quest.QUEST_COMPLETED_TEXT));

                completeQuest(npc, quest);

            } else {
                GameScene.show(new WndQuest(npc, quest.QUEST_GIVEN_TEXT));
            }
        } else {

            GameScene.show(new WndQuest(npc, quest.QUEST_NOT_GIVEN_TEXT));

            //**** START if the quest giver is in the same level as the kill enemies, spawn the enemies in the level when the quest is given to the player ***
            QuestObjective curObjective = quest.getCurObjective();

            String objectiveLevel = curObjective.level;

            if (objectiveLevel.equals("Castle")) { //prevents nullPointer error - Castle uses the CityLevel class, there is no "CastleLevel" class.
                objectiveLevel = "City";
            }

            if (objectiveLevel.equals("Dungeon")) { //prevents nullPointer error - Dungeon uses the SewerLevel class, there is no "DungeonLevel" class.
                objectiveLevel = "Sewer";
            }

            if (objectiveLevel.equals("Shadow Lands")) { //prevents nullPointer error - Shadow Lands uses the ShadowLandsLevel class, there is no "Shadow LandsLevel" class.
                objectiveLevel = "ShadowLands";
            }

            String questObjectiveLevelClassName = objectiveLevel.concat("Level");

            if (Dungeon.level.getClass().getSimpleName().equals(questObjectiveLevelClassName)) {
                spawnKillQuestMobsInSameLevelAsHero(quest, Dungeon.level);
                //spawnKillQuestMobs(quest, Dungeon.level);
            }
            //**** END ****

            // **** Adds quest to the quest journal ***
            addToQuestJournal(quest);
            // **** ****

            setQuestGiven(DataHandler.getInstance().questList, quest.questName);
            quest.given = true;

        }

    }



    /**
     * speak & speak_fetch objective logic
     */

    //gets called when speaking to an NPC you must speak to as part of the speak quest.
    public void handleSpeakQuest(NPC npc, Quest quest) {
        System.out.println("handle speak to quest");


        GameScene.show(new WndQuest(npc, quest.QUEST_COMPLETED_TEXT));

        quest.getCurObjective().objectiveCompleted();

        npc.removeQuest();

        completeQuest(npc, quest);

        npc.sprite.hideQuestHandInIcon();

        npc.assignSpeakToQuest(false);
        npc.setQuestGiver(true);

    }

    //gets called when when interacting with quest giver, not the NPC you must speak to
    public void speakQuestInteract(NPC npc, Quest quest) {
        if (quest.given) {
            GameScene.show(new WndQuest(npc, quest.QUEST_GIVEN_TEXT));
        } else {
            GameScene.show(new WndQuest(npc, quest.QUEST_NOT_GIVEN_TEXT));

            DataHandler.getInstance().givenQuests.add(quest);

            // **** adds quest to quest journal ****
            addToQuestJournal(quest);
            // **** ****

            setQuestGiven(DataHandler.getInstance().questList, quest.questName);
            quest.given = true;

            String questGiverLevel = quest.questGiverLevel;

            if (questGiverLevel.equals(questObjective.level)) {
                setSpeakToQuestNPC(questObjective.speakToNPC);
                Dungeon.level.spawnSpeakToQuestNPCS();
                setQuestNPCIcons();
            }
        }
    }

    public static void setSpeakToQuestNPC(String npcName) {

        for (Mob mob : Dungeon.level.mobs) {
            String curNPCName = mob.getClass().getSimpleName();

            if (curNPCName.equals(npcName)) {
                NPC speakToNPC = (NPC)mob;
                //speakToNPC.questGiver = false;
                speakToNPC.assignSpeakToQuest(true);

                return;
            } else {
                System.out.println("NOT WORKING HERE");
            }
        }

    }

    //gets called when speaking to an NPC you must speak to as part of the speak_fetch quest. NPC will give you an item.
    public void handleSpeakFetchQuest(NPC npc, Quest quest) {

        String questLevel = quest.getCurObjective().level;

        if (questLevel.equals("Castle")) { //prevents nullPointer error - Castle uses the CityLevel class, there is no "CastleLevel" class.
            questLevel = "City";
        }

        if (questLevel.equals("Dungeon")) { //prevents nullPointer error - Dungeon uses the SewerLevel class, there is no "DungeonLevel" class.
            questLevel = "Sewer";
        }

        if (questLevel.equals("Shadow Lands")) { //prevents nullPointer error - Shadow Lands uses the ShadowLandsLevel class, there is no "Shadow LandsLevel" class.
            questLevel = "ShadowLands";
        }


        String questLevelClass = questLevel.concat("Level");

        if (Dungeon.level.getClass().getSimpleName().equals(questLevelClass)) {

            GameScene.show(new WndQuest(npc, quest.QUEST_COMPLETED_TEXT));

            String itemPackage = "com.watabou.pixeldungeon.items.quest.";
            String itemName = questObjective.itemName;
            String itemClassName = itemPackage.concat(itemName);

            try {
                Class<?> itemClass = Class.forName(itemClassName);

                Item questItem = (Item) itemClass.newInstance();

                questItem.doPickUp(Dungeon.hero);

                questItem.assignQuest(quest);

            } catch (Exception e) {
                System.out.println("Error in method: handleSpeakFetchQuest, class: QuestHandler");
                e.printStackTrace();
            }

            completeQuest(npc, quest);

            npc.removeQuest();

            npc.sprite.hideQuestHandInIcon();

            npc.assignSpeakToQuest(false);
        } else {

            GameScene.show(new WndQuest(npc, "You're talking to the wrong version of me brew"));


        }

    }



    /**
     * "helper/misc" methods
     */

    //if the current objective's "speak to npc" is in the same zone as the next objective's item/mob/npc, spawn that item/mob/npc.
    public static void nextObjectiveSpawnHelper(Quest quest) {

        QuestObjective currentObjective = quest.getCurObjective();

        System.out.println("current objective is: " +currentObjective.questType);
        if (currentObjective.level.equals(DataHandler.getInstance().getCurrentLevel())) {

            switch (currentObjective.questType) {

                case "kill":
                case "kill_fetch":

                    QuestObjective curObjective = quest.getCurObjective();

                    String objectiveLevel = curObjective.level;

                    if (objectiveLevel.equals("Castle")) { //prevents nullPointer error - Castle uses the CityLevel class, there is no "CastleLevel" class.
                        objectiveLevel = "City";
                    }

                    if (objectiveLevel.equals("Dungeon")) { //prevents nullPointer error - Dungeon uses the SewerLevel class, there is no "DungeonLevel" class.
                        objectiveLevel = "Sewer";
                    }

                    if (objectiveLevel.equals("Shadow Lands")) { //prevents nullPointer error - Shadow Lands uses the ShadowLandsLevel class, there is no "Shadow LandsLevel" class.
                        objectiveLevel = "ShadowLands";
                    }

                    String questObjectiveLevelClassName = objectiveLevel.concat("Level");

                    if (Dungeon.level.getClass().getSimpleName().equals(questObjectiveLevelClassName)) {
                        spawnKillQuestMobsInSameLevelAsHero(quest, Dungeon.level);
                        //spawnKillQuestMobs(quest, Dungeon.level);
                    }


                    //spawnKillQuestMobsInSameLevelAsHero(quest, Dungeon.level);
                    break;
                case "fetch":
                    spawnQuestItem(currentObjective.itemName, Dungeon.level, quest);
                case "speak":
                case "speak_fetch":
//                    Dungeon.level.spawnSpeakToQuestNPCS();
//                    setSpeakToQuestNPC(currentObjective.speakToNPC);

                    setSpeakToQuestNPC(questObjective.speakToNPC);
                    Dungeon.level.spawnSpeakToQuestNPCS();
                    setQuestNPCIcons();
                    break;

            }

        }
    }

    public static void setQuestGiven(ArrayList<Quest> questList, String givenQuestName) {
        for (Quest quest : questList) {
            if (givenQuestName.equals(quest.questName)) {
                quest.given = true;
            }
        }
    }

    public static void setQuestObjectiveComplete(ArrayList<Quest> questList, String givenQuestName) {

        for (Quest quest : questList) {

            if (givenQuestName.equals(quest.questName)) {

                quest.getCurObjective().objectiveComplete = true;

            }

        }


    }

    public static void setQuestComplete(ArrayList<Quest> questList, String givenQuestName) {
        for (Quest quest : questList) {
            if (givenQuestName.equals(quest.questName)) {
                quest.questCompleted();
            }
        }
    }

    public static void addToQuestJournal(Quest quest) {

        String journalEntry = "";

        QuestObjective objective = quest.getCurObjective();

        switch (objective.questType) {

            case "speak":
                //journalEntry = "Speak to " +objective.speakToNPC+ " in the " +objective.level;
                journalEntry = "Speak to " +DataHandler.getInstance().getNPCName(objective.speakToNPC)+ " in the " +objective.level;
                break;
            case "speak_fetch":
                //journalEntry = "Collect a " +objective.itemName+ " from " +objective.speakToNPC+ " in the " +objective.level;
                journalEntry = "Collect a " +DataHandler.getInstance().getItemName(objective.itemName)+ " from " +DataHandler.getInstance().getNPCName(objective.speakToNPC)+ " in the " +objective.level;
                break;
            case "kill":
                if (objective.leftToKill > 1) {
                    //journalEntry = "Kill " +objective.leftToKill+ " " +objective.enemy+ "'s in the " +objective.level;
                    journalEntry = "Kill " +objective.leftToKill+ " " +DataHandler.getInstance().getEnemyName(objective.enemy)+ "'s in the " +objective.level;
                } else {
                    //journalEntry = "Kill " +objective.leftToKill+ " " +objective.enemy+ " in the " +objective.level;
                    journalEntry = "Kill " +objective.leftToKill+ " " +DataHandler.getInstance().getEnemyName(objective.enemy)+ " in the " +objective.level;
                }
                break;
            case "kill_fetch":
                //journalEntry = "Collect a " +objective.itemName+ " from " +objective.enemy+ " in the " +objective.level;
                journalEntry = "Collect a " +DataHandler.getInstance().getItemName(objective.itemName)+ " from " +DataHandler.getInstance().getEnemyName(objective.enemy)+ " in the " +objective.level;
                break;
            case "fetch":
                //journalEntry = "Collect a " +objective.itemName+ " from the " +objective.level;
                journalEntry = "Collect a " +DataHandler.getInstance().getItemName(objective.itemName)+ " from somewhere in the " +objective.level;
                break;
            case "use_item":
                //journalEntry = "Use " +objective.itemName;
                journalEntry = "Use " +DataHandler.getInstance().getItemName(objective.itemName);
                break;
        }

        //Journal.addQuestEntry(journalEntry);
        QuestJournal.addQuestEntry(journalEntry);
    }

    public static void removeFromQuestJournal(Quest quest) {

        String journalEntry = "";

        QuestObjective objective = quest.getCurObjective();

        switch (objective.questType) {

            case "speak":
                //journalEntry = "Speak to " +objective.speakToNPC+ " in the " +objective.level;
                journalEntry = "Speak to " +DataHandler.getInstance().getNPCName(objective.speakToNPC)+ " in the " +objective.level;
                break;
            case "speak_fetch":
                //journalEntry = "Collect a " +objective.itemName+ " from " +objective.speakToNPC+ " in the " +objective.level;
                journalEntry = "Collect a " +DataHandler.getInstance().getItemName(objective.itemName)+ " from " +DataHandler.getInstance().getNPCName(objective.speakToNPC)+ " in the " +objective.level;
                break;
            case "kill":
                if (objective.leftToKill > 1) {
                    //journalEntry = "Kill " +objective.leftToKill+ " " +objective.enemy+ "'s in the " +objective.level;
                    journalEntry = "Kill " +objective.leftToKill+ " " +DataHandler.getInstance().getEnemyName(objective.enemy)+ "'s in the " +objective.level;
                } else {
                    //journalEntry = "Kill " +objective.leftToKill+ " " +objective.enemy+ " in the " +objective.level;
                    journalEntry = "Kill " +objective.leftToKill+ " " +DataHandler.getInstance().getEnemyName(objective.enemy)+ " in the " +objective.level;
                }
                break;
            case "kill_fetch":
                //journalEntry = "Collect a " +objective.itemName+ " from " +objective.enemy+ " in the " +objective.level;
                journalEntry = "Collect a " +DataHandler.getInstance().getItemName(objective.itemName)+ " from " +DataHandler.getInstance().getEnemyName(objective.enemy)+ " in the " +objective.level;
                break;
            case "fetch":
                //journalEntry = "Collect a " +objective.itemName+ " from the " +objective.level;
                journalEntry = "Collect a " +DataHandler.getInstance().getItemName(objective.itemName)+ " from somewhere in the " +objective.level;
                break;
            case "use_item":
                //journalEntry = "Use " +objective.itemName;
                journalEntry = "Use " +DataHandler.getInstance().getItemName(objective.itemName);
                break;
        }

        QuestJournal.removeQuestEntry(journalEntry);

    }

    public static void showNextObjectiveMessage(Quest quest) {

        QuestObjective previousObjective = quest.questObjectives.get(quest.curObjective - 1);

        //not needed for these types of quests because the speak to npc will tell you what to do next.
        if (previousObjective.questType.equals("speak") || previousObjective.questType.equals("speak_fetch")) {
            return;
        }

        String objectiveDescription = "";

        QuestObjective objective = quest.getCurObjective();

        switch (objective.questType) {

            case "speak":
                objectiveDescription = "Speak to " +DataHandler.getInstance().getNPCName(objective.speakToNPC)+ " in the " +objective.level;
                break;
            case "speak_fetch":
                objectiveDescription = "Collect a " +objective.itemName+ " from " +objective.speakToNPC+ " in the " +objective.level;
                break;
            case "kill":
                if (objective.leftToKill > 1) {
                    objectiveDescription = "Kill " +objective.leftToKill+ " " +objective.enemy+ "'s in the " +objective.level;
                } else {
                    objectiveDescription = "Kill " +objective.leftToKill+ " " +objective.enemy+ " in the " +objective.level;
                }
                break;
            case "kill_fetch":
                objectiveDescription = "Collect a " +objective.itemName+ " from " +objective.enemy+ " in the " +objective.level;
                break;
            case "fetch":
                objectiveDescription = "Collect a " +objective.itemName+ " from the " +objective.level;
                break;
            case "use_item":
                objectiveDescription = "Use " +objective.itemName;
                break;
        }

        GameScene.show(new WndMessage(objectiveDescription));
    }

    public static void completeQuest(NPC npc, Quest quest) {

        System.out.println("Is it the final quest and objective? " +isFinalQuest(quest, quest.getCurObjective()));

        if (quest.curObjective == (quest.questObjectives.size() - 1)) {
            quest.questCompleted();

            showCompleteQuestWindow(quest);

            setQuestComplete(DataHandler.getInstance().questList, quest.questName);
            removeFromQuestJournal(quest);

            Dungeon.level.spawnQuestGiverNPCs();
            setQuestNPCIcons();

            if (actComplete()) {

                DataHandler.getInstance().actComplete = true;
//                try {
//                    Dungeon.saveAll();
//
//                } catch (Exception e) {
//                    System.out.println(e.getMessage());
//                }
//
//
//                DataHandler.getInstance().nextAct();
//
//                int currentAct = DataHandler.getInstance().currentAct;
//
//                switch (currentAct) {
//
//                    case 2:
//                        DataHandler.getInstance().questList = DataHandler.getInstance().actTwoQuests;
//                        break;
//                    case 3:
//                        DataHandler.getInstance().questList = DataHandler.getInstance().actThreeQuests;
//                        break;
//                    case 4:
//                        InterlevelScene.mode = InterlevelScene.Mode.CREDITS;
//                        Game.switchScene(InterlevelScene.class);
//                        return;
//                }
//
//                DataHandler.getInstance().actStarting = true;
//
//                InterlevelScene.mode = InterlevelScene.Mode.NEXTACT;
//                Game.switchScene(InterlevelScene.class);
            }

//
//            if (npc != null) {
//                npc.removeQuest();
//            }

        } else {
            removeFromQuestJournal(quest);

            setQuestObjectiveComplete(DataHandler.getInstance().questList, quest.questName);
            quest.getCurObjective().objectiveComplete = true;

            quest.curObjective++;

            showNextObjectiveMessage(quest);

            nextObjectiveSpawnHelper(quest);

            addToQuestJournal(quest);
        }

        setQuestNPCIcons();
    }

    public static boolean actComplete() {

        for (Quest quest : DataHandler.getInstance().questList) {

            if (!quest.questComplete) {
                return false;
            }
        }

        return true;
    }

    public static void setQuestNPCIcons() {
        for (Mob mob : Dungeon.level.mobs) {

            if (mob instanceof NPC) {

                NPC curNPC = (NPC)mob;

                Quest curQuest = curNPC.quest;

                if (curQuest != null)

                    if (!(curQuest.given) && curNPC.questGiver) {

                        curNPC.sprite.showQuestIcon();
                        System.out.println("showing quest icon");

                    } else {

                        if (curNPC.speakToQuest) {
                            System.out.println(curNPC.getClass().getSimpleName()+ " is a speak to quest npc boi.");
                            curNPC.sprite.showQuestHandInIcon();
                            System.out.println("showing quest hand in icon");

                        }

                    }

            }
        }
    }

    public static boolean isFinalQuest(Quest givenQuest, QuestObjective givenObjective) {

        int questListSize = DataHandler.getInstance().questList.size();

        Quest finalQuest = DataHandler.getInstance().questList.get(questListSize - 1);

        int objectiveListSize = finalQuest.questObjectives.size();

        QuestObjective finalQuestObjective = finalQuest.questObjectives.get(objectiveListSize - 1);

        if (givenQuest == finalQuest && givenObjective == finalQuestObjective) {
            return true;
        }

        return false;
    }

    public static void showCompleteQuestWindow(Quest quest) {

//        if (isFinalQuest(quest, quest.getCurObjective())) {
//
//
//        }

        String questGiverName = quest.questGiver;

        if (quest.getCurObjective().questType.equals("speak")) {
            questGiverName = quest.getCurObjective().speakToNPC;
        }

        NPC questGiver = DataHandler.getInstance().newNPC(questGiverName);

        GameScene.show(new WndQuestNPC(questGiver, quest.QUEST_COMPLETED_TEXT));

    }
}
