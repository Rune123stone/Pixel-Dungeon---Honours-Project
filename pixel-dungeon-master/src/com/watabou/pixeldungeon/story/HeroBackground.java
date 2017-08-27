package com.watabou.pixeldungeon.story;

import java.util.ArrayList;

public class HeroBackground {

    public String background;

    ArrayList<QuestGiver> questGivers;

    public HeroBackground(String background) {
        this.background = background;

        questGivers = new ArrayList<>();
    }

    public void addQuestGiver(QuestGiver questGiver) {
        questGivers.add(questGiver);
    }

}
