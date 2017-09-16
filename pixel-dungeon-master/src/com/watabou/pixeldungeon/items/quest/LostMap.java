package com.watabou.pixeldungeon.items.quest;

import com.watabou.pixeldungeon.items.Item;
import com.watabou.pixeldungeon.sprites.ItemSpriteSheet;

public class LostMap extends Item {
    {
        name = "lost map";
        image = ItemSpriteSheet.SCROLL_BERKANAN;

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
                "The cartographer's lost map.";
    }

    @Override
    public int price() {
        return 100;
    }
}
