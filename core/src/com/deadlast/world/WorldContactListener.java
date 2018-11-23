package com.deadlast.world;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * Handles contact interactions with world bodies.
 * @author Xzytl
 *
 */
public class WorldContactListener implements ContactListener {

	private B2dModel parent;
	
	public WorldContactListener(B2dModel parent) {
		this.parent = parent;
	}
	
	@Override
	public void beginContact(Contact contact) {
		System.out.print("Contact: ");
		Fixture fA = contact.getFixtureA();
		Fixture fB = contact.getFixtureB();
		System.out.println(fA.getBody().getType() + " has hit " + fB.getBody().getType());
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
