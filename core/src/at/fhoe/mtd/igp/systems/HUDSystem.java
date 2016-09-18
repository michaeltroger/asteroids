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
import at.fhoe.mtd.igp.ImageActor;
import at.fhoe.mtd.igp.components.MovementCmp;
import at.fhoe.mtd.igp.components.PoseCmp;
import at.fhoe.mtd.igp.components.ShapeVisualCmp;
import at.fhoe.mtd.igp.components.ShipCmp;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class HUDSystem extends EntitySystem 
	implements Telegraph {

	
	RotateToAction rotateAction;
	ImageActor needleActor;
	Stage hudStage;
	
	public Viewport getViewport() {
		return hudStage.getViewport();
	}
	
	public HUDSystem() {
		super(0);
		
		
		hudStage = new Stage(	new ExtendViewport(640,480)	);
		
	    TextureAtlas hudTextureAtlas = new TextureAtlas("hud.pack");
	    TextureRegion gauge1 = hudTextureAtlas.findRegion("gauge1");
	    TextureRegion needle = hudTextureAtlas.findRegion("needle");
	    ImageActor gauge1Actor = new ImageActor(gauge1);
	    needleActor = new ImageActor(needle);
	    
	 
	    gauge1Actor.setX(gauge1Actor.getX() + gauge1Actor.getWidth()/2);
	    gauge1Actor.setY(gauge1Actor.getY() + gauge1Actor.getHeight()/2);
	    needleActor.setX(needleActor.getX() + gauge1Actor.getWidth()/2);
	    needleActor.setY(needleActor.getY() + needleActor.getHeight()/2);
	    
	    rotateAction = new RotateToAction();
	    rotateAction.setRotation(90);
	   // action.setDuration(duration);
	    needleActor.addAction(rotateAction);
	    
	    
	    Group hudGroup = new Group();
	    hudGroup.addActor(gauge1Actor);
	    hudGroup.addActor(needleActor);
	   // hudGroup.scaleBy(1.4f);
	    
	    hudStage.addActor(hudGroup);	
	    
        MessageManager.getInstance().addListener(this, MovingMsg.MSG_ID);
		
	}

	
	public void update(float deltaTime) {
	
		hudStage.act(deltaTime);
		hudStage.draw();

	}



	@Override
	public boolean handleMessage(Telegram msg) {
		//System.out.println("hud");
		MovingMsg data = (MovingMsg)msg.extraInfo;

		 int key = data.getKey();
		 boolean down = data.isDown();
		
		float currentAngle = rotateAction.getRotation();
		
		//System.out.println(currentAngle);
		
		if (down && currentAngle > -89) {
			rotateAction = new RotateToAction();
			rotateAction.setRotation(currentAngle - 1);
			needleActor.addAction(rotateAction);
		} 
		else if (currentAngle < 89) {
			rotateAction = new RotateToAction();
			rotateAction.setRotation(currentAngle + 1);
			needleActor.addAction(rotateAction);
		}
		
	
		
		return true;
	}
}
