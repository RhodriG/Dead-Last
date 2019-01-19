package com.deadlast.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.deadlast.game.DeadLast;
import com.deadlast.game.GameManager;
import com.deadlast.world.BodyFactory;
import com.deadlast.world.FixtureType;

/**
 * Represents the player character.
 * @author Xzytl
 *
 */
public class Player extends Mob {
	
	private int stealthStat;
	
	private boolean isHidden;
	
	private boolean isAttacking;
	
	private Map<PowerUp.Type, Float> activePowerUps;
	
	private float healCooldown = 1f;
	
	private float attackCooldown = 2f;
	
	private Set<Enemy> enemiesInRange;
	
	//private Sprite sprite = new Sprite(new Texture(Gdx.files.internal("entities/player.png")));
	
	public Player(
			DeadLast game, Sprite sprite, float bRadius,
			Vector2 initialPos, int healthStat, int speedStat, int strengthStat, int stealthStat
	) {
		super(game, 0, sprite, bRadius, initialPos, healthStat, speedStat, strengthStat);
		this.stealthStat = stealthStat;
		this.isHidden = true;
		this.activePowerUps = new HashMap<>();
		this.enemiesInRange = new HashSet<>();
	}
	
	public int getStealthStat() {
		return this.stealthStat;
	}
	
	public boolean isAttacking() {
		return isAttacking;
	}
	
	public void isAttacking(boolean bool) {
		this.isAttacking = bool;
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
		fDef.filter.categoryBits = Entity.PLAYER;
		fDef.filter.maskBits = Entity.BOUNDARY | Entity.ENEMY | Entity.POWERUP | Entity.ENEMY_HEARING | Entity.ENEMY_VISION;
		
		b2body = world.createBody(bDef);
		b2body.createFixture(fDef).setUserData(FixtureType.PLAYER);
		
		BodyFactory bFactory = BodyFactory.getInstance(world);
		bFactory.makeMeleeSensor(b2body, 7, 50, 1f);
		
		b2body.setUserData(this);
		b2body.setSleepingAllowed(false);

		shape.dispose();
	}
	
	public void onPickup(PowerUp powerUp) {
		System.out.println("Picked up power-up: " + powerUp.getType());
		activePowerUps.put(powerUp.getType(), 15f);
	}
	
	public void onMeleeRangeEntered(Enemy enemy) {
		System.out.println("Enemy entered melee range");
		this.enemiesInRange.add(enemy);
	}
	
	public void onMeleeRangeLeft(Enemy enemy) {
		System.out.println("Enemy left melee range");
		this.enemiesInRange.remove(enemy);
	}
	
	public boolean isPowerUpActive(PowerUp.Type type) {
		return activePowerUps.containsKey(type);
	}
	
	@Override
	public void update(float delta) {
		if (isPowerUpActive(PowerUp.Type.REGEN)) {
			if (healCooldown <= 0 && this.getHealth() < this.getMaxHealth()) {
				this.setHealth(this.getHealth() + 1);
				healCooldown = 1f;
			} else {
				healCooldown -= delta;
			}
		}
		for(Map.Entry<PowerUp.Type, Float> entry : activePowerUps.entrySet()) {
			if (entry.getValue() - delta >= 0) {
				activePowerUps.put(entry.getKey(), entry.getValue() - delta);
			} else {
				activePowerUps.remove(entry.getKey());
				System.out.println(entry.getKey() + " expired.");
			}
		}
		if (isAttacking) {
			if (attackCooldown - delta <= 0) {
				enemiesInRange.forEach(e -> e.applyDamage(this.getStrength()));
				attackCooldown = 2f;
			} else {
				attackCooldown -= delta;
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
