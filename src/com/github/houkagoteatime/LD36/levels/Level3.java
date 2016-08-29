package com.github.houkagoteatime.LD36.levels;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.github.houkagoteatime.LD36.entities.enemies.EnemySpawner;
import com.github.houkagoteatime.LD36.screens.GameScreen;

public class Level3 extends Level{

	
	public Level3(GameScreen screen) {
		super("assets/tilesets/level3a.tmx", screen);
		this.setLevel(3);
		EnemySpawner.init(this);
		this.getPlayer().setxPosition(512);
		this.getPlayer().setyPosition(136);
	}
	public void update(float dt) {
		if(this.getPlayer().isGod) {
			this.getPlayerInputProcessor().queryInput();
			this.getPlayer().fly(dt);
			handleGameObjects();
			return;
		}
		updateProjectiles(dt);
		handleProjectileCollision(dt);
		handleGameObjects();
		handleContactDamage(dt);
		this.getPlayerInputProcessor().queryInput();
		this.getPlayer().fly(dt);
		updateEnemies(dt);
		if(this.getPlayer().isDead()) {
			//game.gameOver();
		}
		if(this.getEnemies().isEmpty()) {
			
				this.getPlayer().setSprite(new Sprite(new Texture("assets/pictures/gorillagod.png")));
				this.getPlayer().setSpeed((int)(this.getPlayer().getSpeed() * 3));
				this.getPlayer().isGod = true;
		}
		
	}

	@Override
	public void nextLevel() {
		// TODO Auto-generated method stub
		
	}
}
