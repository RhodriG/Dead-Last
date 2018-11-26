package com.deadlast.world;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.deadlast.entities.Entity;
import com.deadlast.entities.Player;

/**
 * Handles contact interactions with world bodies.
 * @author Xzytl
 *
 */
public class WorldContactListener implements ContactListener {
	
	@Override
	public void beginContact(Contact contact) {
		System.out.println("Contact began!");
		Fixture fA = contact.getFixtureA();
		Fixture fB = contact.getFixtureB();
		if (fA.getUserData() != null && fA.getBody().getUserData() instanceof Entity) {
			// Alert entity to contact
		}
		if (fB.getUserData() != null && fB.getBody().getUserData() instanceof Entity) {
			// Alert entity to contact
		}
		if (
			(fA.isSensor() && fB.getBody().getUserData() instanceof Player) ||
			(fB.isSensor() && fA.getBody().getUserData() instanceof Player)
		) {
			System.out.println("Player has entered detection range of enemy.");
		}
	}

	@Override
	public void endContact(Contact contact) {
		System.out.println("Contact ended!");
		Fixture fA = contact.getFixtureA();
		Fixture fB = contact.getFixtureB();
		if (
			(fA.isSensor() && fB.getBody().getUserData() instanceof Player) ||
			(fB.isSensor() && fA.getBody().getUserData() instanceof Player)
		) {
			System.out.println("Player has left detection range of enemy.");
		}
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
