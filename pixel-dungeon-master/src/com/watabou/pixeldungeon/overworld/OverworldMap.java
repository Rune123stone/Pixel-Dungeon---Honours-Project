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

public class OverworldMap  {

    public static final int overworldMapWidth = 32; //Tiled map width
    //public static final int overworldMapHeight = 32; //use the Tiled map height if map gives problems later on

    public static final int overworldMapHeight = Game.height / OverworldTileMap.SIZE; //sets map height to full screen
    public static int overworldMapLength = overworldMapWidth * overworldMapHeight;

    public static int[] overworldMap = new int[overworldMapLength];
    public static boolean[] water = new boolean[overworldMapLength];

    public static boolean[] passable = new boolean[overworldMapLength];

    //paints tiles of overworld map
    public static void createOverworld() {
        PathFinder.setMapSize(overworldMapWidth, overworldMapHeight); //sets size of map for Pathfinder class

        int mapData[] = integerTileMap();

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
        setPassable();
    }

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

    //returns an int[] array corresponding to the tileMap of longs obtained from the JSON file.
    private static int[] integerTileMap() {
        ArrayList<Long> jsonTileMap = getTileMap("overworld.json");

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

    //sets which cells can be traversed.
    private static void setPassable() {
        for (int i = 0; i < overworldMapLength; i++) {
            passable[i] = true;
        }
    }

    //sets tile textures
    public static String tilesTexture() {
        return Assets.TILES_FOREST;
    }

    //sets water texture
    public static String waterTexture() {
        return Assets.WATER_CAVES;
    }

    // **** START: Zone Logic ****
    private static ArrayList<ZoneNode> zoneNodes;

    //zone name keys:
    public static String TOWN = "TOWN";
    public static String FOREST = "FOREST";
    public static String FIELDS = "FIELDS";
    public static String VILLAGE = "VILLAGE";
    public static String SHADOWLANDS = "SHADOWLANDS";

    //sets the cell that represents where the hero sprite will be positioned should it move to that zone.
    private static void setZoneNodes() {
        zoneNodes = new ArrayList<>();

        ZoneNode townZoneNode = new ZoneNode(13, 4, TOWN);
        ZoneNode forestZoneNode = new ZoneNode(7, 27, FOREST);

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
