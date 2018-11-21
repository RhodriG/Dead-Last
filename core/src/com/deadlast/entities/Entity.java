package com.deadlast.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.deadlast.game.DeadLast;

public abstract class Entity extends Sprite {
	
	// The world the entity exists in
	protected World world;
	// The instance of the game the entity exists in
	private DeadLast game;
	// The score modifier when this entity is picked up/killed
	int scoreValue;
	protected Body b2body;
	
	
	
	public Entity(World world, DeadLast game, int scoreValue) {
		// TODO: potentially load default texture
		super();
		this.world = world;
		this.game = game;
		this.scoreValue = scoreValue;
	}
	
	public Body getBody() {
		return b2body;
	}
	
	public void update(float delta) {
		setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getWidth() / 2);
	}
	
	public abstract void delete();
	
	public abstract void defineBody();
	

}
