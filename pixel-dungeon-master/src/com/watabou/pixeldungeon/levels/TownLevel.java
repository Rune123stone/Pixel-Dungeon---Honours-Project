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
        return Assets.TILES_TOWN;
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

    public boolean isPassable(int n) {

        switch (n) {

            case 658:
            case 659:
            case 660:
            case 661:


            case 690:
            case 691:
            case 692:
            case 693:

            case 722:
            case 723:
            case 724:
            case 725:

            case 754:
            case 755:
            case 756:
            case 757:

            case 652:
            case 653:
            case 654:
            case 655:
            case 656:
            case 657:

            case 684:
            case 685:
            case 686:
            case 687:
            case 688:
            case 689:

            case 716:
            case 717:
            case 718:
            case 719:
            case 720:
            case 721:

            case 748:
            case 749:
            case 750:
            case 751:
            case 752:
            case 753:

            case 780:
            case 781:
            case 782:
            case 783:
            case 784:
            case 785:

            case 812:
            case 813:
            case 814:
            case 815:
            case 816:
            case 817:

            case 427:
            case 428:
            case 429:
            case 430:
            case 431:

            case 459:
            case 460:
            case 461:
            case 462:
            case 463:

            case 491:
            case 492:
            case 493:
            case 494:
            case 495:

            case 523:
            case 524:
            case 525:
            case 526:
            case 527:

            case 555:
            case 556:
            case 557:
            case 558:
            case 559:
                return true;
        }

        return false;

    }

    @Override
    protected void decorate() {
        //PathFinder.setMapSize(townMapWidth, townMapHeight); //sets size of map for Pathfinder class

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
                        //map[i] = mapData[i] - 1;

                        map[i] = mapData[i] - 1;

                        if (isPassable(map[i])) {
                            Terrain.flags[map[i]] = Terrain.flags[Terrain.EMPTY];
                        } else {
                            Terrain.flags[map[i]] = Terrain.flags[Terrain.WALL];
                        }
                    }
                } else {
                    map[i] = 24; //set to water texture
                }
        }

    }

}
