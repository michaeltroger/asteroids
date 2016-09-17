package at.fhoe.mtd.igp;

import at.fhoe.mtd.igp.systems.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import at.fhoe.mtd.igp.components.PowerUpCmp;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import messages.AsteroidDestroyedMsg;

public class MyGame extends ApplicationAdapter implements Telegraph  {
	
	static final int WORLD_WIDTH = 50; /** Game world to be displayed horizontally. */
	static final float BITMAP_SCALE_FACTOR = 1 / 40f;
	static final String backgroundIMG = "stars.jpg";
	static final String explosionAnimation = "particle.party";
	static final int stonesToHitForPowerUp = 7;

	ParticleEffectPool bombEffectPool;
	Array<ParticleEffectPool.PooledEffect> effects = new Array();

	int stonesHit;
	SpriteBatch batcher;
	ParticleEffect particleEffect;
	Engine engine;
	World world;
	OrthographicCamera camera;
	DebugSystem debugSystem;
	boolean debugSystemActive;
	private ImmutableArray<Entity> powerups;
	
	@Override
	public void create () {

		batcher = new SpriteBatch();
		ParticleEffect bombEffect = new ParticleEffect();
		bombEffect.load(Gdx.files.internal(explosionAnimation), Gdx.files.internal(""));

		bombEffectPool = new ParticleEffectPool(bombEffect, 1, 10);


		//particleEffect.setPosition(0,0);
//		particleEffect.start();

	    camera = new OrthographicCamera();
		 
		engine = new Engine();
		world = new World(engine);

		stonesHit = 0;
			
		engine.addSystem(new ShipSystem());
		engine.addSystem(new PhysicsSystem(WORLD_WIDTH)); // initialize with wished size of the world
		engine.addSystem(new BulletSystem());
		engine.addSystem(new AsteroidSystem());
		//engine.addSystem(new VectorRenderSystem(camera));
		engine.addSystem(new BitmapRenderSystem(camera, batcher, BITMAP_SCALE_FACTOR, new Texture(backgroundIMG), WORLD_WIDTH));
		engine.addSystem(new CircleColliderSystem());
		engine.addSystem(new HUDSystem());
		engine.addSystem(new PowerUpSystem());
		engine.addSystem(new OrbSystem());
		
		debugSystem = new DebugSystem(camera);
		debugSystemActive = false;

		world.create();
		
		powerups = engine.getEntitiesFor(Family.getFor(PowerUpCmp.class));

		MessageManager.getInstance().addListener(this, AsteroidDestroyedMsg.MSG_ID);
	}

	@Override
	public void render () {
		GL20 gl = Gdx.gl;
		gl.glClearColor(0, 0, 0, 1);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		camera.position.set(0, 0, 0);
		camera.update();
		batcher.setProjectionMatrix(camera.combined);


		float dt = Gdx.graphics.getDeltaTime();
		engine.update(dt);
		MessageManager.getInstance().update(dt);


		if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
			debugSystemActive = !debugSystemActive;

			if (debugSystemActive) engine.addSystem(debugSystem);
			else engine.removeSystem(debugSystem);
		}


		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			Gdx.app.exit();
		}

		batcher.begin();
		// Update and draw effects:
		for (int i = effects.size - 1; i >= 0; i--) {
			ParticleEffectPool.PooledEffect effect = effects.get(i);
			effect.draw(batcher, dt);
			if (effect.isComplete()) {
				effect.free();
				effects.removeIndex(i);
			}
		}


		batcher.end();

	}

	
	@Override
	public void resize(int width, int height) {	
		// determine aspect ratio of current resolution
		float ar = (float) width / height;
		float calculatedHeight = WORLD_WIDTH / ar;
		
		// Passing true when updating the viewport changes the camera position so it is centered on the stage, making 0,0 the bottom left corner
		
		// always show VIEW_WIDTH of the game world
		// and adapt height correspondingly
		camera.setToOrtho(false, WORLD_WIDTH, calculatedHeight);
		
		engine.getSystem(PhysicsSystem.class).setWorldSize(WORLD_WIDTH, calculatedHeight);
		
		engine.getSystem(HUDSystem.class).getViewport().update( width, height); 		
	}


	@Override
	public boolean handleMessage(Telegram msg) {
		//System.out.println("asteroid destroyed message received");
		AsteroidDestroyedMsg data = (AsteroidDestroyedMsg)msg.extraInfo;
		boolean handled = false;

		int asteroidSize = data.getSize();
		float xPos = data.getX();
		float yPos = data.getY();

		switch(asteroidSize) {

			case 3:
				World.createAsteroid(2, new Vector2(xPos + 5, yPos + 5), new Vector2(0, 1), 20, 3);
				World.createAsteroid(2, new Vector2(xPos + 10, yPos + 10), new Vector2(0, 1), 20, 9);
				World.createAsteroid(2, new Vector2(xPos - 5, yPos - 5), new Vector2(0, 1), 20, 50);
				World.createAsteroid(2, new Vector2(xPos - 10, yPos - 10), new Vector2(0, 1), 20, 23);
				break;

			case 2:
				World.createAsteroid(1, new Vector2(xPos + 5, yPos + 5), new Vector2(0, 1), 20, -45);
				World.createAsteroid(1, new Vector2(xPos - 5, yPos - 5), new Vector2(0, 1), 20, -12);
				break;
			case 1:

				stonesHit++;
				if (stonesHit >= stonesToHitForPowerUp) {
					World.createPowerup(new Vector2(xPos + (int)(Math.random()*10), yPos + (int)(Math.random()*10)), new Vector2(1, 0), (int)(Math.random()*20), (int)(Math.random()*90));

					stonesHit = 0;
				}
				break;

			case 0:

				break;

			default:
		}

		ParticleEffectPool.PooledEffect effect = bombEffectPool.obtain();
		effect.setPosition(xPos, yPos);
		//effect.scaleEffect(0.5f);
		effects.add(effect);

		/*
		particleEffect.start();
		particleEffect.getEmitters().first().setPosition(xPos, yPos);

		//particleEffect.scaleEffect(0.5f);
		batcher.setProjectionMatrix(camera.combined);
		batcher.begin();
		particleEffect.draw(batcher, Gdx.graphics.getDeltaTime());
		batcher.end();
*/


		return handled;
	}
}
