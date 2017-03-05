package com.watabou.pixeldungeon.levels;

import java.util.ArrayList;

public class Cavern  {

    private ArrayList<Cell> cells = new ArrayList<>();
    private boolean killed = false;

    public int getSize() {
        return cells.size();
    }

    public void AddCell( Cell newCell ) {
        cells.add( newCell );
    }

    public void KillCells() {

        killed = true;

        for ( Cell cell : cells )
            cell.setDead();
    }

    public boolean Killed() {
        return killed;
    }
}
