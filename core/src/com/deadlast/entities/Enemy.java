package com.deadlast.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.deadlast.game.DeadLast;

public class Enemy extends Mob {

	public Enemy(World world, DeadLast game, int scoreValue, Sprite sprite, float bRadius, Vector2 initialPos,
			int healthStat, int speedStat, int strengthStat) {
		super(world, game, scoreValue, sprite, bRadius, initialPos, healthStat, speedStat, strengthStat);
		// TODO Auto-generated constructor stub
	}

}
