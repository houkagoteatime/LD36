package com.github.houkagoteatime.LD36.levels;

import com.badlogic.gdx.math.Vector2;
import com.github.houkagoteatime.LD36.entities.enemies.EnemySpawner;
import com.github.houkagoteatime.LD36.screens.GameScreen;

public class Level1 extends Level{

	
	public Level1(GameScreen screen) {
		super("assets/tilesets/level1a.tmx", screen);
		EnemySpawner.init(this);
	}

	@Override
	public void spawnEnemies() {
		EnemySpawner.getInstance().spawnEnemy("archer", new Vector2(200, 200));
		EnemySpawner.getInstance().spawnEnemy("swordsman", new Vector2(300, 300));
	}

}
