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
