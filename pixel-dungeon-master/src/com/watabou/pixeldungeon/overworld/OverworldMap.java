package com.watabou.pixeldungeon.overworld;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.Assets;
import com.watabou.pixeldungeon.PixelDungeon;
import com.watabou.utils.PathFinder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class OverworldMap  {

    public static final int overworldMapWidth = 32; //Tiled map width
    //public static final int overworldMapWidth = Game.width; //Tiled map width
    //public static final int overworldMapHeight = 32; //use the Tiled map height if map gives problems later on

   // public static final int overworldMapHeight = Game.height / OverworldTileMap.SIZE; //sets map height to full screen
    public static final int overworldMapHeight = Game.height;
    public static int overworldMapLength = overworldMapWidth * overworldMapHeight;

    public static int[] overworldMap = new int[overworldMapLength];
    public static boolean[] water = new boolean[overworldMapLength];

    public static boolean[] passable = new boolean[overworldMapLength];

    private static int[] mapData = new int[overworldMapLength];
    private static int[] passableMapData = new int[overworldMapLength];

    public static ArrayList<ZoneNode> zones;

    //paints tiles and sets paths for overworld map.
    public static void createOverworld() {
        PathFinder.setMapSize(overworldMapWidth, overworldMapHeight); //sets size of map for Pathfinder class

        mapData = integerTileMap();
        //passableMapData = mapData;
        //note : 24 is water texture
        //"paints" the overworld with the specified tile textures
        for (int i = 0; i < overworldMapLength; i++) {
            if (i < mapData.length) {
                if (mapData[i] == 0) {
                    overworldMap[i] = 24; //set to water texture
                } else {
                    //note : when using forest_tiles.png tileset, use data - 1
                    overworldMap[i] = mapData[i] - 1;

                }
            } else {
                overworldMap[i] = 24; //set to water texture
            }
        }

        setZoneNodes();

        setZoneTiles();

        //setRandomActOneZones();
        //setFogTileData(firstZone, secondZone);

        setPassableTiles();
    }


    // **** fog test methods START ****
    //evaluation
    public static String firstZone;
    public static String secondZone;
    public static ArrayList<String> tempZones = new ArrayList<>();

    public static void setRandomActOneZones() {
        tempZones.add("Castle");
        tempZones.add("Cave");
        tempZones.add("Forest");
        tempZones.add("Dungeon");
        tempZones.add("Fields");
        tempZones.add("Shadow Lands");

        Collections.shuffle(tempZones);

        tempZones.remove(0);
        tempZones.remove(0);


        for (String zone : tempZones) {
            setFogTileData(zone);
        }

//
//        Random random = new Random();
//        int firstZoneIndex = random.nextInt(tempZones.size() );
//        int secondZoneIndex = random.nextInt(tempZones.size());
//
//        firstZone = tempZones.get(firstZoneIndex);
//        secondZone = tempZones.get(secondZoneIndex);
//
//        while (firstZoneIndex == secondZoneIndex) {
//            secondZoneIndex = random.nextInt(tempZones.size());
//            secondZone = tempZones.get(secondZoneIndex);
//        }
//
//        tempZones.remove(firstZoneIndex);
//        tempZones.remove(secondZoneIndex);


        System.out.println(tempZones.size());
    }

    public static void setRandomActTwoZones() {
//        Random random = new Random();
//        int firstZoneIndex = random.nextInt(tempZones.size());
//        int secondZoneIndex = random.nextInt(tempZones.size());
//
//        firstZone = tempZones.get(firstZoneIndex);
//        secondZone = tempZones.get(secondZoneIndex);
//
//        if (firstZoneIndex == secondZoneIndex) {
//
//            while (firstZoneIndex == secondZoneIndex) {
//                firstZoneIndex = random.nextInt(tempZones.size());
//                secondZoneIndex = random.nextInt(tempZones.size());
//
//                firstZone = tempZones.get(firstZoneIndex);
//                secondZone = tempZones.get(secondZoneIndex);
//            }
//        } else {
//
//            setNoFogTileData(tempZones.get(firstZoneIndex));
//            setNoFogTileData(tempZones.get(secondZoneIndex));
//
//            tempZones.remove(firstZoneIndex);
//            tempZones.remove(secondZoneIndex);
//        }

        Collections.shuffle(tempZones);

        setNoFogTileData(tempZones.get(0));
        setNoFogTileData(tempZones.get(1));

        tempZones.remove(0);
        tempZones.remove(0);

        System.out.println(tempZones.size());

    }

    public static void setRandomActThreeZones() {

        for (String zone : tempZones) {
            System.out.println(zone);
            setNoFogTileData(zone);
        }

    }

    //sets tiles to be their corresponding "fog" tiles.
    public static void setFogTileData(String zoneOne) {
        JSONParser parser = new JSONParser();


        try {
            InputStream is = PixelDungeon.instance.getAssets().open("overworldMap.json");

            JSONObject jsonObject = (JSONObject)parser.parse(new InputStreamReader(is, "UTF-8"));

            JSONArray tileSets = (JSONArray)jsonObject.get("tilesets");

            JSONObject tileSetsContent = (JSONObject)tileSets.get(0);

            JSONObject tileProperties = (JSONObject)tileSetsContent.get("tileproperties");

            for (int i = 0; i < overworldMapLength; i++) {

                if (mapData[i] == 0) {
                    continue;
                }

                Integer curTileID = mapData[i] - 1;

                JSONObject curTile = (JSONObject) tileProperties.get(curTileID.toString());

                String curZone = null;
                String connectedZone = null;

                if (curTile != null) {

                    curZone = (String) curTile.get("zoneName");
                    //connectedZone = (String) curTile.get("connectedZone"); //implements bridge fog, fix!

//                    if (curZone == null && connectedZone == null) {
//                        continue;
//                    }

                    if (curZone == null) {
                        continue;
                    }
                }

                //sets bridge tiles
//                if (connectedZone != null) {
//                    //special case for fields and cave
//                    if ((connectedZone.equals("fieldAndCave")) && zoneOne.equals("Cave") && zoneTwo.equals("Fields")) {
//                        overworldMap[i] = (mapData[i] - 1) + 185;
//                        passableMapData[i] = (mapData[i]) + 185;
//                    }
//
//                    if (connectedZone.equals(zoneOne) || connectedZone.equals(zoneTwo)) {
//                        overworldMap[i] = (mapData[i] - 1) + 185;
//                        passableMapData[i] = (mapData[i] + 185);
//                    }
//                }

                //sets zone tiles
//                if (curZone != null) {
//                    if ((curZone.equals(zoneOne) || curZone.equals(zoneTwo))) {
//                        overworldMap[i] = (mapData[i] - 1) + 1024;
//                        passableMapData[i] = (mapData[i]) + 1024;
//                    }
//                }

                if (curZone != null) {
                    if ((curZone.equals(zoneOne))) {
                        overworldMap[i] = (mapData[i] - 1) + 1024;
                        passableMapData[i] = (mapData[i]) + 1024;
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("error in setting fog tiles: " +e);
        }

        for (ZoneNode zone : zones) {
            if (zone.zoneName.equals(zoneOne)) {
                zone.setAccessible(false);
            }
        }
    }

    //sets zone tiles to be their corresponding "no fog" tiles.
    public static void setNoFogTileData(String zoneOne) {
        JSONParser parser = new JSONParser();

        try {
            InputStream is = PixelDungeon.instance.getAssets().open("overworldMap.json");

            JSONObject jsonObject = (JSONObject)parser.parse(new InputStreamReader(is, "UTF-8"));

            JSONArray tileSets = (JSONArray)jsonObject.get("tilesets");

            JSONObject tileSetsContent = (JSONObject)tileSets.get(0);

            JSONObject tileProperties = (JSONObject)tileSetsContent.get("tileproperties");

            for (int i = 0; i < overworldMapLength; i++) {

                if (mapData[i] == 0) {
                    continue;
                }

                Integer curTileID = mapData[i] - 1;

                JSONObject curTile = (JSONObject) tileProperties.get(curTileID.toString());

                String curZone = null;
                String connectedZone = null;

                if (curTile != null) {

                    curZone = (String) curTile.get("zoneName");
                    //connectedZone = (String) curTile.get("connectedZone");

//                    if (curZone == null && connectedZone == null) {
//                        continue;
//                    }

                    if (curZone == null) {
                        continue;
                    }
                }

                //sets bridge tiles
//                if (connectedZone != null) {
//                    //special case for fields and cave
//                    if ((connectedZone.equals("fieldAndCave")) && zoneOne.equals("Cave") && zoneTwo.equals("Fields")) {
//                        overworldMap[i] = (mapData[i] - 1);
//                        passableMapData[i] = (mapData[i]);
//                    }
//
//                    if (connectedZone.equals(zoneOne) || connectedZone.equals(zoneTwo)) {
//                        overworldMap[i] = (mapData[i] - 1);
//                        passableMapData[i] = (mapData[i]);
//                    }
//                }

                //sets zone tiles
//                if (curZone != null) {
//                    if ((curZone.equals(zoneOne) || curZone.equals(zoneTwo))) {
//                        overworldMap[i] = (mapData[i] - 1);
//                        passableMapData[i] = (mapData[i]);
//                    }
//                }

                if (curZone != null) {
                    if ((curZone.equals(zoneOne))) {
                        overworldMap[i] = (mapData[i] - 1);
                        passableMapData[i] = (mapData[i]);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("error in setting fog tiles: " +e);
        }

        for (ZoneNode zone : zones) {
            if (zone.zoneName.equals(zoneOne)) {
                zone.setAccessible(true);
            }
        }

    }
    // **** fog test methods END ****

    //returns an array list of longs corresponding to the given Json tile map.
    private static ArrayList<Long> getTileMap(String jsonFile) {
        JSONParser parser = new JSONParser();
        ArrayList<Long> jsonTileMap;

        try {
            InputStream is = PixelDungeon.instance.getAssets().open(jsonFile);

            JSONObject jsonObject = (JSONObject)parser.parse(new InputStreamReader(is, "UTF-8"));

            JSONArray layersContent = (JSONArray)jsonObject.get("layers");

            JSONObject jsonObject1 = (JSONObject)layersContent.get(0);

            jsonTileMap = (ArrayList)jsonObject1.get("data");

            return jsonTileMap;

        } catch (Exception e) {
            System.out.println("Error in getting JSON Tile Map: " +e);
        }
        return null;
    }

    //sets which tiles can be traversed based on the "passable" property in the Tiled JSON file.
    public static void setPassableTiles() {
        JSONParser parser = new JSONParser();

        try {
            InputStream is = PixelDungeon.instance.getAssets().open("overworldMap.json");

            JSONObject jsonObject = (JSONObject)parser.parse(new InputStreamReader(is, "UTF-8"));

            JSONArray tileSets = (JSONArray)jsonObject.get("tilesets");

            JSONObject tileSetsContent = (JSONObject)tileSets.get(0);

            JSONObject tileProperties = (JSONObject)tileSetsContent.get("tileproperties");

            for (int i = 0; i < overworldMapLength; i++) {

                if (mapData[i] == 0) {
                    passable[i] = false;
                } else {
                    Integer tileID = mapData[i] - 1;
                    //Integer tileID = overworldMap[i];
                    JSONObject tile = (JSONObject)tileProperties.get(tileID.toString());

                    if (tile != null) {
                        boolean isPassable = (boolean)tile.get("passable");
                        passable[i] = isPassable;
                    } else {
                        passable[i] = false;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("error in setting passable tiles: " +e.getMessage());
        }
    }

    //sets which tiles belong to a specific zone and sets the hero tile of each zone (tile that the hero stands on/moves to).
    private static void setZoneTiles() {
        JSONParser parser = new JSONParser();
        zones = new ArrayList<>();

        try {
            InputStream is = PixelDungeon.instance.getAssets().open("overworldMap.json");

            JSONObject jsonObject = (JSONObject)parser.parse(new InputStreamReader(is, "UTF-8"));

            JSONArray tileSets = (JSONArray)jsonObject.get("tilesets");

            JSONObject tileSetsContent = (JSONObject)tileSets.get(0);

            JSONObject tileProperties = (JSONObject)tileSetsContent.get("tileproperties");

            for (Integer i = 0; i < overworldMapLength; i++) {
                if (mapData[i] == 0) { //if the tile is a water tile, move on to the next iteration
                    continue;
                } else {
                    Integer tileID = mapData[i] - 1;

                    JSONObject curTile = (JSONObject) tileProperties.get(tileID.toString());

                    if (curTile != null) {

                        String zone = (String) curTile.get("zoneName");
                        //boolean fog = (boolean) curTile.get("fog");

                        if (zone != null) {

                            Boolean isHeroPos = (Boolean)curTile.get("heroPos");
                            ZoneNode zoneNode;

                            if (isHeroPos != null) {
                                zoneNode = new ZoneNode(true, tileID, i, zone);
                            } else {
                                zoneNode = new ZoneNode(false, tileID, i, zone);
                            }

                            zones.add(zoneNode);
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("error in setting zone tiles: " +e.getMessage());
        }
    }

    //returns an int[] array corresponding to the tileMap of longs obtained from the JSON file.
    private static int[] integerTileMap() {
        //ArrayList<Long> jsonTileMap = getTileMap("overworldProp.json");
        ArrayList<Long> jsonTileMap = getTileMap("overworldMap.json"); //change when final map is implemented

        int[] mapData = new int[jsonTileMap.size()];
        int k = 0;
        for (long dataValue : jsonTileMap) {
            mapData[k] = (int)dataValue;
            k++;
        }
        return mapData;
    }

    //returns the most efficient step current position to target position based on the cells that can be traversed. (finds a path from current position to target)
    public static int findOverworldPath(int from, int to) {
        return PathFinder.getStep(from, to, passable);
    }

    //sets tile textures
    public static String tilesTexture() {
        //return Assets.TILES_FOREST;
        return Assets.OVERWORLD_MAP_ASSETS;
    }

    //sets water texture
    public static String waterTexture() {
        return Assets.WATER_PRISON;
    }

    // **** START: Zone Logic ****
    private static ArrayList<ZoneNode> zoneNodes;

    //zone name keys:
    public static String TOWN = "TOWN";
    public static String FOREST = "FOREST";
    public static String FIELDS = "FIELDS";
    public static String VILLAGE = "VILLAGE";
    public static String SHADOWLANDS = "SHADOWLANDS";

    //returns the hero position of a given zone
    public static int getZoneHeroPos(String name) {
        for (ZoneNode zoneNode : zones) {
            if (zoneNode.heroPos && zoneNode.zoneName.equals(name)) {
                return zoneNode.mapPos;
            }
        }
        return 0;
    }

    //sets the cell that represents where the hero sprite will be positioned should it move to that zone.
    private static void setZoneNodes() {
        zoneNodes = new ArrayList<>();

        ZoneNode townZoneNode = new ZoneNode(false,13, 141, TOWN);
        ZoneNode forestZoneNode = new ZoneNode(false, 7, 27, FOREST);

        zoneNodes.add(townZoneNode);
        zoneNodes.add(forestZoneNode);
    }

    //returns the position of the zoneNode based on the zone name.
    public static int getZonePos(String name) {
        for (ZoneNode zoneNode : zoneNodes) {
            if (zoneNode.zoneName.equals(name)) {
                return zoneNode.mapPos;
            }
        }
        return 0;
    }
    // **** END: Zone Logic ****

}
