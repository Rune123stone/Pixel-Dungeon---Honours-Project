package com.watabou.pixeldungeon.levels;

import com.watabou.pixeldungeon.Assets;
import com.watabou.pixeldungeon.PixelDungeon;
import com.watabou.pixeldungeon.overworld.ZoneNode;
import com.watabou.utils.PathFinder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TownLevel extends RegularLevel {

    public static final int townMapWidth = 40; //Tiled map width
    public static final int townMapHeight = 40; //use the Tiled map height if map gives problems later on

    public static int townMapLength = townMapWidth * townMapHeight;

    public static int[] townMap = new int[townMapLength];
    public static boolean[] water = new boolean[townMapLength];

    public static boolean[] passable = new boolean[townMapLength];

    private static int[] mapData = new int[townMapLength];
    private static int[] passableMapData = new int[townMapLength];

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
        //ArrayList<Long> jsonTileMap = getTileMap("overworldProp.json");
        ArrayList<Long> jsonTileMap = getTileMap("townMap.json"); //change when final map is implemented

        int[] mapData = new int[jsonTileMap.size()];
        int k = 0;
        for (long dataValue : jsonTileMap) {
            mapData[k] = (int)dataValue;
            k++;
        }
        return mapData;
    }

    //sets tile textures
    @Override
    public String tilesTex() {
        return Assets.TOWN_ASSETS;
    }

    @Override
    public String waterTex() {
        return Assets.WATER_SEWERS;
    }

    @Override
    protected boolean[] water() {
        return new boolean[0];
    }

    @Override
    protected boolean[] grass() {
        return new boolean[0];
    }

    @Override
    protected void decorate() {
        PathFinder.setMapSize(townMapWidth, townMapHeight); //sets size of map for Pathfinder class

        mapData = integerTileMap();
        //passableMapData = mapData;
        //note : 24 is water texture
        //"paints" the overworld with the specified tile textures
        for (int i = 0; i < townMapLength; i++) {
                if (i < mapData.length) {
                    if (mapData[i] == 0) {
                        map[i] = 24; //set to water texture
                    } else {
                        //note : when using forest_tiles.png tileset, use data - 1
                        map[i] = mapData[i] - 1;
                    }
                } else {
                    map[i] = 24; //set to water texture
                }
        }

    }

}
