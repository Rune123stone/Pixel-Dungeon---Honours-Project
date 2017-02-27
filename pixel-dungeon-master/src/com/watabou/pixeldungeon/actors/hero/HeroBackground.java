package com.watabou.pixeldungeon.actors.hero;

import com.watabou.utils.Bundle;

public enum HeroBackground {

    PEASANT("peasant"), KNIGHT("knight"), CRIMINAL("criminal"), OUTSIDER("outsider");

    private String title;

    private HeroBackground(String title) {
        this.title = title;
    }

    private static final String BACKGROUND	= "background";

    //returns a random background
    public static HeroBackground selectBackground() {
        java.util.Random random = new java.util.Random();
        return values()[random.nextInt(values().length)];
    }

    public String title() {
        return title;
    }

    public void storeInBundle( Bundle bundle ) {
        bundle.put( BACKGROUND, toString() );
    }

    public static HeroBackground restoreInBundle( Bundle bundle ) {
        String value = bundle.getString( BACKGROUND );
        return value.length() > 0 ? valueOf( value ) : OUTSIDER;
    }
}