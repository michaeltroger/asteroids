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
package at.fhoe.mtd.igp;

import at.fhoe.mtd.igp.components.*;
import com.badlogic.ashley.core.ComponentMapper;
import messages.CollisionMsg;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class World implements Telegraph {

	static private Engine engine; // maybe using not static is better
	static public TextureAtlas textureAtlas;
	static TextureRegion texturePowerUp;
	static TextureRegion textureShip;
	static TextureRegion textureShip1;
	static TextureRegion textureAsteroidSmall;
	static TextureRegion textureAsteroidMedium;
	static TextureRegion textureAsteroidBig;
	static TextureRegion textureOrb;

	private ComponentMapper<PowerUpCmp> powerUpMapper;
	private ComponentMapper<ShipCmp> shipMapper;
	private ComponentMapper<AsteroidCmp> asteroidMapper;
	private ComponentMapper<OrbCmp> orbMapper;

	public static int lifes = 6;
	static Entity ship;

	public World (Engine engine) {
		
		World.engine = engine;

		loadTextures();

		powerUpMapper = ComponentMapper.getFor(PowerUpCmp.class);
		shipMapper = ComponentMapper.getFor(ShipCmp.class);
		asteroidMapper = ComponentMapper.getFor(AsteroidCmp.class);
		orbMapper = ComponentMapper.getFor(OrbCmp.class);

		MessageManager.getInstance().addListener(this, CollisionMsg.MSG_ID);
	}

	public void loadTextures() {
		textureAtlas = new TextureAtlas("atlas.pack");

		texturePowerUp = textureAtlas.findRegion("powerup");
		textureShip = textureAtlas.findRegion("ship");
		textureShip1 = textureAtlas.findRegion("ship1");
		textureAsteroidSmall = textureAtlas.findRegion("asteroid_small");
		textureAsteroidMedium = textureAtlas.findRegion("asteroid_medium");
		textureAsteroidBig = textureAtlas.findRegion("asteroid_big");
		textureOrb = textureAtlas.findRegion("orb");
	}
	
	public void create() {
		createShip(new Vector2(-8,0), 1, textureShip);
		createShip(new Vector2(8,2), 2, textureShip1);
		
		createAsteroid(3, new Vector2(0, 2), new Vector2( 0, 1 ), 20, 6);
		createAsteroid(3, new Vector2(5, 10), new Vector2( 1, 0 ), 10, 40);
		createAsteroid(3, new Vector2(-4, 3), new Vector2( 1, 0 ), 5, -20);
	}
	
	public static Entity createPowerup(Vector2 pos, Vector2 heading, int speed, float angle) {
		Entity powerUp = new Entity();
		
		float[] verticesOfPowerUp = {
				0.0f, 1.0f, -1.0f, -1.0f,  0.0f, -2.0f, 1.0f, -1.0f
		};
		powerUp.add(new PoseCmp(pos, angle)); // start position/angle
		powerUp.add(new MovementCmp()); 
		powerUp.add(new PowerUpCmp(speed, heading)); 
		powerUp.add(new CircleColliderCmp(1, CircleColliderCmp.CATEGORY_POWERUP, CircleColliderCmp.CATEGORY_PLAYER)); 
		powerUp.add(new ShapeVisualCmp(verticesOfPowerUp,  texturePowerUp, Color.RED, 1));  // offset top
		
		engine.addEntity(powerUp);
		
		return powerUp;
	}
	
	private static Entity createShip(Vector2 pos, int playerNr, TextureRegion texture) { // maybe using not static is better
		ship = new Entity();
			
		float[] verticesOfShip = {
				0.0f, 1.0f, -1.0f, -1.0f, 0.0f, -0.7f, 1.0f, -1.0f
		};
		ship.add(new PoseCmp(pos, 0)); // start position/angle
		ship.add(new MovementCmp()); 
		ship.add(new ShipCmp(playerNr, new Vector2(0, 1), 20, 200)); // heading / thrust /
		ship.add(new CircleColliderCmp(1, CircleColliderCmp.CATEGORY_PLAYER)); 
		ship.add(new ShapeVisualCmp(verticesOfShip,  texture, Color.GREEN, 2));  // offset top
		
		engine.addEntity(ship);
		
		return ship;
	}

	public static Entity createOrb() {
		Entity orb = new Entity();
		orb.add(new PoseCmp() );

		float[] verticesOfBullet = {
				-0.2f, 1.0f, -0.2f, 0f, 0.2f, 0f, 0.2f, 1.0f
		};
		orb.add(new ShapeVisualCmp(verticesOfBullet,  textureOrb, Color.YELLOW, 0));
		orb.add(new CircleColliderCmp(1, CircleColliderCmp.CATEGORY_ORB));
		orb.add(new OrbCmp(4f));

		engine.addEntity(orb);

		return orb;
	}

	public static Entity createAsteroid(int size, Vector2 pos, Vector2 heading, int speed, float angle) { // maybe using not static is better
		Entity asteroid1 = new Entity();
		

		
		asteroid1.add(new PoseCmp(pos, angle));
		asteroid1.add(new MovementCmp());
		asteroid1.add(new AsteroidCmp(size, speed, heading.cpy()) );


		switch (size) {
			case 1: // small
				asteroid1.add(new CircleColliderCmp(1, CircleColliderCmp.CATEGORY_ASTEROID));
				asteroid1.add(new ShapeVisualCmp(new float[]{0.0f, 1.0f, -0.75f, -0.5f, 0.0f, -0.75f, 0.5f, -0.5f	}, textureAsteroidSmall, Color.PINK, 2));
				break;
			case 2: // medium
				asteroid1.add(new CircleColliderCmp(2, CircleColliderCmp.CATEGORY_ASTEROID));
				asteroid1.add(new ShapeVisualCmp(new float[]{0.0f, 4.0f, -3f, -3f, 0.0f, -3f, 2.0f, -2.0f	}, textureAsteroidMedium, Color.PINK, 2));
				break;
			case 3: // big
				asteroid1.add(new CircleColliderCmp(3, CircleColliderCmp.CATEGORY_ASTEROID));
				asteroid1.add(new ShapeVisualCmp(new float[]{0.0f, 6.0f, -3.5f, -3.0f, 0.0f, -4.5f, 3.0f, -3.0f	}, textureAsteroidBig, Color.PINK, 2));
				break;



		}
		engine.addEntity(asteroid1);
		
		return asteroid1;
	}
	
	@Override
	public boolean handleMessage(Telegram msg) {
		CollisionMsg data = (CollisionMsg)msg.extraInfo;
		boolean handled = false;


		if (isPowerUp( data.getSecond()) && isShip(data.getFirst() ) ||
				isPowerUp( data.getFirst()) && isShip(data.getSecond() ) ) {

				createOrb();

			lifes++;
			//System.out.println("You have now: " + lifes + " lifes!");
			handled = true;
		}

		if (isAsteroid(data.getFirst() ) && isShip(data.getSecond()) ||
				isAsteroid(data.getSecond()) && isShip(data.getFirst()) ) {
			lifes--;
		//	System.out.println("You have " + lifes + " lifes left!");
			if (lifes <=0) {
				engine.removeEntity(ship);
				//System.out.println("Game over!");
			}
			handled = true;
		}

		if (isAsteroid(data.getFirst() ) && isOrb(data.getSecond()) ) {
			destroyOrb(data.getSecond());
			handled = true;
			lifes--;
			//System.out.println("You have " + lifes + " lifes left!");
			if (lifes <=0) {
				engine.removeEntity(ship);
				//System.out.println("Game over!");
			}
		}


		if (isAsteroid(data.getSecond() ) && isOrb(data.getFirst()) ) {

			destroyOrb(data.getFirst());
			handled = true;
			lifes--;
			//System.out.println("You have " + lifes + " lifes left!");
			if (lifes <=0) {
				engine.removeEntity(ship);
				//System.out.println("Game over!");
			}
		}

		return handled;
	}

	private boolean isPowerUp(Entity entity) {
		return powerUpMapper.has(entity);
	}
	private boolean isAsteroid(Entity entity) {
		return asteroidMapper.has(entity);
	}
	private boolean isShip(Entity entity) {
		return shipMapper.has(entity);
	}
	private boolean isOrb(Entity entity) {
		return orbMapper.has(entity);
	}

	private void destroyOrb(Entity entity) {

		engine.removeEntity(entity);

	}
}
