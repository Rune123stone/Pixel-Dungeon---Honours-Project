package com.watabou.pixeldungeon.items.quest;

import com.watabou.pixeldungeon.items.Item;
import com.watabou.pixeldungeon.sprites.ItemSpriteSheet;

public class PotionOfImmunity extends Item {
    {
        name = "potion of immunity";
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
                "Will you immune to poisoning for a short time, you hope.";
    }

    @Override
    public int price() {
        return 100;
    }
}
