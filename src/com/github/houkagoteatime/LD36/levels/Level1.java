package com.github.houkagoteatime.LD36.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.github.houkagoteatime.LD36.entities.enemies.SleeperEnemy;
import com.github.houkagoteatime.LD36.screens.GameScreen;

public class Level1 extends Level{

	private final SleeperEnemy e1 = new SleeperEnemy(this, new Sprite(new Texture("assets/pictures/rock.png")), new Sprite(new Texture(Gdx.files.internal("assets/pictures/sword1.png"))), this.getPlayer());

	//private final static Enemy[] enemies = new Enemy[]{e1};
	
	
	public Level1(GameScreen screen) {
		super("assets/Copyofmap.tmx", screen);
		e1.init();
	}

	@Override
	public void spawnEnemies() {
		e1.setxPosition(400);
		e1.setyPosition(400);
		this.getEnemies().add(e1);
	}

}
