package com.watabou.pixeldungeon.actors.mobs.npcs;

import android.util.Log;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.pixeldungeon.Assets;
import com.watabou.pixeldungeon.Challenges;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.Journal;
import com.watabou.pixeldungeon.actors.Actor;
import com.watabou.pixeldungeon.actors.Char;
import com.watabou.pixeldungeon.actors.buffs.Buff;
import com.watabou.pixeldungeon.actors.buffs.Paralysis;
import com.watabou.pixeldungeon.actors.buffs.Roots;
import com.watabou.pixeldungeon.actors.mobs.CursePersonification;
import com.watabou.pixeldungeon.actors.mobs.FetidRat;
import com.watabou.pixeldungeon.actors.mobs.Mob;
import com.watabou.pixeldungeon.effects.CellEmitter;
import com.watabou.pixeldungeon.effects.Speck;
import com.watabou.pixeldungeon.effects.particles.ShadowParticle;
import com.watabou.pixeldungeon.items.Generator;
import com.watabou.pixeldungeon.items.Item;
import com.watabou.pixeldungeon.items.armor.Armor;
import com.watabou.pixeldungeon.items.armor.ClothArmor;
import com.watabou.pixeldungeon.items.quest.DriedRose;
import com.watabou.pixeldungeon.items.quest.RatSkull;
import com.watabou.pixeldungeon.items.weapon.Weapon;
import com.watabou.pixeldungeon.items.weapon.missiles.MissileWeapon;
import com.watabou.pixeldungeon.levels.SewerLevel;
import com.watabou.pixeldungeon.scenes.GameScene;
import com.watabou.pixeldungeon.sprites.GhostSprite;
import com.watabou.pixeldungeon.sprites.GnollSprite;
import com.watabou.pixeldungeon.utils.Utils;
import com.watabou.pixeldungeon.windows.WndKingGnoll;
import com.watabou.pixeldungeon.windows.WndQuest;
import com.watabou.pixeldungeon.windows.WndSadGhost;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.HashSet;

/**
 * Test NPC - Cameron
 */
public class KingGnoll extends NPC {
    {
        name = "Gnoll King";
        spriteClass = GnollSprite.class;

        flying = false;
        state = FOLLOW;
    }

    public KingGnoll() {
        super();

        Sample.INSTANCE.load( Assets.SND_ALERT );
    }

    @Override
    public int defenseSkill( Char enemy ) {
        return 1;
    }

    @Override
    public String defenseVerb() {
        return "lol missed";
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
        sprite.turnTo( pos, Dungeon.hero.pos );
        Sample.INSTANCE.play( Assets.SND_BEE );

        KingGnoll.Quest.type.handler.interact( this );
    }

    @Override
    public String description() {
        return
                "The Gnoll King looks at you with a confused gaze." +
                        "His name tag reads 'Hogger'.";
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


    public static class Quest {

        enum Type {
            ILLEGAL( null ), ROSE( roseQuest );

            public QuestHandler handler;
            private Type( QuestHandler handler ) {
                this.handler = handler;
            }
        }

        private static Quest.Type type;

        private static boolean spawned;
        private static boolean given;
        private static boolean processed;

        private static int depth;

        private static int left2kill;

        public static Weapon weapon;
        public static Armor armor;

        public static void reset() {
            spawned = false;

            weapon = null;
            armor = null;
        }

        private static final String NODE		= "sadGhost";

        private static final String SPAWNED		= "spawned";
        private static final String TYPE		= "type";
        private static final String ALTERNATIVE	= "alternative";
        private static final String LEFT2KILL	= "left2kill";
        private static final String GIVEN		= "given";
        private static final String PROCESSED	= "processed";
        private static final String DEPTH		= "depth";
        private static final String WEAPON		= "weapon";
        private static final String ARMOR		= "armor";

        public static void storeInBundle( Bundle bundle ) {

            Bundle node = new Bundle();

            node.put( SPAWNED, spawned );

            if (spawned) {

                node.put( TYPE, type.toString() );
                if (type == Quest.Type.ROSE) {
                    node.put( LEFT2KILL, left2kill );
                }

                node.put( GIVEN, given );
                node.put( DEPTH, depth );
                node.put( PROCESSED, processed );

                node.put( WEAPON, weapon );
                node.put( ARMOR, armor );
            }

            bundle.put( NODE, node );
        }

        public static void restoreFromBundle( Bundle bundle ) {

            Bundle node = bundle.getBundle( NODE );

            if (!node.isNull() && (spawned = node.getBoolean( SPAWNED ))) {

                type = node.getEnum( TYPE, Quest.Type.class );
                if (type == Quest.Type.ILLEGAL) {
                    //type = node.getBoolean( ALTERNATIVE ) ? Quest.Type.RAT : Quest.Type.ROSE;
                }
                if (type == Quest.Type.ROSE) {
                    left2kill = node.getInt( LEFT2KILL );
                }

                given	= node.getBoolean( GIVEN );
                depth	= node.getInt( DEPTH );
                processed	= node.getBoolean( PROCESSED );

                weapon	= (Weapon)node.get( WEAPON );
                armor	= (Armor)node.get( ARMOR );
            } else {
                reset();
            }
        }

        public static void spawn( SewerLevel level ) {
//            if (!spawned && Dungeon.depth > 1 && Random.Int( 5 - Dungeon.depth ) == 0) {
            if (!spawned && Dungeon.depth == 1) {

                KingGnoll kingGnoll = new KingGnoll();
                do {
                    kingGnoll.pos = level.randomRespawnCell();
                } while (kingGnoll.pos == -1);
                level.mobs.add( kingGnoll );
                Actor.occupyCell( kingGnoll );

                spawned = true;
                type = Type.ROSE;

                given = false;
                processed = false;
                depth = Dungeon.depth;

                for (int i=0; i < 4; i++) {
                    Item another;
                    do {
                        another = (Weapon)Generator.random( Generator.Category.WEAPON );
                    } while (another instanceof MissileWeapon);

                    if (weapon == null || another.level() > weapon.level()) {
                        weapon = (Weapon)another;
                    }
                }

                if (Dungeon.isChallenged( Challenges.NO_ARMOR )) {
                    armor = (Armor)new ClothArmor().degrade();
                } else {
                    armor = (Armor)Generator.random( Generator.Category.ARMOR );
                    for (int i=0; i < 3; i++) {
                        Item another = Generator.random( Generator.Category.ARMOR );
                        if (another.level() > armor.level()) {
                            armor = (Armor)another;
                        }
                    }
                }

                weapon.identify();
                armor.identify();
            }
        }

        public static void processSewersKill( int pos ) {
            if (spawned && given && !processed && (depth == Dungeon.depth)) {
                switch (type) {
                    case ROSE:
                        if (Random.Int( left2kill ) == 0) {
                            Dungeon.level.drop( new DriedRose(), pos ).sprite.drop();
                            processed = true;
                        } else {
                            left2kill--;
                        }
                        break;
                    default:
                }
            }
        }

        public static void complete() {
            weapon = null;
            armor = null;

            Journal.remove( Journal.Feature.GHOST );
        }
    }

    abstract public static class QuestHandler {

        abstract public void interact(  KingGnoll kingGnoll );

        protected void relocate( KingGnoll kingGnoll ) {
            int newPos = -1;
            for (int i=0; i < 10; i++) {
                newPos = Dungeon.level.randomRespawnCell();
                if (newPos != -1) {
                    break;
                }
            }
            if (newPos != -1) {

                Actor.freeCell( kingGnoll.pos );

                CellEmitter.get( kingGnoll.pos ).start( Speck.factory( Speck.LIGHT ), 0.2f, 3 );
                kingGnoll.pos = newPos;
                kingGnoll.sprite.place( kingGnoll.pos );
                kingGnoll.sprite.visible = Dungeon.visible[kingGnoll.pos];
            }
        }
    }

    private static final QuestHandler roseQuest = new QuestHandler() {
        private static final String TXT_ROSE1	=
                "Hey man i knw this is asking alt bt cn u pls hlp.. " +
                        "i cnt find my beatz id rly apprcte it if u culd fnd them" +
                        "pls hlp.";

        private static final String TXT_ROSE2	=
                "It's a piece of cake to make a pretty cake";

        private static final String TXT_ROSE3	=
                "GRAB THAT SHIT IT'S YOURS, BITCH!";

        public void interact( KingGnoll kingGnoll ) {
            if (KingGnoll.Quest.given) {

                Item item = Dungeon.hero.belongings.getItem( DriedRose.class );
                if (item != null) {
                    GameScene.show( new WndKingGnoll( kingGnoll, item, TXT_ROSE3 ) );
                } else {
                    GameScene.show( new WndQuest( kingGnoll, TXT_ROSE2 ) );
                    relocate( kingGnoll );
                }

            } else {
                GameScene.show( new WndQuest( kingGnoll, TXT_ROSE1 ) );
                KingGnoll.Quest.given = true;

                Journal.add( Journal.Feature.GHOST );
            }
        }
    };

    private static final QuestHandler ratQuest = new QuestHandler() {
		private static final String TXT_RAT1	=
			"Hello adventurer... Once I was like you - strong and confident... " +
			"And now I'm dead... But I can't leave this place... Not until I have my revenge... " +
			"Slay the _fetid rat_, that has taken my life...";

		private static final String TXT_RAT2	=
			"Please... Help me... _Slay the abomination_...";

		private static final String TXT_RAT3	=
			"Yes! The ugly creature is slain and I can finally rest... " +
			"Please take one of these items, maybe they " +
			"will be useful to you in your journey...";

		public void interact( KingGnoll kingGnoll ) {
			if (Quest.given) {

				Item item = Dungeon.hero.belongings.getItem( RatSkull.class );
				if (item != null) {
					GameScene.show( new WndKingGnoll( kingGnoll, item, TXT_RAT3 ) );
				} else {
					GameScene.show( new WndQuest( kingGnoll, TXT_RAT2 ) );
					relocate( kingGnoll );
				}

			} else {
				GameScene.show( new WndQuest( kingGnoll, TXT_RAT1 ) );
				Quest.given = true;

				Journal.add( Journal.Feature.GHOST );
			}
		}
	};

	private static final QuestHandler curseQuest = new QuestHandler() {
		private static final String TXT_CURSE1 =
			"Hello adventurer... Once I was like you - strong and confident... " +
			"And now I'm dead... But I can't leave this place, as I am bound by a horrid curse... " +
			"Please... Help me... _Destroy the curse_...";
		private static final String TXT_CURSE2 =
			"Thank you, %s! The curse is broken and I can finally rest... " +
			"Please take one of these items, maybe they " +
			"will be useful to you in your journey...";

		private static final String TXT_YES	= "Yes, I will do it for you";
		private static final String TXT_NO	= "No, I can't help you";

		public void interact( final KingGnoll ghost ) {
			if (Quest.given) {

				GameScene.show( new WndKingGnoll( ghost, null, Utils.format( TXT_CURSE2, Dungeon.hero.className() ) ) );

			} else {
				GameScene.show( new WndQuest( ghost, TXT_CURSE1, TXT_YES, TXT_NO ) {
					protected void onSelect( int index ) {
						if (index == 0) {
							Quest.given = true;

							CursePersonification d = new CursePersonification();
							KingGnoll.replace( ghost, d );

							d.sprite.emitter().burst( ShadowParticle.CURSE, 5 );
							Sample.INSTANCE.play( Assets.SND_GHOST );

							Dungeon.hero.next();
						} else {
							relocate( ghost );
						}
					};
				} );

				Journal.add( Journal.Feature.GHOST );
			}
		}
	};



}
