/**
Copyright 2016 Michael Troger <https://michaeltroger.com>
This file is part of Asteroids Clone with LibGDX.

Asteroids Clone with LibGDX is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Asteroids Clone with LibGDX is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Asteroids Clone with LibGDX.  If not, see <http://www.gnu.org/licenses/>.
*/
package at.fhoe.mtd.igp.systems;

import at.fhoe.mtd.igp.components.PoseCmp;
import at.fhoe.mtd.igp.components.ShapeVisualCmp;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;


public class BitmapRenderSystem extends IteratingSystem {

	private static final Family family = Family.getFor(ShapeVisualCmp.class, 
													   PoseCmp.class);
	
		
	private OrthographicCamera cam;
	private float scaleFactor;
	private SpriteBatch batcher;
	private ComponentMapper<ShapeVisualCmp> shapeVisualMapper;
	private ComponentMapper<PoseCmp> poseMapper;
	private Texture backgroundImage;
	private int worldWidth;
	
	public BitmapRenderSystem(OrthographicCamera camera, SpriteBatch batcher, float scaleFactor, Texture backgroundImage, int worldWidth) {
		super(family);
		
		shapeVisualMapper = ComponentMapper.getFor(ShapeVisualCmp.class);
		poseMapper = ComponentMapper.getFor(PoseCmp.class);


		this.backgroundImage = backgroundImage;
		this.scaleFactor = scaleFactor;
		this.worldWidth = worldWidth;
		this.cam = camera;
		this.batcher = batcher;
	}


	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		
		ShapeVisualCmp shapeVisual = shapeVisualMapper.get(entity);
		PoseCmp pose = poseMapper.get(entity);
	
		batcher.draw(shapeVisual.textureRegion, 
			pose.pos.x- shapeVisual.textureRegion.getRegionWidth()/2, pose.pos.y - shapeVisual.textureRegion.getRegionHeight()/2, // position
			shapeVisual.textureRegion.getRegionWidth()/2, shapeVisual.textureRegion.getRegionHeight()/2,						  // origin
			shapeVisual.textureRegion.getRegionWidth(), shapeVisual.textureRegion.getRegionHeight(), 							  // size
			scaleFactor,scaleFactor,																							  // scale
			pose.angle																											  // angle
		);
	
	}


	@Override
	public void update(float deltaTime) {

		//batcher.setProjectionMatrix(cam.combined);
		//renderer.begin(ShapeType.Line);
		batcher.begin();
		batcher.draw(backgroundImage, -worldWidth / 2, -worldWidth / 2, worldWidth, worldWidth);
		super.update(deltaTime);
		
		batcher.end();
		//renderer.end();
	}
	
	
	

}
