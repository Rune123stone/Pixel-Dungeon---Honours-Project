package com.watabou.pixeldungeon.overworld;

import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.Tilemap;
import com.watabou.pixeldungeon.levels.Level;
import com.watabou.utils.Point;
import com.watabou.utils.PointF;

public class OverworldTileMap extends Tilemap{

    public static final int SIZE = 16; //cell size

    public static OverworldTileMap instance;

    public OverworldTileMap() {
        super(OverworldMap.tilesTexture(), new TextureFilm(OverworldMap.tilesTexture(), SIZE, SIZE));
        map(OverworldMap.overworldMap, OverworldMap.overworldMapWidth);

        instance = this;
    }

    public int screenToTile( int x, int y ) {

        Point p = camera().screenToCamera( x, y ).
                offset( this.point().negate() ).
                invScale( SIZE ).
                floor();

        // we put the 'k' in "komputers"

//        int W = Game.width;
//        int H = Game.height;
//        int w = OverworldMap.overworldMapWidth;
//        int h = OverworldMap.overworldMapHeight;
//        int l = OverworldMap.overworldMapLength;
//        int s = SIZE;
//        int Cx = x/s; // Cell co-ordinate
//        int Cy = y/s;

//        System.out.println("[!]");
//        System.out.println("Game dimensions: ("+W+","+H+")");
//        System.out.println("Map dimensions with tilesize ["+s+"]: ("+w+","+h+")");
//        System.out.println("Map array length is ["+l+"]; should be ["+(w*h)+"]");
//        System.out.println("You tapped pixel ("+x+", "+y+")");
//        System.out.println("Even though pixel was married to ("+(x/s)+", "+(y/s)+")");
//        System.out.println("Pixel is waiting for you at cell number ["+ ((Cy*w)+Cx) +"]");
//        System.out.println("Point identified by Sweden guy: ("+p.x+", "+p.y+")");
//        System.out.println("[!]");

        return p.x >= 0 && p.x < OverworldMap.overworldMapWidth && p.y >= 0 && p.y < OverworldMap.overworldMapHeight ? p.x + p.y * OverworldMap.overworldMapWidth : -1;
    }

    @Override
    public boolean overlapsPoint( float x, float y ) {
        return true;
    }

    public static PointF tileToWorld(int pos ) {
        return new PointF( pos % Level.WIDTH, pos / Level.WIDTH  ).scale( SIZE );
    }

    public static PointF tileCenterToWorld( int pos ) {
        return new PointF(
                (pos % Level.WIDTH + 0.5f) * SIZE,
                (pos / Level.WIDTH + 0.5f) * SIZE );
    }

    public static Image tile(int index ) {
        Image img = new Image( instance.texture );
        img.frame( instance.tileset.get( index ) );
        return img;
    }

    @Override
    public boolean overlapsScreenPoint( int x, int y ) {
        return true;
    }


}
