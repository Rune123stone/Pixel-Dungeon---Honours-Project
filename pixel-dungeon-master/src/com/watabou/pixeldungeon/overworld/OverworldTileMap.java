package com.watabou.pixeldungeon.overworld;

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
        return p.x >= 0 && p.x < Level.WIDTH && p.y >= 0 && p.y < Level.HEIGHT ? p.x + p.y * Level.WIDTH : -1;
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
