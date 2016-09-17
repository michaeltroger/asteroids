package at.fhoe.mtd.igp.systems;

import messages.CollisionMsg;
import at.fhoe.mtd.igp.components.CircleColliderCmp;
import at.fhoe.mtd.igp.components.MovementCmp;
import at.fhoe.mtd.igp.components.PoseCmp;
import at.fhoe.mtd.igp.components.ShipCmp;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.badlogic.gdx.ai.msg.MessageManager;

public class CircleColliderSystem extends EntitySystem {
	
	private static final Family family = Family.getFor(CircleColliderCmp.class, 
												   PoseCmp.class
												   );

	private ComponentMapper<CircleColliderCmp> circleColliderMapper;
	private ComponentMapper<PoseCmp> poseMapper;
	private Engine engine;
	
	/** The entities used by this system */
	private ImmutableArray<Entity> entities;
	
	
	public CircleColliderSystem() {
		super(0);
		
		circleColliderMapper = ComponentMapper.getFor(CircleColliderCmp.class);  //virtueller konstruktor
		poseMapper = ComponentMapper.getFor(PoseCmp.class);  //virtueller konstruktor
		
	}
	
	private boolean testEntities(Entity entity1, Entity entity2) {
		boolean collision = false;
		
		CircleColliderCmp circleCollider1 = circleColliderMapper.get(entity1);
		PoseCmp pose1 = poseMapper.get(entity1);
		
		CircleColliderCmp circleCollider2 = circleColliderMapper.get(entity2);
		PoseCmp pose2 = poseMapper.get(entity2);
		
		// x2−x1)2+(y2−y1)
		if ( pose1.pos.dst2(pose2.pos) 	< circleCollider1.radius * circleCollider1.radius + circleCollider2.radius * circleCollider2.radius
		 && ((circleCollider1.flag & circleCollider2.mask) != 0) && ((circleCollider2.flag & circleCollider1.mask) != 0)
			) {
			collision = true;
		}
		
		return collision;
	}

	public void update(float deltaTime) {
		for (int i = 0; i < entities.size(); ++i) {
			Entity entity1 = entities.get(i);
			for (int j = i + 1; j < entities.size(); ++j) {
				Entity entity2 = entities.get(j);
				
				if(testEntities(entity1, entity2)) {
					
					//System.out.println("collision detected");
					MessageManager.getInstance().dispatchMessage(null, // kein konkreter empfänger
							CollisionMsg.MSG_ID, new CollisionMsg(entity1, entity2));
					
				}
			}
		}
		
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(family);
		this.engine = engine;
	}
	
	
}
