package com.watabou.pixeldungeon.items.quest;

import com.watabou.pixeldungeon.items.Item;
import com.watabou.pixeldungeon.sprites.ItemSpriteSheet;

public class Heirloom extends Item {
    {
        name = "family heirloom";
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
                "An heirloom that is worth it's weight in sentimental-value, and not much else.";
    }

    @Override
    public int price() {
        return 1;
    }
}
