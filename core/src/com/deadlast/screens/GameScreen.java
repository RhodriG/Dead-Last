package com.deadlast.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.deadlast.controller.KeyboardController;
import com.deadlast.entities.Entity;
import com.deadlast.entities.Player;
import com.deadlast.game.DeadLast;
import com.deadlast.world.B2dContactListener;
import com.deadlast.world.B2dModel;
import com.deadlast.world.BodyFactory;

public class GameScreen extends DefaultScreen {
	
	// B2dModel model;
	private OrthographicCamera camera;
	private FitViewport gamePort;
	private Box2DDebugRenderer debugRenderer;
	private KeyboardController controller;
	private World world;
	
	private Texture playerTex;
	private Texture enemyTex;
	private SpriteBatch batch;
	
	private Player player;
	private ArrayList<Entity> enemies;
	private ArrayList<Entity> pickups;

	public GameScreen(DeadLast game) {
		super(game);
		System.out.println("Loaded GameScreen");
		
		camera = new OrthographicCamera(Gdx.graphics.getWidth() / DeadLast.PPM, Gdx.graphics.getHeight() / DeadLast.PPM);
		gamePort = new FitViewport(DeadLast.V_WIDTH, DeadLast.V_HEIGHT, camera);
		
		camera.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
		
		controller = new KeyboardController();
		world = new World(new Vector2(0,0), true);
		// world.setContactListener(new B2dContactListener());
		
		// model = new B2dModel(controller, camera);
		debugRenderer = new Box2DDebugRenderer();
		
		//playerTex = game.resources.manager.get(game.resources.playerImage);
		//enemyTex = game.resources.manager.get(game.resources.enemyImage);
		batch = new SpriteBatch();
		//batch.setProjectionMatrix(camera.combined);
		BodyFactory bodyFactory = BodyFactory.getInstance(world);
		bodyFactory.makeCirclePolyBody(2, 2, 1, BodyFactory.STEEL, BodyType.DynamicBody, false);
		
		player = new Player(world, game, 0, 10, 2, 10);
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
		//batch.setProjectionMatrix(camera.combined);
		batch.begin();
		//batch.draw(playerTex, model.player.getPosition().x - 1, model.player.getPosition().y - 1, 2, 2);
		player.draw(batch);
		batch.end();
	}
	
	public void update(float delta) {
		player.update(delta);
		world.step(1 / 60f, 6, 2);
		camera.position.x = player.getBody().getPosition().x;
		camera.position.y = player.getBody().getPosition().y;
		camera.update();
	}
	
	public void handleInput(float delta) {
		float speed;
		
		if (controller.isShiftDown) {
			speed = player.getSpeed() * 2;
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
		
		if (!controller.up && !controller.down) {
			player.getBody().setLinearVelocity(player.getBody().getLinearVelocity().x, 0);
		}
		if (!controller.left && !controller.right) {
			player.getBody().setLinearVelocity(0, player.getBody().getLinearVelocity().y);
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

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
	}

}
