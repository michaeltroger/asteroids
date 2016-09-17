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
