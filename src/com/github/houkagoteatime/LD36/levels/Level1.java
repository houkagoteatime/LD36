package com.github.houkagoteatime.LD36.levels;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.github.houkagoteatime.LD36.entities.enemies.Enemy;

public class Level1 extends Level{

	private final Enemy e1 = new Enemy(this, 300, 10, new Sprite(new Texture("assets/pictures/rock.png")), 10, this.getPlayer());

	//private final static Enemy[] enemies = new Enemy[]{e1};
	
	
	public Level1() {
		super("assets/Copyofmap.tmx");
		this.getEnemies().add(e1);
	}

	@Override
	public void spawnEnemies() {
		// TODO Auto-generated method stub
		
	}

}
