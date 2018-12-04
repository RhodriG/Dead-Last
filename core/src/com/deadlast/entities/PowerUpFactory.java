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
	
	private static PowerUpFactory getInstance(World world, DeadLast game) {
		if (instance == null ) {
			instance = new PowerUpFactory(world, game);
		}
		return instance;
	}
	
	public PowerUp.Builder get(PowerUpType type) {
		PowerUp.builder builder = new PowerUp.Builder().setWorld(world).setGame(game);
		switch(type) {
		case SNEAK:
			break;
		case INSTA_KILL:
			break;
		case DOUBLE_POINTS:
			break;
		}
	}

}
