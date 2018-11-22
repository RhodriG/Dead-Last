package com.deadlast.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.deadlast.game.DeadLast;

/**
 * Represents a dynamic object in the world.
 * @author Xzytl
 *
 */
public abstract class Entity extends Sprite {
	
	/**
	 * The {@link World} this entity exists in.
	 */
	protected World world;
	/**
	 * The instance of {@link DeadLast} this entity belongs to.
	 */
	private DeadLast game;
	/**
	 * The score modifier when this entity is killed or otherwise interacted with.
	 */
	int scoreValue;
	/**
	 * The {@link Body} that represents this entity in the {@link World}.
	 */
	protected Body b2body;
	
	public Entity(World world, DeadLast game, int scoreValue, float x, float y) {
		// TODO: potentially load default texture
		super(new Texture(Gdx.files.internal("entities/enemy.png")));
		this.world = world;
		this.game = game;
		this.scoreValue = scoreValue;
		setPosition(x, y);
		defineBody();
	}
	
	/**
	 * Gets the body this entity represents.
	 * @return the {@link Body} this entity represents
	 */
	public Body getBody() {
		return b2body;
	}
	
	public void moveTo(float x, float y) {
		b2body.setTransform(x, y, b2body.getAngle());
	}
	
	public void rotateBody(float angle) {
		float radAngle = (float) (angle * Math.PI / 180);
		b2body.setTransform(b2body.getPosition(), radAngle);
	}
	
//	public void setPosition(Vector2 vector) {
//		setPosition(vector.x, vector.y);
//	}
//	
//	public void setPosition(Vector3 vector) {
//		setPosition(vector.x, vector.y);
//	}
	
	public abstract void delete();
	
	/**
	 * Defines this entity's body that exists in the world.
	 */
	protected abstract void defineBody();
	

}
