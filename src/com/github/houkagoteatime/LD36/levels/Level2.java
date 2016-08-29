package com.github.houkagoteatime.LD36.levels;

import com.badlogic.gdx.math.Vector2;
import com.github.houkagoteatime.LD36.entities.enemies.EnemySpawner;
import com.github.houkagoteatime.LD36.screens.GameScreen;

public class Level2 extends Level{

	
	public Level2(GameScreen screen) {
		super("assets/tilesets/level2a.tmx", screen);
		EnemySpawner.init(this);
		this.getPlayer().setxPosition(0);
		this.getPlayer().setyPosition(500);
	}

	@Override
	public void spawnEnemies() {
		
	}

}
