package com.watabou.pixeldungeon.items.quest;

import com.watabou.pixeldungeon.items.Item;
import com.watabou.pixeldungeon.sprites.ItemSpriteSheet;

public class Pens extends Item {
    {
        name = "collection of pens";
        image = ItemSpriteSheet.AMULET;

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
                "A collection of pens.";
    }

    @Override
    public int price() {
        return 100;
    }
}
