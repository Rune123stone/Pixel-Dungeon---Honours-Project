package com.watabou.pixeldungeon.items.quest;

import com.watabou.pixeldungeon.items.Item;
import com.watabou.pixeldungeon.sprites.ItemSpriteSheet;

public class SpecialDust extends Item {
    {
        name = "special dust";
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
                "Like regular Dust, but with poison-cleansing properties.";
    }

    @Override
    public int price() {
        return 100;
    }
}
