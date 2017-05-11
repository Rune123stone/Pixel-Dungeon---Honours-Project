package com.watabou.pixeldungeon.overworld;

public class ZoneNode {
    public int mapPos, tileID;
    public boolean heroPos; //position that the hero moves to/stands on
    public String zoneName;
    private boolean accessible;

    //maybe make inner class inside OverworldMap?
    public ZoneNode(boolean heroPos, int tileID, int mapPos, String zoneName) {
        this.heroPos = heroPos;
        this.tileID = tileID;
        this.mapPos = mapPos;
        this.zoneName = zoneName;
        //mapPos = (y * OverworldMap.overworldMapWidth) + x;
        accessible = true;
    }

    public void setAccessible(boolean accessible) {
        this.accessible = accessible;
    }

    public boolean isAccessible() {
        return accessible;
    }

}
