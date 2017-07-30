/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.watabou.pixeldungeon.actors.mobs.npcs;

import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.actors.mobs.Mob;
import com.watabou.pixeldungeon.items.Heap;
import com.watabou.pixeldungeon.levels.Level;
import com.watabou.pixeldungeon.quests.Quest;
import com.watabou.pixeldungeon.quests.QuestHandler;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public abstract class NPC extends Mob {

	Quest quest;

	QuestHandler questHandler;
	public boolean hasQuestItem;

	public boolean speakToQuest;
	public boolean questGiver; //allows for the assigning of the same quest to multiple NPC's for interaction handling without interfering with who gives you the quest.

	//Bundle TAGS
	public static final String SPEAKTOQUEST = "speakToQuest";
	public static final String QUESTGIVER = "questGiver";
	public static final String QUEST = "Quest";


	{
		HP = HT = 1;
		EXP = 0;
	
		hostile = false;
		state = PASSIVE;

	}

	@Override
	public void storeInBundle( Bundle bundle ) {

		super.storeInBundle( bundle );

		bundle.put( SPEAKTOQUEST, speakToQuest );
		bundle.put( QUESTGIVER, questGiver );
		bundle.put( QUEST, quest);
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {

		super.restoreFromBundle( bundle );

		speakToQuest = bundle.getBoolean(SPEAKTOQUEST);
		questGiver = bundle.getBoolean(QUESTGIVER);

		Bundlable bundlable = bundle.get(QUEST);

		if (bundlable != null) {
			quest = (Quest) bundlable;
		}
	}


	protected void throwItem() {
		Heap heap = Dungeon.level.heaps.get( pos );
		if (heap != null) {
			int n;
			do {
				n = pos + Level.NEIGHBOURS8[Random.Int( 8 )];
			} while (!Level.passable[n] && !Level.avoid[n]);
			Dungeon.level.drop( heap.pickUp(), n ).sprite.drop( pos );
		}
	}

	public void assignQuest(Quest quest) {
		this.quest = quest;
	}

	public void removeQuest() {
		quest = null;
	}

	public void assignQuestItem(boolean hasQuestItem) {
		this.hasQuestItem = hasQuestItem;
	}

	public void assignSpeakToQuest(boolean speakToQuest) {
		this.speakToQuest = speakToQuest;
	}

	public void setQuestGiver(boolean questGiver) {
		this.questGiver = questGiver;
	}

	@Override
	public void beckon( int cell ) {
	}
	
	abstract public void interact();


}
