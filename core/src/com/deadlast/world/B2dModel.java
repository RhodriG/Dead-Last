package com.deadlast.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.deadlast.controller.KeyboardController;

/**
 * 
 * @author Xzytl
 *
 */
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
