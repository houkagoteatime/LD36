package com.github.houkagoteatime.LD36.levels;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.github.houkagoteatime.LD36.PlayerInputProcessor;
import com.github.houkagoteatime.LD36.entities.Player;
import com.github.houkagoteatime.LD36.entities.enemies.Archer;
import com.github.houkagoteatime.LD36.entities.enemies.Enemy;
import com.github.houkagoteatime.LD36.entities.enemies.Gilgamesh;
import com.github.houkagoteatime.LD36.entities.enemies.EnemySpawner;
import com.github.houkagoteatime.LD36.entities.enemies.MeleeEnemy;
import com.github.houkagoteatime.LD36.screens.GameScreen;
import com.github.houkagoteatime.LD36.utils.PathFinder;
import com.github.houkagoteatime.LD36.weapons.Melee;
import com.github.houkagoteatime.LD36.weapons.Projectile;

public abstract class Level {

	public int level;
	private TiledMap tiledMap;
	private OrthogonalTiledMapRendererWithSprites tiledMapRenderer;
	public static final int WALL_OBJECT_LAYER_ID = 1;
	public static final int GAME_OBJECT_LAYER_ID = 8;
	public static final int SPAWN_OBJECT_LAYER_ID = 9;
	public MapProperties mapProp;
	public int mapWidth;
	public int mapHeight;
	public int tilePixelWidth;
	public int tilePixelHeight;
	public int mapPixelWidth;
	public int mapPixelHeight;


	public static final int WALL_LAYER = 0;

	private Player player;
	private ArrayList<Enemy> enemies;
	private ArrayList<Projectile> projectiles;
	private PlayerInputProcessor proc;
	private ArrayList<Melee> meleeWeps;
	private GameScreen game;
	private PathFinder finder;
	public Level(String path, GameScreen game) {
		this.tiledMap  = new TmxMapLoader().load(path);
		this.tiledMapRenderer = new OrthogonalTiledMapRendererWithSprites(tiledMap, this);
		this.mapProp = tiledMap.getProperties();
		calcMapProperties(mapProp);
		this.enemies = new ArrayList<Enemy>();
		this.player = new Player(this, 10, new Sprite(new Texture("assets/pictures/gorilla.png")));
		projectiles = new ArrayList<>();
		proc = new PlayerInputProcessor(player);
		meleeWeps = new ArrayList<>();
		this.game = game;
		finder = new PathFinder(this, 80);
	}

	
	public abstract void nextLevel();
	

	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getLevel() {
		return level;
	}

	public MapObjects getGameObjects() {
		return tiledMap.getLayers().get(GAME_OBJECT_LAYER_ID).getObjects();
	}
	
	public MapObjects getSpawnObjects() {
		return tiledMap.getLayers().get(SPAWN_OBJECT_LAYER_ID).getObjects();
	}
	
	public void setPathFinder() {
		for(Enemy e : enemies) {
			e.setPathFinder(finder);
		}
	}
	
	public void handleGameObjects() {
			for(TiledMapTileMapObject obj: getGameObjects().getByType(TiledMapTileMapObject.class)) {
				Rectangle r = new Rectangle(obj.getX(), + obj.getY(), tilePixelWidth/2, tilePixelHeight/2);
				if(player.getBounds().overlaps(r)) {
					if(obj.getProperties().containsKey("hp10") && obj.getProperties().get("hp10").equals(true)) {
						player.setHealth(player.getHealth() + 10);
						getGameObjects().remove(obj);
					} else if(obj.getProperties().containsKey("level") && enemies.isEmpty()) {
						game.switchLevel(((int)obj.getProperties().get("level")));
					}
				}
			}
	}
	
	public MapObjects getMapObjects() {
		return tiledMap.getLayers().get(WALL_OBJECT_LAYER_ID).getObjects();
	}
	/**
	 * override this to change how the enemies spawn
	 */
	public void spawnEnemies() {
		for(RectangleMapObject spawnPoint: getSpawnObjects().getByType(RectangleMapObject.class)) {
			if(spawnPoint.getProperties().containsKey("archer")) {
				EnemySpawner.getInstance().spawnEnemy("archer", new Vector2((float)spawnPoint.getProperties().get("x"),(float)spawnPoint.getProperties().get("y")));
			} else if(spawnPoint.getProperties().containsKey("swordsman")) {
				EnemySpawner.getInstance().spawnEnemy("swordsman",  new Vector2((float)spawnPoint.getProperties().get("x"),(float)spawnPoint.getProperties().get("y")));
			} else if(spawnPoint.getProperties().containsKey("lancer")) {
				EnemySpawner.getInstance().spawnEnemy("lancer",  new Vector2((float)spawnPoint.getProperties().get("x"),(float)spawnPoint.getProperties().get("y")));
			} else if(spawnPoint.getProperties().containsKey("gilgamesh")) {
				EnemySpawner.getInstance().spawnEnemy("gilgamesh",  new Vector2((float)spawnPoint.getProperties().get("x"),(float)spawnPoint.getProperties().get("y")));
			}
		}
		setPathFinder();
	}

	public PlayerInputProcessor getPlayerInputProcessor() {
		return proc;
	}
	public void update(float dt) {
		if(player.isGod) {
			proc.queryInput();
			player.fly(dt);
			handleGameObjects();
			return;
		}
		updateProjectiles(dt);
		handleProjectileCollision(dt);
		handleGameObjects();
		handleContactDamage(dt);
		proc.queryInput();
		player.update(dt);
		updateEnemies(dt);
		if(player.isDead()) {
			//game.gameOver();
		}
		if(enemies.isEmpty()) {
			//game.gameOver();
			if(level == 3) {
				player.setSprite(new Sprite(new Texture("assets/pictures/gorillagod.png")));
				player.setSpeed((int)(player.getSpeed() * 3));
				player.isGod = true;
			}
		}
		
	}

	public void updateProjectiles(float dt) {

		player.getWeapon().incrementDelayCounter();
		for(Enemy e: enemies) {
			if(e instanceof Archer) {
				((Archer) e).getWeapon().incrementDelayCounter();
			}
			
			if(e instanceof Gilgamesh) {
				((Gilgamesh) e).getGateOfBabylon().incrementDelayCounter();
			}
		}
		Iterator<Projectile> projectileIterator = projectiles.iterator();
		while(projectileIterator.hasNext()) {
			Projectile p = projectileIterator.next();
			if(p.isOutOfRange())
				projectileIterator.remove();
			else if (p.getCollide())
				projectileIterator.remove();
			else
				p.update(dt);
		}
	}

	public void handleContactDamage(float dt) {
		for(Enemy e: enemies) {
			player.iFrameCounter++;
			if(e.getBounds().overlaps(player.getBounds())) {
				if(player.iFrameCounter >= Player.I_FRAME) {
					player.setHealth(player.getHealth() - 10);
					player.iFrameCounter = 0;
				}
			}
		}
	}

	public void handleProjectileCollision(float dt) {
		for(int i = 0; i < projectiles.size(); i++) {
			Projectile p = projectiles.get(i);
			//damage player
			if(!p.isFriendly()) {
				player.iFrameCounter++;
				if(p.getBounds().overlaps(this.getPlayer().getBounds())) {
					if(player.iFrameCounter > Player.I_FRAME) {
						player.setHealth(player.getHealth() - p.getDamage());
						player.iFrameCounter = 0;
					}
				}
			} else {
				//damage enemies
				for(Enemy e: enemies) {
					e.iFrameCounter++;
					if(p.getBounds().overlaps(e.getBounds())) {
						if(e.iFrameCounter >= MeleeEnemy.I_FRAME) {
							e.setHealth(e.getHealth() - p.getDamage());
							e.iFrameCounter = 0;
						} 
						projectiles.remove(i);
					}
				}
			}
		}
	}

	public void handlePlayerMelee(Melee melee) {
		Rectangle rec = melee.getExtension();
		for(Enemy enemy : enemies) {
			if(enemy.getBounds().overlaps(rec)) {
				enemy.setHealth(enemy.getHealth() - Melee.DAMAGE);
			}
		}
		meleeWeps.add(melee);
	}

	public void handleMelee(Melee melee) {
		Rectangle rec = melee.getExtension();
		if(player.getBounds().overlaps(rec)) {
			player.setHealth(player.getHealth() - Melee.DAMAGE);
		}
		meleeWeps.add(melee);
	}

	public void updateEnemies(float dt) {
		for(int i = 0; i < enemies.size(); i++) {
			if(!enemies.get(i).isDead()) {
				enemies.get(i).update(dt);
			} else {
				enemies.remove(i);
			}
		}
	}
	public void calcMapProperties(MapProperties mapProp) {

		mapWidth = mapProp.get("width", Integer.class);
		mapHeight = mapProp.get("height", Integer.class);
		tilePixelWidth = mapProp.get("tilewidth", Integer.class);
		tilePixelHeight = mapProp.get("tileheight", Integer.class);

		mapPixelWidth = mapWidth * tilePixelWidth;
		mapPixelHeight = mapHeight * tilePixelHeight;
	}

	public void dispose() {

	}
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

	public void addEnemies(MeleeEnemy e) {
		enemies.add(e);
	}

	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}

	public void addProjectile(Projectile proj) {
		projectiles.add(proj);
	}

	public void removeProjectile(Projectile proj) {
		projectiles.remove(proj);
	}
	/**
	 * @return the meleeWeps
	 */
	public ArrayList<Melee> getMeleeWeps() {
		return meleeWeps;
	}

	/**
	 * @param meleeWeps the meleeWeps to set
	 */
	public void setMeleeWeps(ArrayList<Melee> meleeWeps) {
		this.meleeWeps = meleeWeps;
	}

	public TiledMap getTiledMap() {
		return tiledMap;
	}

	public OrthogonalTiledMapRendererWithSprites getTiledMapRenderer() {
		return tiledMapRenderer;
	}

}
