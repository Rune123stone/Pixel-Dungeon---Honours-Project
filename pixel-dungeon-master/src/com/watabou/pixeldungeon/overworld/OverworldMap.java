package com.watabou.pixeldungeon.overworld;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.Assets;
import com.watabou.pixeldungeon.PixelDungeon;
import com.watabou.pixeldungeon.actors.Char;
import com.watabou.pixeldungeon.levels.Terrain;
import com.watabou.utils.Graph;
import com.watabou.utils.PathFinder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;

public class OverworldMap  {

    public static final int overworldMapWidth = 32; //Tiled map width
    //public static final int overworldMapHeight = 40; //use the Tiled map height if map gives problems later on

    public static final int overworldMapHeight = Game.height / OverworldTileMap.SIZE; //sets map height to full screen
    public static int overworldMapLength = overworldMapWidth * overworldMapHeight;

    public static int[] overworldMap = new int[overworldMapLength];
    public static boolean[] water = new boolean[overworldMapLength];

    public static ArrayList<Zone> zones;
    public static boolean[] passable = new boolean[overworldMapLength];

    //returns an array list of longs corresponding to the given Json tile map
    public static ArrayList<Long> getTileMaps(String jsonFile) {
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

    //paints tiles of overworld map
    public static void paintOverworld() {

        ArrayList<Long> jsonTileMap = getTileMaps("overworldv2.json");
        //converts array list of longs to int array
        int[] mapData = new int[jsonTileMap.size()];
        int k = 0;
        for (long dataValue : jsonTileMap) {
            mapData[k] = (int)dataValue;
            k++;
        }

        //note : 24 is water texture
        for (int i = 0; i < overworldMapLength; i++) {
            if (i < mapData.length) {
                if (mapData[i] == 0) {
                    overworldMap[i] = 24;
                } else {
                    overworldMap[i] = mapData[i] - 1;
                }
            } else {
                overworldMap[i] = 24;
            }
//            if (mapData[i] == null || mapData[i] == 0) {
//                overworldMap[i] = 24;
//            } else {
//                //note : when using forest_tiles.png tileset, use data - 1
//                overworldMap[i] = mapData[i] - 1;
//            }
//        }
        }
        setZoneNodes();
        setPassable();
    }

    //sets tile textures
    public static String tilesTexture() {
        return Assets.TILES_FOREST;
    }

    //sets water texture
    public static String waterTexture() {
        return Assets.WATER_CAVES;
    }

    //zone building attempts
    //private ArrayList<Zone> zones;
    private Zone zoneEntrance;
    private Zone zoneExit;

    public void buildZones() {
        int distance;
        int minDistance = (int)Math.sqrt(zones.size());

        zoneEntrance = zones.get(0);
        zoneExit = zones.get(5);

        Graph.buildDistanceMap(zones, zoneExit);
        distance = zoneEntrance.distance;

    }

    public void initZones() {
        zones = new ArrayList<>();
    }

    public boolean zoneExists(String thisZoneName) {
        for (Zone zone : zones) {
            if (zone.zoneName.equals(thisZoneName)) {
                return true;
            }
        }
        return false;
    }

    public static ArrayList<ZoneNode> zoneNodes;

    public static void setZoneNodes() {
        zoneNodes = new ArrayList<>();
        ZoneNode townZoneNode = new ZoneNode(13, 4, "TOWN");
        ZoneNode forestZoneNode = new ZoneNode(7, 27, "FOREST");
        zoneNodes.add(townZoneNode);
        zoneNodes.add(forestZoneNode);
    }

    public static int getZonePos(String name) {
        for (ZoneNode zoneNode : zoneNodes) {
            if (zoneNode.zoneName.equals(name)) {
                return zoneNode.mapPos;
            }
        }
        return 0;
    }

    public static int findOverworldPath(Char ch, int from, int to, boolean pass[]) {
        return PathFinder.getStep(from, to, passable);
    }

    public static void setPassable() {
        for (int i = 0; i < overworldMapLength; i++) {
            passable[i] = true;
        }
    }
}
