package com.watabou.pixeldungeon.items.quest;

import com.watabou.noosa.audio.Sample;
import com.watabou.pixeldungeon.Assets;
import com.watabou.pixeldungeon.actors.Actor;
import com.watabou.pixeldungeon.actors.buffs.Invisibility;
import com.watabou.pixeldungeon.actors.mobs.npcs.MirrorImage;
import com.watabou.pixeldungeon.items.scrolls.Scroll;
import com.watabou.pixeldungeon.items.wands.WandOfBlink;
import com.watabou.pixeldungeon.levels.Level;
import com.watabou.pixeldungeon.scenes.GameScene;
import com.watabou.pixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class CultistsScroll extends Scroll {
    private static final int NIMAGES	= 3;

    {
        name = "Cultists Scroll";
        image = ItemSpriteSheet.SCROLL_YNGVI;
    }

    @Override
    protected void doRead() {

        ArrayList<Integer> respawnPoints = new ArrayList<Integer>();

        for (int i = 0; i < Level.NEIGHBOURS8.length; i++) {
            int p = curUser.pos + Level.NEIGHBOURS8[i];
            if (Actor.findChar( p ) == null && (Level.passable[p] || Level.avoid[p])) {
                respawnPoints.add( p );
            }
        }

        int nImages = NIMAGES;
        while (nImages > 0 && respawnPoints.size() > 0) {
            int index = Random.index( respawnPoints );

            MirrorImage mob = new MirrorImage();
            mob.duplicate( curUser );
            GameScene.add( mob );
            WandOfBlink.appear( mob, respawnPoints.get( index ) );

            respawnPoints.remove( index );
            nImages--;
        }

        if (nImages < NIMAGES) {
            setKnown();
        }

        Sample.INSTANCE.play( Assets.SND_READ );
        Invisibility.dispel();

        readAnimation();
    }

    @Override
    public String desc() {
        return
                "The incantation on this scroll will create illusionary twins of the reader, which will chase his enemies.";
    }
}
