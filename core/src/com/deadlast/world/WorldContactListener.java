package com.deadlast.world;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.deadlast.entities.Enemy;
import com.deadlast.entities.Entity;
import com.deadlast.entities.Player;
import com.deadlast.entities.PowerUp;

/**
 * Handles contact interactions with world bodies.
 * @author Xzytl
 *
 */
public class WorldContactListener implements ContactListener {
	
	@Override
	public void beginContact(Contact contact) {
		System.out.println("Contact begun!");
		Fixture fA = contact.getFixtureA();
		Fixture fB = contact.getFixtureB();
		
		if (fA.getUserData() != null && fA.getBody().getUserData() instanceof Entity) {
			// Alert entity to contact
		}
		if (fB.getUserData() != null && fB.getBody().getUserData() instanceof Entity) {
			// Alert entity to contact
		}

		
		// Contact between player and enemy
		if (fA.getBody().getUserData() instanceof Enemy && fB.getBody().getUserData() instanceof Player) {
			if (!fA.isSensor() && !fB.isSensor()) {
				((Enemy)fA.getBody().getUserData()).beginContact(fB.getBody());
			}
		}
		if (fB.getBody().getUserData() instanceof Enemy && fA.getBody().getUserData() instanceof Player) {
			if (!fA.isSensor() && !fB.isSensor()) {
				((Enemy)fB.getBody().getUserData()).beginContact(fA.getBody());
			}
		}
		
		// Player entered enemy sensor
		if (fA.isSensor() && fB.getBody().getUserData() instanceof Player) {
			((Enemy)fA.getBody().getUserData()).beginDetection(fB.getBody());
		}
		if (fB.isSensor() && fA.getBody().getUserData() instanceof Player) {
			((Enemy)fB.getBody().getUserData()).beginDetection(fA.getBody());
		}
		
		// Player picks up powerup
		if (fA.getBody().getUserData() instanceof Player && fB.getBody().getUserData() instanceof PowerUp) {
			((Player)fA.getBody().getUserData()).onPickup((PowerUp)fB.getBody().getUserData());
			((PowerUp)fB.getBody().getUserData()).setAlive(false);
		}
		if (fB.getBody().getUserData() instanceof Player && fA.getBody().getUserData() instanceof PowerUp) {
			((Player)fB.getBody().getUserData()).onPickup((PowerUp)fA.getBody().getUserData());
			((PowerUp)fA.getBody().getUserData()).setAlive(false);
		}
	}

	@Override
	public void endContact(Contact contact) {
		System.out.println("Contact ended!");
		Fixture fA = contact.getFixtureA();
		Fixture fB = contact.getFixtureB();
		
		// Contact between player and enemy
		if (fA.getBody().getUserData() instanceof Enemy && fB.getBody().getUserData() instanceof Player) {
			if (!fA.isSensor() && !fB.isSensor()) {
				((Enemy)fA.getBody().getUserData()).endContact(fB.getBody());
			}
		}
		if (fB.getBody().getUserData() instanceof Enemy && fA.getBody().getUserData() instanceof Player) {
			if (!fA.isSensor() && !fB.isSensor()) {
				((Enemy)fB.getBody().getUserData()).endContact(fA.getBody());
			}
		}
				
		// Player left enemy sensor
		if (fA.isSensor() && fB.getBody().getUserData() instanceof Player) {
			((Enemy)fA.getBody().getUserData()).endDetection(fB.getBody());
		}
		if (fB.isSensor() && fA.getBody().getUserData() instanceof Player) {
			((Enemy)fB.getBody().getUserData()).endDetection(fA.getBody());
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
