package com.deadlast.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.deadlast.game.DeadLast;

/**
 * Represents a moving, 'living' entity
 * @author Xzytl
 *
 */
public class Mob extends Entity {

	/**
	 * Normal maximum health of this mob.
	 */
	private float healthStat;
	/**
	 * Normal maximum speed of this mob.
	 */
	private float speedStat;
	/**
	 * Normal maximum strength of this mob.
	 */
	private float strengthStat;
	
	/**
	 * Current health of this mob.
	 */
	private float currentHealth;
	/**
	 * Current speed of this mob.
	 */
	private float currentSpeed;
	/**
	 * Current strength of this mob.
	 */
	private float currentStrength;
	
	public Mob(
			World world, DeadLast game, int scoreValue, Sprite sprite, float bRadius,
			Vector2 initialPos, int healthStat, int speedStat, int strengthStat
	) {
		super(world, game, scoreValue, sprite, bRadius, initialPos);
		this.healthStat = healthStat;
		this.speedStat = speedStat;
		this.strengthStat = strengthStat;
		resetStats();
	}
	
	/**
	 * Sets the health of the mob.
	 * @param currentHealth		the health to which the mob should be set
	 */
	public void setCurrentHealth(float currentHealth) {
		this.currentHealth = currentHealth;
	}
	
	/**
	 * Set the speed of the mob.
	 * @param currentSpeed		the speed to which the mob should be set
	 */
	public void setCurrentSpeed(float currentSpeed) {
		this.currentSpeed = currentSpeed;
	}
	
	/**
	 * Set the strength of the mob.
	 * @param currentStrength	the strength to which the mob should be set
	 */
	public void setCurrentStrength(float currentStrength) {
		this.currentStrength = currentStrength;
	}
	
	public float getHealth() {
		return currentHealth;
	}
	
	public float getSpeed() {
		return currentSpeed;
	}
	
	public float getStrength() {
		return currentStrength;
	}
	
	/**
	 * Resets all mob stats to their default max values.
	 */
	public void resetStats() {
		this.currentHealth = healthStat;
		this.currentSpeed = speedStat;
		this.currentStrength = strengthStat;
	}

	@Override
	protected void defineBody(Vector2 position) {
		BodyDef bDef = new BodyDef();
		bDef.type = BodyDef.BodyType.DynamicBody;
		bDef.position.set(position);
		
		CircleShape shape = new CircleShape();
		shape.setRadius(this.bRadius);
		
		FixtureDef fDef = new FixtureDef();
		fDef.shape = shape;
		
		b2body = world.createBody(bDef);
		b2body.createFixture(fDef);
		b2body.setUserData(this);

		shape.dispose();
	}

}
