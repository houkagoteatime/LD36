package com.github.houkagoteatime.LD36.levels;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.github.houkagoteatime.LD36.PlayerInputProcessor;
import com.github.houkagoteatime.LD36.entities.Player;
import com.github.houkagoteatime.LD36.entities.enemies.Enemy;
import com.github.houkagoteatime.LD36.weapons.Projectile;

public abstract class Level {
	
	private TiledMap tiledMap;
	private OrthogonalTiledMapRendererWithSprites tiledMapRenderer;
	private TiledMapTileLayer wallLayer;
	
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
	
	public Level(String path) {
		this.tiledMap  = new TmxMapLoader().load(path);
		this.tiledMapRenderer = new OrthogonalTiledMapRendererWithSprites(tiledMap, this);
		this.mapProp = tiledMap.getProperties();
		this.wallLayer = (TiledMapTileLayer)tiledMap.getLayers().get(WALL_LAYER);
		calcMapProperties(mapProp);
		this.enemies = new ArrayList<Enemy>();
		this.player = new Player(this, 100, 10, new Sprite(new Texture("assets/pictures/harambe.jpg")), wallLayer);
		projectiles = new ArrayList<>();
		proc = new PlayerInputProcessor(player);
	}
	
	/**
	 * override this to change how the enemies spawn
	 */
	public abstract void spawnEnemies();

	public void handlePlayerProjectileCollision(float dt) {
		for(int i = 0; i < projectiles.size(); i++) {
			Projectile p = projectiles.get(i);
			for(Enemy e: enemies) {
				/*Rectangle bullet = new Rectangle(
						p.getxPosition(), 
						p.getyPosition(), 
						p.getxPosition() + p.getSprite().getWidth(), 
						p.getyPosition() + p.getSprite().getY());
				Rectangle enemy = new Rectangle(
						e.getxPosition(), 
						e.getyPosition(), 
						e.getxPosition() + e.getSprite().getWidth(), 
						e.getyPosition() + e.getSprite().getY());
				if(bullet.overlaps(enemy)) {
					System.out.println(bullet + ":" + enemy);
					e.setHealth(e.getHealth() - p.getDamage());
					System.out.println(e.getHealth());
					projectiles.remove(i);
				}*/
				//p.getBounds().setCenter(new Vector2(p.getSprite().getX(),p.getSprite().getY()));
				if(p.getBounds().overlaps(e.getBounds())) {
					e.setHealth(e.getHealth() - p.getDamage());
					projectiles.remove(i);
				}
			}
		}
	}
	
	public void update(float dt) {
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
		handlePlayerProjectileCollision(dt);
		proc.queryInput();
		player.update(dt);
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
	
	public TiledMapTileLayer getWallLayer() {
		return (TiledMapTileLayer)tiledMap.getLayers().get(WALL_LAYER);
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
	
	public void addEnemies(Enemy e) {
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
	public TiledMap getTiledMap() {
		return tiledMap;
	}
	
	public OrthogonalTiledMapRendererWithSprites getTiledMapRenderer() {
		return tiledMapRenderer;
	}
	
	public MapProperties getMapProperties() {
		return mapProp;
		
	}
}
