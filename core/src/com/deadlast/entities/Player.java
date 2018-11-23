package com.deadlast.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.deadlast.game.DeadLast;

/**
 * Represents the player character.
 * @author Xzytl
 *
 */
public class Player extends Mob {
	
	private float maxStealth;
	
	private boolean isHidden;
	
	//private Sprite sprite = new Sprite(new Texture(Gdx.files.internal("entities/player.png")));
	
	public Player(
			World world, DeadLast game, int scoreValue, Sprite sprite, float bRadius,
			Vector2 initialPos, int healthStat, int speedStat, int strengthStat, int stealthStat
	) {
		super(world, game, scoreValue, sprite, bRadius, initialPos, healthStat, speedStat, strengthStat);
		this.maxStealth = stealthStat;
		this.isHidden = true;
//		sprite.setSize(1.0f, 1.0f);
//		sprite.setOrigin(0.5f, 0.5f);
//		moveTo(0,0);
	}
	
	public void render(SpriteBatch batch) {
		float posX = b2body.getPosition().x;
		float posY = b2body.getPosition().y;
		float rotation = (float) Math.toDegrees(b2body.getAngle());
		sprite.setPosition(posX, posY);
		sprite.setRotation(rotation);
		sprite.draw(batch);
	}

}
