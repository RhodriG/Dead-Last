package com.deadlast.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.deadlast.game.DeadLast;

/**
 * Factory/utility class for building instances of {@link Enemy}.
 * @author Xzytl
 *
 */
public class EnemyFactory {
	
	private static EnemyFactory instance;
	
	private World world;
	private DeadLast game;
	
	private EnemyFactory(World world, DeadLast game) {
		this.world = world;
		this.game = game;
	}
	
	public static EnemyFactory getInstance(World world, DeadLast game) {
		if (instance == null) {
			instance = new EnemyFactory(world, game);
		}
		return instance;
	}
	
	public Enemy.Builder get(EnemyType type) {
		Enemy.Builder builder = new Enemy.Builder()
				.setWorld(world)
				.setGame(game);
		switch(type) {
		case BOMBER:
			break;
		case FAST:
			builder.setHealthStat(4)
				.setSpeedStat(10)
				.setStrengthStat(5)
				.setDetectionStat(7)
				.setSprite(null)
				.setScoreValue(20)
				.setBodyRadius(0.4f);
			break;
		case HEAVY:
			builder.setHealthStat(10)
				.setSpeedStat(6)
				.setStrengthStat(10)
				.setDetectionStat(9)
				.setSprite(null)
				.setScoreValue(50)
				.setBodyRadius(0.75f);
			break;
		case HORDLING:
			break;
		case JOCKEY:
			break;
		case MR_TICKLE:
			break;
		case NORMAL:
			break;
		default:
			break;
		
		}
		return builder;
	}

}
