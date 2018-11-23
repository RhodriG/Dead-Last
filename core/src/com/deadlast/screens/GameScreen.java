package com.deadlast.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.deadlast.controller.KeyboardController;
import com.deadlast.entities.Enemy;
import com.deadlast.entities.Entity;
import com.deadlast.entities.Player;
import com.deadlast.game.DeadLast;
import com.deadlast.world.BodyFactory;

/**
 * The screen responsible for displaying
 * @author Xzytl
 *
 */
public class GameScreen extends DefaultScreen {
	
	// B2dModel model;
	private OrthographicCamera camera;
	private ExtendViewport gamePort;
	private Box2DDebugRenderer debugRenderer;
	private KeyboardController controller;
	private World world;

	private SpriteBatch batch;
	
	/**
	 * The controllable player character
	 */
	private Player player;
	/**
	 * The enemies on the current level
	 */
	private ArrayList<Enemy> enemies;
	/**
	 * The pickups/powerups on the current level
	 */
	private ArrayList<Entity> pickups;

	public GameScreen(DeadLast game) {
		super(game);
		System.out.println("Loaded GameScreen");
		
		camera = new OrthographicCamera(Gdx.graphics.getWidth() / DeadLast.PPM, Gdx.graphics.getHeight() / DeadLast.PPM);
		gamePort = new ExtendViewport(DeadLast.V_WIDTH / DeadLast.PPM, DeadLast.V_HEIGHT / DeadLast.PPM, camera);
		
		camera.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
		
		controller = new KeyboardController();
		world = new World(new Vector2(0,0), true);
		// world.setContactListener(new B2dContactListener());
		
		debugRenderer = new Box2DDebugRenderer();
		
		batch = new SpriteBatch();
		
		enemies = new ArrayList<>();
		pickups = new ArrayList<>();
		
		BodyFactory bodyFactory = BodyFactory.getInstance(world);
		bodyFactory.makeCirclePolyBody(2, 2, 1, BodyFactory.STEEL, BodyType.DynamicBody, false);
		bodyFactory.makeBoxPolyBody(10, 10, 10, 2, BodyFactory.STEEL, BodyType.StaticBody, true);
		
		player = new Player(world, game, 0, new Sprite(new Texture(Gdx.files.internal("entities/player.png"))), 0.5f, new Vector2(0,0), 5, 5, 5, 5);
		Enemy enemy = new Enemy.Builder()
				.setWorld(world)
				.setGame(game)
				.setScoreValue(10)
				.setSprite(new Sprite(new Texture(Gdx.files.internal("entities/enemy.png"))))
				.setBodyRadius(0.5f)
				.setInitialPosition(new Vector2(-4, -4))
				.setHealthStat(5)
				.setSpeedStat(5)
				.setStrengthStat(5)
				.setDetectionStat(5)
				.build();
		enemies.add(enemy);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(controller);
	}

	@Override
	public void render(float delta) {
		handleInput(delta);
		update(delta);
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		debugRenderer.render(world, camera.combined);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		//player.draw(batch);
		player.render(batch);
		enemies.forEach(enemy -> enemy.render(batch));
		batch.end();
	}
	
	public void update(float delta) {
		//player.update(delta);
		world.step(1 / 60f, 6, 2);
		camera.position.x = player.getBody().getPosition().x;
		camera.position.y = player.getBody().getPosition().y;
		camera.update();
	}
	
	public void handleInput(float delta) {
		float speed;
		
		if (controller.isShiftDown) {
			speed = player.getSpeed() * 1.5f;
		} else {
			speed = player.getSpeed();
		}
		
		if (controller.left) {
			//player.applyForceToCenter(-10, 0, true);
			player.getBody().setLinearVelocity(-1 * speed, player.getBody().getLinearVelocity().y);
		}
		if (controller.right) {
			//player.applyForceToCenter(10, 0, true);
			player.getBody().setLinearVelocity(speed, player.getBody().getLinearVelocity().y);
		}
		if (controller.up) {
			//player.applyForceToCenter(0, 10, true);
			player.getBody().setLinearVelocity(player.getBody().getLinearVelocity().x, speed);
		}
		if (controller.down) {
			//player.applyForceToCenter(0, -10, true);
			player.getBody().setLinearVelocity(player.getBody().getLinearVelocity().x, -1 * speed);
		}
		
		if ((!controller.up && !controller.down) || (controller.up && controller.down)) {
			player.getBody().setLinearVelocity(player.getBody().getLinearVelocity().x, 0);
		}
		if ((!controller.left && !controller.right) || (controller.left && controller.right )) {
			player.getBody().setLinearVelocity(0, player.getBody().getLinearVelocity().y);
		}
		
	}

	@Override
	public void resize(int width, int height) {
		gamePort.update(width, height);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		batch.dispose();
		debugRenderer.dispose();
		world.dispose();
	}

}
