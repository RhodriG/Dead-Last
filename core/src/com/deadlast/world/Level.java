package com.deadlast.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.deadlast.screens.GameScreen;

/**
 * 
 * @author ljd546
 *
 */
public class Level {
	
	private World world;
	private BodyFactory instance;
	public Room[] rooms;
	public TiledMapRenderer tiledMapRenderer;
	
	public Level(String[] roomRefs) {

		for (int i = 0; i < roomRefs.length; i++) {
			
			this.rooms[i] = new Room(roomRefs[i]);
			
		}
			
	}
	public class Room {
		
		public String mapName;
		
		public Room(String mapName) {
			this.mapName = mapName;
			
		}
		
		public void render(OrthographicCamera camera) {
			TiledMap tiledMap = new TmxMapLoader().load(mapName + ".tmx");
			tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
			tiledMapRenderer.setView(camera);
	        tiledMapRenderer.render();
			
			
		}
		
	}
	
}
