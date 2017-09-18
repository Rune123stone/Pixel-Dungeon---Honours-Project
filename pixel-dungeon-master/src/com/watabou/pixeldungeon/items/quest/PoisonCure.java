package com.watabou.pixeldungeon.items.quest;

import com.watabou.pixeldungeon.items.Item;
import com.watabou.pixeldungeon.sprites.ItemSpriteSheet;

public class PoisonCure extends Item {
    {
        name = "poison cure";
        image = ItemSpriteSheet.POTION_CRIMSON;

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
                "The cure for the poison that's been plaguing the Kingdom.";
    }

    @Override
    public int price() {
        return 100;
    }
}
