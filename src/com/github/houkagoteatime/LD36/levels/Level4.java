package com.github.houkagoteatime.LD36.levels;

import com.badlogic.gdx.math.Vector2;
import com.github.houkagoteatime.LD36.entities.enemies.EnemySpawner;
import com.github.houkagoteatime.LD36.screens.GameScreen;

public class Level4 extends Level{

	
	public Level4(GameScreen screen) {
		super("assets/tilesets/level4a.tmx", screen);
		//this.setLevel(4);
		EnemySpawner.init(this);
		this.getPlayer().setxPosition(0);
		this.getPlayer().setyPosition(0);
	}

	@Override
	public void spawnEnemies() {
		
	}

	@Override
	public void nextLevel() {
		// TODO Auto-generated method stub
		
	}

}
