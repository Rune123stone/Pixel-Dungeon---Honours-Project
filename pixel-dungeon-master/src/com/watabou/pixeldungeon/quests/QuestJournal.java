package com.watabou.pixeldungeon.quests;

import com.watabou.noosa.Image;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

public class QuestJournal {

    public static class QuestEntry implements Bundlable {

        private static final String ENTRY = "quest entry";

        public String entry;
        public String enemyImage;

        public QuestEntry(String entry) {
            this.entry = entry;
        }

        @Override
        public void restoreFromBundle(Bundle bundle) {
            entry = bundle.getString(ENTRY);
        }

        @Override
        public void storeInBundle(Bundle bundle) {
            bundle.put(ENTRY, entry);
        }
    }

    public static ArrayList<QuestEntry> questEntries = new ArrayList<>();

    public static void reset() {
        questEntries = new ArrayList<>();
    }

    private static final String QUESTJOURNAL = "quest journal";

    public static void storeInBundle(Bundle bundle) {
        bundle.put(QUESTJOURNAL, questEntries);
    }

    public static void restoreFromBundle(Bundle bundle) {
        questEntries = new ArrayList<>();
        for (Bundlable bundlable : bundle.getCollection(QUESTJOURNAL)) {
            questEntries.add((QuestEntry) bundlable);
        }
    }

    public static void addQuestEntry(String entry) {
        questEntries.add(new QuestEntry(entry));
    }


    public static void removeQuestEntry(String givenEntry) {
        for (QuestEntry questEntry : questEntries) {
            if (questEntry.entry.equals(givenEntry)) {
                questEntries.remove(questEntry);
            }
        }
    }


}
