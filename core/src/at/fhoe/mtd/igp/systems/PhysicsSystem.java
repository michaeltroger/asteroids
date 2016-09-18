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

import at.fhoe.mtd.igp.components.MovementCmp;
import at.fhoe.mtd.igp.components.PoseCmp;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;

public class PhysicsSystem extends IteratingSystem {

	private static final Family family = Family.getFor(PoseCmp.class, 
													   MovementCmp.class);
	
	
	private float hWorldRadius;
	private float vWorldRadius;
	
	private ComponentMapper<PoseCmp> poseMapper;
	private ComponentMapper<MovementCmp> movementMapper;
	
	public PhysicsSystem(float worldWidth) {
		super(family);
		
		poseMapper = ComponentMapper.getFor(PoseCmp.class);  //virtueller konstruktor
		movementMapper = ComponentMapper.getFor(MovementCmp.class);  //virtueller konstruktor
		
		hWorldRadius = worldWidth/2f;
		vWorldRadius = worldWidth/2f;
	}
	
	private Vector2 spawnOnOtherSide(Vector2 pos) {
		if(pos.x < -hWorldRadius) {
			pos.x = hWorldRadius;
		} else if (pos.x > hWorldRadius) {
			pos.x = -hWorldRadius;
		} else if (pos.y < -vWorldRadius) {
			pos.y = vWorldRadius;
		} else if (pos.y > vWorldRadius) {
			pos.y = -vWorldRadius;
		}
		
		return pos;
	}
	
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		
		PoseCmp pose = poseMapper.get(entity);
		MovementCmp movement = movementMapper.get(entity);
				
		pose.pos.mulAdd(movement.vel, deltaTime);
		pose.angle += movement.angVel * deltaTime; // durch deltatime entkoppelt von libgdx
		
		pose.pos = spawnOnOtherSide(pose.pos);
		
		//code: pos += vel * dT
		//gleichung: pn+1 = pn + vn+1 * dT
		
		//System.out.println("physicssystem");
		
		//code: v += (a - d * v)*dT
		//gleichung: Vn+1 = Vn + (a - d * Vn)*dT
		// bewegungsgleichung - max. geschw. ergibt sich aut.
		// vor positions berechnung!!
		
	}
	
	public void setWorldSize(float width, float height) {
		hWorldRadius = width/2f;
		vWorldRadius = height/2f;
	}

}
