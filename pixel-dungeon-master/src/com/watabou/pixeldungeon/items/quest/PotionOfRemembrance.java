package com.watabou.pixeldungeon.items.quest;

import com.watabou.pixeldungeon.actors.hero.Hero;
import com.watabou.pixeldungeon.items.potions.Potion;
import com.watabou.pixeldungeon.sprites.ItemSpriteSheet;

public class PotionOfRemembrance extends Potion{
    {
        name = "Potion of Remembrance";
        image = ItemSpriteSheet.POTION_AMBER;
    }

    @Override
    protected void apply( Hero hero ) {
        setKnown();
    }

    @Override
    public String desc() {
        return
                "Side effects include: memory loss, headaches, death, hair loss ";
    }

    @Override
    public int price() {
        return isKnown() ? 80 * quantity : super.price();
    }
}
