package com.watabou.pixeldungeon.levels;

import java.util.ArrayList;

public class Cavern  {

    private int size;

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
}
