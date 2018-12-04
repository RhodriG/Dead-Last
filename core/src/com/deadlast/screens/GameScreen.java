package com.deadlast.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.deadlast.entities.Enemy;
import com.deadlast.entities.EnemyFactory;
import com.deadlast.entities.EnemyType;
import com.deadlast.entities.Player;
import com.deadlast.entities.PlayerType;
import com.deadlast.game.DeadLast;
import com.deadlast.game.GameManager;
import com.deadlast.world.BodyFactory;

/**
 * The screen responsible for displaying the game world and relevant elements
 * @author Xzytl
 *
 */
public class GameScreen extends DefaultScreen {
	
	/**
	 * The camera that the world is shown through
	 */
	private OrthographicCamera camera;
	private ExtendViewport gamePort;
	/**
	 * The SpriteBatch which renders game sprites
	 */
	private SpriteBatch batch;
	/**
	 * The controllable player character
	 */
	private Player player;
	
	private EnemyFactory enemyFactory;
	
	private GameManager gameManager = GameManager.getInstance(this.game);

	public GameScreen(DeadLast game) {
		super(game);
		System.out.println("Loaded GameScreen");
		
		camera = new OrthographicCamera(Gdx.graphics.getWidth() / DeadLast.PPM, Gdx.graphics.getHeight() / DeadLast.PPM);
		gamePort = new ExtendViewport(DeadLast.V_WIDTH / DeadLast.PPM, DeadLast.V_HEIGHT / DeadLast.PPM, camera);
		
		camera.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
		
		batch = new SpriteBatch();
		
		gameManager.setGameCamera(camera);
		gameManager.setSpriteBatch(batch);
		
		
		/**
		 * The below code is being maintained for the time being, but it should eventually be moved
		 */
		enemyFactory = EnemyFactory.getInstance(game);
		BodyFactory bodyFactory = BodyFactory.getInstance(gameManager.getWorld());
//		bodyFactory.makeCirclePolyBody(2, 2, 1, BodyFactory.STEEL, BodyType.DynamicBody, false);
		bodyFactory.makeBoxPolyBody(10, 10, 10, 2, BodyFactory.STEEL, BodyType.StaticBody, true);
		
		
		PlayerType playerType = PlayerType.STEALTH;
		player = new Player.Builder()
				.setGame(game)
				.setSprite(new Sprite(new Texture(Gdx.files.internal("entities/player.png"))))
				.setBodyRadius(playerType.getBodyRadius())
				.setInitialPosition(new Vector2(0,0))
				.setHealthStat(playerType.getHealth())
				.setSpeedStat(playerType.getSpeed())
				.setStealthStat(playerType.getStealth())
				.setStrengthStat(playerType.getStealth())
				.build();
		player.defineBody();
		
		gameManager.setPlayer(player);
		Enemy enemy1 = new Enemy.Builder()
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
		enemy1.defineBody();
		Enemy enemy2 = enemyFactory.get(EnemyType.HEAVY).setInitialPosition(new Vector2(4, 0)).build();
		enemy2.defineBody();
		Enemy enemy3 = enemyFactory.get(EnemyType.FAST).setInitialPosition(new Vector2(7, 7)).build();
		enemy3.defineBody();
		gameManager.addEnemies(enemy1, enemy2, enemy3);
//		enemies.add(enemy1);
//		
//		enemies.add(enemy2);
//		
//		enemies.forEach(enemy -> enemy.defineBody());
		
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(gameManager.getController());
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		gameManager.update(delta);
		gameManager.render();
		
//		world.step(Gdx.graphics.getDeltaTime(), 6, 2);
//		camera.position.x = player.getBody().getPosition().x;
//		camera.position.y = player.getBody().getPosition().y;
//		camera.update();
//		
//		enemies.forEach(enemy -> enemy.update(player.getBody()));
		
//		if (showDebugRenderer) {
//			debugRenderer.render(world, camera.combined);
//		}
//		batch.setProjectionMatrix(camera.combined);
//		batch.begin();
//		player.render(batch,camera);
//		enemies.forEach(enemy -> enemy.render(batch));
//		batch.end();
	}
	
//	public void handleInput(float delta) {
//		if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
//			showDebugRenderer = !showDebugRenderer;
//		}
//		
//		float speed;
//		
//		if (controller.isShiftDown) {
//			speed = player.getSpeed() * 2.5f;
//		} else {
//			speed = player.getSpeed();
//		}
//		
//		if (controller.left) {
//			//player.applyForceToCenter(-10, 0, true);
//			player.getBody().setLinearVelocity(-1 * speed, player.getBody().getLinearVelocity().y);
//		}
//		if (controller.right) {
//			//player.applyForceToCenter(10, 0, true);
//			player.getBody().setLinearVelocity(speed, player.getBody().getLinearVelocity().y);
//		}
//		if (controller.up) {
//			//player.applyForceToCenter(0, 10, true);
//			player.getBody().setLinearVelocity(player.getBody().getLinearVelocity().x, speed);
//		}
//		if (controller.down) {
//			//player.applyForceToCenter(0, -10, true);
//			player.getBody().setLinearVelocity(player.getBody().getLinearVelocity().x, -1 * speed);
//		}
//		
//		if ((!controller.up && !controller.down) || (controller.up && controller.down)) {
//			player.getBody().setLinearVelocity(player.getBody().getLinearVelocity().x, 0);
//		}
//		if ((!controller.left && !controller.right) || (controller.left && controller.right )) {
//			player.getBody().setLinearVelocity(0, player.getBody().getLinearVelocity().y);
//		}
//		
//	}

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
		gameManager.dispose();
//		debugRenderer.dispose();
//		world.dispose();
	}

}
