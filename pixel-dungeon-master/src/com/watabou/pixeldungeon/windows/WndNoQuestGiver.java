package com.watabou.pixeldungeon.windows;

import com.watabou.input.Touchscreen;
import com.watabou.noosa.BitmapTextMultiline;
import com.watabou.noosa.Game;
import com.watabou.noosa.TouchArea;
import com.watabou.pixeldungeon.Chrome;
import com.watabou.pixeldungeon.scenes.GameScene;
import com.watabou.pixeldungeon.scenes.PixelScene;
import com.watabou.pixeldungeon.ui.Window;
import com.watabou.utils.SparseArray;

public class WndNoQuestGiver extends Window {

    private static final int WIDTH = 120;
    private static final int MARGIN = 6;

    private static final float bgR = 0.77f;
    private static final float bgG = 0.73f;
    private static final float bgB = 0.62f;

    public static final int ID_PEASANT = 0;
    public static final int ID_KNIGHT = 1;
    public static final int ID_CRIMINAL = 2;
    public static final int ID_OUTSIDER = 3;

    private static boolean storyTold = false;

    private static final SparseArray<String> BACKGROUNDSTORIES = new SparseArray<String>();

    static {
        BACKGROUNDSTORIES.put(ID_PEASANT,
                "You care not for the hustle and bustle of the city life. " +
                        "You like food, a lot. " +
                        "You are the peasant.");

        BACKGROUNDSTORIES.put(ID_KNIGHT,
                "Born into the army, you've been training your whole life. " +
                        "You enjoy long walks on the beach. " +
                        "You are the knight. ");

        BACKGROUNDSTORIES.put(ID_CRIMINAL,
                "Stealing from the rich and giving back to the common folk, you're a hero amongst the people." +
                        "Prison isn't too great, but you've gotten used to it. " +
                        "You are the criminal. ");

        BACKGROUNDSTORIES.put(ID_OUTSIDER,
                "For over 20 years, you've travelled accross the lands in search for your next adventure." +
                        "Your feet hurt and you have no friends. " +
                        "You are the outsider.");
    }

    private BitmapTextMultiline tf;

    private float delay;

    public WndNoQuestGiver(String questDialogue) {
        super(0, 0, Chrome.get(Chrome.Type.SCROLL));

        tf = PixelScene.createMultiline(questDialogue, 7);
        tf.maxWidth = WIDTH - MARGIN * 2;
        tf.measure();
        tf.ra = bgR;
        tf.ga = bgG;
        tf.ba = bgB;
        tf.rm = -bgR;
        tf.gm = -bgG;
        tf.bm = -bgB;
        tf.x = MARGIN;
        add(tf);

        add(new TouchArea(chrome) {
            @Override
            protected void onClick(Touchscreen.Touch touch) {
                hide();
            }
        });

        resize((int) (tf.width() + MARGIN * 2), (int) Math.min(tf.height(), 180));
    }


    @Override
    public void update() {
        super.update();

        if (delay > 0 && (delay -= Game.elapsed) <= 0) {
            shadow.visible = chrome.visible = tf.visible = true;
        }
    }

    public static void showQuestDialogue(String questDialogue) {

        WndNoQuestGiver wnd = new WndNoQuestGiver(questDialogue);

        if ((wnd.delay = 0.6f) > 0) {
            wnd.shadow.visible = wnd.chrome.visible = wnd.tf.visible = false;
        }

        Game.scene().add(wnd);

    }
}
