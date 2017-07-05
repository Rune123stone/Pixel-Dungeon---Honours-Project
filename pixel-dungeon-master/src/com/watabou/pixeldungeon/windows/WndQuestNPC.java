package com.watabou.pixeldungeon.windows;

import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.actors.hero.Hero;
import com.watabou.pixeldungeon.actors.mobs.npcs.Ghost;
import com.watabou.pixeldungeon.actors.mobs.npcs.NPC;
import com.watabou.pixeldungeon.items.Item;
import com.watabou.pixeldungeon.utils.GLog;

public class WndQuestNPC extends WndQuest{

    private static final String TXT_WEAPON	= "Weapon";
    private static final String TXT_ARMOR	= "Armor";

    private NPC npc;
    private Item questItem;

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

        if (questItem != null) {
            questItem.detach( Dungeon.hero.belongings.backpack );
        }

        Item reward = index == 0 ? Ghost.Quest.weapon : Ghost.Quest.armor;
        if (reward.doPickUp( Dungeon.hero )) {
            GLog.i( Hero.TXT_YOU_NOW_HAVE, reward.name() );
        } else {
            Dungeon.level.drop( reward, npc.pos ).sprite.drop();
        }

        npc.yell( "Farewell, adventurer!" );
        npc.die( null );

        Ghost.Quest.complete();
    }
}
