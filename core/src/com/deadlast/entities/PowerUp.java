package com.deadlast.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.deadlast.game.DeadLast;

/**
 * Represents a static entity
 * @author RhodriGale
 *
 */
public class PowerUp extends Entity {
	
	private float stealthMultiplier;
	/**
	 * Stealth stat multiplier of power up
	 */
	private float damageMultiplier;
	/**
	 * Damage stat multiplier of power up
	 */
	private float pointsMultiplier;
	/**
	 * Points muliplier of power up
	 */
	
	public PowerUp(
			World world, DeadLast game, Sprite sprite, float bRadius, Vector2 initialPos,
			int stealthMultiplier, int damageMultiplier, int pointsMultiplier
	) {
		super(world, game, sprite, bRadius, initialPos);
		this.stealthMultiplier = stealthMultiplier;
		this.damageMultiplier = damageMultiplier;
		this.pointsMultiplier = pointsMultiplier;
	}
	
	public void defineBody() {
		
	}
	

}
