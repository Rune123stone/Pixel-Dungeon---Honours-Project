package com.watabou.pixeldungeon.items.quest;

import com.watabou.pixeldungeon.actors.mobs.npcs.NPC;
import com.watabou.pixeldungeon.items.Item;
import com.watabou.pixeldungeon.sprites.ItemSpriteSheet;

public class MedicinalHerb extends Item {
    {
        name = "medicinal herb";
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
                "Can be used as medicine, but only for Claudette.";
    }

    @Override
    public int price() {
        return 100;
    }
}
