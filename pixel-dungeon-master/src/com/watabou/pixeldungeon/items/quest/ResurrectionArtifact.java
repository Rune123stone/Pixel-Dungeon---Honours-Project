package com.watabou.pixeldungeon.items.quest;

import com.watabou.pixeldungeon.items.Item;
import com.watabou.pixeldungeon.sprites.ItemSpriteSheet;

public class ResurrectionArtifact extends Item {
    {
        name = "resurrection artifact";
        image = ItemSpriteSheet.SKULL;

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
                "An ancient vessel through which potion can be administered.";
    }

    @Override
    public int price() {
        return 100;
    }
}
