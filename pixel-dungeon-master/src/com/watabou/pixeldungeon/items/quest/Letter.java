package com.watabou.pixeldungeon.items.quest;

import com.watabou.pixeldungeon.actors.hero.Hero;
import com.watabou.pixeldungeon.items.Item;
import com.watabou.pixeldungeon.scenes.GameScene;
import com.watabou.pixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.pixeldungeon.windows.WndHero;
import com.watabou.pixeldungeon.windows.WndMessage;

import java.util.ArrayList;

public class Letter extends Item {
    {
        name = "letter";
        image = ItemSpriteSheet.SCROLL_BERKANAN;

        unique = true;
    }

    String letterContent = "Confidential - do not read.";

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
                "A letter, it's probably not for you though, since you have no friends and all.";
    }

    @Override
    public ArrayList<String> actions(Hero hero ) {
        ArrayList<String> actions = super.actions(hero);
        actions.add( AC_USEQUESTITEM );
        return actions;
    }

    @Override
    public void useQuestItem() {
        super.useQuestItem();
        GameScene.show(new WndMessage(letterContent));

    }

}
