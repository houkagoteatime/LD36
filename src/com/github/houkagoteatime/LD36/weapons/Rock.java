package com.github.houkagoteatime.LD36.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.github.houkagoteatime.LD36.entities.Entity;
import com.github.houkagoteatime.LD36.levels.Level;

public class Rock implements Weapon{
	private Entity owner;
	private Sprite rockProjectileSprite;
	private Level level;
	public static final float RANGE = 200;
	public Rock(Entity owner, Level level) {
		rockProjectileSprite = new Sprite(new Texture(Gdx.files.internal("assets/pictures/rock.png")));
		this.owner = owner;
		this.level = level;
	}

	@Override
	public void attack(float angle) {
		new RockProjectile(rockProjectileSprite, 10, owner.getxPosition(), owner.getyPosition(), angle, RANGE).spawn(level);
	}
	
	public class RockProjectile extends Projectile {
		public RockProjectile(Sprite sprite, int damage, float xPosition, float yPosition, float angle, float range) {
			super(sprite, damage, xPosition, yPosition, angle, range);
		}
		
	}

}
