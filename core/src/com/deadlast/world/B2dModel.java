package com.deadlast.world;

import java.util.Arrays;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.deadlast.controller.KeyboardController;

public class B2dModel {

	public World world;
	public Body player;
	
	private KeyboardController controller;
	private OrthographicCamera camera;
	
	public B2dModel(KeyboardController controller, OrthographicCamera camera) {
		this.controller = controller;
		this.camera = camera;
		world = new World(new Vector2(0, 0), true);
		world.setContactListener(new B2dContactListener(this));
		
		BodyFactory bodyFactory = BodyFactory.getInstance(world);
		
		player = bodyFactory.makeCirclePolyBody(1, 1, 2, BodyFactory.STONE, BodyType.DynamicBody, false);
		bodyFactory.makeCirclePolyBody(4, 4, 1, BodyFactory.STEEL, BodyType.DynamicBody, false);
	}
	
	public void logicStep(float delta) {
		player.setLinearDamping(5.0f);
		if (controller.left) {
			//player.applyForceToCenter(-10, 0, true);
			player.setLinearVelocity(-1 * 5.0f, player.getLinearVelocity().y);
		}
		if (controller.right) {
			//player.applyForceToCenter(10, 0, true);
			player.setLinearVelocity(5.0f, player.getLinearVelocity().y);
		}
		if (controller.up) {
			//player.applyForceToCenter(0, 10, true);
			player.setLinearVelocity(player.getLinearVelocity().x, 5.0f);
		}
		if (controller.down) {
			//player.applyForceToCenter(0, -10, true);
			player.setLinearVelocity(player.getLinearVelocity().x, -1 * 5.0f);
		}
		
		if (controller.isMouse1Down && pointIntersectsBody(player, controller.mouseLocation)) {
			System.out.println("Player was clicked");
		}
		
		world.step(delta, 3, 3);
	}
	
	public boolean pointIntersectsBody(Body body, Vector2 mouseLocation) {
		Vector3 mousePos = new Vector3(mouseLocation, 0);
		camera.unproject(mousePos);
		if(body.getFixtureList().first().testPoint(mousePos.x, mousePos.y)) {
			return true;
		}
		return false;
	}
	
}
