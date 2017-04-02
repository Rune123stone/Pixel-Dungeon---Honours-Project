package com.watabou.pixeldungeon.scenes;

import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.Group;
import com.watabou.noosa.SkinnedBlock;
import com.watabou.noosa.audio.Music;
import com.watabou.pixeldungeon.Assets;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.PixelDungeon;
import com.watabou.pixeldungeon.actors.Actor;
import com.watabou.pixeldungeon.actors.hero.Hero;
import com.watabou.pixeldungeon.overworld.OverworldCellSelector;
import com.watabou.pixeldungeon.overworld.OverworldMap;
import com.watabou.pixeldungeon.overworld.OverworldTileMap;
import com.watabou.pixeldungeon.sprites.HeroSprite;
import com.watabou.pixeldungeon.utils.GLog;

public class OverworldScene extends PixelScene {

    static OverworldScene scene;

    private OverworldTileMap tiles;
    private HeroSprite heroSpite;
    public static Hero hero;

    private static OverworldCellSelector overworldCellSelector;

    //water
    private SkinnedBlock water;

    private Group terrain;
    private Group mobs;

    @Override
    public void create() {
        Music.INSTANCE.play(Assets.THEME, false);
        Music.INSTANCE.volume(1f);

        OverworldMap.paintOverworld();

        super.create();

        scene = this;
        Camera.main.zoom( 1 );
        Camera.main.focusOn((Game.width / 2) - (OverworldMap.overworldMapWidth / 2), Game.height / 2);

        //sets map terrain
        terrain = new Group();
        add(terrain);

        //sets water texture
        water = new SkinnedBlock(OverworldMap.overworldMapWidth * OverworldTileMap.SIZE,
                OverworldMap.overworldMapHeight * OverworldTileMap.SIZE,
                OverworldMap.waterTexture());
        terrain.add(water);
        //////

        tiles = new OverworldTileMap();

        terrain.add(tiles);
        ////////////


        //adds hero to map
        mobs = new Group();
        add (mobs);

        //brightness(PixelDungeon.brightness());
        hero = new Hero();
        hero.live();

        heroSpite = new HeroSprite();
        int townNodePos = OverworldMap.getZonePos("TOWN");
        //heroSpite.place(townNodePos);
        heroSpite.placeOnOverworld(townNodePos);
        heroSpite.updateArmor();
        heroSpite.scaleSpriteToOverworld(3);

        mobs.add(heroSpite);
        /////////////

        //adds cell listener (ability to drag, touch, etc.)
        add(overworldCellSelector = new OverworldCellSelector(tiles));
        ////////////
    }

    public void destroy() {
        scene = null;
        super.destroy();
    }

    @Override
    public synchronized void update() {
//        if (Dungeon.hero == null) {
//            return;
//        }

        super.update();

        //animates water
        water.offset(0, -5 * Game.elapsed);

        Actor.process();
    }

    public static void selectCell(CellSelector.Listener listener) {
        overworldCellSelector.listener = listener;
    }

    public static void overworldReady() {
        selectCell(defaultCellListener);
    }

    private static final CellSelector.Listener defaultCellListener = new CellSelector.Listener() {
        @Override
        public void onSelect( Integer cell ) {
            if (Dungeon.hero.handle( cell )) {
                Dungeon.hero.next();
            }
        }
        @Override
        public String prompt() {
            return null;
        }
    };



}
