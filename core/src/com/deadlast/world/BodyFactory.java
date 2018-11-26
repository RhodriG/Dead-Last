package com.deadlast.world;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * 
 * @author Xzytl
 * @deprecated
 */
public class BodyFactory {
	
	private static BodyFactory instance;
	private World world;
	
	public static final int STEEL = 0;
	public static final int WOOD = 1;
	public static final int RUBBER = 2;
	public static final int STONE = 3;
	
	private final float DEGTORAD = 0.0174533f;
	
	private BodyFactory(World world) {
		this.world = world;
	}
	
	public static BodyFactory getInstance(World world) {
		if (instance == null) {
			instance = new BodyFactory(world);
		}
		return instance;
	}
	
	public static FixtureDef makeFixture(int material, Shape shape) {
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		
		switch(material){
		case 0:
			fixtureDef.density = 1f;
			fixtureDef.friction = 0.3f;
			fixtureDef.restitution = 0.1f;
			break;
		case 1:
			fixtureDef.density = 0.5f;
			fixtureDef.friction = 0.7f;
			fixtureDef.restitution = 0.3f;
			break;
		case 2:
			fixtureDef.density = 1f;
			fixtureDef.friction = 0f;
			fixtureDef.restitution = 1f;
			break;
		case 3:
			fixtureDef.density = 1f;
			fixtureDef.friction = 0.9f;
			fixtureDef.restitution = 0.01f;
		default:
			fixtureDef.density = 7f;
			fixtureDef.friction = 0.5f;
			fixtureDef.restitution = 0.3f;
		}
		
		return fixtureDef;
	}
	
	public Body makeCirclePolyBody(float posx, float posy, float radius, int material, BodyType bodyType) {
		return makeCirclePolyBody(posx, posy, radius, material, bodyType, false);
	}
	
	public Body makeCirclePolyBody(float posx, float posy, float radius, int material, BodyType bodyType, boolean fixedRotation) {
		BodyDef boxBodyDef = new BodyDef();
		boxBodyDef.type = bodyType;
		boxBodyDef.position.x = posx;
		boxBodyDef.position.y = posy;
		boxBodyDef.fixedRotation = fixedRotation;
		
		Body boxBody = world.createBody(boxBodyDef);
		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(radius / 2);
		boxBody.createFixture(makeFixture(material, circleShape));
		circleShape.dispose();
		
		return boxBody;
	}
	
	public Body makeBoxPolyBody(float posx, float posy, float width, float height, int material, BodyType bodyType, boolean fixedRotation) {
		BodyDef boxBodyDef = new BodyDef();
		boxBodyDef.type = bodyType;
		boxBodyDef.position.x = posx;
		boxBodyDef.position.y = posy;
		boxBodyDef.fixedRotation = fixedRotation;
		
		Body boxBody = world.createBody(boxBodyDef);
		PolygonShape polyShape = new PolygonShape();
		polyShape.setAsBox(width / 2, height / 2);
		boxBody.createFixture(makeFixture(material, polyShape));
		polyShape.dispose();
		
		return boxBody;
	}
	
	public Body makePolyShapeBody(Vector2[] vertices, float posx, float posy, int material, BodyType bodyType) {
		BodyDef boxBodyDef = new BodyDef();
		boxBodyDef.type = bodyType;
		boxBodyDef.position.x = posx;
		boxBodyDef.position.y = posy;
		Body boxBody = world.createBody(boxBodyDef);
		
		PolygonShape polyShape = new PolygonShape();
		polyShape.set(vertices);
		boxBody.createFixture(makeFixture(material, polyShape));
		
		return boxBody;
	}
	
	public void makeConeSensor(Body body, float size) {
		FixtureDef fixtureDef = new FixtureDef();
		PolygonShape polyShape = new PolygonShape();
		
		float radius = size;
		Vector2[] vertices = new Vector2[5];
		
		vertices[0] = new Vector2(0,0);
		for (int i = 2; i < 6; i++) {
			float angle = (float) (i / 6.0 * 145 * DEGTORAD);
			vertices[i - 1] = new Vector2(radius * ((float)Math.cos(angle)), radius * ((float)Math.sin(angle)));
		}
		polyShape.set(vertices);
		fixtureDef.shape = polyShape;
		body.createFixture(fixtureDef);
		polyShape.dispose();
	}
	
	public void makeAllFixturesSensors(Body body) {
		for (Fixture fix : body.getFixtureList()) {
			fix.setSensor(true);
		}
	}

}
