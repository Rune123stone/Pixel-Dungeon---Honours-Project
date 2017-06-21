package com.watabou.pixeldungeon.levels;

public class Cell {

    //General variables
    private boolean alive, corner, border;

    //Forest variables
    private boolean lake, tempLake, grass, tempGrass;

    //Fields variables
    private boolean windmillCell;

    //Shadow Lands Variables
    private boolean trapCell;

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

    public void setWindmillCell() {
        windmillCell = true;
    }

    public boolean isWindmillCell() {
        return windmillCell;
    }

    public void setTrapCell() {
        trapCell = true;
    }

    public boolean isTrapCell() {
        return trapCell;
    }


    public void setCorner() {
        corner = true;
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

    public boolean isCorner() {
        return corner;
    }

    public boolean isBorder() {
        return border;
    }

    public boolean isLake() {
        return lake;
    }

    public void setLake() {
        lake = true;
    }

    public boolean isGrass() {
        return grass;
    }

    public void setGrass() {
        grass = true;
    }

    //find better solution
    public boolean isTempLake() {
        return tempLake;
    }

    public void setTempLake() {
        tempLake = true;
    }

    public boolean isTempGrass() {
        return tempGrass;
    }

    public void setTempGrass() {
        tempGrass = true;
    }
    /////

    public boolean needsCavern() {
        return ( cavernIndex == -1 && alive );
    }

    public boolean hasCavern() {
        return !needsCavern();
    }



}
