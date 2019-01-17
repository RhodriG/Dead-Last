package com.deadlast.game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.deadlast.controllers.KeyboardController;
import com.deadlast.entities.Enemy;
import com.deadlast.entities.EnemyFactory;
import com.deadlast.entities.Entity;
import com.deadlast.entities.Player;
import com.deadlast.entities.PowerUp;
import com.deadlast.screens.GameScreen;
import com.deadlast.stages.Hud;
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
	private ArrayList<Entity> powerUps;
	private EnemyFactory enemyFactory;
	
	private OrthographicCamera gameCamera;
	private SpriteBatch batch;
	
	private Hud hud;
	
	private int totalScore;
	
	private int score;
	private float time;
	
	private GameManager(DeadLast game) {
		System.out.println("Created GameManager instance!");
		this.game = game;
		
		controller = new KeyboardController();
		enemyFactory = EnemyFactory.getInstance(game);
		
		loadLevel();
	}
	
	/**
	 * Fetches the instance of GameManager belonging to the specified game, or creates a new one
	 * if one has not yet been created.
	 * @param game	the game instance to fetch the game manager for
	 * @return		an instance of GameManager attached to the specified game instance
	 */
	public static GameManager getInstance(DeadLast game) {
		if (instance == null) {
			instance = new GameManager(game);
			System.out.println("Created GameManager instance");
		}
		return instance;
	}
	
	/**
	 * Creates parameters required when a new level is loaded.
	 */
	public void loadLevel() {
		System.out.println("Loading level...");
		world = new World(Vector2.Zero, true);
		world.setContactListener(new WorldContactListener());
		debugRenderer = new Box2DDebugRenderer();
		
		hud = new Hud(game);
		
		this.entities = new ArrayList<>();
		this.enemies = new ArrayList<>();
		this.powerUps = new ArrayList<>();
		
		score = 0;
		time = 0;
		
	}
	
	public void clearLevel() {
		world.dispose();
		hud.dispose();
		debugRenderer.dispose();
		totalScore += score;
	}
	
	/**
	 * Sets the {@link OrthographicCamera} used by {@link GameScreen} to display the game world.
	 * Must be set before update() is called.
	 * @param camera	the camera to use.
	 */
	public void setGameCamera(OrthographicCamera camera) {
		this.gameCamera = camera;
	}
	
	/**
	 * Sets the {@link SpriteBatch} used to render the entities stored in this manager.
	 * Must be set before update() or render() are called.
	 * @param batch		the SpriteBatch to use
	 */
	public void setSpriteBatch(SpriteBatch batch) {
		this.batch = batch;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
		this.entities.add(player);
	}
	
	/**
	 * Adds an enemy to the list of enemies and entities.
	 * @param enemy	the enemy to add
	 */
	public void addEnemy(Enemy enemy) {
		this.enemies.add(enemy);
		this.entities.add(enemy);
	}
	
	/**
	 * Adds an enemy or multiple enemies to the list of enemies and entities.
	 * @param enemies	the enemies to add
	 */
	public void addEnemies(Enemy... enemies) {
		for (Enemy enemy : enemies) {
			addEnemy(enemy);
		}
	}
	
	/**
	 * Removes an enemy from the list of entities and the list of enemies.
	 * @param enemy	the enemy to remove
	 */
	public void removeEnemy(Enemy enemy) {
		this.enemies.remove(enemy);
		this.entities.remove(enemy);
	}
	
	/**
	 * Adds a power-up to the list of power-up's and entities
	 * @param powerUp the power-up to add
	 */
	public void addPowerUp(PowerUp powerUp) {
		this.powerUps.add(powerUp);
		this.entities.add(powerUp);
	}
	
	/**
	 * Removes a power-up from the list of entities and the list of power-up's
	 * @param powerUp the power-up to remove
	 */
	public void removePowerUp(PowerUp powerUp) {
		this.powerUps.remove(powerUp);
		this.entities.remove(powerUp);
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
	
	public Player getPlayer() {
		return player;
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
		entities.forEach(entity -> entity.update(delta));
		// Fetch and delete dead entities
		List<Entity> deadEntities = entities.stream().filter(e -> (!e.isAlive() && !(e instanceof Player))).collect(Collectors.toList());
		deadEntities.forEach(e -> e.delete());
		deadEntities.forEach(e -> this.score += e.getScoreValue());
		deadEntities.forEach(e -> entities.remove(e));
		
		if (showDebugRenderer) {
			debugRenderer.render(world, gameCamera.combined);
		}
		
		time += delta;
		this.hud.setTime((int)Math.round(Math.floor(time)));
		this.hud.setHealth(this.player.getHealth());
		this.hud.setScore(this.score);
	}
	
	/**
	 * Processes user input from a {@link InputController} (in this case, {@link KeyboardController}).
	 */
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
	
	/**
	 * Renders entities held by this game manager.
	 */
	public void render() {
		if (batch == null) return;
		batch.setProjectionMatrix(gameCamera.combined);
		batch.begin();
		entities.forEach(entity -> entity.render(batch));
		batch.end();
		hud.stage.draw();
	}

	@Override
	public void dispose() {
		world.dispose();
		debugRenderer.dispose();
	}

}
