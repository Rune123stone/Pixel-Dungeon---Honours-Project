package com.watabou.pixeldungeon.items.quest;

import com.watabou.pixeldungeon.items.Item;
import com.watabou.pixeldungeon.sprites.ItemSpriteSheet;

public class Artifact extends Item {
    {
        name = "sacred artifact";
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
                "A powerful relic.";
    }

    @Override
    public int price() {
        return 100;
    }
}
