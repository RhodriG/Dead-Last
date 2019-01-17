package com.deadlast.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.deadlast.game.DeadLast;

/* 
 * Does Nothing
 * @author RhodriG
 * 
 */

public class PowerUpFactory {
	
	private static PowerUpFactory instance;
	
	private World world;
	private DeadLast game;
	
	private PowerUpFactory(World world, DeadLast game) {
		this.world = world;
		this.game = game;
	}
	
	public static PowerUpFactory getInstance(World world, DeadLast game) {
		if (instance == null ) {
			instance = new PowerUpFactory(world, game);
		}
		return instance;
	}
	
	public PowerUp.Builder get(PowerUpType type) {
		PowerUp.Builder builder = new PowerUp.Builder().setWorld(world).setGame(game);
		switch(type) {
		case SNEAK:
			builder.setStealthMultiplier(2)
				.setDamageMultiplier(1)
				.setPointsMultiplier(1)
				.setSprite(null)
				.setBodyRadius(1f);
			break;
		case DOUBLE_DAMAGE:
			builder.setStealthMultiplier(1)
			.setDamageMultiplier(2)
			.setPointsMultiplier(1)
			.setSprite(null)
			.setBodyRadius(1f);
			break;
		case DOUBLE_POINTS:
			builder.setStealthMultiplier(1)
			.setDamageMultiplier(1)
			.setPointsMultiplier(2)
			.setSprite(null)
			.setBodyRadius(1f);
			break;
		}
		return builder;
	}

}
