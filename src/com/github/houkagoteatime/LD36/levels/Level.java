package com.github.houkagoteatime.LD36.levels;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.github.houkagoteatime.LD36.entities.Player;
import com.github.houkagoteatime.LD36.entities.enemies.Enemy;

public abstract class Level {
	
	private TiledMap tiledMap;
	private OrthogonalTiledMapRendererWithSprites tiledMapRenderer;
	
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
	
	public Level(String path) {
		this.tiledMap  = new TmxMapLoader().load(path);
		this.tiledMapRenderer = new OrthogonalTiledMapRendererWithSprites(tiledMap);
		this.mapProp = tiledMap.getProperties();
		calcMapProperties(mapProp);
		this.enemies = new ArrayList<Enemy>();
		this.player = new Player(100, 10, new Sprite(new Texture("assets/pictures/harambe.jpg")), getWallLayer(tiledMap));
	}
	
	/**
	 * override this to change how the enemies spawn
	 */
	public abstract void spawnEnemies();

	public void update(float dt) {
		
		
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
	
	public TiledMapTileLayer getWallLayer(TiledMap tiledMap) {
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
	
	/*public ArrayList<Projectiles> getProjectiles() {
		return projectiles;
	}*/
	

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
