package com.watabou.pixeldungeon.overworld;

public class ZoneNode {
    int x, y, mapPos;
    String zoneName;
    private boolean accessible;

    public ZoneNode(int x, int y, String zoneName) {
        this.x = x;
        this.y = y;
        this.zoneName = zoneName;
        mapPos = (y * OverworldMap.overworldMapWidth) + x;
        accessible = true;
    }

    public void setAccessible(boolean accessible) {
        this.accessible = accessible;
    }


}
