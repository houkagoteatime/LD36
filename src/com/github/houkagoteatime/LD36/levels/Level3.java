package com.github.houkagoteatime.LD36.levels;

import com.badlogic.gdx.math.Vector2;
import com.github.houkagoteatime.LD36.entities.enemies.EnemySpawner;
import com.github.houkagoteatime.LD36.screens.GameScreen;

public class Level3 extends Level{

	
	public Level3(GameScreen screen) {
		super("assets/tilesets/level3a.tmx", screen);
		EnemySpawner.init(this);
		this.getPlayer().setxPosition(0);
		this.getPlayer().setyPosition(0);
	}

	@Override
	public void spawnEnemies() {
		
	}

}
