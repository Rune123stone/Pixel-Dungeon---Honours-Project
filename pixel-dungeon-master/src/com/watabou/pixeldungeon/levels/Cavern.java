package com.watabou.pixeldungeon.levels;

import java.util.ArrayList;

public class Cavern  {

    private int size;
    private ArrayList<CavernCells> cavernCells = new ArrayList<>();

    public Cavern() {
        size = 0;
    }

    public void Grow() {
        size++;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void addCavernCells(int x, int y) {
        cavernCells.add(new CavernCells(x,y));
    }

    public ArrayList<CavernCells> getCells() {
        ArrayList<CavernCells> cells = new ArrayList<>();
        for (CavernCells i : cavernCells) {
            cells.add(i);
        }
        return cells;
    }

    class CavernCells {
        int x, y;

        private CavernCells(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
