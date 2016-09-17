package at.fhoe.mtd.igp.systems;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import messages.AsteroidDestroyedMsg;
import messages.CollisionMsg;
import at.fhoe.mtd.igp.World;
import at.fhoe.mtd.igp.components.AsteroidCmp;
import at.fhoe.mtd.igp.components.MovementCmp;
import at.fhoe.mtd.igp.components.PoseCmp;
import at.fhoe.mtd.igp.components.ShapeVisualCmp;
import at.fhoe.mtd.igp.components.ShipCmp;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.math.Vector2;

public class AsteroidSystem extends IteratingSystem
	implements Telegraph {

	private ComponentMapper<MovementCmp> movementMapper;
	private ComponentMapper<PoseCmp> poseMapper;
	private ComponentMapper<AsteroidCmp> asteroidMapper;
	private Engine engine;

	private static final Family family = Family.getFor(MovementCmp.class, 
													  PoseCmp.class,
													  AsteroidCmp.class
													   );
	


	public AsteroidSystem() {
		super(family);
		
		movementMapper = ComponentMapper.getFor(MovementCmp.class);  //virtueller konstruktor
		poseMapper = ComponentMapper.getFor(PoseCmp.class);  //virtueller konstruktor
		asteroidMapper = ComponentMapper.getFor(AsteroidCmp.class);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		MovementCmp movement = movementMapper.get(entity);
		PoseCmp pose = poseMapper.get(entity);
		AsteroidCmp asteroid = asteroidMapper.get(entity);
		
		movement.vel = asteroid.heading.cpy().rotate(pose.angle).scl(asteroid.speed); // beschleunigungs vektor

	}
	
	@Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        this.engine = engine;
        MessageManager.getInstance().addListener(this, CollisionMsg.MSG_ID);
    }

	@Override
	public boolean handleMessage(Telegram msg) {
		CollisionMsg data = (CollisionMsg)msg.extraInfo;
		boolean handled = false;
		
		 if ( isAsteroid( data.getFirst() ) ) {
			 destroyAsteroid(data.getFirst());
			 handled = true;
		 }
		 if ( isAsteroid( data.getSecond() ) ) {
			 destroyAsteroid(data.getSecond());
			 handled = true;
		 }
		
		return handled;
	}
	
	private boolean isAsteroid(Entity entity) {
		   return asteroidMapper.has(entity);
	  }

	 private void destroyAsteroid(Entity entity) {
		 AsteroidCmp asteroid = asteroidMapper.get(entity);
		 PoseCmp pose = poseMapper.get(entity);

		 MessageManager.getInstance().dispatchMessage(null, // kein konkreter empfänger
				 AsteroidDestroyedMsg.MSG_ID, new AsteroidDestroyedMsg(pose.pos.x, pose.pos.y, asteroid.size));


		 engine.removeEntity(entity);
	
	 }

}
