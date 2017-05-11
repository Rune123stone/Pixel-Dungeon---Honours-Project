package com.watabou.pixeldungeon.scenes;

import com.watabou.noosa.*;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.ui.Button;
import com.watabou.pixeldungeon.Assets;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.GamesInProgress;
import com.watabou.pixeldungeon.PixelDungeon;
import com.watabou.pixeldungeon.actors.Actor;
import com.watabou.pixeldungeon.actors.hero.Hero;
import com.watabou.pixeldungeon.levels.*;
import com.watabou.pixeldungeon.overworld.*;
import com.watabou.pixeldungeon.sprites.HeroSprite;
import com.watabou.pixeldungeon.ui.ExitButton;
import com.watabou.pixeldungeon.ui.RedButton;
import com.watabou.pixeldungeon.ui.Toolbar;
import com.watabou.pixeldungeon.utils.GLog;
import com.watabou.pixeldungeon.windows.WndOptions;

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

    //UI buttons
    private static OverworldButton btnEnterLevel; private String TXT_ENTER = "Enter Level";
    private static OverworldButton btnJournal; private String TXT_JOURNAL = "Journal";

    public static Level level;

    @Override
    public void create() {
        Music.INSTANCE.play(Assets.OVERWORLD_THEME, true);
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

        //adds buttons to map
        createButtons();
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
        hero.curPos = OverworldMap.getZoneHeroPos("Town");
        hero.currentZone = "Town";

        heroSprite = new OverworldHeroSprite();
        int townNodePos = OverworldMap.getZonePos("Town");

        heroSprite.placeOnOverworld(townNodePos);
        heroSprite.updateSprite();
        heroSprite.scaleSpriteToOverworld();

        mobs.add(heroSprite);
    }

    private void createButtons() {
        btnEnterLevel = new OverworldButton( TXT_ENTER ) {
            @Override
            protected void onClick() {
                String zone = hero.currentZone;
                System.out.println(zone);
                InterlevelScene.mode = InterlevelScene.Mode.DESCEND;
                Game.switchScene(InterlevelScene.class);
//                switch (zone) {
//                    case "Forest":
//                        Dungeon.level = new ForestLevel();
//                        InterlevelScene.mode = InterlevelScene.Mode.DESCEND;
//                        Game.switchScene(InterlevelScene.class);
//                        break;
//                    case "Cave":
//                        Dungeon.level = new CavesLevel();
//                        InterlevelScene.mode = InterlevelScene.Mode.DESCEND;
//                        Game.switchScene(InterlevelScene.class);
//                        break;
//                    case "Dungeon":
//                        Dungeon.level = new SewerLevel();
//                        InterlevelScene.mode = InterlevelScene.Mode.DESCEND;
//                        Game.switchScene(InterlevelScene.class);
//                        break;
//                    case "Town":
//                        Dungeon.level = new TownLevel();
//                        InterlevelScene.mode = InterlevelScene.Mode.DESCEND;
//                        Game.switchScene(InterlevelScene.class);
//                        break;
//                    default:
//                        System.out.println("Level not implemented yet.");
//                }

                System.out.println("button clicked");
            }
        };
        add(btnEnterLevel);
        btnEnterLevel.setRect(Game.width / 2, 70, Game.width / 2 - 30, 70);
        btnEnterLevel.setPos((Game.width - btnEnterLevel.width()) - 20, Game.height - btnEnterLevel.height());

        btnJournal = new OverworldButton( TXT_JOURNAL ) {
            @Override
            protected void onClick() {
                System.out.println("journal clicked");
            }
        };
        add(btnJournal);
        btnJournal.setRect(Game.width / 2, 70, Game.width / 2 - 25, 70);
        btnJournal.setPos(0, Game.height - btnEnterLevel.height());


        //temp buttons
        OverworldButton btnActTwo = new OverworldButton("Act Two") {
            @Override
            protected void onClick() {
                OverworldMap.setRandomActTwoZones();

                OverworldMap.setPassableTiles();

                Actor.removeActor();

                //create();

                setMapTerrain();

                createHero();

                Actor.overworldActorInit();

                createButtons();
            }
        };
        add(btnActTwo);
        btnActTwo.setRect(0, Game.height / 2 + 180, Game.width, 40);
        btnActTwo.setPos(0, Game.height / 2 + 180);

        OverworldButton btnActThree = new OverworldButton("Act Three") {
            @Override
            protected void onClick() {
                OverworldMap.setRandomActThreeZones();

                OverworldMap.setPassableTiles();

                Actor.removeActor();

                //create();

                setMapTerrain();

                createHero();

                Actor.overworldActorInit();

                createButtons();
            }
        };
        add(btnActThree);
        btnActThree.setRect(0, Game.height / 2 + 230, Game.width, 40);
        btnActThree.setPos(0, Game.height / 2 + 230);

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
        heroSprite.placeOnOverworld(hero.curPos);

        overworldCellSelector.enabled = hero.ready;
    }

    public static void selectCell(OverworldCellSelector.Listener listener) {
        overworldCellSelector.listener = listener;
    }

    public static void overworldReady() {
        selectCell(defaultCellListener);
    }

    private static final OverworldCellSelector.Listener defaultCellListener = new OverworldCellSelector.Listener() {
        @Override
        public void onSelect( Integer cell ) {
            int curPos = hero.curPos; //hero's current position on the overworld map
            String curZone = hero.currentZone; //hero's current zone on the overworld map
            //checks if the cell clicked is in a zone you're not in. If it's not, move the hero there.
            for (ZoneNode zoneNode : OverworldMap.zones) {

                if (zoneNode.mapPos == cell && !(zoneNode.zoneName.equals(curZone)) && zoneNode.isAccessible()) {
                    String destinationZone = zoneNode.zoneName;

                    int destinationPos = OverworldMap.getZoneHeroPos(destinationZone);

//                    if (hero.overworldHandle(destinationPos, curPos)) {
//                        hero.next();
//                    }

                    if (hero.reachedDestination) {
                        hero.overworldHandle(destinationPos, curPos);
                        hero.currentZone = destinationZone;
                        hero.reachedDestination = false;
                    }


                    break;
                }
            }

//            if (hero.overworldHandle(cell)) {
//                //sets the current actor to the Hero on the overworld map
//                hero.next();
//            }
        }
    };

    public static class OverworldButton extends RedButton {

        private static final int SECONDARY_COLOR_N = 0xCACFC2;
        private static final int SECONDARY_COLOR_H = 0xFFFF88;

        public OverworldButton(String primary) {
            super(primary);
        }

        @Override
        protected void createChildren() {
            super.createChildren();
        }

        @Override
        protected void layout() {
            super.layout();

            text.scale.set(1.5f);
        }
    }

}