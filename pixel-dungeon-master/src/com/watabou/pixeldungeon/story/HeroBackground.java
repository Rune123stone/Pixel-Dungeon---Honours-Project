package com.watabou.pixeldungeon.story;

import java.util.ArrayList;
import java.util.Collections;

public class HeroBackground {

    public String background;
    public ArrayList<String> possibleStartingLevels;

    ArrayList<QuestGiver> questGivers;

    public HeroBackground(String background) {
        this.background = background;

        possibleStartingLevels = new ArrayList<>();
        questGivers = new ArrayList<>();
    }

    public void addQuestGiver(QuestGiver questGiver) {
        questGivers.add(questGiver);
    }

    public void addStartingLevel(String startingLevel) {
        possibleStartingLevels.add(startingLevel);
    }

    public String getRandomStartingLevel() {
        Collections.shuffle(possibleStartingLevels);
        return possibleStartingLevels.get(0);
    }

}
