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
package com.watabou.pixeldungeon.items.quest;

import com.watabou.noosa.audio.Sample;
import com.watabou.pixeldungeon.Assets;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.actors.blobs.Blob;
import com.watabou.pixeldungeon.actors.blobs.Fire;
import com.watabou.pixeldungeon.items.potions.Potion;
import com.watabou.pixeldungeon.scenes.GameScene;
import com.watabou.pixeldungeon.sprites.ItemSpriteSheet;

public class PotionOfLiquidFlame extends Potion {

	{
		name = "Potion of Liquid Flame";
		image = ItemSpriteSheet.POTION_AMBER;
	}
	
	@Override
	public void shatter( int cell ) {
		
		if (Dungeon.visible[cell]) {
			setKnown();
			
			splash( cell );
			Sample.INSTANCE.play( Assets.SND_SHATTER );
		}
		
		GameScene.add( Blob.seed( cell, 2, Fire.class ) );
	}
	
	@Override
	public String desc() {
		return
			"This flask contains an unstable compound which will burst " +
			"violently into flame upon exposure to open air.";
	}
	
	@Override
	public int price() {
		return isKnown() ? 40 * quantity : super.price();
	}
}
