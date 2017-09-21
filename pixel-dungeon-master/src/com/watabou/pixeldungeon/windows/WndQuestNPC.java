package com.watabou.pixeldungeon.windows;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.Challenges;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.actors.hero.Hero;
import com.watabou.pixeldungeon.actors.mobs.npcs.Ghost;
import com.watabou.pixeldungeon.actors.mobs.npcs.NPC;
import com.watabou.pixeldungeon.items.Generator;
import com.watabou.pixeldungeon.items.Item;
import com.watabou.pixeldungeon.items.armor.Armor;
import com.watabou.pixeldungeon.items.armor.ClothArmor;
import com.watabou.pixeldungeon.items.weapon.Weapon;
import com.watabou.pixeldungeon.items.weapon.missiles.MissileWeapon;
import com.watabou.pixeldungeon.scenes.InterlevelScene;
import com.watabou.pixeldungeon.story.DataHandler;
import com.watabou.pixeldungeon.utils.GLog;

public class WndQuestNPC extends WndQuest{

    private static final String TXT_WEAPON	= "Weapon";
    private static final String TXT_ARMOR	= "Armor";

    private NPC npc;
    private Item questItem;

    Weapon weapon = null;
    Armor armor = null;

    public WndQuestNPC( final NPC npc, final Item item, String text ) {

        super( npc, text, TXT_WEAPON, TXT_ARMOR );

        this.npc = npc;
        questItem = item;
    }

    public WndQuestNPC(final NPC npc, String text) {
        super( npc, text, TXT_WEAPON, TXT_ARMOR );

        this.npc = npc;
    }

    @Override
    protected void onSelect( int index ) {

        for (int i=0; i < 4; i++) {
            Item another;
            do {
                another = (Weapon) Generator.random( Generator.Category.WEAPON );
            } while (another instanceof MissileWeapon);

            if (weapon == null || another.level() > weapon.level()) {
                weapon = (Weapon)another;
            }
        }

        if (Dungeon.isChallenged( Challenges.NO_ARMOR )) {
            armor = (Armor)new ClothArmor().degrade();
        } else {
            armor = (Armor)Generator.random( Generator.Category.ARMOR );
            for (int i=0; i < 3; i++) {
                Item another = Generator.random( Generator.Category.ARMOR );
                if (another.level() > armor.level()) {
                    armor = (Armor)another;
                }
            }
        }

        weapon.identify();
        armor.identify();


        try {
            Item reward = index == 0 ? weapon : armor;
            if (reward.doPickUp(Dungeon.hero)) {
                GLog.i(Hero.TXT_YOU_NOW_HAVE, reward.name());
            } else {
                Dungeon.level.drop(reward, npc.pos).sprite.drop();
            }

        } catch (Exception e) {

        }

        if (DataHandler.getInstance().actComplete) {
            try {
                Dungeon.saveAll();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }


            DataHandler.getInstance().nextAct();

            int currentAct = DataHandler.getInstance().currentAct;

            switch (currentAct) {

                case 2:
                    DataHandler.getInstance().questList = DataHandler.getInstance().actTwoQuests;
                    break;
                case 3:
                    DataHandler.getInstance().questList = DataHandler.getInstance().actThreeQuests;
                    break;
                case 4:
                    InterlevelScene.mode = InterlevelScene.Mode.CREDITS;
                    Game.switchScene(InterlevelScene.class);
                    return;
            }

            DataHandler.getInstance().actStarting = true;

            InterlevelScene.mode = InterlevelScene.Mode.NEXTACT;
            Game.switchScene(InterlevelScene.class);

            DataHandler.getInstance().actComplete = false;
        }
    }
}
