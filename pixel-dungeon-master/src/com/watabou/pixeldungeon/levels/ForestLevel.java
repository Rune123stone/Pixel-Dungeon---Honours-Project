package com.watabou.pixeldungeon.levels;

import com.watabou.noosa.Game;
import com.watabou.noosa.Group;
import com.watabou.noosa.Scene;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.pixeldungeon.Assets;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.DungeonTilemap;
import com.watabou.pixeldungeon.actors.mobs.npcs.Blacksmith;
import com.watabou.pixeldungeon.levels.painters.Painter;
import com.watabou.pixeldungeon.overworld.OverworldMap;
import com.watabou.pixeldungeon.scenes.OverworldScene;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class ForestLevel extends RegularLevel {

    {
        color1 = 0x534f3e;
        color2 = 0xb9d661;

        viewDistance = 6;
    }

    private float chanceOfAlive = 0.50f;

    private ArrayList<Cavern> caverns;
    private int minCavernSize = 30;

    private int deathLimit = 3;
    private int birthLimit = 4;


    private static Cell[][] cellMap = new Cell[WIDTH][HEIGHT];

    /*
    The following code is responsible for initialising the forest map and quality checking the forest (making sure it has enough empty terrain & filling caverns that are too small).
     */
    private void buildForest() {

        do {
            generateNoise();

            for (int i = 0; i < 6; i++) {
                cellMap = doSimulationStep(cellMap);
            }

            initCavernMap();
            fillSmallCaverns();
            setBorderCells();
            fillLake(8);
            setGrassCells();
        } while (caverns.size() > 1);

    }

    private void generateNoise() {

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (Math.random() < chanceOfAlive) {
                    cellMap[x][y] = new Cell(true);
                } else {
                    cellMap[x][y] = new Cell(false);
                }
            }
        }
    }

    private Cell[][] doSimulationStep(Cell[][] oldMap) {
        Cell[][] newMap = new Cell[WIDTH][HEIGHT];

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                int neighbours = countAliveNeighbours(oldMap, x, y);

                if (oldMap[x][y].isAlive()) {
                    if (neighbours <= deathLimit) {
                        newMap[x][y] = new Cell(false);
                    } else {
                        newMap[x][y] = new Cell(true);
                    }
                } else {
                    if (neighbours >= birthLimit) {
                        newMap[x][y] = new Cell(true);
                    } else {
                        newMap[x][y] = new Cell(false);
                    }
                }
            }
        }
        return newMap;
    }

    private int countAliveNeighbours(Cell[][] cellMap, int x, int y) {
        int count = 0;

        for (int row = -1; row < 2; row++) {
            for (int col = -1; col < 2; col++) {

                int neighbour_x = x + col;
                int neighbour_y = y + row;

                if (row == 0 && col == 0) {}
                else if (neighbour_x < 0 || neighbour_y < 0 || neighbour_x >= LENGTH || neighbour_y >= cellMap[0].length || neighbour_x >= WIDTH || neighbour_y >= HEIGHT) {}
                else if (cellMap[neighbour_x][neighbour_y].isAlive()) {count++;}
            }
        }
        return count;
    }

    private void setBorderCells() {

        for (int x = 0; x < WIDTH; x++) {
            (cellMap[x][0]).setBorder();
            (cellMap[x][HEIGHT-1]).setBorder();
        }

        for (int y = 0; y < HEIGHT; y++) {
            (cellMap[0][y]).setBorder();
            (cellMap[WIDTH-1][y]).setBorder();
        }

    }


    /*
    The following code is responsible for finding caverns within the cave.
    */
    private void initCavernMap() {

        caverns = new ArrayList<>();

        System.out.println("Processing cavern map...");

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {

                if ( (cellMap[x][y]).needsCavern() ) {
                    caverns.add( new Cavern() );
                    fillCavern(x, y, caverns.size() - 1 );
                }
            }
        }

        System.out.println("Processed cavern map.");

        int primarySize = ( getPrimaryCavern() ).getSize();

        System.out.println("Found "+caverns.size()+" caverns, the largest having a size of "+primarySize+" cells.");

    }

    private Cavern getPrimaryCavern() {

        Cavern primary = new Cavern();
        int primarySize = 0;

        for ( Cavern check : caverns ) {
            if (check.getSize() >= primarySize)
                primary = check;
        }

        return primary;
    }

    private void fillCavern(int x, int y, int cavernIndex) {

        // Given a starting point, assign any connected cells to this cavern

        // Cancel condition: ignore this call if index out of bounds
        if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT)
            return;

        // Stop condition: when this cell is not both alive and unassigned (-1)
        if ( (cellMap[x][y]).hasCavern() ) {
            return;
        }

        (cellMap[x][y]).assignCavern( cavernIndex );
        (caverns.get(cavernIndex)).AddCell( cellMap[x][y] );

        fillCavern(x - 1, y, cavernIndex);
        fillCavern(x, y - 1, cavernIndex);
        fillCavern(x, y + 1, cavernIndex);
        fillCavern(x + 1, y, cavernIndex);
    }

    private void fillSmallCaverns() {

        for (Cavern cavern : caverns)
            if (cavern.getSize() < minCavernSize)
                cavern.KillCells();
    }

    /*
    The following code is responsible for generating a lake in the forest.
     */
    private void fillLake(int i) {
        int k = 0;
        startingLakeCell();

        while (k < i) {
            for (int x = 0; x < WIDTH; x++) {
                for (int y = 0; y < HEIGHT; y++) {
                    if (cellMap[x][y].isLake()) {
                        if (x > 0 && !cellMap[x - 1][y].isLake() && cellMap[x - 1][y].isAlive())
                            cellMap[x - 1][y].setTempLake();
                        if (y > 0 && !cellMap[x][y - 1].isLake() && cellMap[x][y - 1].isAlive())
                            cellMap[x][y - 1].setTempLake();
                        if (x + 1 < WIDTH && !cellMap[x + 1][y].isLake() && cellMap[x + 1][y].isAlive())
                            cellMap[x + 1][y].setTempLake();
                        if (y + 1 < HEIGHT && !cellMap[x][y + 1].isLake() && cellMap[x][y + 1].isAlive())
                            cellMap[x][y + 1].setTempLake();
                    }
                }
            }

             for (int x = 0; x < WIDTH; x++) {
                 for (int y = 0; y < HEIGHT; y++) {
                     if (cellMap[x][y].isTempLake()) {
                         cellMap[x][y].setLake();
                     }
                 }
              }
            k++;
        }
    }

    private void startingLakeCell() {
        int x, y;
        do {
            x = (int) (Math.random() * ((WIDTH) - 1));
            y = (int) (Math.random() * ((WIDTH) - 1));
        } while (cellMap[x][y].isDead());

        cellMap[x][y].setLake();
    }

    /*
    The following code is responsible for generating grass in the forest.
     */
    private void setGrassCells() {
        int k = 0;

        while (k < 3) {
            for (int x = 0; x < WIDTH; x++) {
                for (int y = 0; y < HEIGHT; y++) {
                    if ((cellMap[x][y].isDead() || cellMap[x][y].isGrass()) && !cellMap[x][y].isLake()) {
                        if (x > 0 && cellMap[x - 1][y].isAlive())
                            cellMap[x - 1][y].setTempGrass();
                        if (y > 0 && cellMap[x][y - 1].isAlive())
                            cellMap[x][y - 1].setTempGrass();
                        if (x + 1 < WIDTH && cellMap[x + 1][y].isAlive())
                            cellMap[x + 1][y].setTempGrass();
                        if (y + 1 < HEIGHT && cellMap[x][y + 1].isAlive())
                            cellMap[x][y + 1].setTempGrass();
                    }
                }
            }

            for (int x = 0; x < WIDTH; x++) {
                for (int y = 0; y < HEIGHT; y++) {
                    if (cellMap[x][y].isTempGrass()) {
                        cellMap[x][y].setGrass();
                    }
                }
            }
            k++;
        }
    }

    public static int spawnPos() {
        int pos = (int) (Math.random() * (map.length));

        int x = pos % WIDTH;
        int y = (int) Math.floor(pos / WIDTH);

        while (cellMap[x][y].isDead() || cellMap[x][y].isLake()) {
            pos = (int) (Math.random() * (map.length));

            x = pos % WIDTH;
            y = (int) Math.floor(pos / WIDTH);
        }

        return pos;
    }

    @Override
    public String tilesTex() {
        return Assets.TILES_CAVES;
        //return Assets.TILES_FOREST;
    }

    @Override
    public String waterTex() {
        return Assets.WATER_SEWERS;
    }

    protected boolean[] water() {
        return Patch.generate( feeling == Feeling.WATER ? 0.60f : 0.45f, 6 );
    }

    protected boolean[] grass() {
        return Patch.generate( feeling == Feeling.GRASS ? 0.55f : 0.35f, 3 );
    }

    @Override
    protected void assignRoomType() {
        super.assignRoomType();

        Blacksmith.Quest.spawn( rooms );
    }

    @Override
    protected void decorate() {
        try {
            Terrain.flags[Terrain.WATER] = Terrain.LIQUID | Terrain.UNSTITCHABLE; //allows the her to NOT pass over water (lake).
            Terrain.flags[Terrain.WALL_DECO] = Terrain.PASSABLE; //allows the her to pass over wall decoration cells (will be grass cells).

            buildForest();
        } catch (Exception e) {}

        for (int i = 0; i < LENGTH; i++) {
            int x = i % WIDTH;
            int y = (int) Math.floor(i / WIDTH);
            if (cellMap[x][y].isAlive() && !cellMap[x][y].isGrass()) {
                map[i] = Terrain.EMPTY;
            } else if (!cellMap[x][y].isGrass()) {
                map[i] = Terrain.WALL;
            } else {
                map[i] = Terrain.GRASS;
            }

            if (cellMap[x][y].isLake()) {
                map[i] = Terrain.WATER;
            }


        }
    }

    @Override
    public String tileName( int tile ) {
        switch (tile) {
            case Terrain.GRASS:
                return "Fluorescent moss";
            case Terrain.HIGH_GRASS:
                return "Fluorescent mushrooms";
            case Terrain.WATER:
                return "Freezing cold water.";
            default:
                return super.tileName( tile );
        }
    }

    @Override
    public String tileDesc( int tile ) {
        switch (tile) {
            case Terrain.ENTRANCE:
                return "The ladder leads up to the upper depth.";
            case Terrain.EXIT:
                return "The ladder leads down to the lower depth.";
            case Terrain.HIGH_GRASS:
                return "Huge mushrooms block the view.";
            case Terrain.WALL_DECO:
                return "A vein of some ore is visible on the wall. Gold?";
            case Terrain.BOOKSHELF:
                return "Who would need a bookshelf in a cave?";
            default:
                return super.tileDesc( tile );
        }
    }

    @Override
    public void addVisuals( Scene scene ) {
        super.addVisuals( scene );
        addVisuals( this, scene );
    }

    public static void addVisuals( Level level, Scene scene ) {
        for (int i=0; i < LENGTH; i++) {
            if (level.map[i] == Terrain.WALL_DECO) {
                scene.add( new Vein( i ) );
            }
        }
    }

    private static class Vein extends Group {

        private int pos;

        private float delay;

        public Vein( int pos ) {
            super();

            this.pos = pos;

            delay = Random.Float( 2 );
        }

        @Override
        public void update() {

            if (visible = Dungeon.visible[pos]) {

                super.update();

                if ((delay -= Game.elapsed) <= 0) {

                    delay = Random.Float();

                    PointF p = DungeonTilemap.tileToWorld( pos );
                    ((Sparkle)recycle( Sparkle.class )).reset(
                            p.x + Random.Float( DungeonTilemap.SIZE ),
                            p.y + Random.Float( DungeonTilemap.SIZE ) );
                }
            }
        }
    }

    public static final class Sparkle extends PixelParticle {

        public void reset( float x, float y ) {
            revive();

            this.x = x;
            this.y = y;

            left = lifespan = 0.5f;
        }

        @Override
        public void update() {
            super.update();

            float p = left / lifespan;
            size( (am = p < 0.5f ? p * 2 : (1 - p) * 2) * 2 );
        }
    }
}