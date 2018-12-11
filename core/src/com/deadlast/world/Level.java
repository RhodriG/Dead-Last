package com.deadlast.world;

import java.util.ArrayList;

import com.badlogic.gdx.assets.loaders.resolvers.ExternalFileHandleResolver;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
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
	
	public Room[] rooms;
	protected TmxMapLoader roomLoader;
	/*
	 * Level constructor must be passed a string of rooms which are the filenames of the maps without the .tmx suffix.
	 */
	public Level(String[] roomRefs) {

		for (int i = 0; i < roomRefs.length; i++) {
			
			this.rooms[i] = new Room(roomRefs[i]);
			
		}
		
		roomLoader = new TmxMapLoader(new ExternalFileHandleResolver());
			
	}
	public class Room {
		
		private String mapName;	
		private TiledMap levelMap;
		private TiledMapTileLayer spawnLayer;
		private ArrayList<SpawnPoint> powerSpawnPoints; //places to load in powerups.
		private ArrayList<SpawnPoint> zombieSpawnPoints; //places to load in zombies
		private float[][] roomExits;//places to load in new level or spawn player in
		
		private Room(String mapName) {
			this.mapName = mapName;
			this.levelMap = roomLoader.load(this.getFilePath());
			this.spawnLayer = (TiledMapTileLayer) levelMap.getLayers().get("spawn-points");
			
			//TODO: CHANGE BELOW TO ARRAY LISTS
			this.powerSpawnPoints = new ArrayList<SpawnPoint>();
			this.zombieSpawnPoints = new ArrayList<SpawnPoint>();
			this.roomExits = new float[5][];
		}
		
		public String getMapName() {
			return this.mapName;
		}
		
		public String getFilePath() {
			return "Dead-Last\\core\\assets\\maps\\" + this.mapName + ".tmx";
		}
		
		public TiledMap getMap() {
			return this.levelMap;
		}
		/*
		 * Generates spawn points from a layer called spawn-points in the tiled map file.
		 */
		private void parseSpawnPoints() {
			
			for(int i= 0; i < spawnLayer.getWidth(); i++) {
				for(int j=0; j<spawnLayer.getHeight(); j++) {
					//TODO: EDIT BELOW SO IT KEEPS TRACK OF WHERE IN THE LISTS IT IS
					switch (spawnLayer.getCell(i, j).getTile().getId()){
					case 0: roomExits[0][0] = i;
							roomExits[0][1] = j;
							break;
					//TODO: Replace below with appropriate text when powerUps have been made.
					//Case 1 through 5 implement PowerUp spawn points
					case 1: powerSpawnPoints.add(new SpawnPoint(i,j,"P1TYPE"));
							break;
					case 2: powerSpawnPoints.add(new SpawnPoint(i,j,"P2TYPE"));
							break;
					case 3: powerSpawnPoints.add(new SpawnPoint(i,j,"P3TYPE"));
							break;
					case 4: powerSpawnPoints.add(new SpawnPoint(i,j,"P4TYPE"));
							break;
					case 5: powerSpawnPoints.add(new SpawnPoint(i,j,"P5TYPE"));
							break;
					//TODO: Replace these with appropriate values for newer zombie types.
					//Case 6 through 11 implement zombie spawn points
					case 6: zombieSpawnPoints.add(new SpawnPoint(i,j,"NORMAL"));
							break;
					case 7: zombieSpawnPoints.add(new SpawnPoint(i,j,"FAST"));
							break;
					case 8: zombieSpawnPoints.add(new SpawnPoint(i,j,"HEAVY"));
							break;
					case 9: zombieSpawnPoints.add(new SpawnPoint(i,j,"JOCKEY"));
							break;
					case 10: zombieSpawnPoints.add(new SpawnPoint(i,j,"HORDLING"));
							break;
					case 11: zombieSpawnPoints.add(new SpawnPoint(i,j,"MR_TICKLE"));
							break;
					default: break;
					}
				}
				
				
			}
		}
		
	}
	public class SpawnPoint{
		
		public float xPos;
		public float yPos;
		public String type;
		
		public SpawnPoint(float x, float y, String type) {
			this.xPos = x;
			this.yPos = y;
			this.type = type;
			
		}
	}
}
