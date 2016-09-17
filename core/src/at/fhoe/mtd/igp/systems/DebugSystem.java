/**
This file is part of Asteroid Clone by Michael Troger.

Asteroid Clone by Michael Troger is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Asteroid Clone by Michael Troger is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Asteroid Clone by Michael Troger.  If not, see <http://www.gnu.org/licenses/>.
*/
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
