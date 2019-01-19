package com.deadlast.game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
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
import com.deadlast.entities.PlayerType;
import com.deadlast.entities.PowerUp;
import com.deadlast.screens.GameScreen;
import com.deadlast.stages.Hud;
import com.deadlast.world.Level;
import com.deadlast.world.WorldContactListener;

/**
 * 
 * @author Xzytl
 *
 */
public class GameManager implements Disposable {
	
	private static GameManager instance;
	private final DeadLast game;
	
	private boolean gameRunning = false;
	
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private boolean showDebugRenderer = false;
	
	private KeyboardController controller;
	
	private PlayerType playerType;
	private Player player;
	private Vector2 playerSpawn;
	private ArrayList<Entity> entities;
	private ArrayList<Enemy> enemies;
	private ArrayList<Entity> powerUps;
	private EnemyFactory enemyFactory;
	
	private OrthographicCamera gameCamera;
	private SpriteBatch batch;
	
	private Hud hud;
	
	private TiledMapRenderer tiledMapRenderer;
	
	private int totalScore;
	
	private int score;
	private float time;
	
	private GameManager(DeadLast game) {
		System.out.println("Created GameManager instance!");
		this.game = game;
		
		controller = new KeyboardController();
		enemyFactory = EnemyFactory.getInstance(game);
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
		}
		return instance;
	}
	
	public void setGameRunning(boolean running) {
		this.gameRunning = running;
	}
	
	public boolean isGameRunning() {
		return gameRunning;
	}
	
	/**
	 * Creates/refreshes parameters required when a new level is loaded.
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
		
		Level level = new Level(game);
		tiledMapRenderer = new OrthogonalTiledMapRenderer(level.load());
		
		player = new Player.Builder()
				.setGame(game)
				.setSprite(new Sprite(new Texture(Gdx.files.internal("entities/player.png"))))
				.setBodyRadius(playerType.getBodyRadius())
				.setInitialPosition(playerSpawn)
				.setHealthStat(playerType.getHealth())
				.setSpeedStat(playerType.getSpeed())
				.setStealthStat(playerType.getStealth())
				.setStrengthStat(playerType.getStrength())
				.build();
		player.defineBody();
		entities.add(player);
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
	
	public Player getPlayer() {
		return player;
	}
	
	public void setPlayerType(PlayerType type) {
		this.playerType = type;
	}
	
	public PlayerType getPlayerType() {
		return playerType;
	}
	
	public void setPlayerSpawn(Vector2 playerSpawn) {
		this.playerSpawn = playerSpawn;
	}
	
	/**
	 * Adds an enemy to the list of enemies and entities.
	 * @param enemy	the enemy to add
	 */
	public void addEnemy(Enemy.Type type, Vector2 initialPos) {
		Enemy enemy = enemyFactory.get(type).setInitialPosition(initialPos).build();
		enemy.defineBody();
		this.enemies.add(enemy);
		this.entities.add(enemy);
	}
	
	/**
	 * Adds an enemy or multiple enemies to the list of enemies and entities.
	 * @param enemies	the enemies to add
	 */
//	public void addEnemies(Enemy... enemies) {
//		for (Enemy enemy : enemies) {
//			addEnemy(enemy);
//		}
//	}
	
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
	public void addPowerUp(PowerUp.Type type, Vector2 initialPos) {
		PowerUp powerUp = new PowerUp(game, 10, new Sprite(new Texture(Gdx.files.internal("entities/player.png"))), 0.25f, initialPos, type);
		powerUp.defineBody();
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
	
	public int getScoreMultiplier() {
		if (player != null && player.isPowerUpActive(PowerUp.Type.DOUBLE_POINTS)) {
			return 2;
		}
		return 1;
	}
	
	public float getSpeedMultiplier() {
		if (player != null && player.isPowerUpActive(PowerUp.Type.SPEED)) {
			return 1.5f;
		}
		return 1f;
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
		deadEntities.forEach(e -> this.score += (e.getScoreValue() * getScoreMultiplier()));
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
			speed = player.getSpeed() * 0.5f;
		} else {
			speed = player.getSpeed();
		}
		
		speed *= getSpeedMultiplier();
		
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
		if (controller.isSpaceDown) {
			
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
		/*
		 * First param: projection matrix
		 * 2nd and 3rd: x and y coords for the bottom left corner of the map.
		 * 4th and 5th: x and y dimension of a portion of the map. (eg. input of 3f,3f will render 3x3 box relative to the map)
		 */
		tiledMapRenderer.setView(gameCamera.combined, 0f,0f,200f,200f);
		tiledMapRenderer.render();
	}

	@Override
	public void dispose() {
		world.dispose();
		debugRenderer.dispose();
	}

}
