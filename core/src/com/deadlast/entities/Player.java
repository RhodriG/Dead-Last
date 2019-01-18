package com.deadlast.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.deadlast.game.DeadLast;
import com.deadlast.game.GameManager;

/**
 * Represents the player character.
 * @author Xzytl
 *
 */
public class Player extends Mob {
	
	private int stealthStat;
	
	private boolean isHidden;
	
	private Map<PowerUp.Type, Float> activePowerUps;
	
	//private Sprite sprite = new Sprite(new Texture(Gdx.files.internal("entities/player.png")));
	
	public Player(
			DeadLast game, Sprite sprite, float bRadius,
			Vector2 initialPos, int healthStat, int speedStat, int strengthStat, int stealthStat
	) {
		super(game, 0, sprite, bRadius, initialPos, healthStat, speedStat, strengthStat);
		this.stealthStat = stealthStat;
		this.isHidden = true;
		this.activePowerUps = new HashMap<>();
	}
	
	public int getStealthStat() {
		return this.stealthStat;
	}
	
	@Override
	public void render(SpriteBatch batch) {
		Vector2 mousePos = GameManager.getInstance(this.game).getMouseWorldPos();
		double angle = Math.toDegrees(Math.atan2(mousePos.y - b2body.getPosition().y, mousePos.x - b2body.getPosition().x));
		this.setAngle(angle - 90);
		super.render(batch);
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
	
	public void onPickup(PowerUp powerUp) {
		System.out.println("Picked up power-up: " + powerUp.getType());
		activePowerUps.put(powerUp.getType(), 15f);
	}
	
	public boolean isPowerUpActive(PowerUp.Type type) {
		return activePowerUps.containsKey(type);
	}
	
	@Override
	public void update(float delta) {
		for(Map.Entry<PowerUp.Type, Float> entry : activePowerUps.entrySet()) {
			if (entry.getValue() - delta > 0) {
				activePowerUps.put(entry.getKey(), entry.getValue() - delta);
			} else {
				activePowerUps.remove(entry.getKey());
				System.out.println(entry.getKey() + " expired.");
			}
		}
	}
	
	public static class Builder {
		
		private DeadLast game;
		private Sprite sprite;
		private float bRadius;
		private Vector2 initialPos;
		private int healthStat;
		private int speedStat;
		private int strengthStat;
		private int stealthStat;

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
		
		public Builder setStealthStat(int stealthStat) {
			this.stealthStat = stealthStat;
			return this;
		}
		
		public Player build() {
			return new Player(
				game, sprite, bRadius, initialPos, healthStat, speedStat, strengthStat, stealthStat
			);
		}
		
	}

}
