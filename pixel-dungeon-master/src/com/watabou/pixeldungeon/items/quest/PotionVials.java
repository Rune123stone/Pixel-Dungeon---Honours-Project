package com.watabou.pixeldungeon.items.quest;

import com.watabou.pixeldungeon.items.Item;
import com.watabou.pixeldungeon.sprites.ItemSpriteSheet;

public class PotionVials extends Item {
    {
        name = "giant rat skull";
        image = ItemSpriteSheet.POTION_AMBER;

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
                "A collection of empty potion vials.";
    }

    @Override
    public int price() {
        return 100;
    }
}
