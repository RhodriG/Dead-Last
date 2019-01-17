package com.deadlast.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.deadlast.entities.Enemy.Builder;
import com.deadlast.game.DeadLast;
import com.deadlast.world.BodyFactory;

/**
 * Represents a static entity
 * @author RhodriGale
 *
 */
public class PowerUp extends StaticEntity {
	
	public PowerUp(World world, DeadLast game, int scoreValue, Sprite sprite, float bRadius, Vector2 initialPos,
			float stealthMultiplier, float damageMultiplier, float pointsMultiplier) {
		super(world, game, scoreValue, sprite, bRadius, initialPos, stealthMultiplier, damageMultiplier, pointsMultiplier);
	}
	
	@Override
	public void defineBody() {
		BodyDef bDef = new BodyDef();
		bDef.type = BodyDef.BodyType.DynamicBody;
		bDef.position.set(initialPos);
		
		// The physical body of the powerup
		FixtureDef fBodyDef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(this.bRadius);
		fBodyDef.shape = shape;
		
		// Create body and add fixtures
		b2body = world.createBody(bDef);
		b2body.createFixture(fBodyDef);
		
		BodyFactory bFactory = BodyFactory.getInstance(world);
		
		b2body.setUserData(this);

		shape.dispose();
		
		b2body.setLinearDamping(5.0f);
	}
	
	/**
	 * Utility for building PowerUp instances.
	 * @author RhodriG
	 *
	 */
	public static class Builder {
		
		private World world;
		private DeadLast game;
		private Sprite sprite;
		private float bRadius;
		private Vector2 initialPos;
		private float stealthMultiplier;
		private float damageMultiplier;
		private float pointsMultiplier;
		
		public Builder setWorld(World world) {
			this.world = world;
			return this;
		}
		
		public Builder setGame(DeadLast game) {
			this.game = game;
			return this;
		}
		
		public Builder setSprite(Sprite sprite) {
			this.sprite = sprite;
			return this;
		}
		
		public Builder setBodyRadius(float bRadius) {
			this.bRadius = bRadius;
			return this;
		}
		
		public Builder setInitialPosition(Vector2 initialPos) {
			this.initialPos = initialPos;
			return this;
		}
		
		public Builder setStealthMultiplier(float stealthMultiplier) {
			this.stealthMultiplier = stealthMultiplier;
			return this;
		}
		
		public Builder setDamageMultiplier(float damageMultiplier) {
			this.damageMultiplier = damageMultiplier;
			return this;
		}
		
		public Builder setPointsMultiplier(float pointsMultiplier) {
			this.pointsMultiplier = pointsMultiplier;
			return this;
		}
		/**
		 * Converts builder object into instance of PowerUp
		 * @return an instance of PowerUp with the provided parameters
		 * @throws IllegalArgumentException if required parameters are not provided
		 */
		public PowerUp build() {
			// Ensure variables are not undefined
			// Note that primitive's are initialised as zero by default
			if (world == null) {
				throw new IllegalArgumentException("Invalid 'world' parameter");
			}
			if (game == null) {
				throw new IllegalArgumentException("Invalid 'game' parameter");
			}
			if (sprite == null) {
				sprite = new Sprite(new Texture(Gdx.files.internal("entities/powerup.png")));
			}
			if (bRadius == 0) {
				bRadius = 0.5f;
			}
			if (initialPos == null) {
				throw new IllegalArgumentException("Invalid 'initialPos' parameter");
			}
			return new PowerUp(
					world, game, 0, sprite, bRadius, initialPos, stealthMultiplier, damageMultiplier, pointsMultiplier
			);
		}
	}

}
