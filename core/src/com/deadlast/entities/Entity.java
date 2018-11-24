package com.deadlast.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.deadlast.game.DeadLast;

/**
 * Represents a dynamic object in the world.
 * @author Xzytl
 *
 */
public abstract class Entity {
	
	/**
	 * The {@link World} this entity exists in.
	 */
	protected World world;
	/**
	 * The instance of {@link DeadLast} this entity belongs to.
	 */
	protected DeadLast game;
	/**
	 * The radius of this entity's body.
	 */
	protected float bRadius;
	/**
	 * The score modifier when this entity is killed or otherwise interacted with.
	 */
	protected int scoreValue;
	/**
	 * The {@link Body} that represents this entity in the {@link World}.
	 */
	protected Body b2body;
	/**
	 * The sprite that represents this entity in the world
	 */
	protected Sprite sprite;
	
	
	/**
	 * Creates an entity using the default score value and texture and places it at the origin.
	 * @param world		the {@link World} the entity exists in
	 * @param game		the instance of the game the entity belongs to
	 */
	public Entity(World world, DeadLast game) {
		this(world, game, 0, game.resources.manager.get(game.resources.entityImage), 0.5f, new Vector2(0,0));
	}
	
	public Entity(World world, DeadLast game, int scoreValue) {
		this(world, game, scoreValue, game.resources.manager.get(game.resources.entityImage), 0.5f, new Vector2(0,0));
	}
	
	/**
	 * Creates an entity with a score value and a specific sprite.
	 * @param world			the {@link World} the entity exists in
	 * @param game			the instance of the game the entity belongs to
	 * @param scoreValue	the score value given when this entity is interacted with
	 * @param sprite		the {@link Sprite} that represents this entity in the world
	 * @param bRadius		the radius of the circular body that represents this entity
	 * @param initialPos	the position the entity should spawn in the world
	 */
	public Entity(World world, DeadLast game, int scoreValue, Sprite sprite, float bRadius, Vector2 initialPos) {
		this.world = world;
		this.game = game;
		this.scoreValue = scoreValue;
		this.sprite = sprite;
		this.bRadius = bRadius;
		sprite.setSize(bRadius * 2, bRadius * 2);
		sprite.setOrigin(bRadius, bRadius);
		defineBody(initialPos);
	}
	
	/**
	 * Gets the body this entity represents.
	 * @return the {@link Body} this entity represents
	 */
	public Body getBody() {
		return b2body;
	}
	
	/**
	 * Teleports the entity to the specified location. Be aware that it may cause issues with physics
	 * objects.
	 * @param x		The x coordinate of the destination
	 * @param y		The y coordinate of the destination
	 */
	public void moveTo(float x, float y) {
		b2body.setTransform(x, y, b2body.getAngle());
	}
	
	/**
	 * Rotates the entity by a specific number of degrees.
	 * @param angle		the number of degrees to rotate the body by
	 */
	public void setAngle(float angle) {
		b2body.setTransform(b2body.getPosition(), (float)Math.toRadians(angle));
	}
	
	public void setAngle(double angle) {
		setAngle((float)angle);
	}
	
	public void delete() {
		world.destroyBody(this.b2body);
		b2body.setUserData(null);
		b2body = null;
	}
	
	/**
	 * Defines this entity's body that exists in the world.
	 */
	protected abstract void defineBody(Vector2 position);
	
	/**
	 * Draws this entity's sprite in the correct place every render cycle.
	 * @param batch	the {@link SpriteBatch} used to draw the sprite
	 */
	public void render(SpriteBatch batch) {
		float posX = b2body.getPosition().x - bRadius;
		float posY = b2body.getPosition().y - bRadius;
		float rotation = (float) Math.toDegrees(b2body.getAngle());
		sprite.setPosition(posX, posY);
		sprite.setRotation(rotation);
		sprite.draw(batch);
	}

}
