package com.deadlast.world;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.deadlast.controller.KeyboardController;

public class B2dModel {

	public World world;
	private Body player;
	
	private KeyboardController controller;
	
	public B2dModel(KeyboardController controller) {
		this.controller = controller;
		world = new World(new Vector2(0, 0), true);
		world.setContactListener(new B2dContactListener(this));
		
		BodyFactory bodyFactory = BodyFactory.getInstance(world);
		
		player = bodyFactory.makeCirclePolyBody(1, 1, 2, BodyFactory.RUBBER, BodyType.DynamicBody, false);
		bodyFactory.makeCirclePolyBody(4, 4, 1, BodyFactory.STEEL, BodyType.DynamicBody, false);
	}
	
	public void logicStep(float delta) {
		
		if (controller.left) {
			player.applyForceToCenter(-10, 0, true);
		}
		if (controller.right) {
			player.applyForceToCenter(10, 0, true);
		}
		if (controller.up) {
			player.applyForceToCenter(0, 10, true);
		}
		if (controller.down) {
			player.applyForceToCenter(0, -10, true);
		}
		world.step(delta, 3, 3);
	}
	
}
