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
import com.watabou.pixeldungeon.levels.FieldsLevel;
import com.watabou.pixeldungeon.levels.Level;
import com.watabou.pixeldungeon.levels.SewerLevel;
import com.watabou.pixeldungeon.quests.QuestHandler;
import com.watabou.pixeldungeon.quests.QuestObjective;
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
        state = PASSIVE;
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

//        QuestHandler.QUEST_COMPLETED_TEXT = "Yay you did it";
//        QuestHandler.QUEST_GIVEN_TEXT = "Hey, you're not done";
//        QuestHandler.QUEST_NOT_GIVEN_TEXT = "Hey I've got a quest for you little Billy";

        //QuestHandler.fetchQuestInteract(this);

        if (quest != null) {
            QuestObjective curObjective = quest.questObjectives.get(quest.curObjective);
            QuestHandler questHandler = new QuestHandler(curObjective);

//            if (speakToQuest) {
//                questHandler.speakToNPC(this);
//            }

//            switch (curObjective.questType) {
//                case "FETCH":
//                    questHandler.fetchQuestCompleteInteract(this, quest);
//                    break;
//            }
        }



        //KingGnoll.Quest.type.handler.interact( this );
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

    public static void spawn(Level level, NPC npc) {
            //KingGnoll kingGnoll = new KingGnoll();
            do {
                npc.pos = level.randomRespawnCell();
                //kingGnoll.pos = 105;
//                kingGnoll.pos = FieldsLevel.spawnPos();
                System.out.println(npc.pos);
            } while (npc.pos == -1);
            level.mobs.add( npc );
            Actor.occupyCell( npc );
    }




}
