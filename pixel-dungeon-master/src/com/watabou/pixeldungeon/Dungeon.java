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
package com.watabou.pixeldungeon;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.actors.Actor;
import com.watabou.pixeldungeon.actors.Char;
import com.watabou.pixeldungeon.actors.buffs.Amok;
import com.watabou.pixeldungeon.actors.buffs.Light;
import com.watabou.pixeldungeon.actors.buffs.Rage;
import com.watabou.pixeldungeon.actors.hero.Hero;
import com.watabou.pixeldungeon.actors.hero.HeroClass;
import com.watabou.pixeldungeon.actors.mobs.npcs.Blacksmith;
import com.watabou.pixeldungeon.actors.mobs.npcs.Imp;
import com.watabou.pixeldungeon.actors.mobs.npcs.Ghost;
import com.watabou.pixeldungeon.actors.mobs.npcs.Wandmaker;
import com.watabou.pixeldungeon.items.Ankh;
import com.watabou.pixeldungeon.items.Item;
import com.watabou.pixeldungeon.items.potions.Potion;
import com.watabou.pixeldungeon.items.rings.Ring;
import com.watabou.pixeldungeon.items.scrolls.Scroll;
import com.watabou.pixeldungeon.items.wands.Wand;
import com.watabou.pixeldungeon.levels.*;
import com.watabou.pixeldungeon.overworld.OverworldHero;
import com.watabou.pixeldungeon.scenes.GameScene;
import com.watabou.pixeldungeon.scenes.InterlevelScene;
import com.watabou.pixeldungeon.scenes.OverworldScene;
import com.watabou.pixeldungeon.scenes.StartScene;
import com.watabou.pixeldungeon.ui.QuickSlot;
import com.watabou.pixeldungeon.utils.BArray;
import com.watabou.pixeldungeon.utils.Utils;
import com.watabou.pixeldungeon.windows.WndResurrect;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;
import com.watabou.utils.SparseArray;

public class Dungeon {
	
	public static int potionOfStrength;
	public static int scrollsOfUpgrade;
	public static int scrollsOfEnchantment;
	public static boolean dewVial;		// true if the dew vial can be spawned
	
	public static int challenges;
	
	public static Hero hero;
	public static Level level;
	
	public static int depth;
	public static int gold;
	// Reason of death
	public static String resultDescription;
	
	public static HashSet<Integer> chapters;
	
	// Hero's field of view
	public static boolean[] visible = new boolean[Level.LENGTH];
	
	public static boolean nightMode;
	
	public static SparseArray<ArrayList<Item>> droppedItems;
	
	public static void init() {

		challenges = PixelDungeon.challenges();
		
		Actor.clear();
		
		PathFinder.setMapSize( Level.WIDTH, Level.HEIGHT );
		
		Scroll.initLabels();
		Potion.initColors();
		Wand.initWoods();
		Ring.initGems();
		
		Statistics.reset();
		Journal.reset();
		
		depth = 0;
		gold = 0;
		
		droppedItems = new SparseArray<ArrayList<Item>>();
		
		potionOfStrength = 0;
		scrollsOfUpgrade = 0;
		scrollsOfEnchantment = 0;
		dewVial = true;
		
		chapters = new HashSet<Integer>();
		
		Ghost.Quest.reset();
		Wandmaker.Quest.reset();
		Blacksmith.Quest.reset();
		Imp.Quest.reset();
		
		Room.shuffleTypes();
		
		QuickSlot.primaryValue = null;
		QuickSlot.secondaryValue = null;
		
		hero = new Hero();
		hero.live();
		
		Badges.reset();

		StartScene.curClass = HeroClass.WARRIOR;
		StartScene.curClass.initHero( hero );
	}
	
	public static boolean isChallenged( int mask ) {
		return (challenges & mask) != 0;
	}
	
	public static Level newLevel() {
		
		Dungeon.level = null;
		Actor.clear();
		
		depth++;
		if (depth > Statistics.deepestFloor) {
			Statistics.deepestFloor = depth;
			
			if (Statistics.qualifiedForNoKilling) {
				Statistics.completedWithNoKilling = true;
			} else {
				Statistics.completedWithNoKilling = false;
			}
		}
		
		Arrays.fill( visible, false );
		Level level;

		switch(OverworldScene.hero.currentZone) {
			case "Forest":
				level = new ForestLevel();
				break;
			case "Cave":
				level = new CavesLevel();
				break;
			case "Dungeon":
				level = new SewerLevel();
				break;
			case "Fields":
				level = new FieldsLevel();
				break;
			case "Town":
				level = new TownLevel();
				break;
			case "Castle":
				level = new CityLevel();
				break;
			case "Shadow Lands":
				level = new ShadowLandsLevel();
				break;
			default:
				level = new DeadEndLevel();
				System.out.println("Level not implemented yet.");
		}

//		switch(hero.heroBackground) {
//			case CRIMINAL:
//				level = new ForestLevel();
//				break;
//			case OUTSIDER:
//				level = new ForestLevel();
//				break;
//			case KNIGHT:
//				level = new ForestLevel();
//				break;
//			case PEASANT:
//				level = new ForestLevel();
//				break;
//			default:
//			level = new DeadEndLevel();
//			Statistics.deepestFloor--;
//		}
//		switch (depth) {
//		case 1:
//		case 2:
//		case 3:
//		case 4:
//			level = new SewerLevel();
//			break;
//		case 5:
//			level = new SewerBossLevel();
//			break;
//		case 6:
//		case 7:
//		case 8:
//		case 9:
//			level = new PrisonLevel();
//			break;
//		case 10:
//			level = new PrisonBossLevel();
//			break;
//		case 11:
//		case 12:
//		case 13:
//		case 14:
//			level = new CavesLevel();
//			break;
//		case 15:
//			level = new CavesBossLevel();
//			break;
//		case 16:
//		case 17:
//		case 18:
//		case 19:
//			level = new CityLevel();
//			break;
//		case 20:
//			level = new CityBossLevel();
//			break;
//		case 21:
//			level = new LastShopLevel();
//			break;
//		case 22:
//		case 23:
//		case 24:
//			level = new HallsLevel();
//			break;
//		case 25:
//			level = new HallsBossLevel();
//			break;
//		case 26:
//			level = new LastLevel();
//			break;
//		default:
//			level = new DeadEndLevel();
//			Statistics.deepestFloor--;
//		}
		
		level.create();

		Statistics.qualifiedForNoKilling = !bossLevel();
		
		return level;
	}

	public static void resetLevel() {
		
		Actor.clear();
		
		Arrays.fill( visible, false );
		
		level.reset();
		switchLevel( level, level.entrance );
	}
	
	public static boolean shopOnLevel() {
		return depth == 6 || depth == 11 || depth == 16;
	}
	
	public static boolean bossLevel() {
		return bossLevel( depth );
	}
	
	public static boolean bossLevel( int depth ) {
		return depth == 5 || depth == 10 || depth == 15 || depth == 20 || depth == 25;
	}
	
	@SuppressWarnings("deprecation")
	public static void switchLevel( final Level level, int pos ) {
		
		nightMode = new Date().getHours() < 7;
		
		Dungeon.level = level;
		Actor.init();
		
		Actor respawner = level.respawner();
		if (respawner != null) {
			Actor.add( level.respawner() );
		}
		
		hero.pos = pos != -1 ? pos : level.exit;
		
		Light light = hero.buff( Light.class );
		hero.viewDistance = light == null ? level.viewDistance : Math.max( Light.DISTANCE, level.viewDistance );
		
		observe();
	}

	public static void dropToChasm( Item item ) {
		int depth = Dungeon.depth + 1;
		ArrayList<Item> dropped = (ArrayList<Item>)Dungeon.droppedItems.get( depth );
		if (dropped == null) {
			Dungeon.droppedItems.put( depth, dropped = new ArrayList<Item>() ); 
		}
		dropped.add( item );
	}
	
	public static boolean posNeeded() {
		int[] quota = {4, 2, 9, 4, 14, 6, 19, 8, 24, 9};
		return chance( quota, potionOfStrength );
	}
	
	public static boolean souNeeded() {
		int[] quota = {5, 3, 10, 6, 15, 9, 20, 12, 25, 13};
		return chance( quota, scrollsOfUpgrade );
	}
	
	public static boolean soeNeeded() {
		return Random.Int( 12 * (1 + scrollsOfEnchantment) ) < depth;
	}
	
	private static boolean chance( int[] quota, int number ) {
		
		for (int i=0; i < quota.length; i += 2) {
			int qDepth = quota[i];
			if (depth <= qDepth) {
				int qNumber = quota[i + 1];
				return Random.Float() < (float)(qNumber - number) / (qDepth - depth + 1);
			}
		}
		
		return false;
	}
	
	private static final String RG_GAME_FILE	= "game.dat";
	private static final String RG_DEPTH_FILE	= "depth%d.dat";
	
	private static final String WR_GAME_FILE	= "warrior.dat";
	private static final String WR_DEPTH_FILE	= "warrior%d.dat";
	
	private static final String MG_GAME_FILE	= "mage.dat";
	private static final String MG_DEPTH_FILE	= "mage%d.dat";
	
	private static final String RN_GAME_FILE	= "ranger.dat";
	private static final String RN_DEPTH_FILE	= "ranger%d.dat";
	
	private static final String VERSION		= "version";
	private static final String CHALLENGES	= "challenges";
	private static final String HERO		= "hero";
	private static final String GOLD		= "gold";
	private static final String DEPTH		= "depth";
	private static final String LEVEL		= "level";
	private static final String DROPPED		= "dropped%d";
	private static final String POS			= "potionsOfStrength";
	private static final String SOU			= "scrollsOfEnhancement";
	private static final String SOE			= "scrollsOfEnchantment";
	private static final String DV			= "dewVial";
	private static final String CHAPTERS	= "chapters";
	private static final String QUESTS		= "quests";
	private static final String BADGES		= "badges";

	//Cameron Save Variables

	//hero position keys for bundle - handles hero's position in each zone.
	private static final String FORESTHEROPOS = "forestHeroPos";
	private static final String CAVESHEROPOS = "cavesHeroPos";
	private static final String DUNGEONHEROPOS = "dungeonHeroPos";
	private static final String TOWNHEROPOS = "townHeroPos";
	private static final String SHADOWLANDSHEROPOS = "shadowLandsHeroPos";
	private static final String FIELDSHEROPOS = "fieldsHeroPos";
	private static final String CASTLEHEROPOS = "castleHeroPos";

	//hero keys for bundle - handles storage of heroes xp, quests, inventory etc so that they're constant between zones.
	private static final String FORESTHERO = "forestHero";
	private static final String CAVESHERO = "cavesHero";
	private static final String DUNGEONHERO = "dungeonHero";
	private static final String TOWNHERO = "townHero";
	private static final String SHADOWLANDSHERO = "shadowLandsHero";
	private static final String FIELDSHERO = "fieldsHero";
	private static final String CASTLEHERO = "castleHero";

	private static final String FORESTHEROGOLD = "forestHeroGold";
	private static final String CAVESHEROGOLD = "cavesHeroGold";
	private static final String DUNGEONHEROGOLD = "dungeonHeroGold";
	private static final String TOWNHEROGOLD = "townHeroGold";
	private static final String SHADOWLANDSHEROGOLD = "shadowLandsHeroGold";
	private static final String FIELDSHEROGOLD = "fieldsHeroGold";
	private static final String CASTLEHEROGOLD = "castleHeroGold";
	///
	
	public static String gameFile( HeroClass cl ) {
		switch (cl) {
		case WARRIOR:
			return WR_GAME_FILE;
		case MAGE:
			return MG_GAME_FILE;
		case HUNTRESS:
			return RN_GAME_FILE;
		default:
			return RG_GAME_FILE;
		}
	}
	
	private static String depthFile( HeroClass cl ) {
		switch (cl) {
		case WARRIOR:
			return WR_DEPTH_FILE;
		case MAGE:
			return MG_DEPTH_FILE;
		case HUNTRESS:
			return RN_DEPTH_FILE;
		default:
			return RG_DEPTH_FILE;
		}
	}
	
	public static void saveGame( String fileName ) throws IOException {
		try {
			Bundle bundle = new Bundle();
			
			bundle.put( VERSION, Game.version );
			bundle.put( CHALLENGES, challenges );
			bundle.put( HERO, hero );
			bundle.put( GOLD, gold );
			bundle.put( DEPTH, depth );

			for (int d : droppedItems.keyArray()) {
				bundle.put( String.format( DROPPED, d ), droppedItems.get( d ) );
			}
			
			bundle.put( POS, potionOfStrength );
			bundle.put( SOU, scrollsOfUpgrade );
			bundle.put( SOE, scrollsOfEnchantment );
			bundle.put( DV, dewVial );
			
			int count = 0;
			int ids[] = new int[chapters.size()];
			for (Integer id : chapters) {
				ids[count++] = id;
			}
			bundle.put( CHAPTERS, ids );
			
			Bundle quests = new Bundle();
			Ghost		.Quest.storeInBundle( quests );
			Wandmaker	.Quest.storeInBundle( quests );
			Blacksmith	.Quest.storeInBundle( quests );
			Imp			.Quest.storeInBundle( quests );
			bundle.put( QUESTS, quests );
			
			Room.storeRoomsInBundle( bundle );
			
			Statistics.storeInBundle( bundle );
			Journal.storeInBundle( bundle );
			
			QuickSlot.save( bundle );
			
			Scroll.save( bundle );
			Potion.save( bundle );
			Wand.save( bundle );
			Ring.save( bundle );
			
			Bundle badges = new Bundle();
			Badges.saveLocal( badges );
			bundle.put( BADGES, badges );
			
			OutputStream output = Game.instance.openFileOutput( fileName, Game.MODE_PRIVATE );
			Bundle.write( bundle, output );
			output.close();
			
		} catch (Exception e) {

			GamesInProgress.setUnknown( hero.heroClass );
		}
	}

	public static void saveLevel() throws IOException {
		Bundle bundle = new Bundle();
		bundle.put( LEVEL, level );

		//OutputStream output = Game.instance.openFileOutput( Utils.format( depthFile( hero.heroClass ), depth ), Game.MODE_PRIVATE ); //uncomment to restore back to normal.

		// *** Saves individual levels as well as the hero's position, gold, and items in those specific levels. ***
		OutputStream output = null;

		switch (OverworldScene.hero.currentZone) {
			case "Forest":
				bundle.put( FORESTHERO, hero);
				bundle.put( FORESTHEROGOLD, gold );
				bundle.put( FORESTHEROPOS, hero.pos);
				output = Game.instance.openFileOutput("Forest", Game.MODE_PRIVATE);
				break;
			case "Dungeon":
				bundle.put( DUNGEONHERO, hero);
				bundle.put( DUNGEONHEROGOLD, gold );
				bundle.put( DUNGEONHEROPOS, hero.pos);
				output = Game.instance.openFileOutput("Dungeon", Game.MODE_PRIVATE);
				break;
			case "Cave":
				bundle.put( CAVESHERO, hero);
				bundle.put( CAVESHEROGOLD, gold );
				bundle.put( CAVESHEROPOS, hero.pos);
				output = Game.instance.openFileOutput("Caves", Game.MODE_PRIVATE);
				break;
			case "Castle":
				bundle.put( CASTLEHERO, hero);
				bundle.put( CASTLEHEROGOLD, gold );
				bundle.put( CASTLEHEROPOS, hero.pos);
				output = Game.instance.openFileOutput("Castle", Game.MODE_PRIVATE);
				break;
			case "Shadow Lands":
				bundle.put( SHADOWLANDSHERO, hero);
				bundle.put( SHADOWLANDSHEROGOLD, gold );
				bundle.put( SHADOWLANDSHEROPOS, hero.pos);
				output = Game.instance.openFileOutput("Shadow Lands", Game.MODE_PRIVATE);
				break;
			case "Town":
				bundle.put( TOWNHERO, hero);
				bundle.put( TOWNHEROGOLD, gold );
				bundle.put( TOWNHEROPOS, hero.pos);
				output = Game.instance.openFileOutput("Town", Game.MODE_PRIVATE);
				break;
			case "Fields":
				bundle.put( FIELDSHERO, hero);
				bundle.put( FIELDSHEROGOLD, gold );
				bundle.put( FIELDSHEROPOS, hero.pos);
				output = Game.instance.openFileOutput("Fields", Game.MODE_PRIVATE);
				break;
		}
		// *** END ***

		Bundle.write( bundle, output );
		output.close();
	}

	public static void saveAll() throws IOException {
		if (hero.isAlive()) {
			
			Actor.fixTime();
			saveGame( gameFile( hero.heroClass ) );
			saveLevel();
			
			GamesInProgress.set( hero.heroClass, depth, hero.lvl, challenges != 0 );
			
		} else if (WndResurrect.instance != null) {
			
			WndResurrect.instance.hide();
			Hero.reallyDie( WndResurrect.causeOfDeath );
			
		}
	}

	public static void loadGame( HeroClass cl ) throws IOException {
		loadGame( gameFile( cl ), true );
	}
	
	public static void loadGame( String fileName ) throws IOException {
		loadGame( fileName, false );
	}
	
	public static void loadGame( String fileName, boolean fullLoad ) throws IOException {
		
		Bundle bundle = gameBundle( fileName );

		Dungeon.challenges = bundle.getInt( CHALLENGES );
		
		Dungeon.level = null;
		Dungeon.depth = -1;
		
		if (fullLoad) {
			PathFinder.setMapSize( Level.WIDTH, Level.HEIGHT );
		}
		
		Scroll.restore( bundle );
		Potion.restore( bundle );
		Wand.restore( bundle );
		Ring.restore( bundle );
		
		potionOfStrength = bundle.getInt( POS );
		scrollsOfUpgrade = bundle.getInt( SOU );
		scrollsOfEnchantment = bundle.getInt( SOE );
		dewVial = bundle.getBoolean( DV );
		
		if (fullLoad) {
			chapters = new HashSet<Integer>();
			int ids[] = bundle.getIntArray( CHAPTERS );
			if (ids != null) {
				for (int id : ids) {
					chapters.add( id );
				}
			}
			
			Bundle quests = bundle.getBundle( QUESTS );
			if (!quests.isNull()) {
				Ghost.Quest.restoreFromBundle( quests );
				Wandmaker.Quest.restoreFromBundle( quests );
				Blacksmith.Quest.restoreFromBundle( quests );
				Imp.Quest.restoreFromBundle( quests );
			} else {
				Ghost.Quest.reset();
				Wandmaker.Quest.reset();
				Blacksmith.Quest.reset();
				Imp.Quest.reset();
			}
			
			Room.restoreRoomsFromBundle( bundle );
		}
		
		Bundle badges = bundle.getBundle( BADGES );
		if (!badges.isNull()) {
			Badges.loadLocal( badges );
		} else {
			Badges.reset();
		}
		
		QuickSlot.restore( bundle );
		
		@SuppressWarnings("unused")
		String version = bundle.getString( VERSION );
		
		hero = null;
		hero = (Hero)bundle.get( HERO );
		
		QuickSlot.compress();
		
		gold = bundle.getInt( GOLD );
		depth = bundle.getInt( DEPTH );
		
		Statistics.restoreFromBundle( bundle );
		Journal.restoreFromBundle( bundle );
		
		droppedItems = new SparseArray<ArrayList<Item>>();
		for (int i=2; i <= Statistics.deepestFloor + 1; i++) {
			ArrayList<Item> dropped = new ArrayList<Item>();
			for (Bundlable b : bundle.getCollection( String.format( DROPPED, i ) ) ) {
				dropped.add( (Item)b );
			}
			if (!dropped.isEmpty()) {
				droppedItems.put( i, dropped );
			}
		}
	}

	//loads hero gold and items
	public static void loadHero() throws IOException {

		String previousZone = OverworldScene.previousZone;

		switch (previousZone) {
			case "Forest":
				Bundle bundle = gameBundle(previousZone);
				Dungeon.hero = (Hero)bundle.get(FORESTHERO);
				gold = bundle.getInt(FORESTHEROGOLD);
				break;
			case "Dungeon":
				bundle = gameBundle(previousZone);
				Dungeon.hero = (Hero)bundle.get(DUNGEONHERO);
				gold = bundle.getInt(DUNGEONHEROGOLD);
				break;
			case "Caves":
				bundle = gameBundle(previousZone);
				Dungeon.hero = (Hero)bundle.get(CAVESHERO);
				gold = bundle.getInt(CAVESHEROGOLD);
				break;
			case "Town":
				System.out.println("Im here ellen");
				bundle = gameBundle(previousZone);
				Dungeon.hero = (Hero)bundle.get(TOWNHERO);
				gold = bundle.getInt(TOWNHEROGOLD);
				break;
			case "Shadow Lands":
				bundle = gameBundle(previousZone);
				Dungeon.hero = (Hero)bundle.get(SHADOWLANDSHERO);
				gold = bundle.getInt(SHADOWLANDSHEROGOLD);
				break;
			case "Castle":
				bundle = gameBundle(previousZone);
				Dungeon.hero = (Hero)bundle.get(CASTLEHERO);
				gold = bundle.getInt(CASTLEHEROGOLD);
				break;
			case "Fields":
				bundle = gameBundle(previousZone);
				Dungeon.hero = (Hero)bundle.get(FIELDSHERO);
				gold = bundle.getInt(FIELDSHEROGOLD);
				break;
		}

	}

	public static Level loadLevel( HeroClass cl ) throws IOException {

		Dungeon.level = null;
		Actor.clear();

		//InputStream input = Game.instance.openFileInput( Utils.format( depthFile( cl ), depth ) ) ; //uncomment to restore back to normal

		// *** Loads individual levels as well as the hero's position in those levels. ***
		InputStream input = null;
		String posKey = "";

		switch (OverworldScene.hero.currentZone) {
			case "Forest":
				Terrain.flags[Terrain.WATER] = Terrain.LIQUID | Terrain.UNSTITCHABLE; //allows the her to NOT pass over water (lake).
				Terrain.flags[Terrain.WALL_DECO] = Terrain.PASSABLE; //allows the her to pass over wall decoration cells (will be grass cells).

				posKey = FORESTHEROPOS;
				input = Game.instance.openFileInput("Forest");
				break;
			case "Dungeon":
				Terrain.flags[Terrain.WATER] = Terrain.PASSABLE | Terrain.LIQUID | Terrain.UNSTITCHABLE; //allows the her to pass over water.
				Terrain.flags[Terrain.WALL_DECO] = Terrain.flags[Terrain.WALL]; //allows the her to NOT pass over wall decoration cells.

				posKey = DUNGEONHEROPOS;
				input = Game.instance.openFileInput("Dungeon");
				break;
			case "Cave":
				Terrain.flags[Terrain.WATER] = Terrain.PASSABLE | Terrain.LIQUID | Terrain.UNSTITCHABLE; //allows the her to pass over water.
				Terrain.flags[Terrain.WALL_DECO] = Terrain.flags[Terrain.WALL]; //allows the her to NOT pass over wall decoration cells.

				posKey = CAVESHEROPOS;
				input = Game.instance.openFileInput("Caves");
				break;
			case "Castle":
				Terrain.flags[Terrain.WATER] = Terrain.PASSABLE | Terrain.LIQUID | Terrain.UNSTITCHABLE; //allows the her to pass over water.
				Terrain.flags[Terrain.WALL_DECO] = Terrain.flags[Terrain.WALL]; //allows the her to NOT pass over wall decoration cells.

				posKey = CASTLEHEROPOS;
				input = Game.instance.openFileInput("Castle");
				break;
			case "Shadow Lands":
				Terrain.flags[Terrain.WATER] = Terrain.PASSABLE | Terrain.LIQUID | Terrain.UNSTITCHABLE; //allows the her to pass over water.
				Terrain.flags[Terrain.WALL_DECO] = Terrain.flags[Terrain.WALL]; //allows the her to NOT pass over wall decoration cells.

				posKey = SHADOWLANDSHEROPOS;
				input = Game.instance.openFileInput("Shadow Lands");
				break;
			case "Town":
				Terrain.flags[Terrain.WATER] = Terrain.PASSABLE | Terrain.LIQUID | Terrain.UNSTITCHABLE; //allows the her to pass over water.
				Terrain.flags[Terrain.WALL_DECO] = Terrain.flags[Terrain.WALL]; //allows the her to NOT pass over wall decoration cells.

				posKey = TOWNHEROPOS;
				input = Game.instance.openFileInput("Town");
				break;
			case "Fields":
				Terrain.flags[Terrain.WATER] = Terrain.PASSABLE | Terrain.LIQUID | Terrain.UNSTITCHABLE; //allows the her to pass over water.
				Terrain.flags[Terrain.WALL_DECO] = Terrain.flags[Terrain.WALL]; //allows the her to NOT pass over wall decoration cells.

				posKey = FIELDSHEROPOS;
				input = Game.instance.openFileInput("Fields");
				break;
		}
		// *** END ***


		Bundle bundle = Bundle.read( input );

		// *** sets the hero's pos to where it was in the specified level. ***
		hero.pos = bundle.getInt(posKey);
		// *** END ***

		input.close();


		return (Level)bundle.get( "level" );
	}

	public static void deleteGame( HeroClass cl, boolean deleteLevels ) {
		
		Game.instance.deleteFile( gameFile( cl ) );
		
		if (deleteLevels) {
			int depth = 1;
			while (Game.instance.deleteFile( Utils.format( depthFile( cl ), depth ) )) {
				depth++;
			}
		}
		
		GamesInProgress.delete( cl );
	}
	
	public static Bundle gameBundle( String fileName ) throws IOException {
		
		InputStream input = Game.instance.openFileInput( fileName );
		Bundle bundle = Bundle.read( input );
		input.close();
		
		return bundle;
	}
	
	public static void preview( GamesInProgress.Info info, Bundle bundle ) {
		info.depth = bundle.getInt( DEPTH );
		info.challenges = (bundle.getInt( CHALLENGES ) != 0);
		if (info.depth == -1) {
			info.depth = bundle.getInt( "maxDepth" );	// FIXME
		}
		Hero.preview( info, bundle.getBundle( HERO ) );
	}
	
	public static void fail( String desc ) {
		resultDescription = desc;
		if (hero.belongings.getItem( Ankh.class ) == null) { 
			Rankings.INSTANCE.submit( false );
		}
	}
	
	public static void win( String desc ) {
		
		hero.belongings.identify();
		
		if (challenges != 0) {
			Badges.validateChampion();
		}
		
		resultDescription = desc;
		Rankings.INSTANCE.submit( true );
	}
	
	public static void observe() {

		if (level == null) {
			return;
		}
		level.updateFieldOfView( hero );
		System.arraycopy( Level.fieldOfView, 0, visible, 0, visible.length );
		
		BArray.or( level.visited, visible, level.visited );
		
		GameScene.afterObserve();
	}
	
	private static boolean[] passable = new boolean[Level.LENGTH];
	
	public static int findPath( Char ch, int from, int to, boolean pass[], boolean[] visible ) {

		//if the cell is adjacent to where you are, simply return that cell instead of running Pathfinder.getStep
		if (Level.adjacent( from, to )) {
			return Actor.findChar( to ) == null && (pass[to] || Level.avoid[to]) ? to : -1;
		}
		
		if (ch.flying || ch.buff( Amok.class ) != null || ch.buff( Rage.class ) != null) {
			//returns passable array based on: passable[i] = pass[i] || Level.avoid[i]
			BArray.or( pass, Level.avoid, passable );
		} else {
			//arraycopy(Object source, int sourcePos, Object destination, int destinationPos, int numberOfElements) :
			//Copies the numberOfElements (Level.LENGTH) from the source array (pass) beginning with the element at sourcePos(0) to the array destination (passable) starting at destinationPos(0).
			System.arraycopy( pass, 0, passable, 0, Level.LENGTH );
		}

		// Ensures that you can't walk through other characters
		for (Actor actor : Actor.all()) {
			if (actor instanceof Char) {
				int pos = ((Char)actor).pos;
				if (visible[pos]) {
					passable[pos] = false;
				}
			}
		}

		return PathFinder.getStep( from, to, passable );
		
	}

	public static int flee( Char ch, int cur, int from, boolean pass[], boolean[] visible ) {
		
		if (ch.flying) {
			BArray.or( pass, Level.avoid, passable );
		} else {
			System.arraycopy( pass, 0, passable, 0, Level.LENGTH );
		}
		
		for (Actor actor : Actor.all()) {
			if (actor instanceof Char) {
				int pos = ((Char)actor).pos;
				if (visible[pos]) {
					passable[pos] = false;
				}
			}
		}
		passable[cur] = true;
		
		return PathFinder.getStepBack( cur, from, passable );
		
	}

}
