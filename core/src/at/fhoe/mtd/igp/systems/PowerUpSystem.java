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

import at.fhoe.mtd.igp.MyGame;
import at.fhoe.mtd.igp.components.*;
import messages.CollisionMsg;
import at.fhoe.mtd.igp.World;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.math.Vector2;

public class PowerUpSystem extends IteratingSystem
implements Telegraph {

	private ComponentMapper<MovementCmp> movementMapper;
	private ComponentMapper<PoseCmp> poseMapper;
	private ComponentMapper<PowerUpCmp> powerUpMapper;
	private ComponentMapper<ShipCmp> shipMapper;
	private Engine engine;
	
	private static final Family family = Family.getFor(MovementCmp.class, 
													  PoseCmp.class,
													  PowerUpCmp.class
													   );
	
	
	public PowerUpSystem() {
		super(family);
		
		movementMapper = ComponentMapper.getFor(MovementCmp.class);  //virtueller konstruktor
		poseMapper = ComponentMapper.getFor(PoseCmp.class);  //virtueller konstruktor
		powerUpMapper = ComponentMapper.getFor(PowerUpCmp.class);
		shipMapper = ComponentMapper.getFor(ShipCmp.class);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		MovementCmp movement = movementMapper.get(entity);
		PoseCmp pose = poseMapper.get(entity);
		PowerUpCmp powerUp = powerUpMapper.get(entity);
		
		movement.vel = powerUp.heading.cpy().rotate(pose.angle).scl(powerUp.speed); // beschleunigungs vektor
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
		
		 if ( isPowerUp( data.getFirst() ) ) {
			 destroyPowerUp(data.getFirst());
			 handled = true;
		 }
		 if ( isPowerUp( data.getSecond() ) ) {
			 destroyPowerUp(data.getSecond());
			 handled = true;
		 }


		return handled;
	}

	private boolean isPowerUp(Entity entity) {
		   return powerUpMapper.has(entity);
	  }

	private boolean isShip(Entity entity) {
		return shipMapper.has(entity);
	}

	 private void destroyPowerUp(Entity entity) {
		 PowerUpCmp powerUp = powerUpMapper.get(entity);
		 PoseCmp pose = poseMapper.get(entity);
		
		 engine.removeEntity(entity);
	
	 }

}
