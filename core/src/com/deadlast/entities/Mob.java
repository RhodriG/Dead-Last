package com.deadlast.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.deadlast.game.DeadLast;
import com.deadlast.game.GameManager;

/**
 * Represents a moving, 'living' entity
 * @author Xzytl
 *
 */
public class Mob extends Entity {

	/**
	 * Normal maximum health of this mob.
	 */
	private int healthStat;
	/**
	 * Normal maximum speed of this mob.
	 */
	private int speedStat;
	/**
	 * Normal maximum strength of this mob.
	 */
	private int strengthStat;
	
	/**
	 * Current health of this mob.
	 */
	private int currentHealth;
	/**
	 * Current speed of this mob.
	 */
	private int currentSpeed;
	/**
	 * Current strength of this mob.
	 */
	private int currentStrength;
	
	public Mob(
			DeadLast game, int scoreValue, Sprite sprite, float bRadius,
			Vector2 initialPos, int healthStat, int speedStat, int strengthStat
	) {
		super(game, scoreValue, sprite, bRadius, initialPos);
		this.healthStat = healthStat;
		this.speedStat = speedStat;
		this.strengthStat = strengthStat;
		resetStats();
	}
	
	/**
	 * Sets the health of the mob.
	 * @param currentHealth		the health to which the mob should be set
	 */
	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
	}
	
	/**
	 * Set the speed of the mob.
	 * @param currentSpeed		the speed to which the mob should be set
	 */
	public void setCurrentSpeed(int currentSpeed) {
		this.currentSpeed = currentSpeed;
	}
	
	/**
	 * Set the strength of the mob.
	 * @param currentStrength	the strength to which the mob should be set
	 */
	public void setCurrentStrength(int currentStrength) {
		this.currentStrength = currentStrength;
	}
	
	public int getHealth() {
		return currentHealth;
	}
	
	public int getSpeed() {
		return currentSpeed;
	}
	
	public int getStrength() {
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
	public void defineBody() {
		BodyDef bDef = new BodyDef();
		bDef.type = BodyDef.BodyType.DynamicBody;
		bDef.position.set(initialPos);
		
		CircleShape shape = new CircleShape();
		shape.setRadius(this.bRadius);
		
		FixtureDef fDef = new FixtureDef();
		fDef.shape = shape;
		
		b2body = world.createBody(bDef);
		b2body.createFixture(fDef);
		b2body.setUserData(this);

		shape.dispose();
	}
	
	public void update(float delta) {};

}
