package com.github.houkagoteatime.LD36.levels;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.github.houkagoteatime.LD36.entities.enemies.EnemySpawner;
import com.github.houkagoteatime.LD36.screens.GameScreen;

public class Level1 extends Level{

	
	public Level1(GameScreen screen) {
		super("assets/tilesets/level1a.tmx", screen);
		EnemySpawner.init(this);
		this.getPlayer().setxPosition(0);
		this.getPlayer().setyPosition(0);
	}

	/* (non-Javadoc)
	 * @see com.github.houkagoteatime.LD36.levels.Level#nextLevel()
	 */
	@Override
	public void nextLevel() {
		this.dispose();
	}

	/* (non-Javadoc)
	 * @see com.github.houkagoteatime.LD36.levels.Level#update(float)
	 */
	@Override
	public void update(float dt) {
		super.update(dt);
	}
}
