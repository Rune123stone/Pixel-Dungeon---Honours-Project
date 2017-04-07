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
import com.watabou.pixeldungeon.ui.ExitButton;
import com.watabou.pixeldungeon.utils.GLog;

public class OverworldScene extends PixelScene {

    static OverworldScene scene;

    private OverworldTileMap tiles;
    public static HeroSprite heroSpite;
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
        hero.pos = OverworldMap.getZonePos("TOWN");

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
        Actor.overworldActorInit();
    }

    public void destroy() {
        scene = null;
        super.destroy();
    }

    @Override
    public synchronized void update() {
        super.update();

        //animates water
        water.offset(0, -5 * Game.elapsed);

        Actor.overworldProcess();
        heroSpite.placeOnOverworld(hero.pos);

        overworldCellSelector.enabled = hero.ready;
    }

    public static void selectCell(OverworldCellSelector.Listener listener) {
        overworldCellSelector.listener = listener;
    }

    public static void overworldReady() {
        selectCell(defaultCellListener);;
    }

    private static final OverworldCellSelector.Listener defaultCellListener = new OverworldCellSelector.Listener() {
        @Override
        public void onSelect( Integer cell ) {
//            int count = 1;
//            for (Integer integer : hero.moveCells(cell)) {
//                System.out.println("Path cell : " +count+ " , " +integer);
//                count++;
//                heroSpite.placeOnOverworld(integer);
//                try {
//                    Thread.sleep(100);
//                } catch (Exception e) {
//
//                }
//            }
            if (hero.overworldHandle(cell)) {
                //sets the current actor to the Hero on the overworld map
                hero.next();
            }
        }
    };



}
