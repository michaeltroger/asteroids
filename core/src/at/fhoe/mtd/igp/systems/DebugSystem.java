package at.fhoe.mtd.igp.systems;

import at.fhoe.mtd.igp.components.CircleColliderCmp;
import at.fhoe.mtd.igp.components.MovementCmp;
import at.fhoe.mtd.igp.components.PoseCmp;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class DebugSystem extends IteratingSystem {

	private OrthographicCamera cam;

	private ShapeRenderer renderer;
	private static final Family family = Family.getFor(PoseCmp.class, 
														CircleColliderCmp.class);
	private ComponentMapper<PoseCmp> poseMapper;
	private ComponentMapper<CircleColliderCmp> circleColliderMapper;

	
	public DebugSystem(OrthographicCamera camera) {
		super(family);
		
		poseMapper = ComponentMapper.getFor(PoseCmp.class);
		circleColliderMapper = ComponentMapper.getFor(CircleColliderCmp.class);
		
		this.cam = camera;
		renderer = new ShapeRenderer();
	}

	
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		CircleColliderCmp circleCollider = circleColliderMapper.get(entity);
		PoseCmp pose = poseMapper.get(entity);
		
		renderer.setProjectionMatrix(cam.combined);
		renderer.begin(ShapeRenderer.ShapeType.Line);
			renderer.identity();
			renderer.setColor(Color.ORANGE);
			renderer.circle(pose.pos.x, pose.pos.y, circleCollider.radius);
		renderer.end();
	}

}
