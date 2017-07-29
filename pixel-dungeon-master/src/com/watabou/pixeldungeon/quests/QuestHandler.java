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
import com.watabou.pixeldungeon.windows.WndQuest;
import com.watabou.pixeldungeon.windows.WndQuestNPC;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Iterator;

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

                    //if the quest giver level is the same as the speakTo NPC level
                    if (DataHandler.getInstance().getCurrentLevel().equals(questObjective.level)) {
                        setSpeakToQuestNPC(questObjective.speakToNPC);
                    }

                    break;
                case "speak_fetch":
                    speakQuestInteract(npc, quest);

                    if (DataHandler.getInstance().getCurrentLevel().equals(questObjective.level)) {
                        setSpeakToQuestNPC(questObjective.speakToNPC);
                    }

            }
        }

    }

    /**
     * USE ITEM quest methods & variables
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
     * FETCH quest methods & variables
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
                GameScene.show(new WndQuestNPC(npc, item, questObjective.QUEST_COMPLETED_TEXT));

                completeQuest(npc, quest);

            } else {
                GameScene.show(new WndQuest(npc, questObjective.QUEST_GIVEN_TEXT));
            }
        } else {
            GameScene.show(new WndQuest(npc, questObjective.QUEST_NOT_GIVEN_TEXT));

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
            String questObjectiveLevelClassName = questObjective.level.concat("Level");

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
     * kill quest methods & variables
     */
    //public boolean killedAll = false;

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
            questObjective.leftToKill--;

            addToQuestJournal(quest);
        } else {
            System.out.println("YEAHH BOIIIIIIII");
            //setQuestComplete(DataHandler.getInstance().questList, quest.questName);
            //killedAll = true;
        }
    }

    //gets called when interacting with quest giver of Kill Quest
    public void killQuestInteract(NPC npc, Quest quest) {

        if (quest.given) {
            if (questObjective.leftToKill == 0) { //checks if a hero has killed all necessary enemies
                GameScene.show(new WndQuestNPC(npc, questObjective.QUEST_COMPLETED_TEXT));

                completeQuest(npc, quest);

            } else {
                GameScene.show(new WndQuest(npc, questObjective.QUEST_GIVEN_TEXT));
            }
        } else {
            GameScene.show(new WndQuest(npc, questObjective.QUEST_NOT_GIVEN_TEXT));

            //**** START if the quest giver is in the same level as the kill enemies, spawn the enemies in the level when the quest is given to the player ***
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

            if (Dungeon.level.getClass().getSimpleName().equals(questGiverLevelClassName)) {
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
     * SPECIAL CASE: kill + fetch quest
     */
    public void handleKillFetchQuest(Mob curMob) {

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
                setUseItemQuest(questItem);
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
                GameScene.show(new WndQuestNPC(npc, item, questObjective.QUEST_COMPLETED_TEXT));

                completeQuest(npc, quest);

            } else {
                GameScene.show(new WndQuest(npc, questObjective.QUEST_GIVEN_TEXT));
            }
        } else {

            GameScene.show(new WndQuest(npc, questObjective.QUEST_NOT_GIVEN_TEXT));

            //**** START if the quest giver is in the same level as the kill enemies, spawn the enemies in the level when the quest is given to the player ***
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

            if (Dungeon.level.getClass().getSimpleName().equals(questGiverLevelClassName)) {
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
     * SpeakTo NPC quest
     */
    //gets called when speaking to an NPC you must speak to as part of the speak quest.
    public void handleSpeakQuest(NPC npc, Quest quest) {
        System.out.println("handle speak to quest");
        GameScene.show(new WndQuest(npc, questObjective.QUEST_COMPLETED_TEXT));

        quest.getCurObjective().objectiveCompleted();

//        if (quest.curObjective == (quest.questObjectives.size() - 1)) {
//            System.out.println("quest completed");
//            quest.questCompleted();
//            setQuestComplete(DataHandler.getInstance().questList, quest.questName);
//
//            String journalEntry = "Speak to " +quest.getCurObjective().speakToNPC+ " in the " +questObjective.level;
//            //Journal.removeQuestEntry(journalEntry);
//
//            npc.removeQuest();
//        } else {
//            quest.curObjective++;
//        }

        completeQuest(npc, quest);

        npc.assignSpeakToQuest(false);

    }

    //gets called when when interacting with quest giver, not the NPC you must speak to
    public void speakQuestInteract(NPC npc, Quest quest) {
        if (quest.given) {
            GameScene.show(new WndQuestNPC(npc, questObjective.QUEST_GIVEN_TEXT));
        } else {
            GameScene.show(new WndQuestNPC(npc, questObjective.QUEST_NOT_GIVEN_TEXT));

            DataHandler.getInstance().givenQuests.add(quest);

            // **** adds quest to quest journal ****
            addToQuestJournal(quest);
            // **** ****

            setQuestGiven(DataHandler.getInstance().questList, quest.questName);
            quest.given = true;

        }
    }

    public static void setQuestGiven(ArrayList<Quest> questList, String givenQuestName) {
        for (Quest quest : questList) {
            if (givenQuestName.equals(quest.questName)) {
                quest.given = true;
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

    /**
     * SPECIAL CASE: SpeakTo NPC + Fetch quest
     */
    //gets called when speaking to an NPC you must speak to as part of the speak_fetch quest. NPC will give you an item.
    public void handleSpeakFetchQuest(NPC npc, Quest quest) {

        GameScene.show(new WndQuest(npc, questObjective.QUEST_COMPLETED_TEXT));

        String itemPackage = "com.watabou.pixeldungeon.items.quest.";
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

        completeQuest(npc, quest);
        npc.assignSpeakToQuest(false);

    }


    /**
     * The following methods will handle the spawning of quest npc's, enemies, and items if said variables are in the same level as the quest giver.
     * These get will get called on interaction with the quest giver npc.
     */

    public static void setSpeakToQuestNPC(String npcName) {

        for (Mob mob : Dungeon.level.mobs) {
            String curNPCName = mob.getClass().getSimpleName();

            if (curNPCName.equals(npcName)) {
                NPC speakToNPC = (NPC)mob;
                speakToNPC.assignSpeakToQuest(true);

                return;
            }
        }

    }



    public static void addToQuestJournal(Quest quest) {

        String journalEntry = "";

        QuestObjective objective = quest.getCurObjective();

        switch (objective.questType) {

            case "speak":
                journalEntry = "Speak to " +objective.speakToNPC+ " in the " +objective.level;
                break;
            case "speak_fetch":
                journalEntry = "Collect a " +objective.itemName+ " from " +objective.speakToNPC+ " in the " +objective.level;
                break;
            case "kill":
                if (objective.leftToKill > 1) {
                    journalEntry = "Kill " +objective.leftToKill+ " " +objective.enemy+ "'s in the " +objective.level;
                } else {
                    journalEntry = "Kill " +objective.leftToKill+ " " +objective.enemy+ " in the " +objective.level;
                }
                break;
            case "kill_fetch":
                journalEntry = "Collect a " +objective.itemName+ " from " +objective.enemy+ " in the " +objective.level;
                break;
            case "fetch":
                journalEntry = "Collect a " +objective.itemName+ " from the " +objective.level;
                break;
            case "use_item":
                journalEntry = "Use " +objective.itemName;
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
                journalEntry = "Speak to " +objective.speakToNPC+ " in the " +objective.level;
                break;
            case "speak_fetch":
                journalEntry = "Collect a " +objective.itemName+ " from " +objective.speakToNPC+ " in the " +objective.level;
                break;
            case "kill":
                if (objective.leftToKill > 1) {
                    journalEntry = "Kill " +objective.leftToKill+ " " +objective.enemy+ "'s in the " +objective.level;
                } else {
                    journalEntry = "Kill " +objective.leftToKill+ " " +objective.enemy+ " in the " +objective.level;
                }
                break;
            case "kill_fetch":
                journalEntry = "Collect a " +objective.itemName+ " from " +objective.enemy+ " in the " +objective.level;
                break;
            case "fetch":
                journalEntry = "Collect a " +objective.itemName+ " from the " +objective.level;
                break;
            case "use_item":
                journalEntry = "Use " +objective.itemName;
                break;
        }

        QuestJournal.removeQuestEntry(journalEntry);

    }

    public static void completeQuest(NPC npc, Quest quest) {

        if (quest.curObjective == (quest.questObjectives.size() - 1)) {
            System.out.println("quest completed");
            quest.questCompleted();

            setQuestComplete(DataHandler.getInstance().questList, quest.questName);
            removeFromQuestJournal(quest);
            System.out.println("Act complete?: " +actComplete());

            if (actComplete()) {

                DataHandler.getInstance().nextAct();

                int currentAct = DataHandler.getInstance().currentAct;

                if (currentAct == 2) {
                    DataHandler.getInstance().questList = DataHandler.getInstance().actTwoQuests;
                } else {
                    DataHandler.getInstance().questList = DataHandler.getInstance().actThreeQuests;
                }

                InterlevelScene.mode = InterlevelScene.Mode.DESCEND;
                Game.switchScene(InterlevelScene.class);
            }


//            if (npc != null) {
//                //npc.removeQuest();
//
//            }

        } else {
            removeFromQuestJournal(quest);
            quest.curObjective++;
            addToQuestJournal(quest);
        }
    }

    public static boolean actComplete() {

        for (Quest quest : DataHandler.getInstance().questList) {

            if (!quest.questComplete) {
                return false;
            }
        }

        return true;
    }


    public static void assignQuest(Quest quest, String npcName) {
        for (Mob mob : Dungeon.level.mobs) {
            String curNPCName = mob.getClass().getSimpleName();

            if (curNPCName.equals(npcName)) {
                NPC speakToNPC = (NPC)mob;

                speakToNPC.assignQuest(quest);

                return;
            }
        }
    }

    public static void assignNotQuestGiver(String npcName) {
        for (Mob mob : Dungeon.level.mobs) {
            String curNPCName = mob.getClass().getSimpleName();

            if (curNPCName.equals(npcName)) {
                NPC speakToNPC = (NPC)mob;

                speakToNPC.setQuestGiver(false);

                return;
            }
        }
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
