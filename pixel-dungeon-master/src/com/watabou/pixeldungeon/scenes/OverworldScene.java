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
import com.watabou.pixeldungeon.overworld.*;
import com.watabou.pixeldungeon.sprites.HeroSprite;
import com.watabou.pixeldungeon.ui.ExitButton;
import com.watabou.pixeldungeon.utils.GLog;

public class OverworldScene extends PixelScene {

    static OverworldScene scene;

    private OverworldTileMap tiles;

    public static OverworldHero hero;
    public static OverworldHeroSprite heroSprite;

    private static OverworldCellSelector overworldCellSelector;

    //water
    private SkinnedBlock water;

    private Group terrain;
    private Group mobs;

    @Override
    public void create() {
        Music.INSTANCE.play(Assets.THEME, false);
        Music.INSTANCE.volume(1f);

        OverworldMap.createOverworld();

        super.create();

        scene = this;
        Camera.main.zoom( 1 );
        Camera.main.focusOn((Game.width / 2) - (OverworldMap.overworldMapWidth / 2), Game.height / 2);

        setMapTerrain();

        //adds hero to map
        createHero();

        //adds cell listener (ability to drag, touch, etc.)
        add(overworldCellSelector = new OverworldCellSelector(tiles));

        //allows the hero to run continuously
        Actor.overworldActorInit();
    }

    private void setMapTerrain() {
        terrain = new Group();
        add(terrain);

        //sets water texture
        water = new SkinnedBlock(OverworldMap.overworldMapWidth * OverworldTileMap.SIZE,
                OverworldMap.overworldMapHeight * OverworldTileMap.SIZE,
                OverworldMap.waterTexture());
        terrain.add(water);

        tiles = new OverworldTileMap();

        terrain.add(tiles);
    }

    private void createHero() {
        mobs = new Group();
        add (mobs);

        hero = new OverworldHero();
        hero.mapPos = OverworldMap.getZonePos("TOWN");

        heroSprite = new OverworldHeroSprite();
        int townNodePos = OverworldMap.getZonePos("TOWN");

        heroSprite.placeOnOverworld(townNodePos);
        heroSprite.updateSprite();
        heroSprite.scaleSpriteToOverworld();

        mobs.add(heroSprite);
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

        //animates hero
        Actor.process(); //if process() gives problems later down the line for the overworld, use this (overworldProcess()).

        //always update the sprites position on the map.
        heroSprite.placeOnOverworld(hero.mapPos);

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
            if (hero.overworldHandle(cell)) {
                //sets the current actor to the Hero on the overworld map
                hero.next();
            }
        }
    };



}
