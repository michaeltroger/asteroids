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

import messages.CollisionMsg;
import messages.MovingMsg;
import at.fhoe.mtd.igp.World;
import at.fhoe.mtd.igp.components.BulletCmp;
import at.fhoe.mtd.igp.components.CircleColliderCmp;
import at.fhoe.mtd.igp.components.MovementCmp;
import at.fhoe.mtd.igp.components.PoseCmp;
import at.fhoe.mtd.igp.components.ShapeVisualCmp;
import at.fhoe.mtd.igp.components.ShipCmp;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class ShipSystem extends IteratingSystem  {
	private static final Family family = Family.getFor(MovementCmp.class, 
													  ShapeVisualCmp.class, 
													   PoseCmp.class,
													  ShipCmp.class
													   );
	
	private float SHOOT_PAUSE = 0.1f;
	private ComponentMapper<MovementCmp> movementMapper;
	private ComponentMapper<PoseCmp> poseMapper;
	private ComponentMapper<ShapeVisualCmp> shapeVisualMapper;
	private ComponentMapper<ShipCmp> shipMapper;
	private float timeSinceLastShot;
	private Engine engine;
	private float[] verticesOfBullet = {
			//0.0f, 1.0f, -1.0f, -1.0f, 0.0f, -0.7f, 1.0f, -1.0f
			-0.2f, 1.0f, -0.2f, 0f, 0.2f, 0f, 0.2f, 1.0f
	};
	private static Vector2 shipPos;

	public static Vector2 getShipPos() {
		return shipPos.cpy();
	}

	public void setShootPause(float shootPause) {
		SHOOT_PAUSE = shootPause;
	}


	public ShipSystem() {
		super(family);

		movementMapper = ComponentMapper.getFor(MovementCmp.class);  //virtueller konstruktor
		poseMapper = ComponentMapper.getFor(PoseCmp.class);  //virtueller konstruktor
		shipMapper = ComponentMapper.getFor(ShipCmp.class);
		shapeVisualMapper = ComponentMapper.getFor(ShapeVisualCmp.class);
		timeSinceLastShot = SHOOT_PAUSE;
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		//System.out.println("bleib stehen");
		MovementCmp movement = movementMapper.get(entity);
		PoseCmp pose = poseMapper.get(entity);
		ShipCmp ship = shipMapper.get(entity);
		ShapeVisualCmp shapeVisual = shapeVisualMapper.get(entity);
		
		Input input = Gdx.input;
		
		// turning
		if (input.isKeyPressed(ship.left)) {
			movement.angVel = ship.torque;
		} else if (input.isKeyPressed(ship.right)) {
			movement.angVel = -ship.torque;
		} else {
			movement.angVel = 0;
		}

		shipPos = pose.pos;
		
		// moving
		if (input.isKeyPressed(ship.up)) {
			
			movement.acc = ship.heading.cpy().rotate(pose.angle).scl(ship.thrust); // beschleunigungs vektor
			movement.vel.mulAdd(movement.acc, deltaTime);
			//movement.vel = new Vector2(0,1).rotate(pose.angle).scl(ship.thrust); // beschleunigungs vektor	
			//code: v += (a - d * v)*dT
			//gleichung: Vn+1 = Vn + (a - d * Vn)*dT
			
			MessageManager.getInstance().dispatchMessage(null, // kein konkreter empfänger
					MovingMsg.MSG_ID, new MovingMsg(Keys.UP, true));
		} 
		else {
			MessageManager.getInstance().dispatchMessage(null, // kein konkreter empfänger
				MovingMsg.MSG_ID, new MovingMsg(Keys.UP, false));
			
			movement.vel.x = 0;
			movement.vel.y = 0;
		}
		
		timeSinceLastShot += deltaTime;

		if (timeSinceLastShot >= SHOOT_PAUSE && Gdx.input.isKeyJustPressed(ship.shoot)) {
		//	System.out.println("shoot");
			Entity bullet = new Entity();
			
			//+ shapeVisual.offsetToTop
			Vector2 offset = new Vector2(0, shapeVisual.offsetToTop);
			offset.rotate(pose.angle);
			
			Vector2 shipPos = new Vector2(pose.pos.x, pose.pos.y);

			bullet.add(new PoseCmp(new Vector2(offset.add(shipPos)), pose.angle));
			bullet.add(new MovementCmp());
			bullet.add(new BulletCmp(50, 1, new Vector2(0, 1)));
			bullet.add(new CircleColliderCmp(1, CircleColliderCmp.CATEGORY_BULLET));
			bullet.add(new ShapeVisualCmp(verticesOfBullet,  World.textureAtlas.findRegion("bullet"), Color.BLUE, 0));
			//long id = bullet.getId();
			engine.addEntity(bullet);

			timeSinceLastShot = 0;
		} 
		
	}
	
	 @Override
	    public void addedToEngine(Engine engine) {
	        super.addedToEngine(engine);
	        this.engine = engine;
	    }




}
