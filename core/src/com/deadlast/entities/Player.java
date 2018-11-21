package com.deadlast.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.deadlast.game.DeadLast;

public class Player extends Entity implements Moveable {

	private float healthStat;
	private float speedStat;
	private float strengthStat;
	
	private float currentHealth;
	private float currentSpeed;
	private float currentStrength;
	
	public Player(World world, DeadLast game, int scoreValue, int healthStat, int speedStat, int strengthStat) {
		super(world, game, scoreValue);
		this.healthStat = healthStat;
		this.speedStat = speedStat;
		this.strengthStat = strengthStat;
		setTexture(game.resources.manager.get(game.resources.playerImage));
		resetStats();
		defineBody();
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
	
	public void resetStats() {
		this.currentHealth = healthStat;
		this.currentSpeed = speedStat;
		this.currentStrength = strengthStat;
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void defineBody() {
		BodyDef bDef = new BodyDef();
		bDef.type = BodyDef.BodyType.DynamicBody;
		bDef.position.set(0,0);
		
		CircleShape shape = new CircleShape();
		shape.setRadius(0.5f);
		
		FixtureDef fDef = new FixtureDef();
		fDef.shape = shape;
		
		b2body = world.createBody(bDef);
		b2body.createFixture(fDef).setUserData(this);
		//b2body.setLinearDamping(3.0f);
		
		shape.dispose();
	}

}
