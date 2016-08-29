package com.github.houkagoteatime.LD36.entities.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.github.houkagoteatime.LD36.levels.Level;

public class EnemySpawner {
	private Level level;
	private static EnemySpawner INSTANCE = null;
	private Sprite rock = new Sprite(new Texture("assets/pictures/rock.png"));
	private Sprite rockproj = new Sprite(new Texture(Gdx.files.internal("assets/pictures/arrow.png")));
	private Sprite sword = new Sprite(new Texture(Gdx.files.internal("assets/pictures/sword1.png")));
	private Sprite spear = new Sprite(new Texture(Gdx.files.internal("assets/pictures/spear.png")));
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
			newEnemy = new Archer(level, rock, rockproj, level.getPlayer());
			break;
		case "swordsman":
			newEnemy = new MeleeEnemy(level, rock, sword, level.getPlayer());

			break;
		case "lancer":
			newEnemy  = new Lancer(level, rock, spear, level.getPlayer());
			newEnemy.setCollidable(false);
			break;
		default:
			throw new IllegalArgumentException("No enemy of that name");
		}
		if(newEnemy != null) {
			newEnemy.init();
			newEnemy.setxPosition(pos.x);
			newEnemy.setyPosition(pos.y);
			level.getEnemies().add(newEnemy);

		}
	}
}
