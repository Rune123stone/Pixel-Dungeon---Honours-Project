package com.watabou.pixeldungeon.items.quest;

import com.watabou.pixeldungeon.items.Item;
import com.watabou.pixeldungeon.sprites.ItemSpriteSheet;

public class Tools extends Item {
    {
        name = "box o' tools";
        image = ItemSpriteSheet.TOMAHAWK;

        unique = true;
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public String info() {
        return
                "A box of tools with 'if found, please return to: Farmer Randy' written on the side.";
    }

    @Override
    public int price() {
        return 100;
    }
}
