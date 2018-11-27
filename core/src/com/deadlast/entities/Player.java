package com.deadlast.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.deadlast.game.DeadLast;
import com.deadlast.world.BodyFactory;

/**
 * Represents the player character.
 * @author Xzytl
 *
 */
public class Player extends Mob {
	
	private int stealthStat;
	
	private boolean isHidden;
	
	private final float DEGTORAD = 0.0174533f;
	
	//private Sprite sprite = new Sprite(new Texture(Gdx.files.internal("entities/player.png")));
	
	public Player(
			World world, DeadLast game, int scoreValue, Sprite sprite, float bRadius,
			Vector2 initialPos, int healthStat, int speedStat, int strengthStat, int stealthStat
	) {
		super(world, game, scoreValue, sprite, bRadius, initialPos, healthStat, speedStat, strengthStat);
		this.stealthStat = stealthStat;
		this.isHidden = true;
	}
	
	public int getStealthStat() {
		return this.stealthStat;
	}
	
	public void render(SpriteBatch batch, OrthographicCamera camera) {
		Vector3 mousePos3D = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
		Vector2 mousePos = new Vector2(mousePos3D.x, mousePos3D.y);
		double angle = Math.toDegrees(Math.atan2(mousePos.y - b2body.getPosition().y, mousePos.x - b2body.getPosition().x));
		this.setAngle(angle - 90);
		render(batch);
	}
	
	@Override
	public void render(SpriteBatch batch) {
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

}
