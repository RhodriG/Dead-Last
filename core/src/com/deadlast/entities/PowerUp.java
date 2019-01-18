package com.deadlast.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.deadlast.game.DeadLast;
import com.deadlast.world.FixtureType;

public class PowerUp extends Entity {
	
	private Type type;
	
	public PowerUp(DeadLast game, int scoreValue, Sprite sprite, float bRadius, Vector2 initialPos, Type type) {
		super(game, scoreValue, sprite, bRadius, initialPos);
		this.type = type;
	}

	public enum Type {
		STEALTH,
		DOUBLE_DAMAGE,
		DOUBLE_POINTS,
		REGEN,
		SPEED
	}
	
	public Type getType() {
		return type;
	}

	@Override
	public void defineBody() {
		BodyDef bDef = new BodyDef();
		bDef.type = BodyDef.BodyType.StaticBody;
		bDef.position.set(initialPos);
		
		CircleShape shape = new CircleShape();
		shape.setRadius(this.bRadius);
		
		FixtureDef fDef = new FixtureDef();
		fDef.shape = shape;
		fDef.filter.categoryBits = Entity.POWERUP;
		fDef.filter.maskBits = Entity.PLAYER;
		
		b2body = world.createBody(bDef);
		b2body.createFixture(fDef).setUserData(FixtureType.POWERUP);
		b2body.setUserData(this);

		shape.dispose();
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}

}
