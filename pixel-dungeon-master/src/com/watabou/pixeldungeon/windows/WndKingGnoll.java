package com.watabou.pixeldungeon.windows;

import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.actors.hero.Hero;
import com.watabou.pixeldungeon.actors.mobs.npcs.Ghost;
import com.watabou.pixeldungeon.actors.mobs.npcs.KingGnoll;
import com.watabou.pixeldungeon.items.Item;
import com.watabou.pixeldungeon.utils.GLog;

public class WndKingGnoll extends WndQuest {
    private static final String TXT_WEAPON	= "Gnoll's weapon";
    private static final String TXT_ARMOR	= "Gnoll's armor";

    private KingGnoll kingGnoll;
    private Item questItem;

    public WndKingGnoll( final KingGnoll kingGnoll, final Item item, String text ) {

        super( kingGnoll, text, TXT_WEAPON, TXT_ARMOR );

        this.kingGnoll = kingGnoll;
        questItem = item;
    }

    @Override
    protected void onSelect( int index ) {

        if (questItem != null) {
            questItem.detach( Dungeon.hero.belongings.backpack );
        }

        Item reward = index == 0 ? KingGnoll.Quest.weapon : KingGnoll.Quest.armor;
        if (reward.doPickUp( Dungeon.hero )) {
            GLog.i( Hero.TXT_YOU_NOW_HAVE, reward.name() );
        } else {
            Dungeon.level.drop( reward, kingGnoll.pos ).sprite.drop();
        }

        kingGnoll.yell( "Farewell, adventurer!" );
        kingGnoll.die( null );

        KingGnoll.Quest.complete();
    }
}
