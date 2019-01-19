package com.deadlast.world;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.badlogic.gdx.assets.loaders.resolvers.ExternalFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.deadlast.entities.Entity;
import com.deadlast.entities.PowerUp;
import com.deadlast.entities.Enemy;
import com.deadlast.entities.EnemyFactory;
import com.deadlast.game.DeadLast;
import com.deadlast.game.GameManager;

/**
 * Represents a level in the game
 * Holds spawn locations from the player, zombies, and power-ups
 * @author ljd546
 * @author Xzytl
 */
public class Level {
	
	@SuppressWarnings("unused")
	private DeadLast game;
	private Vector2 playerSpawn;
	private List<SpawnPoint<Enemy.Type>> enemySpawns;
	private List<SpawnPoint<PowerUp.Type>> powerUpSpawns;
	public List<Exit> exits;//points to load in new room/level
	public List<Entrance> entrances;//points where the player will load in.
	public ArrayList<Vector2> boundaries;//walls of the room
	
	private String mapName;	
	private TiledMap levelMap;
	
	private TiledMapTileLayer spawnLayer;
	private TiledMapTileLayer indexLayer;
	private int spawnTileSetOffset;
	private int indexTileSetOffset;
	private GameManager gameManager;
	
	public Level(DeadLast game) {
		this.game = game;
		this.gameManager = GameManager.getInstance(game);
		enemySpawns = new ArrayList<>();
		powerUpSpawns = new ArrayList<>();
		exits = new ArrayList<>();
		entrances = new ArrayList<>();
		boundaries = new ArrayList<>();
		this.mapName = "Level1";
		parseMap();
		this.spawnLayer.setVisible(false);
		this.indexLayer.setVisible(false);
		this.findLevelEnds();
	}
	
	public void parseMap() {
	
		this.levelMap = new TmxMapLoader().load("maps/Level1.tmx");

		System.out.println("FILE LOADED: /map/" + this.mapName + ".tmx");
		this.spawnTileSetOffset = (Integer) this.levelMap.getTileSets().getTileSet("SpawnPointTileset").getProperties().get("firstgid");
		System.out.println(String.valueOf(this.spawnTileSetOffset));
		this.indexTileSetOffset = (Integer) this.levelMap.getTileSets().getTileSet("indexTileset").getProperties().get("firstgid");
		System.out.println(String.valueOf(this.indexTileSetOffset));

		this.spawnLayer = (TiledMapTileLayer) levelMap.getLayers().get("spawn-layer");
		this.indexLayer = (TiledMapTileLayer) levelMap.getLayers().get("index-layer");
		this.spawnLayer.setVisible(true);
		this.indexLayer.setVisible(true);
		System.out.println(spawnLayer.getWidth() + " x " + spawnLayer.getHeight());
		for(int i=0; i < spawnLayer.getWidth(); i++) {
			for(int j=0; j<spawnLayer.getHeight(); j++) {
				
				if (spawnLayer.getCell(i,j) == null) {
					continue;
				}
				//This case statement detects the type of tile on the spawn layer.
				//Commented out cases are for later use.
				switch ((spawnLayer.getCell(i, j).getTile().getId() - spawnTileSetOffset) + 1) {
					//Case 1 implements room exits (ie. teleports to other parts of the level.)
					case 1: exits.add(
							new Exit(new Vector2(i,j),
							(indexLayer.getCell(i, j).getTile().getId() - indexTileSetOffset) + 1));
							System.out.println("Exit added at: " + String.valueOf(i) + " " + String.valueOf(j));
							break;
					//Case 2 through 6 implement PowerUp spawn points
					case 2: powerUpSpawns.add(
								new SpawnPoint<PowerUp.Type>(PowerUp.Type.DOUBLE_POINTS, new Vector2(i,j))
							);
							System.out.println("Double Points PuP added at: " + String.valueOf(i) + " " + String.valueOf(j));
							break;
					case 3: powerUpSpawns.add(
								new SpawnPoint<PowerUp.Type>(PowerUp.Type.DOUBLE_DAMAGE, new Vector2(i,j))
							);
							System.out.println("Double Damage PuP added at: " + String.valueOf(i) + " " + String.valueOf(j));
							break;
					case 4: powerUpSpawns.add(
								new SpawnPoint<PowerUp.Type>(PowerUp.Type.REGEN, new Vector2(i,j))
							);
							System.out.println("Regen PuP added at: " + String.valueOf(i) + " " + String.valueOf(j));
							break;	
//						case 5: powerUpSpawns.add(
//								new SpawnPoint<PowerUp.Type>(PowerUp.Type.REGEN, new Vector2(i,j))
//								);
//								break;
//						case 6: powerUpSpawns.add(
//								new SpawnPoint<PowerUp.Type>(PowerUp.Type.REGEN, new Vector2(i,j))
//								);
//								break;
					
					//Case 7 through 12 implement zombie spawn points
					case 7: enemySpawns.add(
							new SpawnPoint<Enemy.Type>(Enemy.Type.FAST, new Vector2(4,0))
							);
							System.out.println("FAST Spawn added at: " + String.valueOf(i) + " " + String.valueOf(j));
							break;
					case 8:	enemySpawns.add(
							new SpawnPoint<Enemy.Type>(Enemy.Type.HEAVY, new Vector2(7,7))
							);
							System.out.println("HEAVY Spawn added at: " + String.valueOf(i) + " " + String.valueOf(j));
							break;
//						case 9: enemySpawns.add(
//								new SpawnPoint<Enemy.Type>(Enemy.Type.HEAVY, new Vector2(7,7))
//								);
//								System.out.println("FAST Spawn added at: " + String.valueOf(i) + " " + String.valueOf(j));
//								break;
//						case 10: enemySpawns.add(
//								new SpawnPoint<Enemy.Type>(Enemy.Type.HEAVY, new Vector2(7,7))
//								);
//								System.out.println("FAST Spawn added at: " + String.valueOf(i) + " " + String.valueOf(j));
//								break;
//						case 11: enemySpawns.add(
//								new SpawnPoint<Enemy.Type>(Enemy.Type.HEAVY, new Vector2(7,7))
//								);
//								System.out.println("FAST Spawn added at: " + String.valueOf(i) + " " + String.valueOf(j));
//								break;	
//						case 12: enemySpawns.add(
//								new SpawnPoint<Enemy.Type>(Enemy.Type.HEAVY, new Vector2(7,7))
//								);
//								System.out.println("FAST Spawn added at: " + String.valueOf(i) + " " + String.valueOf(j));
//								break;
					//Remaining cases are spare.
//						case 13: 
//								break;
//						case 14:
//								break;
//						case 15:
//								break;
//						case 16:
//								break;
					case 17:
							entrances.add(new Entrance(new Vector2(i,j) ,(indexLayer.getCell(i, j).getTile().getId() - indexTileSetOffset) + 1));
							
							System.out.println("Entrance added at: " + String.valueOf(i) + " " + String.valueOf(j));
							break;
					case 28:
							boundaries.add(new Vector2(i,j));
							break;
						
					default:
						break;
				}
			}
	
	
		}
		System.out.println(entrances.size());
		entrances.sort(new Comparator<Entrance>() {
			
			 public int compare(Entrance e1, Entrance e2){
		         if(e1.id == e2.id) {
		             return 0;
		         }else{
		        	 return e1.id < e2.id ? -1 : 1;}
		         }
			 }
		     );
		
		playerSpawn = entrances.get(0).location;
		System.out.println("Finished loading");
	}
	/*
	 * Finds the max ID of player spawns, turns exits into end of levels if their target is larger.
	 */
	public void findLevelEnds() {
		System.out.println("Finding level ends");
		int maxTargetId = entrances.get(entrances.size() -1).id;
		for (int i = 0; i < this.exits.size(); i++) {
			if (exits.get(i).targetId > maxTargetId) {
				exits.get(i).isEnd = true;
			}
			
		}
		System.out.println("Finished finding level ends");
		
	}
	public TiledMap load() {
		System.out.println("Loading level");
		enemySpawns.forEach(
			es -> gameManager.addEnemy(es.getType(), es.getSpawnLocation())
		);
		powerUpSpawns.forEach(
			ps -> gameManager.addPowerUp(ps.getType(), ps.getSpawnLocation())
		);
		gameManager.setPlayerSpawn(playerSpawn);
		return this.levelMap;
	}
	
	static class SpawnPoint<K> {
		final K type;
		final Vector2 spawnLoc;
		
		public SpawnPoint(K type, Vector2 spawnLoc) {
			this.type = type;
			this.spawnLoc = spawnLoc;
		}
		
		public K getType() {
			return type;
		}
		
		public Vector2 getSpawnLocation() {
			return spawnLoc;
		}
	}
	static class Exit{
		final Vector2 location;
		final int targetId;
		boolean isEnd;
		public Exit(Vector2 location, int targetId) {
			this.location = location;
			this.targetId = targetId;
			this.isEnd = false;
			
		}
	}
	static class Entrance{
		final Vector2 location;
		final int id;
		public Entrance(Vector2 location, int id) {
			this.location = location;
			this.id = id;
		}
		
	}
	
	public String getMapName() {
		return this.mapName;
	}
	
	
	public TiledMap getMap() {
		return this.levelMap;
	}
	
	public List<SpawnPoint<Enemy.Type>> getZombieSpawnPoints(){
		return this.enemySpawns;
	}
	
	public List<SpawnPoint<PowerUp.Type>> getPowerUpSpawnPoints(){
		return this.powerUpSpawns;
		
	}
	
	public List<Exit> getExits(){
		return this.exits;
	}
	
	public List<Entrance> getEntrances(){
		return this.entrances;
	}
	
	public ArrayList<Vector2> getboundaries(){
		return this.boundaries;
	}
	
}
