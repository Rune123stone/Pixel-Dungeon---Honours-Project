package com.watabou.pixeldungeon.overworld;

import com.watabou.utils.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class Zone extends Rect implements Graph.Node, Bundlable {

    public HashSet<Zone> neighbours = new HashSet<Zone>();
    public HashMap<Zone, Gateway> connected = new HashMap<>();

    public int distance;
    public int price = 1;

    //testing
    public int[][] area;
    public String zoneName;

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public void addNeighbour(Zone otherZone) {
        neighbours.add(otherZone);
        otherZone.neighbours.add(this);
    }

    public void connect(Zone zone) {
        connected.put(zone, null);
        zone.connected.put(this, null);
    }

    public Gateway entrance() {
        return connected.values().iterator().next();
    }

    public boolean inside(int p) {
        int x = p & OverworldMap.overworldMapWidth;
        int y = p / OverworldMap.overworldMapWidth;

        return x > left && y > top && x < right && y < bottom;
    }

    //does this make sense?
    public Point center() {
        return new Point(top / 2, left / 2);
    }

    // **** Graph.Node interface ****
    @Override
    public int distance() {
        return distance;
    }

    @Override
    public void distance(int value) {
        distance = value;
    }

    @Override
    public int price() {
        return price;
    }

    @Override
    public void price(int value) {
        price = value;
    }

    @Override
    public Collection<? extends Graph.Node> edges() {
        return neighbours;
    }

    // **** END of Graph.Node interface ****

    @Override
    public void storeInBundle(Bundle bundle) {
        bundle.put("left", left);
        bundle.put("top", top);
        bundle.put("right", right);
        bundle.put("bottom", bottom);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        left = bundle.getInt( "left" );
        top = bundle.getInt( "top" );
        right = bundle.getInt( "right" );
        bottom = bundle.getInt( "bottom" );
    }

    //Overworld Map version of a Room's Door
    public static class Gateway extends Point {
        public Gateway(int x, int y) {
            super(x, y);
        }
    }

    public class Node {
        int x, y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }




}
