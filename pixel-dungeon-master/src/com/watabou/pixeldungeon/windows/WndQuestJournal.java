package com.watabou.pixeldungeon.windows;

import android.graphics.RectF;
import com.watabou.noosa.BitmapText;
import com.watabou.noosa.BitmapTextMultiline;
import com.watabou.noosa.Image;
import com.watabou.noosa.ui.Component;
import com.watabou.pixeldungeon.Assets;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.Journal;
import com.watabou.pixeldungeon.PixelDungeon;
import com.watabou.pixeldungeon.actors.mobs.Mob;
import com.watabou.pixeldungeon.quests.QuestJournal;
import com.watabou.pixeldungeon.scenes.PixelScene;
import com.watabou.pixeldungeon.sprites.BatSprite;
import com.watabou.pixeldungeon.sprites.CharSprite;
import com.watabou.pixeldungeon.ui.Icons;
import com.watabou.pixeldungeon.ui.ScrollPane;
import com.watabou.pixeldungeon.ui.Window;

import java.util.Collections;

public class WndQuestJournal extends Window {

    private static final int WIDTH = 125;
    private static final int HEIGHT_P	= 160;
    private static final int HEIGHT_L	= 144;

    private static final int ITEM_HEIGHT	= 18;
    private static final int MARGIN = 4;

    private static final String TXT_TITLE	= "Quest Journal";

    private BitmapText txtTitle;
    private ScrollPane list;

    public WndQuestJournal() {

        super();
        resize( WIDTH, PixelDungeon.landscape() ? HEIGHT_L : HEIGHT_P );

        txtTitle = PixelScene.createText( TXT_TITLE, 9 );
        txtTitle.hardlight( Window.TITLE_COLOR );
        txtTitle.measure();
        txtTitle.x = PixelScene.align( PixelScene.uiCamera, (WIDTH - txtTitle.width()) / 2 );
        add( txtTitle );

        Component content = new Component();

        float pos = 6;


        for (QuestJournal.QuestEntry questEntry : QuestJournal.questEntries) {

            ListQuestItem questItem = new ListQuestItem(questEntry.entry);
            questItem.setRect(0, pos, WIDTH, ITEM_HEIGHT);
           //content.add(questItem);

            BitmapTextMultiline info = PixelScene.createMultiline( questEntry.entry, 8);
            info.maxWidth = WIDTH - 5;
            info.measure();
            info.x = info.y = MARGIN;

            content.add(info);

            questItem.feature.y = pos;

            pos += questItem.height();
        }

        content.setSize(WIDTH, pos);

        list = new ScrollPane( content );
        add( list );

        list.setRect(0, txtTitle.height(), WIDTH, height - txtTitle.height());

        //content.add(enemyImage);


    }

    public static class ListQuestItem extends Component {

        private BitmapText feature;
        private BitmapText depth;

        private Image icon;

        public ListQuestItem( String questEntry ) {
            super();

            feature.text( questEntry );
            feature.measure();

//            if (d == Dungeon.depth) {
//                feature.hardlight( TITLE_COLOR );
//                depth.hardlight( TITLE_COLOR );
//            }
        }

        @Override
        protected void createChildren() {
            feature = PixelScene.createText( 9 );
            add( feature );

            //icon = Icons.get( Icons.FOREST );
            //add ( icon );
        }

        @Override
        protected void layout() {

//          icon.x = width - icon.width;
//
//            depth.x = icon.x - 1 - depth.width();
//            depth.y = PixelScene.align( y + (height - depth.height()) / 2 );
//
          //  icon.y = feature.baseLine() / 2;

            feature.y = PixelScene.align(  feature.baseLine() );
        }



    }



}
