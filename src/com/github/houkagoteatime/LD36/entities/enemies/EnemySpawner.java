package com.github.houkagoteatime.LD36.entities.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.github.houkagoteatime.LD36.levels.Level;

public class EnemySpawner {
	private Level level;
	private static EnemySpawner INSTANCE = null;
	
	private EnemySpawner() {
	}
	
	public static void init(Level level) {
		if(INSTANCE == null)
			INSTANCE = new EnemySpawner();
		INSTANCE.setLevel(level);
	}
	/**
	 * @param level the level to set
	 */
	private void setLevel(Level level) {
		this.level = level;
	}
	
	public static EnemySpawner getInstance() {
		return INSTANCE;
	}
	
	public void spawnEnemy(String enemy, Vector2 pos) {
		Enemy newEnemy;
		switch (enemy) {
		case "archer":
			newEnemy = new Archer(level, new Sprite(new Texture("assets/pictures/rock.png")), new Sprite(new Texture(Gdx.files.internal("assets/pictures/rockproj.png"))), level.getPlayer());
			newEnemy.init();
			newEnemy.setxPosition(pos.x);
			newEnemy.setyPosition(pos.y);
			break;
		case "swordsman":
			newEnemy = new MeleeEnemy(level, new Sprite(new Texture("assets/pictures/rock.png")), new Sprite(new Texture(Gdx.files.internal("assets/pictures/sword1.png"))), level.getPlayer());
			newEnemy.init();
			newEnemy.setxPosition(pos.x);
			newEnemy.setyPosition(pos.y);
			break;
		default:
			throw new IllegalArgumentException("No enemy of that name");
		}
		level.getEnemies().add(newEnemy);

	}
}
