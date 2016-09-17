package at.fhoe.mtd.igp.systems;

import messages.CollisionMsg;
import at.fhoe.mtd.igp.components.BulletCmp;
import at.fhoe.mtd.igp.components.MovementCmp;
import at.fhoe.mtd.igp.components.PoseCmp;
import at.fhoe.mtd.igp.components.ShapeVisualCmp;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;

public class BulletSystem extends IteratingSystem 
	implements Telegraph{

	private static final Family family = Family.getFor(MovementCmp.class, 
													   PoseCmp.class,
													  BulletCmp.class
													   );
	
	private ComponentMapper<MovementCmp> movementMapper;
	private ComponentMapper<PoseCmp> poseMapper;
	private ComponentMapper<BulletCmp> bulletMapper;
	private ComponentMapper<ShapeVisualCmp> shapeVisualMapper;
	private Engine engine;
	
	public BulletSystem() {
		super(family);

		movementMapper = ComponentMapper.getFor(MovementCmp.class);  //virtueller konstruktor
		poseMapper = ComponentMapper.getFor(PoseCmp.class);  //virtueller konstruktor
		bulletMapper = ComponentMapper.getFor(BulletCmp.class);
	}
	
	
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		//System.out.println("bulletsystem");
		MovementCmp movement = movementMapper.get(entity);
		PoseCmp pose = poseMapper.get(entity);
		BulletCmp bullet = bulletMapper.get(entity);
		
		//pose.pos.x+=1;
		if (bullet.lifeTime > 0) {
			bullet.lifeTime -= deltaTime;
			movement.vel = bullet.heading.cpy().rotate(pose.angle).scl(bullet.speed); // beschleunigungs vektor
		} else {
			destroyBullet(entity);	
		}	
		
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
		
		 if ( isBullet( data.getFirst() ) ) {
			  destroyBullet(data.getFirst());
		 }
		 if ( isBullet( data.getSecond() ) ) {
			 destroyBullet(data.getSecond());
		 }
		
		return true;
	}
	
	 private boolean isBullet(Entity entity) {
		   return bulletMapper.has(entity);
	  }

	 private void destroyBullet(Entity entity) {
	      engine.removeEntity(entity);
	 }
}
