package com.deadlast.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.deadlast.controller.KeyboardController;
import com.deadlast.entities.Enemy;
import com.deadlast.entities.EnemyFactory;
import com.deadlast.entities.Entity;
import com.deadlast.entities.Player;
import com.deadlast.world.WorldContactListener;

public class GameManager implements Disposable {
	
	private static GameManager instance;
	private final DeadLast game;
	
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private boolean showDebugRenderer = false;
	
	private KeyboardController controller;
	
	private Player player;
	private ArrayList<Entity> entities;
	private ArrayList<Enemy> enemies;
	private ArrayList<Entity> pickups;
	private EnemyFactory enemyFactory;
	
	private OrthographicCamera gameCamera;
	private OrthographicCamera hudCamera;
	private SpriteBatch batch;
	
	private int score;
	private int time;
	
	private GameManager(DeadLast game) {
		this.game = game;
		world = new World(Vector2.Zero, true);
		world.setContactListener(new WorldContactListener());
		debugRenderer = new Box2DDebugRenderer();
		controller = new KeyboardController();
		
		enemyFactory = EnemyFactory.getInstance(game);
		
		this.entities = new ArrayList<>();
		this.enemies = new ArrayList<>();
		this.pickups = new ArrayList<>();
	}
	
	public static GameManager getInstance(DeadLast game) {
		if (instance == null) {
			instance = new GameManager(game);
		}
		return instance;
	}
	
	public void setGameCamera(OrthographicCamera camera) {
		this.gameCamera = camera;
	}
	
	public void setHudCamera(OrthographicCamera camera) {
		this.hudCamera = camera;
	}
	
	public void setSpriteBatch(SpriteBatch batch) {
		this.batch = batch;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
		this.entities.add(player);
	}
	
	public void addEnemy(Enemy enemy) {
		this.enemies.add(enemy);
		this.entities.add(enemy);
	}
	
	public void addEnemies(Enemy... enemies) {
		for (Enemy enemy : enemies) {
			addEnemy(enemy);
		}
	}
	
	public void removeEnemy(Enemy enemy) {
		this.enemies.remove(enemy);
		this.entities.remove(enemy);
	}
	
	public World getWorld() {
		return world;
	}
	
	public KeyboardController getController() {
		return controller;
	}
	
	/**
	 * Gets the mouse position in screen coordinates (origin top-left).
	 * @return	a {@link Vector2} representing the mouse's position on the screen
	 */
	public Vector2 getMouseScreenPos() {
		return new Vector2(Gdx.input.getX(), Gdx.input.getY());
	}
	
	/**
	 * Gets the mouse position in world coordinates (origin center).
	 * @return	a {@link Vector2} representing the mouse's position in the world
	 */
	public Vector2 getMouseWorldPos() {
		Vector3 mousePos3D = gameCamera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
		Vector2 mousePos = new Vector2(mousePos3D.x, mousePos3D.y);
		return mousePos;
	}
	
	public Vector2 getPlayerPos() {
		return player.getBody().getPosition();
	}
	
	public void update(float delta) {
		if(gameCamera == null || batch == null) return;
		handleInput();
		// Step through the physics world simulation
		world.step(1/60f, 6, 2);
		// Centre the camera on the player character
		gameCamera.position.x = player.getBody().getPosition().x;
		gameCamera.position.y = player.getBody().getPosition().y;
		gameCamera.update();
		entities.forEach(Entity::update);
		
		if (showDebugRenderer) {
			debugRenderer.render(world, gameCamera.combined);
		}
	}
	
	public void handleInput() {
		if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
			showDebugRenderer = !showDebugRenderer;
		}
		
		float speed;
		
		if (controller.isShiftDown) {
			speed = player.getSpeed() * 2.5f;
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
	
	public void render() {
		batch.setProjectionMatrix(gameCamera.combined);
		batch.begin();
		entities.forEach(entity -> entity.render(batch));
		batch.end();
	}

	@Override
	public void dispose() {
		world.dispose();
		debugRenderer.dispose();
	}

}
