/**
This file is part of Asteroid Clone by Michael Troger.

Asteroid Clone by Michael Troger is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Asteroid Clone by Michael Troger is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Asteroid Clone by Michael Troger.  If not, see <http://www.gnu.org/licenses/>.
*/
package at.fhoe.mtd.igp;

/* 
 * Copyright (c) 2015 Roman Divotkey, Univ. of Applied Sciences Upper Austria.
 * All rights reserved.
 *  
 * THIS CODE IS PROVIDED AS EDUCATIONAL MATERIAL AND NOT INTENDED TO ADDRESS
 * ALL REAL WORLD PROBLEMS AND ISSUES IN DETAIL.
 */



import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;

public class ImageActor extends Actor {

	private TextureRegion region;	
	
	public ImageActor(TextureRegion region) {
		this.region = region;
		setSize(region.getRegionWidth(), region.getRegionHeight());
		setOrigin(Align.center);
		setPosition(-getWidth() / 2, -getHeight() / 2);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
		batch.draw(region, getX(), getY(), getOriginX(), getOriginY(),
				getWidth(), getHeight(), getScaleX(), getScaleY(),
				getRotation());
	}

}
