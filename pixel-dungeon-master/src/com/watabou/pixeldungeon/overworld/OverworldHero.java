package com.watabou.pixeldungeon.overworld;

import com.watabou.pixeldungeon.actors.Char;
import com.watabou.pixeldungeon.actors.hero.HeroAction;
import com.watabou.pixeldungeon.scenes.OverworldScene;
import com.watabou.utils.Bundle;

public class OverworldHero extends Char {
    public boolean ready;

    private HeroAction curAction;
    public HeroAction lastAction; //consider removing
    public boolean reachedDestination = true;

    //public int mapPos;
    public String currentZone; //current zone of the hero on the overworld map
    public int curPos;  //current position of the hero on the overworld map

    public OverworldHero() {
        //update heroSprite armor?
    }

    //bundle keys
    private String curPosKey = "CURPOS";
    private String zoneKey = "ZONE";

    @Override
    public void storeInBundle(Bundle bundle) {
        bundle.put(curPosKey, curPos);
        bundle.put(zoneKey, currentZone);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        curPos = bundle.getInt(curPosKey);
        currentZone = bundle.getString(zoneKey);
    }

    //tells the sprite to move through the map
    @Override
    public boolean act() {
        if (curAction == null) {
            ready();
            return false;
        } else {
            if (curAction instanceof HeroAction.Move) {
                return overworldActMove((HeroAction.Move)curAction);
            }
        }
        return false;
    }

    //indicates that the sprite is finished with it's action and is ready to receive another action.
    public void ready() {
        sprite.idle();
        curAction = null;
        ready = true;

        OverworldScene.overworldReady();
    }

    // **** handles path finding across the map ****

    //move towards the target zone by one step.
    private boolean moveTowardsTarget(final int target) {  //translates to getCloserOverworldMap(final int target)
        //reachedDestination = false;
        int step;
        //boolean[] passable = OverworldMap.passable;

        //step = OverworldMap.findOverworldPath(mapPos, target);
        step = OverworldMap.findOverworldPath(curPos, target);

        //if a path exists between pos and target
        if (step != -1) {
            //int oldPos = mapPos;
            int oldPos = curPos;
            //mapPos = step; //should be the same as (overworldMove(step)).
            curPos = step;
            //sprite.overworldMove(oldPos, mapPos);
            sprite.overworldMove(oldPos, curPos);
            spend(1);

            if (curPos == target) {
                reachedDestination = true;
            }

            return true;
        } else {
            return false;
        }


    }

    private boolean overworldActMove(HeroAction.Move action) {
        if (moveTowardsTarget(action.dst)) {
            return true;
        } else {
            ready();
            return false;
        }
    }

    //sets the action to a "move" action and sets the target cell
    public boolean overworldHandle(int cell, int from) { //consider an array list of possible cells (zones)
        curAction = new HeroAction.Move(cell);
        lastAction = null;

        curPos = from;

        return act();
    }

    @Override
    public void next() {
        super.next();
    }
}
