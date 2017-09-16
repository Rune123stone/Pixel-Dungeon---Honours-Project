package com.watabou.pixeldungeon.items.quest;

import com.watabou.pixeldungeon.items.Item;
import com.watabou.pixeldungeon.sprites.ItemSpriteSheet;

public class PlantSample extends Item {
    {
        name = "plant sample";
        image = ItemSpriteSheet.DUST;

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
                "A strange plant sample.";
    }

    @Override
    public int price() {
        return 100;
    }
}
