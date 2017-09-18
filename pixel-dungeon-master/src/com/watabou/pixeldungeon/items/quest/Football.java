package com.watabou.pixeldungeon.items.quest;

import com.watabou.pixeldungeon.items.Item;
import com.watabou.pixeldungeon.sprites.ItemSpriteSheet;

public class Football extends Item {
    {
        name = "football";
        image = ItemSpriteSheet.BOOMERANG;

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
                "What is this strange prolate-spheroid-shaped device?";
    }

    @Override
    public int price() {
        return 1;
    }
}
