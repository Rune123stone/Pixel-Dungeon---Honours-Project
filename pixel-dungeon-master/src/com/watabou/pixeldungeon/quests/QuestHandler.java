package com.watabou.pixeldungeon.quests;

import com.watabou.pixeldungeon.Dungeon;
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

    /**
     *FETCH quest methods & variables
     */
    //handles the interaction of FETCH quests. Works for KILL + FETCH quests as well.
    public  void fetchQuestCompleteInteract(NPC npc, Quest quest) { //take in item as well.

        if (quest.given) {
            Item item = Dungeon.hero.belongings.getItem(DriedRose.class); //checks if a hero has this item. DriedRose is used as an example in the mean time.
            if (item != null) {
                GameScene.show(new WndQuestNPC(npc, item, questObjective.QUEST_COMPLETED_TEXT));
                quest.curObjective++;
            } else {
                GameScene.show(new WndQuest(npc, questObjective.QUEST_GIVEN_TEXT ));
            }
        } else {
            GameScene.show(new WndQuest(npc, questObjective.QUEST_NOT_GIVEN_TEXT));
            quest.given = true;
        }
    }

    public void fetchQuestItemFromNPC(NPC npc) {
        try {

            String itemPackage = "com.watabou.pixeldungeon.items.quest.";
            String itemClassName = itemPackage.concat(questObjective.itemName);
            Class<?> item = Class.forName(itemClassName);

            questObjective.QUEST_GIVEN_TEXT = "Ey Boi, I got something 4 u..";

            if (npc.hasQuestItem) {
                GameScene.show(new WndQuest(npc, questObjective.QUEST_GIVEN_TEXT ));

                Dungeon.level.drop((Item)item.newInstance(), Dungeon.hero.pos).sprite.drop();
                npc.assignQuestItem(false);
            }

        } catch (Exception e) {
            System.out.println("Error in method: fetchQuestItemFromNPC");
            e.printStackTrace();
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

            Dungeon.level.drop((Item)item.newInstance(), randomPos);


        } catch (Exception e) {
            System.out.println("Problem in spawnQuestItem(): ");
            e.printStackTrace();
        }
    }

    /**
     * KILL quest methods & variables
     */
    public boolean killedAll = false;

    //works for KILL + FETCH quests as well
    public void spawnKillQuestMobs(Level level) {
        try {

            //gets the enemy class
            String mobPackage = "com.watabou.pixeldungeon.actors.mobs.";
            String mobClassName = mobPackage.concat(questObjective.enemy);
            Class<?> enemy = Class.forName(mobClassName);
            System.out.println("Enemy type: " +enemy.getSimpleName());

            for (int i = 0; i < questObjective.leftToKill; i++) { //for all enemies that need to spawn
                Mob enemyMob = (Mob) enemy.newInstance(); //spawn new enemy
                enemyMob.partOfKillQuest = true; //used for handling the die() method in the mob's class.
                enemyMob.pos = level.randomRespawnCell(); //sets the enemy's position
                SewerLevel.mobs.add(enemyMob); //adds the enemy to the level
                Actor.occupyCell(enemyMob);

                System.out.println("spawned mob "+i+ " at position " +enemyMob.pos);
            }
        } catch (Exception e) {
            System.out.println("spawnKillQuestMobs: ");
            e.printStackTrace();
        }
    }

    //gets called when quest enemy is killed
    public void handleKillQuest() {
        if (questObjective.leftToKill != 0) {
            questObjective.leftToKill--;
        } else {
            killedAll = true;
        }
    }

    //gets called when interacting with quest giver of Kill Quest
    public void killQuestCompleteInteract(NPC npc, Quest quest) {

        if (quest.given) {
            if (questObjective.leftToKill == 0) { //checks if a hero has killed all necessary enemies
                GameScene.show(new WndQuestNPC(npc, questObjective.QUEST_COMPLETED_TEXT));
                quest.curObjective++;
            } else {
                GameScene.show(new WndQuest(npc, questObjective.QUEST_GIVEN_TEXT ));
            }
        } else {
            GameScene.show(new WndQuest(npc, questObjective.QUEST_NOT_GIVEN_TEXT));
            quest.given = true;
        }
    }

    /**
     * SPECIAL CASE: KILL + FETCH quest
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
                Dungeon.level.drop((Item)item.newInstance(), curMob.pos).sprite.drop();
            }

        } catch (Exception e) {
            System.out.println("Error in handleKillFetchQuest: ");
            e.printStackTrace();
        }
    }

    /**
     * SpeakTo NPC quest
     */
    public void speakToNPC(NPC npc, Quest quest) {
        if (npc.speakToQuest) {
            GameScene.show(new WndQuest(npc, questObjective.QUEST_GIVEN_TEXT ));
            quest.curObjective++;
            npc.assignSpeakToQuest(false);
        }
    }






}
