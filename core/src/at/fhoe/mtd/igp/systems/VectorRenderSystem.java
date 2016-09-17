package at.fhoe.mtd.igp.systems;

import at.fhoe.mtd.igp.components.PoseCmp;
import at.fhoe.mtd.igp.components.ShapeVisualCmp;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;


public class VectorRenderSystem extends IteratingSystem {

	private static final Family family = Family.getFor(ShapeVisualCmp.class, 
													   PoseCmp.class);
	
		
	private OrthographicCamera cam;

	private ShapeRenderer renderer;
	private ComponentMapper<ShapeVisualCmp> shapeVisualMapper;
	private ComponentMapper<PoseCmp> poseMapper;
	
	public VectorRenderSystem(OrthographicCamera camera) {
		super(family);
		
		shapeVisualMapper = ComponentMapper.getFor(ShapeVisualCmp.class);
		poseMapper = ComponentMapper.getFor(PoseCmp.class);
		
		this.cam = camera;
		renderer = new ShapeRenderer();
		
	}


	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		
		ShapeVisualCmp shapeVisual = shapeVisualMapper.get(entity);
		PoseCmp pose = poseMapper.get(entity);
		
		
			renderer.identity();
			
			/*
			renderer.setColor(Color.ORANGE);
			renderer.circle(15, 15, 0.5f, 12);
			renderer.circle(-15, 15, 0.5f, 12);
			renderer.circle(15, -15, 0.5f, 12);
			renderer.circle(-15, -15, 0.5f, 12);
			*/
			
			renderer.translate(pose.pos.x, pose.pos.y, 0.0f);
			renderer.rotate(0.0f, 0.0f, 1.0f, pose.angle);
			renderer.setColor(shapeVisual.color);
			renderer.polygon(shapeVisual.vertices);
		
		
	}


	@Override
	public void update(float deltaTime) {

		renderer.setProjectionMatrix(cam.combined);
		renderer.begin(ShapeType.Line);
		
		super.update(deltaTime);
		
		renderer.end();
	}
	
	
	

}
