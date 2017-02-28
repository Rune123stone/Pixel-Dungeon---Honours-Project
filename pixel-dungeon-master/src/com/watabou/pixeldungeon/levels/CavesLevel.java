/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.watabou.pixeldungeon.levels;

import com.watabou.noosa.Game;
import com.watabou.noosa.Group;
import com.watabou.noosa.Scene;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.pixeldungeon.Assets;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.DungeonTilemap;
import com.watabou.pixeldungeon.actors.mobs.npcs.Blacksmith;
import com.watabou.pixeldungeon.levels.Room.Type;
import com.watabou.pixeldungeon.levels.painters.Painter;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;
import com.watabou.utils.Rect;

import java.util.ArrayList;

public class CavesLevel extends RegularLevel {

	{
		color1 = 0x534f3e;
		color2 = 0xb9d661;
		
		viewDistance = 6;
	}

	private boolean[] cellMap = new boolean[LENGTH];
	private int[][] cavernMap;

	private float chanceOfAlive = 0.45f;
	private int floodFillCount = 0;
	private ArrayList<Cavern> caverns;

	int deathLimit = 7;
	int birthLimit = 2;



	private void initialiseCellMap() {
		for (int i = 0; i < LENGTH; i++) {
			if (Math.random() < chanceOfAlive) {
				cellMap[i] = true;
			} else {
				cellMap[i] = false;
			}
		}

		for (int i = 0; i < 6; i++) {
			try {
				cellMap = doSimulationStep(cellMap);
			} catch (Exception e) {}
		}
	}

	private boolean[] doSimulationStep(boolean[] oldMap) throws Exception {
		boolean[] newMap = new boolean[LENGTH];

		for (int i = 0; i < oldMap.length; i++) {
			int neighbours = CountAliveNeighbours(oldMap, i);

			if (oldMap[i]) {
				if (neighbours <= 2) {
					newMap[i] = false;
				} else {
					newMap[i] = true;
				}
			} else {
				if (neighbours >= 5) {
					newMap[i] = true;
				} else {
					newMap[i] = false;
				}
			}
		}
		return newMap;
	}

	private boolean CellAlive(int x, int y) {
		// Gets the value of a cell from the 1d array given 2d coordinates

		return ( cellMap[ (y * HEIGHT) + x ] );
	}

	private void InitCavernMap() {

		System.out.println("Initializing cavern map...");

		cavernMap = new int[WIDTH][HEIGHT];
		caverns = new ArrayList<>();

		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				if (CellAlive(x, y))
					cavernMap[x][y] = -1; // indicates cell is unassigned
				else
					cavernMap[x][y] = -2; // indicates cell is dead
			}
		}

		System.out.println("Initialized cavern map.");

		SeekCavern();
	}

	private void SeekCavern() {

		System.out.println("Processing cavern map...");

		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {

				if (cavernMap[x][y] == -1) {
					caverns.add( new Cavern() );
					FillCavern(x, y, caverns.size() - 1 );
				}
			}
		}

		System.out.println("Processed cavern map.");

		int primaryIndex = -1;
		int primarySize = 0;
		for (int i = 0; i < caverns.size(); i++) {

			Cavern check = caverns.get(i);

			if (check.getSize() > primarySize) {

				primaryIndex = i;
				primarySize = check.getSize();
			}
		}

		System.out.println("Found "+caverns.size()+" caverns, the largest having index "+primaryIndex+" with a size of "+primarySize+" cells.");
	}

	private void FillCavern(int x, int y, int cavernIndex) {

		// Given a starting point, assign any connected cells to this cavern

		// Cancel condition: ignore this call if index out of bounds
		if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT)
			return;

		// Stop condition: when this cell is not both alive and unassigned (-1)
		if (cavernMap[x][y] != -1)
			return;

		cavernMap[x][y] = cavernIndex;
		(caverns.get(cavernIndex)).Grow();

		FillCavern(x - 1, y, cavernIndex);
		FillCavern(x - 1, y - 1, cavernIndex);
		FillCavern(x - 1, y + 1, cavernIndex);

		FillCavern(x, y - 1, cavernIndex);
		FillCavern(x, y + 1, cavernIndex);

		FillCavern(x + 1, y, cavernIndex);
		FillCavern(x + 1, y - 1, cavernIndex);
		FillCavern(x + 1, y + 1, cavernIndex);
	}
//	private void FillNeighbour(int x, int y, int cavernIndex) {
//
//		if (x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT)
//			FillCavern(x,y,cavernIndex);
//	}



	//finds the starting point for the flood-fill algorithm
	private int findStartCell() throws Exception {
		for (int i = 0; i < map.length; i++) {
			if (cellMap[i]) {
				int firstCellNeighbours = CountAliveNeighbours(cellMap, i);
				int secondCellNeighbours = CountAliveNeighbours(cellMap, i + 1);
				int thirdCellNeighbours = CountAliveNeighbours(cellMap, i - WIDTH);
				int fourthCellNeighbours = CountAliveNeighbours(cellMap, i + WIDTH);
				if (firstCellNeighbours == 8 && secondCellNeighbours == 8 && thirdCellNeighbours == 8 && fourthCellNeighbours == 8) {return i;}
			}
		}
		return 0;
	}

	private void floodFill(int x, int y, boolean[][] TwoDimensionalCellMap) {
		if (x < 0 || y < 0 || x >= WIDTH || y >= HEIGHT ) {
			return;
		}

		if (TwoDimensionalCellMap[x][y]) {
			floodFillCount++;
		} else {
			return;
		}

		TwoDimensionalCellMap[x][y] = false;

		floodFill(x, y - 1, TwoDimensionalCellMap);
		floodFill(x, y + 1, TwoDimensionalCellMap);
		floodFill(x  - 1, y, TwoDimensionalCellMap);
		floodFill(x + 1, y, TwoDimensionalCellMap);
	}

	private void findCavern(boolean[] cellMap) throws Exception {
		floodFillCount = 0;
		caverns = new ArrayList<>();
		boolean[][] TwoDimensionalMap = Get2dMap(cellMap, WIDTH);

//		for (int x = 0; x < WIDTH; x++) {
//			for (int y = 0; y < HEIGHT; y++) {
//				if (TwoDimensionalMap[x][y]) {
//					floodFill(x,y,TwoDimensionalMap);
//					Cavern cavern = new Cavern(x,y);
//					cavern.setSize(floodFillCount);
//					cavern.setVIsited();
//					if (floodFillCount > 8) {
//
//					}
//				}
//				floodFillCount = 0;
//			}
//		}
	}
//	private int CountAliveNeighbours(boolean[] cellMap, int mapWidth, int i) throws Exception {
//
//		boolean[][] map = Get2dMap(cellMap, mapWidth);
//		int mapHeight = (map[0]).length;
//		int x = i % mapWidth;
//		int y = (int) Math.floor(i / mapWidth);
//
//		int count = 0;
//
//		if ( y > 0 ) { // if not on top row
//			if (x > 0) count += ((map[x - 1][y - 1]) ? 1 : 0); // if not in left col
//			count += ((map[x][y - 1]) ? 1 : 0);
//			if (x < mapWidth - 1) count += ((map[x + 1][y - 1]) ? 1 : 0); // if not in right col
//		}
//
//		if ( x > 0) count += ((map[x-1][y]) ? 1 : 0); // if not in left col
//		if ( x < mapWidth - 1) count += ((map[x+1][y]) ? 1 : 0); // if not in right col
//
//		if ( y < mapHeight - 1 ) {
//			if (x > 0) count += ((map[x - 1][y + 1]) ? 1 : 0); // if not in left col
//			count += ((map[x][y + 1]) ? 1 : 0);
//			if (x < mapWidth - 1) count += ((map[x + 1][y + 1]) ? 1 : 0); // if not in right col
//		}
//
//		return count;
//	}



	private int CountAliveNeighbours(boolean[] cellMap, int i) throws Exception {
		int count = 0;

		boolean[][] map = Get2dMap(cellMap, WIDTH);
		int x = i % WIDTH;
		int y = (int)Math.floor(i / WIDTH);

		for (int row = -1; row < 2; row++) {
			for (int col = -1; col < 2; col++) {

				int neighbour_x = x + col;
				int neighbour_y = y + row;

				if (row == 0 && col == 0) {}
				else if (neighbour_x < 0 || neighbour_y < 0 || neighbour_x >= LENGTH || neighbour_y >= map[0].length || neighbour_x >= WIDTH || neighbour_y >= HEIGHT) {}
				else if (map[neighbour_x][neighbour_y]) {count++;}
				}
			}
		return count;
	}

	private boolean[][] Get2dMap(boolean[] cellMap, int mapWidth) throws Exception {

		if (cellMap.length % mapWidth != 0)
			throw new Exception("Map width does not divide map size.");

		int mapHeight = cellMap.length / mapWidth;
		boolean[][] map = new boolean[mapWidth][mapHeight];

		for (int row = 0; row < mapHeight; row++) {
			for (int col = 0; col < mapWidth; col++) {
				map[col][row] = cellMap[ row*mapWidth + col ];
			}
		}

		return map;
	}

	private void qualityCheck() throws Exception {
		initialiseCellMap();
		int startCell = findStartCell();
		int x = startCell % WIDTH;
		int y = (int) Math.floor(startCell / WIDTH);
		boolean[][] TwoDCellMap = Get2dMap(cellMap, WIDTH);
		floodFill(x, y, TwoDCellMap);
	}

	@Override
	public String tilesTex() {
		return Assets.TILES_CAVES;
	}
	
	@Override
	public String waterTex() {
		return Assets.WATER_CAVES;
	}
	
	protected boolean[] water() {
		System.out.println(Feeling.WATER);
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
		initialiseCellMap();

		try {
			int startCell = findStartCell();
			System.out.println("Start Cell: " +startCell);
			int x = startCell % WIDTH;
			int y = (int)Math.floor(startCell / WIDTH);
			boolean[][] TwoDCellMap = Get2dMap(cellMap, WIDTH);
			floodFill(x, y, TwoDCellMap);

			while (floodFillCount < 1100) {
				System.out.println("Total empty in area: " +floodFillCount);
				qualityCheck();
			}

			InitCavernMap();

		} catch (Exception e) {}


		for (Room room : rooms) {
			if (room.type != Room.Type.STANDARD) {
				continue;
			}

			if (room.width() <= 3 || room.height() <= 3) {
				continue;
			}

			int s = room.square();

			if (Random.Int( s ) > 8) {
				int corner = (room.left + 1) + (room.top + 1) * WIDTH;
				if (map[corner - 1] == Terrain.WALL && map[corner - WIDTH] == Terrain.WALL) {
					//map[corner] = Terrain.WALL;
				}
			}

			if (Random.Int( s ) > 8) {
				int corner = (room.right - 1) + (room.top + 1) * WIDTH;
				if (map[corner + 1] == Terrain.WALL && map[corner - WIDTH] == Terrain.WALL) {
					//map[corner] = Terrain.WALL;
				}
			}

			if (Random.Int( s ) > 8) {
				int corner = (room.left + 1) + (room.bottom - 1) * WIDTH;
				if (map[corner - 1] == Terrain.WALL && map[corner + WIDTH] == Terrain.WALL) {
					//map[corner] = Terrain.WALL;
				}
			}

			if (Random.Int( s ) > 8) {
				int corner = (room.right - 1) + (room.bottom - 1) * WIDTH;
				if (map[corner + 1] == Terrain.WALL && map[corner + WIDTH] == Terrain.WALL) {
					//map[corner] = Terrain.WALL;
				}
			}

			for (Room n : room.connected.keySet()) {
				if ((n.type == Room.Type.STANDARD || n.type == Room.Type.TUNNEL) && Random.Int( 3 ) == 0) {
					Painter.set( this, room.connected.get( n ), Terrain.EMPTY_DECO );
				}
			}
		}

		try {
			findCavern(cellMap);
		} catch (Exception e) {}

		for (int i = 0; i < LENGTH; i++) {
			if (cellMap[i]) {
				map[i] = Terrain.EMPTY;
			} else {
				map[i] = Terrain.WALL;
			}
		}

//		for (int i=WIDTH + 1; i < LENGTH - WIDTH; i++) {
//			if (map[i] == Terrain.EMPTY) {
//				int n = 0;
//				if (map[i+1] == Terrain.WALL) {
//					n++;
//				}
//				if (map[i-1] == Terrain.WALL) {
//					n++;
//				}
//				if (map[i+WIDTH] == Terrain.WALL) {
//					n++;
//				}
//				if (map[i-WIDTH] == Terrain.WALL) {
//					n++;
//				}
//				if (Random.Int( 6 ) <= n) {
//					map[i] = Terrain.EMPTY_DECO;
//				}
//			}
//		}
//
//		for (int i=0; i < LENGTH; i++) {
//			if (map[i] == Terrain.WALL && Random.Int( 12 ) == 0) {
//				map[i] = Terrain.WALL_DECO;
//			}
//		}

		while (true) {
			int pos = roomEntrance.random();
			if (pos != entrance) {
				map[pos] = Terrain.SIGN;
				break;
			}
		}

		if (Dungeon.bossLevel( Dungeon.depth + 1 )) {
			return;
		}

//		for (Room r : rooms) {
//			if (r.type == Type.STANDARD) {
//				for (Room n : r.neigbours) {
//					if (n.type == Type.STANDARD && !r.connected.containsKey( n )) {
//						Rect w = r.intersect( n );
//						if (w.left == w.right && w.bottom - w.top >= 5) {
//
//							w.top += 2;
//							w.bottom -= 1;
//
//							w.right++;
//
//							Painter.fill( this, w.left, w.top, 1, w.height(), Terrain.CHASM );
//
//						} else if (w.top == w.bottom && w.right - w.left >= 5) {
//
//							w.left += 2;
//							w.right -= 1;
//
//							w.bottom++;
//
//							Painter.fill( this, w.left, w.top, w.width(), 1, Terrain.CHASM );
//						}
//					}
//				}
//			}
//		}
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