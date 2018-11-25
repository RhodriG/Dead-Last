package com.deadlast.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.deadlast.game.DeadLast;

/**
 * A hostile mob that will attempt to damage the player.
 * @author Xzytl
 *
 */
public class Enemy extends Mob {
	
	private int detectionStat;

	public Enemy(World world, DeadLast game, int scoreValue, Sprite sprite, float bRadius, Vector2 initialPos,
			int healthStat, int speedStat, int strengthStat, int detectionStat) {
		super(world, game, scoreValue, sprite, bRadius, initialPos, healthStat, speedStat, strengthStat);
		this.detectionStat = detectionStat;
	}
	
	public int getDetectionStat() {
		return this.detectionStat;
	}
	
	@Override
	public void defineBody() {
		BodyDef bDef = new BodyDef();
		bDef.type = BodyDef.BodyType.DynamicBody;
		bDef.position.set(initialPos);
		
		// The physical body of the enemy
		FixtureDef fBodyDef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(this.bRadius);
		fBodyDef.shape = shape;
		
		// The 'hearing' radius of the enemy
		FixtureDef fDetectionDef = new FixtureDef();
		CircleShape detectionShape = new CircleShape();
		detectionShape.setRadius(this.bRadius + ((0.1f * (float)this.detectionStat)) + 0.5f);
		fDetectionDef.shape = detectionShape;
		fDetectionDef.isSensor = true;
		
		// Create body and add fixtures
		b2body = world.createBody(bDef);
		b2body.createFixture(fBodyDef);
		b2body.createFixture(fDetectionDef);
		b2body.setUserData(this);

		shape.dispose();
		detectionShape.dispose();
		
		b2body.setLinearDamping(5.0f);
	}
	
	/**
	 * Utility for building Enemy instances.
	 * @author Xzytl
	 *
	 */
	public static class Builder {
		
		private World world;
		private DeadLast game;
		private int scoreValue;
		private Sprite sprite;
		private float bRadius;
		private Vector2 initialPos;
		private int healthStat;
		private int speedStat;
		private int strengthStat;
		private int detectionStat;
		
		public Builder setWorld(World world) {
			this.world = world;
			return this;
		}
		
		public Builder setGame(DeadLast game) {
			this.game = game;
			return this;
		}
		
		public Builder setScoreValue(int scoreValue) {
			this.scoreValue = scoreValue;
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
		
		public Builder setHealthStat(int healthStat) {
			this.healthStat = healthStat;
			return this;
		}
		
		public Builder setSpeedStat(int speedStat) {
			this.speedStat = speedStat;
			return this;
		}
		
		public Builder setStrengthStat(int strengthStat) {
			this.strengthStat = strengthStat;
			return this;
		}
		
		public Builder setDetectionStat(int detectionStat) {
			this.detectionStat = detectionStat;
			return this;
		}
		
		/**
		 * Converts builder object into instance of Enemy
		 * @return an instance of Enemy with the provided parameters
		 * @throws IllegalArgumentException if required parameters are not provided
		 */
		public Enemy build() {
			// Ensure variables are not undefined
			// Note that primitive's are initialised as zero by default
			if (world == null) {
				throw new IllegalArgumentException("Invalid 'world' parameter");
			}
			if (game == null) {
				throw new IllegalArgumentException("Invalid 'game' parameter");
			}
			if (sprite == null) {
				sprite = new Sprite(new Texture(Gdx.files.internal("entities/enemy.png")));
			}
			if (bRadius == 0) {
				bRadius = 0.5f;
			}
			if (initialPos == null) {
				throw new IllegalArgumentException("Invalid 'initialPos' parameter");
			}
			return new Enemy(
					world, game, scoreValue, sprite, bRadius, initialPos, healthStat, speedStat,
					strengthStat, detectionStat
			);
		}
	}

}
