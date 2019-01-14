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
 * @author RhodriG
 *
 */
public class StaticEntity extends Entity {
	
	/**
	 * Stealth stat multiplier of power up
	 */
	private float stealthMultiplier;
	
	/**
	 * Damage stat multiplier of power up
	 */
	private float damageMultiplier;
	
	/**
	 * Points muliplier of power up
	 */
	private float pointsMultiplier;
	
	
	public StaticEntity(
			World world, DeadLast game, int scoreValue, Sprite sprite, float bRadius,
			Vector2 initialPos, float stealthMultiplier, float damageMultiplier, float pointsMultiplier
	) {
		super(world, game, scoreValue, sprite, bRadius, initialPos);
		this.stealthMultiplier = stealthMultiplier;
		this.damageMultiplier = damageMultiplier;
		this.pointsMultiplier = pointsMultiplier;
	}
	
	public float getStealthMultiplier() {
		return stealthMultiplier;
	}
	
	public float getDamageMultiplier() {
		return damageMultiplier;
	}
	
	public float getPointsMultiplier() {
		return pointsMultiplier;
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

}
