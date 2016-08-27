package com.github.houkagoteatime.LD36.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.github.houkagoteatime.LD36.entities.Entity;

public class Rock implements Weapon{
	private Entity owner;
	private Sprite rockProjectile;
	public Rock(Entity owner) {
		rockProjectile = new Sprite(new Texture(Gdx.files.internal("assets/pictures/rock.png")));
		this.owner = owner;
	}

	@Override
	public void attack(float angle) {
	}
	
	public class RockProjectile extends Projectile {
		public RockProjectile(Sprite sprite, int damage, float xPosition, float yPosition, float angle) {
			super(sprite, damage, xPosition, yPosition, angle);
		}
		
	}

}
