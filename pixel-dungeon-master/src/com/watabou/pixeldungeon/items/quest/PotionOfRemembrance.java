package com.watabou.pixeldungeon.items.quest;

import com.watabou.pixeldungeon.actors.hero.Hero;
import com.watabou.pixeldungeon.items.potions.Potion;

public class PotionOfRemembrance extends Potion{
    {
        name = "Potion of Remembrance";
    }

    @Override
    protected void apply( Hero hero ) {
        setKnown();
        //hero.earnExp( hero.maxExp() - hero.exp );
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
