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
	 * Maximum health of this mob.
	 */
	private float maxHealth;
	/**
	 * Maximum speed of this mob.
	 */
	private float maxSpeed;
	/**
	 * Maximum strength of this mob.
	 */
	private float maxStrength;
	
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
		this.maxHealth = healthStat;
		this.maxSpeed = speedStat;
		this.maxStrength = strengthStat;
		resetStats();
	}
	
	public void setCurrentHealth(float currentHealth) {
		this.currentHealth = currentHealth;
	}
	
	public void setCurrentSpeed(float currentSpeed) {
		this.currentSpeed = currentSpeed;
	}
	
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
		this.currentHealth = maxHealth;
		this.currentSpeed = maxSpeed;
		this.currentStrength = maxStrength;
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
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
		b2body.createFixture(fDef).setUserData(this);

		shape.dispose();
	}

}
