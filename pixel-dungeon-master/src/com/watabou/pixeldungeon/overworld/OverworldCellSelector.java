package com.watabou.pixeldungeon.overworld;

import com.watabou.input.Touchscreen;
import com.watabou.noosa.TouchArea;
import com.watabou.pixeldungeon.PixelDungeon;
import com.watabou.pixeldungeon.scenes.CellSelector;
import com.watabou.pixeldungeon.scenes.OverworldScene;
import com.watabou.pixeldungeon.scenes.PixelScene;
import com.watabou.utils.GameMath;
import com.watabou.utils.PointF;

public class OverworldCellSelector extends TouchArea {

    public Listener listener = null;

    public boolean enabled;

    private float dragThreshold;

    public OverworldCellSelector(OverworldTileMap overworldTileMap) {
        super(overworldTileMap);
        camera = overworldTileMap.camera();

        dragThreshold = PixelScene.defaultZoom * OverworldTileMap.SIZE / 2;
    }

    @Override
    protected void onClick( Touchscreen.Touch touch ) {
        System.out.println("im being called");
        if (dragging) {

            dragging = false;

        } else {
            select( ((OverworldTileMap)target).screenToTile(
                    (int)touch.current.x,
                    (int)touch.current.y ) );
//            System.out.println((int)touch.current.x);
//            System.out.println((int)touch.current.y);
        }
    }

    public void select( int cell ) {
//        if (enabled && listener != null && cell != -1) {
//
//            listener.onSelect( cell );
//            OverworldScene.overworldReady();
//        }
//        if (enabled && cell != -1) {
//            listener.onSelect( cell );
//            OverworldScene.overworldReady();
//            System.out.println("yes");
//
//        }
        OverworldScene.overworldReady();
        listener.onSelect( cell );

    }

    private boolean pinching = false;
    private Touchscreen.Touch another;
    private float startZoom;
    private float startSpan;

    @Override
    protected void onTouchDown( Touchscreen.Touch t ) {

        if (t != touch && another == null) {

            if (!touch.down) {
                touch = t;
                onTouchDown( t );
                return;
            }

            pinching = true;

            another = t;
            startSpan = PointF.distance( touch.current, another.current );
            startZoom = camera.zoom;

            dragging = false;
        }
    }

    @Override
    protected void onTouchUp( Touchscreen.Touch t ) {
        if (pinching && (t == touch || t == another)) {

            pinching = false;

            int zoom = Math.round( camera.zoom );
            camera.zoom( zoom );
            PixelDungeon.zoom( (int)(zoom - PixelScene.defaultZoom) );

            dragging = true;
            if (t == touch) {
                touch = another;
            }
            another = null;
            lastPos.set( touch.current );
        }
    }

    private boolean dragging = false;
    private PointF lastPos = new PointF();

    //consider removing / only allowing camera to be dragged when zoomed in
//    @Override
//    protected void onDrag( Touchscreen.Touch t ) {
//
//        camera.target = null;
//
//        if (pinching) {
//
//            float curSpan = PointF.distance( touch.current, another.current );
//            camera.zoom( GameMath.gate(
//                    PixelScene.minZoom,
//                    startZoom * curSpan / startSpan,
//                    PixelScene.maxZoom ) );
//
//        } else {
//
//            if (!dragging && PointF.distance( t.current, t.start ) > dragThreshold) {
//
//                dragging = true;
//                lastPos.set( t.current );
//
//            } else if (dragging) {
//                camera.scroll.offset( PointF.diff( lastPos, t.current ).invScale( camera.zoom ) );
//                lastPos.set( t.current );
//            }
//        }
//
//    }

    public interface Listener {
        void onSelect( Integer cell );
    }
}
