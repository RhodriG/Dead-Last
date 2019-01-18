package com.deadlast.world;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.deadlast.entities.Entity;
import com.deadlast.entities.PowerUp;
import com.deadlast.entities.Enemy;
import com.deadlast.entities.EnemyFactory;
import com.deadlast.game.DeadLast;
import com.deadlast.game.GameManager;

/**
 * 
 * @author ljd546
 *
 */
public class Level {
	
	private DeadLast game;
	private Vector2 playerSpawn;
	private List<SpawnPoint<Enemy.Type>> enemySpawns;
	private List<SpawnPoint<PowerUp.Type>> powerUpSpawns;
	
	private GameManager gameManager;
	
	public Level(DeadLast game) {
		this.game = game;
		this.gameManager = GameManager.getInstance(game);
		enemySpawns = new ArrayList<>();
		powerUpSpawns = new ArrayList<>();
		parseMap();
	}
	
	public void parseMap() {
		enemySpawns.add(
			new SpawnPoint<Enemy.Type>(Enemy.Type.FAST, new Vector2(4,0))
		);
		enemySpawns.add(
			new SpawnPoint<Enemy.Type>(Enemy.Type.HEAVY, new Vector2(7,7))
		);
		powerUpSpawns.add(
			new SpawnPoint<PowerUp.Type>(PowerUp.Type.DOUBLE_POINTS, new Vector2(-2,-2))
		);
		powerUpSpawns.add(
			new SpawnPoint<PowerUp.Type>(PowerUp.Type.DOUBLE_DAMAGE, new Vector2(2,-2))
		);
		playerSpawn = new Vector2(0,0);
	}
	
	public void load() {
		enemySpawns.forEach(
			es -> gameManager.addEnemy(es.getType(), es.getSpawnLocation())
		);
		powerUpSpawns.forEach(
			ps -> gameManager.addPowerUp(ps.getType(), ps.getSpawnLocation())
		);
		gameManager.setPlayerSpawn(playerSpawn);
	}
	
	static class SpawnPoint<K> {
		final K type;
		final Vector2 spawnLoc;
		
		public SpawnPoint(K type, Vector2 spawnLoc) {
			this.type = type;
			this.spawnLoc = spawnLoc;
		}
		
		public K getType() {
			return type;
		}
		
		public Vector2 getSpawnLocation() {
			return spawnLoc;
		}
	}
	
	
}
