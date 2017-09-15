package com.watabou.pixeldungeon.items.quest;

import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.effects.Enchanting;
import com.watabou.pixeldungeon.effects.Speck;
import com.watabou.pixeldungeon.items.Item;
import com.watabou.pixeldungeon.items.armor.Armor;
import com.watabou.pixeldungeon.items.scrolls.InventoryScroll;
import com.watabou.pixeldungeon.items.weapon.Weapon;
import com.watabou.pixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.pixeldungeon.utils.GLog;
import com.watabou.pixeldungeon.windows.WndBag;

public class ScrollOfDefence extends InventoryScroll {
    private static final String TXT_GLOWS	= "your %s glows in the dark";

    {
        name = "Scroll of Defence";
        inventoryTitle = "Select an enchantable item";
        mode = WndBag.Mode.ENCHANTABLE;
        image = ItemSpriteSheet.SCROLL_GYFU;
    }

    @Override
    protected void onItemSelected( Item item ) {

        ScrollOfRemoveCurse.uncurse( Dungeon.hero, item );

        if (item instanceof Weapon) {

            ((Weapon)item).enchant();

        } else {

            ((Armor)item).inscribe();

        }

        item.fix();

        curUser.sprite.emitter().start( Speck.factory( Speck.LIGHT ), 0.1f, 5 );
        Enchanting.show( curUser, item );
        GLog.w( TXT_GLOWS, item.name() );
    }

    @Override
    public String desc() {
        return
                "This scroll is able to imbue a weapon or an armor " +
                        "with a random enchantment, granting it a special power.";
    }
}
