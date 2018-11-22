package com.deadlast.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
	
	private Sprite sprite = new Sprite(new Texture(Gdx.files.internal("entities/player.png")));
	
	public Player(World world, DeadLast game, int scoreValue, float x, float y, int healthStat, int speedStat, int strengthStat, int stealthStat) {
		super(world, game, scoreValue, x, y, healthStat, speedStat, strengthStat);
		this.maxStealth = stealthStat;
		this.isHidden = true;
		sprite.setSize(1.0f, 1.0f);
		//sprite.setOrigin(0.5f, 0.5f);
		moveTo(0,0);
	}
	
	public void render(SpriteBatch batch) {
		int posX = (int) (b2body.getPosition().x * DeadLast.PPM);
		int posY = (int) (b2body.getPosition().y * DeadLast.PPM);
		float rotation = (float) Math.toDegrees(b2body.getAngle());
		sprite.setPosition(posX, posY);
		sprite.setRotation(rotation);
		sprite.draw(batch);
	}

}
