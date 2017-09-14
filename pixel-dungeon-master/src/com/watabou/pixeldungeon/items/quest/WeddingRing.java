package com.watabou.pixeldungeon.items.quest;

import com.watabou.pixeldungeon.actors.blobs.ToxicGas;
import com.watabou.pixeldungeon.actors.buffs.Burning;
import com.watabou.pixeldungeon.actors.buffs.Poison;
import com.watabou.pixeldungeon.actors.mobs.Eye;
import com.watabou.pixeldungeon.actors.mobs.Warlock;
import com.watabou.pixeldungeon.actors.mobs.Yog;
import com.watabou.pixeldungeon.items.rings.Ring;
import com.watabou.pixeldungeon.levels.traps.LightningTrap;
import com.watabou.pixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

import java.util.HashSet;

public class WeddingRing extends Ring {
    {
        name = "Wedding Ring";
        image = ItemSpriteSheet.RING_EMERALD;
    }

    @Override
    protected Ring.RingBuff buff( ) {
        return new WeddingRing.Resistance();
    }

    @Override
    public String desc() {
        return isKnown() ?
                "A ring symbolising the eternal union between two lovers." +
                        "Cooldown: 2 years" :
                super.desc();
    }

    private static final HashSet<Class<?>> EMPTY = new HashSet<Class<?>>();
    private static final HashSet<Class<?>> FULL;
    static {
        FULL = new HashSet<Class<?>>();
        FULL.add( Burning.class );
        FULL.add( ToxicGas.class );
        FULL.add( Poison.class );
        FULL.add( LightningTrap.Electricity.class );
        FULL.add( Warlock.class );
        FULL.add( Eye.class );
        FULL.add( Yog.BurningFist.class );
    }

    public class Resistance extends Ring.RingBuff {

        public HashSet<Class<?>> resistances() {
            if (Random.Int( level + 3 ) >= 3) {
                return FULL;
            } else {
                return EMPTY;
            }
        }

        public float durationFactor() {
            return level < 0 ? 1 : (2 + 0.5f * level) / (2 + level);
        }
    }
}
