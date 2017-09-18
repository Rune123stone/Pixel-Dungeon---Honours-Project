package com.watabou.pixeldungeon.actors.mobs.npcs;

import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.pixeldungeon.Assets;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.actors.Char;
import com.watabou.pixeldungeon.actors.buffs.Buff;
import com.watabou.pixeldungeon.actors.buffs.Paralysis;
import com.watabou.pixeldungeon.actors.buffs.Roots;
import com.watabou.pixeldungeon.actors.mobs.Mob;
import com.watabou.pixeldungeon.scenes.GameScene;
import com.watabou.pixeldungeon.sprites.ShopkeeperSprite;

import java.util.HashSet;

public class DressMaker extends NPC {
    {
        name = "dress maker";
        spriteClass = ShopkeeperSprite.class;

        state = PASSIVE;
    }

    public DressMaker() {
        super();

        Sample.INSTANCE.load( Assets.SND_GHOST );


    }

    @Override
    public int defenseSkill( Char enemy ) {
        return 1000;
    }

    @Override
    public String defenseVerb() {
        return "evaded";
    }

    @Override
    public float speed() {
        return 0.5f;
    }

    @Override
    protected Char chooseEnemy() {
        return null;
    }

    @Override
    public void damage( int dmg, Object src ) {
    }


    @Override
    public void add( Buff buff ) {
    }

    @Override
    public boolean reset() {
        return true;
    }

    @Override
    public void interact() {
        super.interact();
        sprite.turnTo( pos, Dungeon.hero.pos );
        Sample.INSTANCE.play( Assets.SND_GHOST );
    }

    @Override
    public String description() {
        return
                "The ghost is barely visible. It looks like a shapeless " +
                        "spot of faint light with a sorrowful face.";
    }

    private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
    static {
        IMMUNITIES.add( Paralysis.class );
        IMMUNITIES.add( Roots.class );
    }

    @Override
    public HashSet<Class<?>> immunities() {
        return IMMUNITIES;
    }

    public static void replace(final Mob a, final Mob b ) {
        final float FADE_TIME = 0.5f;

        a.destroy();
        a.sprite.parent.add( new AlphaTweener( a.sprite, 0, FADE_TIME ) {
            protected void onComplete() {
                a.sprite.killAndErase();
                parent.erase( this );
            };
        } );

        b.pos = a.pos;
        GameScene.add( b );

        b.sprite.flipHorizontal = a.sprite.flipHorizontal;
        b.sprite.alpha( 0 );
        b.sprite.parent.add( new AlphaTweener( b.sprite, 1, FADE_TIME ) );
    }
}
