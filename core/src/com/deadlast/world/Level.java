package com.deadlast.world;

import java.util.ArrayList;

import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ExternalFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
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
import com.deadlast.entities.EnemyType;
import com.deadlast.entities.Entity;
import com.deadlast.screens.GameScreen;

/**
 * 
 * @author ljd546
 *
 */
public class Level {
	
	public Room[] rooms;
	private Room currentRoom;
	
	
	/*
	 * Level constructor must be passed a string of rooms which are the filenames of the maps without the .tmx suffix.
	 */
	public Level(String[] roomRefs) {
		this.rooms = new Room[roomRefs.length];
		for (int i = 0; i < roomRefs.length; i++) {
			
			this.rooms[i] = new Room(roomRefs[i]);
			
		}
		
		
		currentRoom = this.rooms[0];	
	}
	
	public Room getCurrentRoom() {
		return this.currentRoom;
	}
	public class Room {
		
		private String mapName;	
		private TiledMap levelMap;
		private TiledMapTileLayer spawnLayer;
		public ArrayList<SpawnPoint> powerSpawnPoints; //points to load in powerups.
		public ArrayList<SpawnPoint> zombieSpawnPoints; //points to load in zombies
		public ArrayList<Vector2> roomExits;//points to load in new room/level
		public ArrayList<Vector2> roomEntrances;//points where the player will load in.
		public ArrayList<Vector2> roomBoundaries;//walls of the room
		protected TmxMapLoader roomLoader;
		
		private Room(String mapName) {
			
			this.mapName = mapName;
			System.out.println("/map/" + this.mapName + ".tmx");
			this.levelMap = new TmxMapLoader(new ExternalFileHandleResolver()).load("Dead-Last\\core\\assets\\maps\\" + this.mapName + ".tmx");
				
			this.spawnLayer = (TiledMapTileLayer) levelMap.getLayers().get("spawn-layer");
			
			
			this.powerSpawnPoints = new ArrayList<SpawnPoint>();
			this.zombieSpawnPoints = new ArrayList<SpawnPoint>();
			
			this.roomExits = new ArrayList<Vector2>();
			this.roomEntrances = new ArrayList<Vector2>();
			this.roomBoundaries = new ArrayList<Vector2>();
			this.parseSpawnPoints();
		}
		
		public String getMapName() {
			return this.mapName;
		}
		
		
		public TiledMap getMap() {
			return this.levelMap;
		}
		
		public ArrayList<SpawnPoint> getZombieSpawnPoints(){
			return this.zombieSpawnPoints;
		}
		
		public ArrayList<SpawnPoint> getPowerUpSpawnPoints(){
			return this.powerSpawnPoints;
			
		}
		
		public ArrayList<Vector2> getRoomExits(){
			return this.roomExits;
		}
		
		public ArrayList<Vector2> getRoomEntrances(){
			return this.roomEntrances;
		}
		
		public ArrayList<Vector2> getRoomBoundaries(){
			return this.roomBoundaries;
		}
		/*
		 * Generates spawn points from a layer called spawn-points in the tiled map file.
		 */
		private void parseSpawnPoints() {
			System.out.println(String.valueOf(spawnLayer.getWidth()) + " " + String.valueOf(spawnLayer.getHeight()));
			for(int i=0; i < spawnLayer.getWidth(); i++) {
				for(int j=0; j<spawnLayer.getHeight(); j++) {
					
					if (spawnLayer.getCell(i,j) == null) {
						
						continue;
					}
					//This case statement detects the type of tile on the spawn layer. If you need to add more types of spawn point, edit this.
					switch (spawnLayer.getCell(i, j).getTile().getId()) {
						
						case 1: roomExits.add(new Vector2(i,j));
								System.out.println("Exit added at: " + String.valueOf(i) + " " + String.valueOf(j));
								System.out.println(String.valueOf(spawnLayer.getCell(i, j).getTile().getId()));
								break;
						//TODO: Replace below with ENUMs
						//TODO: Replace below with appropriate text when powerUps have been made.
						//Case 1 through 5 implement PowerUp spawn points
							
	//					case 2: powerSpawnPoints.add(new SpawnPoint(i,j,"P1TYPE"));
	//							break;
	//					case 3: powerSpawnPoints.add(new SpawnPoint(i,j,"P2TYPE"));
	//							break;
	//					case 4: powerSpawnPoints.add(new SpawnPoint(i,j,"P3TYPE"));
	//							break;
	//					case 5: powerSpawnPoints.add(new SpawnPoint(i,j,"P4TYPE"));
	//							break;
	//					case 6 powerSpawnPoints.add(new SpawnPoint(i,j,"P5TYPE"));
	//							break;
						
						//Case 6 through 11 implement zombie spawn points
						case 7: zombieSpawnPoints.add(new SpawnPoint(i,j,EnemyType.NORMAL));
						System.out.println("Zombie 1 Spawn added at: " + String.valueOf(i) + " " + String.valueOf(j));
						System.out.println(String.valueOf(spawnLayer.getCell(i, j).getTile().getId()));
							break;
						case 8: zombieSpawnPoints.add(new SpawnPoint(i,j,EnemyType.FAST));
							break;
						case 9: zombieSpawnPoints.add(new SpawnPoint(i,j,EnemyType.BOMBER));
							break;
						case 10: zombieSpawnPoints.add(new SpawnPoint(i,j,EnemyType.HEAVY));
							break;
						case 11: zombieSpawnPoints.add(new SpawnPoint(i,j,EnemyType.JOCKEY));
							break;		
						case 12: zombieSpawnPoints.add(new SpawnPoint(i,j,EnemyType.MR_TICKLE));
							break;
						//Remaining cases are spare.
	//					case 13: 
	//							break;
	//					case 14:
	//							break;
	//					case 15:
	//							break;
	//					case 16:
	//							break;
						case 17:
								roomEntrances.add(new Vector2(i,j));
								System.out.println("Entrance added at: " + String.valueOf(i) + " " + String.valueOf(j));
								System.out.println(String.valueOf(spawnLayer.getCell(i, j).getTile().getId()));
								break;
						case 28:
								roomBoundaries.add(new Vector2(i,j));
								
								break;
							
						default:
							break;
					}
				}
				
				
			}
		}
		
	}
	
	//TODO: Change this to take enum param rather than string
	/*
	 * SpawnPoint has a position, as well as a type which can be passed to the game manager.
	 */
	public class SpawnPoint{
		
		public Vector2 position;
		public EnemyType type;
		
		public SpawnPoint(float x, float y, EnemyType type) {
			
			this.position = new Vector2(x,y);
			this.type = type;
			
		}
		public EnemyType getType() {
			return this.type;
		}
		
		public Vector2 getPos() {
			return this.position;
		}
		
	}
}
