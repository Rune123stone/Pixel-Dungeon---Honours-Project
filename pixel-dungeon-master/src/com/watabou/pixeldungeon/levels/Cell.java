package com.watabou.pixeldungeon.levels;

public class Cell {

    private boolean alive, corner, border;
    private int cavernIndex = -1;

    public Cell(boolean alive) {
        this.alive = alive;
    }

    public void setAlive() {
        alive = true;
    }

    public void setDead() {
        alive = false;
    }

    public void setBorder() {
        border = true;
        setDead();
    }

    public void assignCavern(int cavern) {
        this.cavernIndex = cavern;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isDead() {
        return !alive;
    }

    public boolean isBorder() {
        return border;
    }

    public boolean needsCavern() {
        return ( cavernIndex == -1 && alive );
    }

    public boolean hasCavern() {
        return !needsCavern();
    }

}
