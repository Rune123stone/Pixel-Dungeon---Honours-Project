package com.watabou.pixeldungeon.overworld;

import com.watabou.gltextures.SmartTexture;
import com.watabou.gltextures.TextureCache;
import com.watabou.noosa.Camera;
import com.watabou.noosa.TextureFilm;
import com.watabou.pixeldungeon.Assets;
import com.watabou.pixeldungeon.actors.hero.Hero;
import com.watabou.pixeldungeon.actors.hero.HeroClass;
import com.watabou.pixeldungeon.scenes.OverworldScene;
import com.watabou.pixeldungeon.scenes.StartScene;
import com.watabou.pixeldungeon.sprites.CharSprite;

public class OverworldHeroSprite extends CharSprite {
    private static final int FRAME_WIDTH      =  12;
    private static final int FRAME_HEIGHT     =  15;

    private static final int RUN_FRAMERATE    =  20;

    private static TextureFilm tiers;
    private static int tier;

    public OverworldHeroSprite() {
        super();

        link(OverworldScene.hero);

        //texture(OverworldHero.heroClass.spritesheet());

        //texture(Assets.WARRIOR); //using default textures for sprite for now, fix by using the commented line above when it's implemented.

        //if it stops working, use Assets.WARRIOR etc.
        switch (StartScene.curClass.title) {
            case "warrior":
                texture(HeroClass.WARRIOR.spritesheet());
                break;
            case "mage":
                texture(HeroClass.MAGE.spritesheet());
                break;
            case "rogue":
                texture(HeroClass.ROGUE.spritesheet());
                break;
            case "huntress":
                texture(HeroClass.HUNTRESS.spritesheet());
                break;
        }


        updateSprite();

        idle();
    }

    //updates the sprites armor visuals as well as updates the run and idle animations.
    public void updateSprite() { //translates to updateArmor()

        if (tier == 0) { //if tier hasn't been assigned yet
            tier = 1;
        }

        //maximum of 6 tiers.
        TextureFilm film = new TextureFilm(tiers(), tier, FRAME_WIDTH, FRAME_HEIGHT); //set key to the hero's current tier eventually

        idle = new Animation(1, true);
        idle.frames(film, 0, 0, 0, 1, 0, 0, 1, 1);

        run = new Animation(RUN_FRAMERATE, true);
        run.frames(film, 2, 3, 4, 5, 6, 7);
    }

    public static void setTier(int t) {
        tier = t;
    }

    public static TextureFilm tiers() {
        if (tiers == null) {
            SmartTexture texture = TextureCache.get(Assets.ROGUE);
            tiers = new TextureFilm(texture, texture.width, FRAME_HEIGHT);
        }

        return tiers;
    }

    //places the sprite on the overworld map
    public void placeOnOverworld(int p) {
        point(overworldToCamera(p));
        Camera.main.target = null;
    }

    private com.watabou.utils.PointF overworldToCamera(int cell) {
        final int cellSize = OverworldTileMap.SIZE;

        return new com.watabou.utils.PointF(
                ((cell % OverworldMap.overworldMapWidth) + 0.5f) * cellSize - width * 0.5f,
                ((cell / OverworldMap.overworldMapWidth) + 1.0f) * cellSize - height
        );
    }

    //scales sprite's size to match the overworld's size
    public void scaleSpriteToOverworld() {
        scale.x *= 3;
        scale.y *= 3;
    }
}
