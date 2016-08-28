package com.github.houkagoteatime.LD36.levels;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.github.houkagoteatime.LD36.PlayerInputProcessor;
import com.github.houkagoteatime.LD36.entities.Player;
import com.github.houkagoteatime.LD36.entities.enemies.Archer;
import com.github.houkagoteatime.LD36.entities.enemies.Enemy;
import com.github.houkagoteatime.LD36.entities.enemies.MeleeEnemy;
import com.github.houkagoteatime.LD36.screens.GameScreen;
import com.github.houkagoteatime.LD36.utils.PathFinder;
import com.github.houkagoteatime.LD36.weapons.Melee;
import com.github.houkagoteatime.LD36.weapons.Projectile;

public abstract class Level {

	private TiledMap tiledMap;
	private OrthogonalTiledMapRendererWithSprites tiledMapRenderer;
	int objectLayerId = 0;
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
		this.player = new Player(this, 75, 10, new Sprite(new Texture("assets/pictures/harambe.jpg")));
		projectiles = new ArrayList<>();
		proc = new PlayerInputProcessor(player);
		meleeWeps = new ArrayList<>();
		this.game = game;
		finder = new PathFinder(this, 80);
		for(Enemy enemy : enemies) {
			enemy.setPathFinder(finder);
		}
	}

	public MapObjects getMapObjects() {
		int objectLayerId = 0;
		return tiledMap.getLayers().get(objectLayerId).getObjects();
	}
	/**
	 * override this to change how the enemies spawn
	 */
	public abstract void spawnEnemies();

	public void update(float dt) {
		updateProjectiles(dt);
		handleProjectileCollision(dt);
		handleContactDamage(dt);
		proc.queryInput();
		player.update(dt);
		updateEnemies(dt);
		if(enemies.isEmpty() || player.isDead()) {
			game.gameOver();
		}

	}

	public void updateProjectiles(float dt) {

		player.getWeapon().incrementDelayCounter();
		for(Enemy e: enemies) {
			if(e instanceof Archer) {
				((Archer) e).getWeapon().incrementDelayCounter();
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
