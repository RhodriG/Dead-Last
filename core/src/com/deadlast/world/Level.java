package com.deadlast.world;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

/**
 * 
 * @author ljd546
 *
 */
public class Level {
	
	private World world;
	private BodyFactory instance;
	public Room[] rooms;
	
	public Level(World world) {
		this.world = world;
		this.instance = BodyFactory.getInstance(world);
	}
	
	
	
	public class Room {
		
		public Map tiledMap;
		public Body[] roomLayout;
		
		public void genLayout() {
			
		}
		
	}
	
}
